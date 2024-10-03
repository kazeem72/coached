package ng.com.justjava.coached.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface CoacheeRepository extends JpaRepository<Coachee, Long> {
    List<Coachee> findByOrganization_Name(String name);
    Optional<Coachee> findByEmail(String email);
    List<Coachee> findByEmailIn(Collection<String> emails);

    List<Coachee> findByFullNameContainsIgnoreCaseOrEmailContainsIgnoreCaseOrderByFullNameAsc(String fullName, String email);





}