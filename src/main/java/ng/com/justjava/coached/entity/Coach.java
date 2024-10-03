package ng.com.justjava.coached.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.type.SqlTypes;
import org.springframework.data.rest.core.annotation.RestResource;

import java.time.LocalDateTime;
import java.util.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Coach extends Completable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;


    @Column(unique = true)
    private String email;
    private String firstName;
    private String secondName;
    private String lastName;

    private String myPicture;

    private String myVideo;
    private Integer experience;

    private String biography;

    private Boolean verify;

    @ElementCollection
    @Column(name = "my_award")
    @CollectionTable(name = "Coach_myAwards", joinColumns = @JoinColumn(name = "owner_id"))
    private List<String> myAwards = new ArrayList<>();

    @ElementCollection
    @Column(name = "my_certification")
    @CollectionTable(name = "Coach_myCertifications", joinColumns = @JoinColumn(name = "owner_id"))
    private Set<String> myCertifications = new LinkedHashSet<>();

    @ElementCollection
    @CollectionTable(name = "Coach_award", joinColumns = @JoinColumn(name = "owner_id"))
    private List<Award> award = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "Coach_reviews", joinColumns = @JoinColumn(name = "owner_id"))
    private List<Reviews> reviews = new ArrayList<>();

    @Transient
    public String getFullName(){
        return firstName+" "+ (secondName==null?"":secondName +", ") +lastName;
    }
    @OneToOne(orphanRemoval = true)
    @Cascade({CascadeType.ALL})
    @JoinColumn(name = "booked_id")
    private Diary booked;

    @OneToOne(orphanRemoval = true)
    @Cascade({CascadeType.ALL})
    @JoinColumn(name = "available_id")
    private Diary available;
    /*@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "coach_id")
    private Set<SessionRequest> sessionRequests = new LinkedHashSet<>();*/

    @ManyToMany
    @RestResource(exported = false)
    @Cascade({CascadeType.PERSIST,CascadeType.DELETE_ORPHAN})
    @JoinTable(name = "Coach_areaOfInterests",
            joinColumns = @JoinColumn(name = "coach_id"),
            inverseJoinColumns = @JoinColumn(name = "areaOfInterests_id"))
    private List<AreaOfInterest> areaOfInterests = new ArrayList<>();

    @ElementCollection
    @Column(name = "focus_area")
    @CollectionTable(name = "Coach_focusArea", joinColumns = @JoinColumn(name = "owner_id"))
    private List<String> focusArea = new ArrayList<>();



    public Coach addAward(String award){
        this.myAwards.add(award);
        return this;
    }
    public Coach addCertificate(String certificate){
        this.myCertifications.add(certificate);
        return this;
    }
    public String getMyDisplayPicture(){
        return myPicture!=null?myPicture:"standing man.svg";
    }

    public String getMyDisplayVideo(){

        return myVideo!=null?myVideo:"vid1.mp4";
    }
    public Boolean getDiaryFilled(){
        if(available!=null && !available.getEvents().isEmpty())
            return true;

        return false;

    }
    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Coach coach = (Coach) o;
        return getId() != null && Objects.equals(getId(), coach.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    @PostLoad
    public void postLoad() {
        setLastActive(LocalDateTime.now());
    }
}
