package ng.com.justjava.coached.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class SessionCoachees {
    @ManyToOne
    @JoinColumn(name = "coachee_id")
    private Coachee coachee;

    @OneToOne(orphanRemoval = true,cascade = CascadeType.ALL)
    @JoinColumn(name = "assessment_id")
    private Assessment assessment;

    private String assesmentLink;
    private Boolean assesmentSigned = false;


}