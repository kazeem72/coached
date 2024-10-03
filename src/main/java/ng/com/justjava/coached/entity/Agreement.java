package ng.com.justjava.coached.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Agreement {
    private String description;
    private Boolean signed=false;
    private String link;
}