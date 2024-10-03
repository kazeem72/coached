package ng.com.justjava.coached.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ng.com.justjava.coached.valueObject.FullName;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Coachee extends Completable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id", nullable=false)
    private Long id;

    @Column(unique = true)
    private String email;

    private String fullName;
    private Boolean admin=false;
    private Boolean employee=false;
    private String myPicture;
    private String status;

    @ElementCollection
    @Column(name = "focus_area")
    @CollectionTable(name = "Coachee_focusArea", joinColumns = @JoinColumn(name = "owner_id"))
    private List<String> focusArea = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "organization_id")
    private Organization organization;

/*
    public Boolean getEmployee(){

        return getOrganization()!=null&&!getAdmin();
    }
*/

    public String getMyOrganization(){
        return organization!=null
                &&organization.getName()!=null?"Brand Manager@ "+organization.getName():"";
    }
    public String getMyDisplayPicture(){
        return myPicture!=null?myPicture:"standing man.svg";
    }
}
