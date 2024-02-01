import my.application.springbootapp.SpringBootAppApplication;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
@ContextConfiguration
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SpringBootAppApplication.class)
class DemoApplicationTests {
    @Autowired
    TestRestTemplate restTemplate;

    @Container
    private final GenericContainer<?> devApp = new GenericContainer<>("devapp")
            .withExposedPorts(8080);
    @Container
    private final GenericContainer<?> prodApp = new GenericContainer<>("prodapp")
            .withExposedPorts(8081);


    @Test
    void contextLoads() {
        ResponseEntity<String> forEntityDevApp = restTemplate.getForEntity("http://localhost:" + devApp.getMappedPort(8080), String.class);
        ResponseEntity<String> forEntityProdApp = restTemplate.getForEntity("http://localhost:" + prodApp.getMappedPort(8081), String.class);

        System.out.println("devApp " + forEntityDevApp.getBody());
        System.out.println("prodApp " + forEntityProdApp.getBody());
    }

}
