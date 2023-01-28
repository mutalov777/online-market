package uz.mutalov.onlinemarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.mutalov.onlinemarket.entity.Message;
import uz.mutalov.onlinemarket.repository.base.BaseRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository  extends JpaRepository<Message,Long>, BaseRepository {
    @Query(value = "from Message m where m.text like :text")
    Optional<List<Message>> findAllByStartWith(String text);
}
