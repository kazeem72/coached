package ng.com.justjava.coached.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Embeddable
public class Reviews {
    private String review;
    private String reviewer;
    private LocalDate dateReviewed;
    private String reviewerStatus;

    @ManyToOne
    @JoinColumn(name = "coachee_id")
    private Coachee coachee;

}