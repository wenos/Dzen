package edu.mirea.myinvest.domain.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "jn_user", indexes = {
        @Index(name = "idx_user_username", columnList = "username"),
        @Index(name = "idx_user_email", columnList = "email")
})
public class User implements UserDetails {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
    @SequenceGenerator(name = "user_id_seq", sequenceName = "user_id_seq", allocationSize = 1)
    private Long id;

    /**
     * Имя пользователя
     */
    @Column(name = "username", unique = true, nullable = false)
    private String username;



    /**
     * Пароль
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * Адрес электронной почты
     */
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    /**
     * Роль пользователя
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    /**
     * Время конца блокировки
     */
    @Column(name = "banned_at")
    private OffsetDateTime bannedAt;

    /**
     * Настоящее имя
     */
    @Column(name = "real_name")
    private String realName;

    /**
     * Информация о пользователе
     */
    @Column(name = "about", length = 500)
    private String about;

    /**
     * Пол пользователя
     */
    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private UserGender gender;

    /**
     * Фотография пользователя (Ссылка на файл)
     */
    @OneToOne
    @JoinColumn(name = "avatar_id")
    private UploadedFile avatar;



    /**
     * Посты пользователя
     */
    @OneToMany(mappedBy = "author", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @EqualsAndHashCode.Exclude
    private List<Post> posts;



    /**
     * Комментарии пользователя
     */
    @OneToMany(mappedBy = "author", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @EqualsAndHashCode.Exclude
    private List<Comment> comments;


    @OneToMany(mappedBy = "user")
    private Set<PostUserRel> postUserRel;

    /**
     * Время создания
     */
    @CreationTimestamp
    @Column(name = "created_at")
    private OffsetDateTime createdAt;


    /**
     * Время последнего обновления
     */
    @UpdateTimestamp
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    /**
     * Время удаления
     */
    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return bannedAt == null || bannedAt.isBefore(OffsetDateTime.now());
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        var now = OffsetDateTime.now();
        return deletedAt == null || deletedAt.isAfter(now);
    }

    /**
     * Проверка, если ли у пользователя права модератора
     *
     * @return true, если пользователь модератор или администратор
     */
    public boolean isModerator() {
        return role == Role.ROLE_MODERATOR || isAdmin();
    }


    /**
     * Проверка, является ли пользователь администратором
     *
     * @return true, если пользователь администратор
     */
    public boolean isAdmin() {
        return role == Role.ROLE_ADMIN;
    }
}
