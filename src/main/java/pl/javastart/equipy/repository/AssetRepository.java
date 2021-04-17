package pl.javastart.equipy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.javastart.equipy.model.Asset;

import java.util.List;
import java.util.Optional;

public interface AssetRepository extends JpaRepository<Asset, Long> {

    @Query("SELECT a FROM Asset a WHERE lower(a.name) like lower(concat('%', :search, '%') ) " +
            "or lower(a.serialNumber) like lower(concat('%', :search, '%') ) ")
    List<Asset> findByNameOrSerialNumber(@Param("search") String search);

    Optional<Asset> findBySerialNumber(String serialNumber);
}
