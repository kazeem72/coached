package ng.com.justjava.coached.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="system")
public class SystemParameters {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name="system_parameters", joinColumns=@JoinColumn(name="system_id"))
    @MapKeyColumn(name="key")
    @Column(name="value")
    private Map<String,String> parameters=new HashMap<>();

    @ElementCollection
    @Column(name = "focus_area")
    @CollectionTable(name = "system_focusAreas", joinColumns = @JoinColumn(name = "owner_id"))
    private List<String> focusAreas = new ArrayList<>();

    private String module;

    @Override
    public String toString() {
        return "SystemParameters{" +
                "id=" + id +
                ", parameters=" + parameters +
                ", focusAreas=" + focusAreas +
                ", module='" + module + '\'' +
                '}';
    }
}
