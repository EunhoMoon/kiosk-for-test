package study.janek.cafekiosk.spring.api.controller.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import study.janek.cafekiosk.spring.api.controller.product.dto.ProductCreateRequest;
import study.janek.cafekiosk.spring.api.service.product.ProductService;
import study.janek.cafekiosk.spring.api.service.product.response.ProductResponse;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static study.janek.cafekiosk.spring.domain.product.ProductSellingStatus.SELLING;
import static study.janek.cafekiosk.spring.domain.product.ProductType.HANDMADE;

@WebMvcTest(controllers = ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @Test
    @DisplayName("신규 상품을 등록한다.")
    void createProduct() throws Exception {
        //given
        ProductCreateRequest request = ProductCreateRequest.builder()
            .type(HANDMADE)
            .sellingStatus(SELLING)
            .name("아메리카노")
            .price(4000)
            .build();

        // expect
        mockMvc.perform(
                post("/api/v1/products/new")
                    .content(objectMapper.writeValueAsBytes(request))
                    .contentType(APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("신규 상품 등록시 상품 타입은 필수값이다.")
    void createProductWithoutType() throws Exception {
        //given
        ProductCreateRequest request = ProductCreateRequest.builder()
            .sellingStatus(SELLING)
            .name("아메리카노")
            .price(4000)
            .build();

        // expect
        mockMvc.perform(
                post("/api/v1/products/new")
                    .content(objectMapper.writeValueAsBytes(request))
                    .contentType(APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code" ).value("400"))
            .andExpect(jsonPath("$.status" ).value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message" ).value("상품 타입은 필수입니다."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    @DisplayName("신규 상품 등록시 상품 판매상태는 필수값이다.")
    void createProductWithoutStatus() throws Exception {
        //given
        ProductCreateRequest request = ProductCreateRequest.builder()
            .type(HANDMADE)
            .name("아메리카노")
            .price(4000)
            .build();

        // expect
        mockMvc.perform(
                post("/api/v1/products/new")
                    .content(objectMapper.writeValueAsBytes(request))
                    .contentType(APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code" ).value("400"))
            .andExpect(jsonPath("$.status" ).value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message" ).value("상품 판매상태는 필수입니다."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    @DisplayName("신규 상품 등록시 상품 이름 필수값이다.")
    void createProductWithoutName() throws Exception {
        //given
        ProductCreateRequest request = ProductCreateRequest.builder()
            .sellingStatus(SELLING)
            .type(HANDMADE)
            .price(4000)
            .build();

        // expect
        mockMvc.perform(
                post("/api/v1/products/new")
                    .content(objectMapper.writeValueAsBytes(request))
                    .contentType(APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code" ).value("400"))
            .andExpect(jsonPath("$.status" ).value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message" ).value("상품 이름은 필수입니다."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    @DisplayName("신규 상품 등록시 상품 가격은 양수이다.")
    void createProductWithZeroPrice() throws Exception {
        //given
        ProductCreateRequest request = ProductCreateRequest.builder()
            .sellingStatus(SELLING)
            .type(HANDMADE)
            .name("아메리카노")
            .price(0)
            .build();

        // expect
        mockMvc.perform(
                post("/api/v1/products/new")
                    .content(objectMapper.writeValueAsBytes(request))
                    .contentType(APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code" ).value("400"))
            .andExpect(jsonPath("$.status" ).value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message" ).value("상품 가격은 양수여야 합니다."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    @DisplayName("판매 상품을 조회한다.")
    void getSellingProducts() throws Exception {
        //given
        List<ProductResponse> result = List.of();
        when(productService.getSellingProduct()).thenReturn(result);

        // expect
        mockMvc.perform(
                get("/api/v1/products/selling")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code" ).value("200"))
            .andExpect(jsonPath("$.status" ).value("OK"))
            .andExpect(jsonPath("$.message" ).value("OK"))
            .andExpect(jsonPath("$.data").isArray());
    }

}