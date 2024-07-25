package ng.com.justjava.coached.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {
    String meetingType;
    Integer frequency;
    List<String> employeeList = new ArrayList<String>();
}
