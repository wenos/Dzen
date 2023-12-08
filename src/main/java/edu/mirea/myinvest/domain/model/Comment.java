package edu.mirea.myinvest.domain.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "jn_comment", indexes = {
        @Index(name = "idx_comment_post_id", columnList = "post_id")
})
public class Comment {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_id_seq")
    @SequenceGenerator(name = "comment_id_seq", sequenceName = "comment_id_seq", allocationSize = 1)
    private Long id;

    /**
     * Пост, к которому относится комментарий
     */
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "post_id")
    private Post post;

    /**
     * Автор комментария
     */
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "author_id")
    private User author;

    /**
     * Содержание комментария
     */
    @Column(name = "content", length = 1000)
    private String content;

    /**
     * Является ли комментарий анонимным
     */
    @Column(name = "is_anonymous", columnDefinition = "boolean default false")
    private Boolean isAnonymous = false;

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
     * Проверяет, является ли пользователь автором комментария
     *
     * @param user пользователь
     * @return true, если пользователь является автором комментария
     */
    public boolean isAuthor(User user) {
        if (user == null) {
            return false;
        }
        return user.getId().equals(author.getId());
    }
}
