package uz.mutalov.onlinemarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.mutalov.onlinemarket.entity.Cart;
import uz.mutalov.onlinemarket.repository.base.BaseRepository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer>, BaseRepository {

}
