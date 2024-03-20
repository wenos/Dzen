package edu.mirea.myinvest.repository;

import edu.mirea.myinvest.domain.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.StyledEditorKit;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsByTitle(String value);
}
