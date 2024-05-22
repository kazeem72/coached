package ng.com.justjava.coached.valueObject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FullName {
    private String firstName;
    private String secondName;
    private String lastName;
}
