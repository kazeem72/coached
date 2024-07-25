package ng.com.justjava.coached.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Embeddable
public class Certificate {
    private String name;
    private String awardBy;
    private LocalDate dateIssued;

}