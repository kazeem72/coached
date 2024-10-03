package ng.com.justjava.coached.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long> {
    List<Session> findByCoach_EmailAndSessionStatus(String email, SessionStatus sessionStatus);

    List<Session> findByCoach_Email(String email);

    List<Session> findBySessionOwnerOrderByActualDateAsc(String sessionOwner);

    List<Session> findBySessionOwnerAndSessionStatusOrderByActualDateAsc(String sessionOwner, SessionStatus sessionStatus);

    List<Session> findBySessionStatus(SessionStatus sessionStatus);



}