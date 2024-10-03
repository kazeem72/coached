package ng.com.justjava.coached.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SystemParametersRepository extends JpaRepository<SystemParameters, Long> {
    Optional<SystemParameters> findByModule(String module);
}