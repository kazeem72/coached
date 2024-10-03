package ng.com.justjava.coached.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractAuditable;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "invoice")
public class Invoice  extends AbstractAuditable <Invoice, Long> {
    private String invoiceID;

    private Double amountDue;
    private Double taxPercent;
    private LocalDate issueDate;
    private LocalDate dueDate = LocalDate.now();

    private String issueTo;
    private Boolean paid = false;




    public String getDisplayNumber(){

        return "Invoice_"+getId();
    }
    public String getStatus(){
        return getExpired()?"Expired":(getPaid()?"Paid":"Pending");
    }


    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;


    @OneToOne(mappedBy = "invoice")
    @JoinColumn(name = "session_id")
    private Session session;

    public Boolean getExpired(){
        if(dueDate==null)
            dueDate=LocalDate.now();

        return (dueDate.isBefore(LocalDate.now()) && !paid);
    }

}