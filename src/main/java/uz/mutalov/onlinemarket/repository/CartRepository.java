package uz.mutalov.onlinemarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.mutalov.onlinemarket.entity.Cart;
import uz.mutalov.onlinemarket.repository.base.BaseRepository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer>, BaseRepository {

    @Query(value = "from Cart c where c.id=:id and c.checked=true")
    Optional<Cart> findByIdAndOrderedTrue(Integer id);

}
