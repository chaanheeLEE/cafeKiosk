package sample.cafekiosk.spring.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static sample.cafekiosk.spring.domain.product.ProductSellingType.*;
import static sample.cafekiosk.spring.domain.product.ProductType.*;

@ActiveProfiles("test")
//@SpringBootTest
@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("원하는 판매상태를 가지는 상품들을 조회한다.")
    @Test
    public void findAllBySellingTypeIn() {
        //given
        Product product1 = createProduct("001", HANDMADE, SELLING, "아메리카노", 4000);
        Product product2 = createProduct("002", HANDMADE, HOLD, "카페라떼", 5000);
        Product product3 = createProduct("003", BAKERY, STOP_SELLING, "케이크", 6000);

        productRepository.saveAll(List.of(product1, product2, product3));

        //when
        List<Product> products = productRepository.findAllBySellingTypeIn(List.of(SELLING, HOLD));

        //then
        assertThat(products).hasSize(2)
                .extracting("productNumber", "name", "SellingType")
                .containsExactlyInAnyOrder(
                        tuple("001", "아메리카노", SELLING),
                        tuple("002", "카페라떼", HOLD)
                );
    }

    @DisplayName("상품번호리스트로 상품들을 조회한다.")
    @Test
    public void findAllByProductNumberIn() {
        //given
        Product product1 = createProduct("001", HANDMADE, SELLING, "아메리카노", 4000);
        Product product2 = createProduct("002", HANDMADE, HOLD, "카페라떼", 5000);
        Product product3 = createProduct("003", BAKERY, STOP_SELLING, "케이크", 6000);

        productRepository.saveAll(List.of(product1, product2, product3));

        //when
        List<Product> products = productRepository.findAllByProductNumberIn(List.of("001", "002"));

        //then
        assertThat(products).hasSize(2)
                .extracting("productNumber", "name", "SellingType")
                .containsExactlyInAnyOrder(
                        tuple("001", "아메리카노", SELLING),
                        tuple("002", "카페라떼", HOLD)
                );

    }

    @DisplayName("가장 마지막으로 저장된 상품번호 가져오기")
    @Test
    public void findLatestProductNumber() {
        //given
        String targetProductNumber = "003";

        Product product1 = createProduct("001", HANDMADE, SELLING, "아메리카노", 4000);
        Product product2 = createProduct("002", HANDMADE, HOLD, "카페라떼", 5000);
        Product product3 = createProduct(targetProductNumber, BAKERY, STOP_SELLING, "케이크", 6000);

        productRepository.saveAll(List.of(product1, product2, product3));

        //when
        String latestProductNumber = productRepository.findLatestProductNumber();

        //then
        assertThat(latestProductNumber).isEqualTo("003");
    }

    @DisplayName("상품이 없을때, 가장 마지막으로 저장된 상품번호 가져오기")
    @Test
    public void findLatestProductNumberWhenProductsIsEmpty() {
        //when
        String latestProductNumber = productRepository.findLatestProductNumber();

        //then
        assertThat(latestProductNumber).isNull();
    }

    private static Product createProduct(String productNumber, ProductType type,
                                         ProductSellingType sellingType, String name, int price) {
        return Product.builder()
                .productNumber(productNumber)
                .type(type)
                .sellingType(sellingType)
                .name(name)
                .price(price)
                .build();
    }
}
