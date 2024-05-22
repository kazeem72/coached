package ng.com.justjava.coached.camunda_client;

//import ch.brix.camunda.connector.mailThymeleaf.Function;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.stereotype.Component;

@Component
public class CamundaServiceClient {

    @JobWorker(type = "email1")
    public void handleJobFoo(final ActivatedJob job) {

        
        System.out.println(" The Activated Job==="+job.getBpmnProcessId());
        // do whatever you need to do
    }
}
