package backendprojekti.directory;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import backendprojekti.directory.controller.ContactController;

@SpringBootTest
public class ContactControllerSmokeTest {

    @Autowired
    ContactController controller;

    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }
}
