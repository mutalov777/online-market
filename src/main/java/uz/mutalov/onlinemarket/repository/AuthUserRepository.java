package uz.mutalov.onlinemarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import uz.mutalov.onlinemarket.dto.admin.ChartData;
import uz.mutalov.onlinemarket.dto.admin.UserData;
import uz.mutalov.onlinemarket.dto.auth.UserDTO;
import uz.mutalov.onlinemarket.entity.AuthUser;
import uz.mutalov.onlinemarket.repository.base.BaseRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Long>, BaseRepository {

    Optional<AuthUser> findByEmail(String email);

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Query(value = "select a.id from AuthUser a where a.email=:email ")
    Optional<Long> getUserId(String email);

    @Query(value = "select new uz.mutalov.onlinemarket.dto.auth.UserDTO(a.id,a.fullName,a.phone) from AuthUser a where a.id=:id")
    Optional<UserDTO> findFullNameAndPhoneById(Long id);

    @Query(value = "from AuthUser a where a.email=:email and a.role='USER'")
    Optional<AuthUser> findAllByRoleUser(String email);


    @Query(value = "select  new uz.mutalov.onlinemarket.dto.admin.ChartData(o.createdAt,count(o)) from UserOrder o where o.createdAt>=current_date-7 group by o.createdAt")
    Optional<List<ChartData>> findWeeklyCartData();

    @Query(value ="select new uz.mutalov.onlinemarket.dto.admin.UserData(a.createdAt,count(a)) from AuthUser a where a.createdAt>=current_date-7 group by a.createdAt")
    Optional<List<UserData>> findWeeklyUserCount();

    @Query(value = """
            select count(o) from user_order o where o.created_at>=current_date and o.created_at<current_date+1
            union all
            select count(o) from user_order o where o.created_at>=current_date and o.created_at<current_date+1 and o.delivered=true
            union all
            select count(o) from user_order o where o.created_at>=current_date and o.created_at<current_date+1 and o.delivered=false;
            """, nativeQuery = true)
    Optional<List<Long>> findDailyOrders();


}