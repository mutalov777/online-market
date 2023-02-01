package uz.mutalov.onlinemarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.mutalov.onlinemarket.entity.ProductCategory;
import uz.mutalov.onlinemarket.repository.base.BaseRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<ProductCategory, Integer>, BaseRepository {
    @Query(value = "from ProductCategory c left join fetch c.products where c.name=:category")
    Optional<ProductCategory> findByName(String category);

    @Query(value = "select c.name from ProductCategory c where UPPER(c.name) like :name")
    List<String> findNameStartWith(String name);
}
