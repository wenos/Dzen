package edu.mirea.myinvest.domain.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;


/**
 * Сущность загруженного файла
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "jn_uploaded_file")
public class UploadedFile {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    /**
     * Оригинальное имя файла
     */
    @Column(name = "original_name")
    private String originalName;

    /**
     * Расширение файла
     */
    @Column(name = "extension")
    private String extension;

    /**
     * Имя на диске (UUID)
     */
    @Column(name = "path")
    private String path;

    /**
     * Размер файла
     */
    @Column(name = "size")
    private Long size;

    /**
     * Связанные файлы
     */
    @OneToMany(mappedBy = "file", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<PostFileRel> posts;

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
}
