package ng.com.justjava.coached.services;

import ng.com.justjava.coached.entity.Coach;
import ng.com.justjava.coached.entity.CoachRepository;
import ng.com.justjava.coached.entity.Diary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CoachService {

    @Autowired
    CoachRepository coachRepository;
    public Coach createCoach(Map coachData, Diary diary){
        System.out.println(" The userAttributes ============ "+coachData);

        Coach coach = Coach.builder()
                .email((String)coachData.get("email"))
                .firstName((String)coachData.get("given_name"))
                .lastName((String)coachData.get("family_name"))
                //.focusArea((List<String>)coachData.get("interestArea"))
                .available(diary)
                .build();

        System.out.println("1 The new coach here = "+coach);
        coach = coachRepository.save(coach);
        System.out.println("2 The new coach here = "+coach);
        return  coach;
    }
}
