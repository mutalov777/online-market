package uz.mutalov.onlinemarket.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.mutalov.onlinemarket.entity.Product;
import uz.mutalov.onlinemarket.repository.base.BaseRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, BaseRepository {
    Optional<List<Product>> findAllByCategoryName(String category, Pageable pageable);
    Optional<List<Product>> findAllByName(String name, Pageable pageable);
}
