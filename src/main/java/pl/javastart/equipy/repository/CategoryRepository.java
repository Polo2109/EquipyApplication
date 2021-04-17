package pl.javastart.equipy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.javastart.equipy.model.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    public Optional<Category> findByName(String name);
}
