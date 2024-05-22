package ng.com.justjava.coached;

import io.camunda.zeebe.spring.client.EnableZeebeClient;
import io.camunda.zeebe.spring.client.annotation.Deployment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages={"ng.com.justjava","ch.brix.camunda.connector.mailThymeleaf"})
@EnableZeebeClient
//@Deployment(resources = "classpath:coached-bpmn.bpmn")
public class CoachedApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoachedApplication.class, args);
    }

}
