package edu.mirea.vitality.blog.domain.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "jn_post")
public class Post {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_id_seq")
    @SequenceGenerator(name = "post_id_seq", sequenceName = "post_id_seq", allocationSize = 1)
    private Long id;

    /**
     * Заголовок поста
     */
    @Column(name = "title")
    private String title;


    /**
     * Содержимое поста
     */
    @Column(name = "content", length = 10_000)
    private String content;


    @Column(name = "likes")
    private Long likes;

    /**
     * Автор поста
     */
    @ManyToOne
    @JoinColumn(name = "author_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User author;

    /**
     * Связанные файлы
     */
    @OneToMany(mappedBy = "post", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<PostFileRel> attachments;

    /**
     * Комментарии для записи
     */
    @OneToMany(mappedBy = "post", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Comment> comments;

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


    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "category_id")
    Category category;

    @OneToMany(mappedBy = "post")
    private Set<PostUserRel> postUserRel;




    /**
     * Добавить файлы к посту
     *
     * @param files Список файлов
     */
    public void addFiles(List<UploadedFile> files) {
        if (attachments == null) {
            attachments = new ArrayList<>();
        }
        attachments.addAll(
                files.stream()
                        .map(file -> PostFileRel.builder().post(this).file(file).build())
                        .toList());
    }

    /**
     * Проверка, является ли пользователь автором поста
     *
     * @param user Пользователь
     * @return true, если пользователь является автором поста
     */
    public boolean isAuthor(User user) {
        if (user == null) {
            return false;
        }
        return user.getId().equals(author.getId());
    }
}
