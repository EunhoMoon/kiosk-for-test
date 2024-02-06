package study.janek.cafekiosk.spring.api.service.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import study.janek.cafekiosk.spring.client.mail.MailSendClient;
import study.janek.cafekiosk.spring.domain.history.mail.MailSendHistory;
import study.janek.cafekiosk.spring.domain.history.mail.MailSendHistoryRepository;
import study.janek.cafekiosk.spring.domain.order.Order;
import study.janek.cafekiosk.spring.domain.order.OrderRepository;
import study.janek.cafekiosk.spring.domain.order.OrderStatus;
import study.janek.cafekiosk.spring.domain.product.Product;
import study.janek.cafekiosk.spring.domain.product.ProductRepository;
import study.janek.cafekiosk.spring.domain.product.ProductType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static study.janek.cafekiosk.spring.domain.product.ProductSellingStatus.SELLING;
import static study.janek.cafekiosk.spring.domain.product.ProductType.HANDMADE;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
class OrderStatisticsServiceTest {

    @Autowired
    OrderStatisticsService orderStatisticsService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    MailSendHistoryRepository mailSendHistoryRepository;

    @MockBean
    MailSendClient mailSendClient;

    @Test
    @DisplayName("결제 완료 주문에 대한 매출 통계 메일을 전송한다.")
    void sendOrderStatisticsMail() {
        //given
        LocalDateTime now = LocalDateTime.of(2024, 2, 6, 9, 38);

        Product product1 = createProduct(HANDMADE, "001", 1000);
        Product product2 = createProduct(HANDMADE, "002", 2000);
        Product product3 = createProduct(HANDMADE, "003", 3000);
        List<Product> products = productRepository.saveAll(List.of(product1, product2, product3));

        Order order1 = createPaymentCompletedOrder(products, LocalDateTime.of(2024, 2, 5, 23, 59, 59));
        Order order2 = createPaymentCompletedOrder(products, now);
        Order order3 = createPaymentCompletedOrder(products, LocalDateTime.of(2024, 2, 6, 23, 59, 59));
        Order order4 = createPaymentCompletedOrder(products, LocalDateTime.of(2024, 2, 7, 0, 0, 0));

        // stubbing
        Mockito.when(mailSendClient.sendEmail(any(String.class), any(String.class), any(String.class), any(String.class)))
            .thenReturn(true);

        // when
        boolean result = orderStatisticsService.sendOrderStatisticsMail(LocalDate.of(2024, 2, 6), "test@co.kr");

        // then
        assertThat(result).isTrue();
        List<MailSendHistory> histories = mailSendHistoryRepository.findAll();
        assertThat(histories).hasSize(1)
            .extracting("content")
            .contains("총 매출 합계는 12000원 입니다.");
    }

    private Order createPaymentCompletedOrder(List<Product> products, LocalDateTime now) {
        Order order = Order.builder()
            .products(products)
            .orderStatus(OrderStatus.PAYMENT_COMPLETED)
            .registeredDateTime(now)
            .build();

        return orderRepository.save(order);
    }

    private Product createProduct(ProductType type, String productNumber, int price) {
        return Product.builder()
            .type(type)
            .productNumber(productNumber)
            .price(price)
            .sellingStatus(SELLING)
            .name("메뉴 이름")
            .build();
    }

}