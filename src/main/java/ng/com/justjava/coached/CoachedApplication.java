package ng.com.justjava.coached;


import feign.form.spring.SpringFormEncoder;
import io.camunda.zeebe.spring.client.EnableZeebeClient;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication//(scanBasePackages={"ng.com.justjava","ch.brix.camunda.connector.mailThymeleaf"})
@EnableZeebeClient
@EnableFeignClients
@EnableDiscoveryClient
//@Deployment(resources = "classpath:coached-bpmn.bpmn")
public class CoachedApplication {

    public static void main(String[] args) {

        SpringApplication.run(CoachedApplication.class, args);
    }
}
