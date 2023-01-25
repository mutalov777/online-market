package uz.mutalov.onlinemarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import uz.mutalov.onlinemarket.entity.AuthUser;
import uz.mutalov.onlinemarket.entity.Cart;
import uz.mutalov.onlinemarket.repository.base.BaseRepository;

import java.util.Optional;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Long>, BaseRepository {

    Optional<AuthUser> findByEmail(String email);

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Query(value = "select a.id from AuthUser a where a.email=:email ")
    Optional<Long> getUserId(String email);

    @Query(value = "select c.* from cart c join auth_user_carts auc on c.id = auc.cart_id" +
            " join auth_user au on auc.auth_user_id = au.id where c.id=:id and au.email=:email", nativeQuery = true)
    Optional<Cart> findUserCartByEmailAndCartId(String email, Integer id);
}
