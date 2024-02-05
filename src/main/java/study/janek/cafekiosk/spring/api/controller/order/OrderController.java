package study.janek.cafekiosk.spring.api.controller.order;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import study.janek.cafekiosk.spring.api.ApiResponse;
import study.janek.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import study.janek.cafekiosk.spring.api.service.order.OrderService;
import study.janek.cafekiosk.spring.api.service.order.response.OrderResponse;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/api/v1/orders/new")
    public ApiResponse<OrderResponse> createOrder(@Valid @RequestBody OrderCreateRequest request) {
        return ApiResponse.ok(orderService.createOrder(request.toServiceRequest(), LocalDateTime.now()));
    }

}
