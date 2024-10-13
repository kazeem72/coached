package ng.com.justjava.coached.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "document")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String header;
    private String subject;
    private String documentContent;

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", header='" + header + '\'' +
                ", subject='" + subject + '\'' +
                ", documentContent='" + documentContent + '\'' +
                '}';
    }
}