package ng.com.justjava.coached.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.sql.Blob;

@Getter
@Setter
@Entity
@Table(name = "materials")
public class Materials {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String description;
    private String shortDescription;
    private String focusArea;
    private String link;
    private String mediaType;

    @Override
    public String toString() {
        return "Materials{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", focusArea='" + focusArea + '\'' +
                ", link='" + link + '\'' +
                ", mediaType='" + mediaType + '\'' +
                '}';
    }
}