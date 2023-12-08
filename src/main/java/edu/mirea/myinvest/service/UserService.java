package edu.mirea.myinvest.service;

import edu.mirea.myinvest.config.security.SuperUserConfig;
import edu.mirea.myinvest.domain.dto.user.UpdateUserEmailRequest;
import edu.mirea.myinvest.domain.dto.user.UpdateUserInfoRequest;
import edu.mirea.myinvest.domain.dto.user.UpdateUserPasswordRequest;
import edu.mirea.myinvest.domain.dto.user.UpdateUserRoleRequest;
import edu.mirea.myinvest.domain.dto.user.UpdateUserUsernameRequest;
import edu.mirea.myinvest.domain.dto.user.UserBanRequest;
import edu.mirea.myinvest.domain.dto.user.UserFilter;
import edu.mirea.myinvest.domain.model.Role;
import edu.mirea.myinvest.domain.model.User;
import edu.mirea.myinvest.domain.model.UserGender;
import edu.mirea.myinvest.exception.user.ForbiddenAccessProblem;
import edu.mirea.myinvest.exception.user.InvalidUserPasswordProblem;
import edu.mirea.myinvest.exception.user.UserDeletedProblem;
import edu.mirea.myinvest.exception.user.UserNotFoundProblem;
import edu.mirea.myinvest.exception.user.UserNotUniqueEmailProblem;
import edu.mirea.myinvest.exception.user.UserNotUniqueUsernameProblem;
import edu.mirea.myinvest.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final SuperUserConfig superUserConfig;


    /**
     * Создание суперпользователя, если его нет
     */
    @PostConstruct
    public void initSuperUser() {
        if (!superUserConfig.isSuperuserEnabled()) {
            return;
        }
        var superuserId = superUserConfig.getSuperuserId();
        var user = repository.findById(superuserId);

        if (user.isEmpty()) {
            User u = new User();
            u.setId(superuserId);
            u.setUsername("superuser");
            u.setEmail(RandomStringUtils.randomAlphanumeric(10) + "@tinkoff.ru");
            u.setPassword(passwordEncoder.encode(superUserConfig.getSuperuserDefaultPassword()));
            u.setRole(Role.ROLE_ADMIN);
            repository.save(u);
            log.info("Создан суперпользователь с именем пользователя superuser и паролем superuser");
        } else {
            if (!user.get().isAdmin()) {
                user.get().setRole(Role.ROLE_ADMIN);
                repository.save(user.get());
                log.info("Пользователь с ID {} теперь является суперпользователем", superuserId);
            }
        }
    }

    /**
     * Сохранение пользователя
     *
     * @return сохраненный пользователь
     */
    public User save(User user) {
        return repository.save(user);
    }


    /**
     * Создание пользователя
     *
     * @return созданный пользователь
     */
    public User create(User user) {
        if (repository.existsByUsername(user.getUsername())) {
            throw new UserNotUniqueUsernameProblem(user.getUsername());
        }

        if (repository.existsByEmail(user.getEmail())) {
            throw new UserNotUniqueEmailProblem(user.getEmail());
        }

        return save(user);
    }

    /**
     * Получение текущего пользователя
     *
     * @return текущий пользователь
     */
    public User getCurrentUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }


    /**
     * Найти пользователя по ID
     *
     * @param userId ID пользователя для поиска
     * @return Optional, содержащий пользователя, если найден
     */
    public Optional<User> findById(Long userId) {
        return repository.findById(userId);
    }

    /**
     * Получить пользователя по ID
     *
     * @param userId ID пользователя для поиска
     * @return Найденный пользователь или выбросить исключение, если пользователь не найден
     */
    public User getById(Long userId) {
        return findById(userId).orElseThrow(UserNotFoundProblem::new);
    }

    /**
     * Найти пользователя по имени пользователя
     *
     * @param username Имя пользователя для поиска
     * @return Optional, содержащий пользователя, если найден
     */
    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    /**
     * Получить пользователя по имени пользователя
     *
     * @param username Имя пользователя для поиска
     * @return Найденный пользователь или выбросить исключение, если пользователь не найден
     */
    public User getByUsername(String username) {
        var user = findByUsername(username)
                .orElseThrow(UserNotFoundProblem::new);
        if (user.isEnabled()) {
            return user;
        } else {
            throw new UserDeletedProblem();
        }
    }


    /**
     * Установить дату блокировки пользователю
     *
     * @param request Запрос на блокировку пользователя
     */
    public void setBannedAt(UserBanRequest request) {
        User currentUser = getCurrentUser();
        User user = getById(request.id());

        if (!hasAccessToUser(currentUser, user)) {
            throw new ForbiddenAccessProblem();
        }

        user.setBannedAt(OffsetDateTime.now()
                .plusDays(Optional.ofNullable(request.days()).orElse(0))
                .plusHours(Optional.ofNullable(request.hours()).orElse(0))
                .plusMinutes(Optional.ofNullable(request.minutes()).orElse(0)));

        save(user);
    }

    /**
     * Сбросить дату блокировки пользователя
     *
     * @param userId Идентификатор пользователя для сброса блокировки
     */
    public void resetBannedAt(Long userId) {
        User currentUser = getCurrentUser();
        User user = getById(userId);

        if (!hasAccessToUser(currentUser, user)) {
            throw new ForbiddenAccessProblem();
        }

        user.setBannedAt(null);
        save(user);
    }

    /**
     * Изменить пароль пользователя
     *
     * @param request Запрос на изменение пароля
     */
    public void changePassword(UpdateUserPasswordRequest request) {
        User currentUser = getCurrentUser();

        if (!passwordEncoder.matches(request.oldPassword(), currentUser.getPassword())) {
            throw new InvalidUserPasswordProblem();
        }

        currentUser.setPassword(passwordEncoder.encode(request.newPassword()));
        save(currentUser);
    }

    /**
     * Обновить имя пользователя
     *
     * @param request Запрос на обновление имени пользователя
     */
    public void selfUpdateUsername(UpdateUserUsernameRequest request) {
        User current = getCurrentUser();
        String username = request.username();

        if (repository.existsByUsername(username)
                && !current.getUsername().equals(username)) {
            throw new UserNotUniqueUsernameProblem(username);
        }

        current.setUsername(username);
        save(current);
    }


    /**
     * Обновить почту пользователя
     *
     * @param request Запрос на обновление почты пользователя
     */
    public void selfUpdateEmail(UpdateUserEmailRequest request) {
        User user = getCurrentUser();
        String email = request.email();

        if (repository.existsByEmail(email)
                && !user.getEmail().equals(email)) {
            throw new UserNotUniqueEmailProblem(email);
        }

        user.setEmail(email);
        save(user);
    }


    /**
     * Получение по фильтру
     *
     * @param filter фильтр
     * @return Найденные пользователи
     */
    public Page<User> findByFilter(UserFilter filter) {
        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize());

        return repository.findAllWithFilter(
                filter.getId(),
                filter.getUsername(),
                filter.getEmail(),
                Optional.ofNullable(filter.getRole()).map(Role::valueOf).orElse(null),
                filter.getIsBanned(),
                OffsetDateTime.now(),
                pageable
        );
    }

    /**
     * Изменить роль пользователя
     *
     * @param request Запрос на изменение роли
     */
    public void changeRole(UpdateUserRoleRequest request) {
        User currentUser = getCurrentUser();
        User user = getById(request.id());

        if (!hasAccessToUser(currentUser, user)) {
            throw new ForbiddenAccessProblem();
        }

        user.setRole(Role.valueOf(request.role()));
        save(user);
    }

    /**
     * Проверка имеет ли пользователем права над другим пользователем
     *
     * @param currentUser текущий пользователь
     * @param user        пользователь, права на которого проверяются
     * @return true, если текущий пользователь имеет права на пользователя
     */
    public boolean hasAccessToUser(User currentUser, User user) {
        /*
            Права доступа:
            Суперпользователь - Админ - да
            Суперпользователь - Модератор - да
            Суперпользователь - Пользователь - да

            Админ - Админ - нет
            Админ - Модератор - да
            Админ - Пользователь - да

            Модератор - Админ - нет
            Модератор - Модератор - нет
            Модератор - Пользователь - да

            Управлять собой - нет
         */
        if (currentUser.getId().equals(user.getId())) {
            return false;
        }

        if (superUserConfig.isSuperuser(currentUser.getId())) {
            return true;
        }

        // Проверка никогда не должна сработать, но предосторожность не помешает
        if (superUserConfig.isSuperuser(user.getId())) {
            return false;
        }

        if (currentUser.isAdmin()) {
            return !user.getRole().equals(Role.ROLE_ADMIN);
        }

        return currentUser.getRole().equals(Role.ROLE_MODERATOR);
    }

    /**
     * Обновление информации о пользователе
     *
     * @param request Запрос на обновление информации о пользователе
     */
    public void selfUpdateInfo(UpdateUserInfoRequest request) {
        User user = getCurrentUser();

        user.setRealName(request.realName());
        if (request.gender() != null) {
            user.setGender(UserGender.valueOf(request.gender()));
        }
        user.setAbout(request.about());

        save(user);
    }

    /**
     * Является ли текущий пользователь суперпользователем
     *
     * @return true, если текущий пользователь суперпользователь
     */
    public boolean isSuperuser() {
        var user = getCurrentUser();
        return superUserConfig.isSuperuser(user.getId());
    }
}
