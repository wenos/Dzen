package edu.mirea.vitality.blog.repository;

import edu.mirea.vitality.blog.domain.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.StyledEditorKit;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsByTitle(String value);
}
