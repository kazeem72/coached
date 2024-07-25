package ng.com.justjava.coached.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long> {
    List<Session> findByManyToOneAndSessionOwnerOrderByActualDateAsc(Boolean manyToOne, String sessionOwner);

}