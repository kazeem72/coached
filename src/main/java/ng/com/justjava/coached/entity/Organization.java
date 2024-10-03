package ng.com.justjava.coached.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "organization")
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;
    private String phoneNumber;
    private String email;
    @OneToOne(cascade =CascadeType.ALL ,orphanRemoval = true)
    @JoinColumn(name = "org_admin_id")
    private Coachee orgAdmin;

    @OneToMany(mappedBy = "organization",
            cascade = CascadeType.ALL)
    private List<Coachee> coachees = new ArrayList<>();


    public Integer getTotalEmployee(){
        return coachees.size();
    }
    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Invoice> invoices = new ArrayList<>();

    public Organization addCoachee(Coachee coachee){
        coachee.setOrganization(this);
        coachees.add(coachee);
        return this;
    }
    public Organization addInvoice(Invoice invoice){
        invoice.setOrganization(this);
        invoices.add(invoice);
        return this;
    }

}