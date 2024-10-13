package ng.com.justjava.coached.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {

    Long coachId;
    String meetingType;
    Integer frequency;

    String caseNote;
    LocalDate proposedStartDate;

    Integer preview=0;

    LocalDate expiryDate;
    List<String> employeeList = new ArrayList<String>();

    public String getDisplay(){
        return meetingType;//("Group".equalsIgnoreCase(meetingType)?"One To One":"One to Many");
    }

    @Override
    public String toString() {
        return "BookingDTO{" +
                "coachId=" + coachId +
                ", meetingType='" + meetingType + '\'' +
                ", frequency=" + frequency +
                ", caseNote='" + caseNote + '\'' +
                ", proposedStartDate=" + proposedStartDate +
                ", preview=" + preview +
                ", expiryDate=" + expiryDate +
                ", employeeList=" + employeeList +
                '}';
    }
}
