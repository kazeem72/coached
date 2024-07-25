package ng.com.justjava.coached.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoacheeRepository extends JpaRepository<Coachee, Long> {
    Optional<Coachee> findByEmail(String email);

}