package study.janek.cafekiosk.spring.api.service.mail;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import study.janek.cafekiosk.spring.client.mail.MailSendClient;
import study.janek.cafekiosk.spring.domain.history.mail.MailSendHistory;
import study.janek.cafekiosk.spring.domain.history.mail.MailSendHistoryRepository;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MailServiceTest {

    @Mock
//    @Spy
    MailSendClient mailSendClient;

    @Mock
    MailSendHistoryRepository mailSendHistoryRepository;

    @InjectMocks
    MailService mailService;

    @Test
    @DisplayName("메일 전송 테스트")
    void sendMail() {
        //given
//        MailSendClient mailSendClient = mock(MailSendClient.class);
//        MailSendHistoryRepository mailSendHistoryRepository = mock(MailSendHistoryRepository.class);
//        MailService mailService = new MailService(mailSendClient, mailSendHistoryRepository);

//        when(mailSendClient.sendEmail(anyString(), anyString(), anyString(), anyString()))
//            .thenReturn(true);
        // spy 사용시 원하는 기능(sendMail)만 stubbing하고, 나머지 기능은 그대로 사용
//        doReturn(true)
//            .when(mailSendClient)
//            .sendEmail(anyString(), anyString(), anyString(), anyString());
        BDDMockito.given(mailSendClient.sendEmail(anyString(), anyString(), anyString(), anyString()))
            .willReturn(true);

        // when
        boolean result = mailService.sendMail("", "", "", "");

        // then
        Assertions.assertThat(result).isTrue();
        verify(mailSendHistoryRepository, times(1)).save(any(MailSendHistory.class));
    }

}