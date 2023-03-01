package uz.mutalov.onlinemarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.mutalov.onlinemarket.entity.Message;
import uz.mutalov.onlinemarket.repository.base.BaseRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>, BaseRepository {
    @Query(value = "from Message m where m.text like :text")
    Optional<List<Message>> findAllByStartWith(String text);

    @Query(value = "from Message m where (m.to.email=:email and m.from.id=:id) or (m.to.id=:id and m.from.email=:email)")
    List<Message> findAllByFromId(Long id, String email);

    @Query(value = """
            (
            select m.from_id
             from auth_user a
                      left join  message m on a.id = m.to_id
             where  a.email =:email and m.from_id is not null
             group by m.from_id
             )
            union
            (
                select m.to_id
                from auth_user a
                         left join  message m on a.id = m.from_id
                where  a.email =:email and m.to_id is not null
                group by m.to_id
            )
            """, nativeQuery = true)
    List<Long> findAllByMessageTableExist(String email);

    @Query(value = "from Message m where (m.from.id=:id and m.to.email=:email) or (m.from.email=:email and m.to.id=:id)")
    List<Message> findAllMessageByFromIdAndToEmail(Long id, String email);
}
