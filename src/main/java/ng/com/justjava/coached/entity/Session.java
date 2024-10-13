package ng.com.justjava.coached.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private Integer frequency;
    private Boolean manyToOne;
    private String sessionOwner;
    private LocalDate scheduledDate;
    private LocalDate actualDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "session_status")
    private SessionStatus sessionStatus;

    private String caseNote;
    private LocalDate proposedStartDate;
    @ManyToOne
    @JoinColumn(name = "coach_id")
    private Coach coach;

    @Embedded
    private Agreement coachAgreement;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "description", column = @Column(name = "org_agreement_description")),
            @AttributeOverride(name = "signed", column = @Column(name = "org_agreement_signed")),
            @AttributeOverride(name = "link", column = @Column(name = "org_agreement_link"))
    })
    private Agreement orgAgreement;

    @OneToOne(cascade = CascadeType.ALL ,orphanRemoval = true)
    private Invoice invoice;

    @ElementCollection
    @CollectionTable(name = "Session_sessionCoachees",
            joinColumns = @JoinColumn(name = "owner_id"))
    private List<SessionCoachees> sessionCoachees = new ArrayList<>();

    @ElementCollection
    @Column(name = "schedule")
    @CollectionTable(name = "Session_schedules", joinColumns = @JoinColumn(name = "owner_id"))
    private List<Date> schedules = new ArrayList<>();

    public Session addSchedule(Date schedule){
        schedules.add(schedule);
        return this;
    }
    public Session addSessionCoachee(SessionCoachees coachee){
        sessionCoachees.add(coachee);
        return  this;
    }
    public Session addAllCoachees(List<Coachee> coachees){
        coachees.addAll(coachees);
        return  this;
    }

    public Session addInvoice(Invoice invoice){
        this.invoice=invoice;
        return this;
    }

    public Integer getTotalCoachees(){
        return sessionCoachees.size();
    }

    public Long getTotalCoacheeAlreadySigned(){
        return sessionCoachees.stream().filter(sessionCoachee->
                sessionCoachee.getAssesmentSigned()).count();
    }

    public boolean getCanSchedule(){
        if(getCoachAgreement().getSigned()&&getOrgAgreement().getSigned()
        && getInvoice().getPaid()
                &&(getTotalCoachees().intValue()==getTotalCoacheeAlreadySigned().intValue()))
            return true;
        return  false;
    }

    public SessionCoachees getCoachee(String email){
        for (SessionCoachees sessionCoachee: sessionCoachees    ) {
            if(sessionCoachee.getCoachee().getEmail().equalsIgnoreCase(email))
                return sessionCoachee;
        }
        return null;
    }
    public Coachee getSessionBooker(){
        return sessionCoachees.stream().filter(sessionCoachee ->
                sessionCoachee.getCoachee().getAdmin()||
                !sessionCoachee.getCoachee().getEmployee())
                .findFirst().get().getCoachee();
    }
}
