package study.janek.cafekiosk.spring;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import study.janek.cafekiosk.spring.client.mail.MailSendClient;

@SpringBootTest
@ActiveProfiles("test")
public abstract class IntegrationTestSupport {
    @MockBean
    protected MailSendClient mailSendClient;
}
