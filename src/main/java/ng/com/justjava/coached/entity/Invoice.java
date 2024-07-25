package ng.com.justjava.coached.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractAuditable;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "invoice")
public class Invoice  extends AbstractAuditable <Invoice, Long> {
    private String invoiceID;

    private String issueTo;
    @ManyToOne
    @JoinColumn(name = "session_id")
    private Session session;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;



}