package ng.com.justjava.coached.entity;

import jakarta.annotation.Resource;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ng.com.justjava.coached.valueObject.FullName;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.type.SqlTypes;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Coach {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String firstName;
    private String secondName;
    private String lastName;

    private Integer experience;

    private String biography;

    @ElementCollection
    @CollectionTable(name = "Coach_certificate", joinColumns = @JoinColumn(name = "owner_id"))
    private List<Certificate> certificate = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "Coach_award", joinColumns = @JoinColumn(name = "owner_id"))
    private List<Award> award = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "Coach_reviews", joinColumns = @JoinColumn(name = "owner_id"))
    private List<Reviews> reviews = new ArrayList<>();

    @Transient
    public String getFullName(){
        return firstName+" "+ secondName +", "+lastName;
    }
    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "diary_id")
    private Diary diary;

    /*@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "coach_id")
    private Set<SessionRequest> sessionRequests = new LinkedHashSet<>();*/

    @ManyToMany
    @RestResource(exported = false)
    @Cascade({CascadeType.PERSIST,CascadeType.DELETE_ORPHAN})
    @JoinTable(name = "Coach_areaOfInterests",
            joinColumns = @JoinColumn(name = "coach_id"),
            inverseJoinColumns = @JoinColumn(name = "areaOfInterests_id"))
    private Set<AreaOfInterest> areaOfInterests = new LinkedHashSet<>();


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
}
