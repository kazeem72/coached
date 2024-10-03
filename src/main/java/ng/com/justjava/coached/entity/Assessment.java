package ng.com.justjava.coached.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Entity
@Table(name = "assessment")
public class Assessment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String theCurrentRole;
    private String guidingValues;
    private String currentRoleEnjoyed;
    private String mostChallenging;
    private String workImpact;
    private String yourWhy;
    private String greatestAchievement;
    private String lifeToBe;
    private String topThree;
    private String professionalGoal;
    private Integer communicationCurrentScore;
    private Integer communicationPriorityOrder;
    private Integer stakeholderEngagementPriorityOrder;
    private Integer stakeholderEngagementCurrentyScore;
    private Integer influencingPriorityOrder;
    private String toBeSupported;

}