package ng.com.justjava.coached.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CoachRepository extends JpaRepository<Coach, Long> {
    Optional<Coach> findByEmail(String email);

    List<Coach> findByVerifyTrue();

    List<Coach> findByVerifyFalse();





}