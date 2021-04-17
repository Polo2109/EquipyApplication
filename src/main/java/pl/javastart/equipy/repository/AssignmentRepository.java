package pl.javastart.equipy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.javastart.equipy.model.Assignment;

import java.util.Optional;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    Optional<Assignment> findByAsset_IdAndEndIsNull(Long assetId);
}
