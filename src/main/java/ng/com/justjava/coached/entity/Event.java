package ng.com.justjava.coached.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Data
@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String description;

    @ElementCollection
    @Column(name = "event_time")
    @CollectionTable(name = "event_eventTime", joinColumns = @JoinColumn(name = "owner_id"))
    private List<String> eventTime = new ArrayList<>();

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate eventDate;

    public void addEventTime(String eventTime){
        this.eventTime.add(eventTime);
    }

}