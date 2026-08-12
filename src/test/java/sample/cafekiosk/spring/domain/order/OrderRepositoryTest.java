package sample.cafekiosk.spring.domain.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import sample.cafekiosk.spring.IntegrationTestSupport;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductSellingType;
import sample.cafekiosk.spring.domain.product.ProductType;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static sample.cafekiosk.spring.domain.product.ProductSellingType.*;
import static sample.cafekiosk.spring.domain.product.ProductType.BAKERY;
import static sample.cafekiosk.spring.domain.product.ProductType.HANDMADE;

@Transactional
class OrderRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("해당일자에 주문 상태에 맞는 주문을 가져온다.")
    @Test
    public void findOrdersBy() {
        //given
        Product product1 = createProduct("001", HANDMADE, SELLING, "아메리카노", 4000);
        Product product2 = createProduct("002", HANDMADE, HOLD, "카페라떼", 5000);
        Product product3 = createProduct("003", BAKERY, STOP_SELLING, "케이크", 6000);

        List<Product> products = List.of(product1, product2, product3);
        productRepository.saveAll(products);

        LocalDateTime registeredDateTime = LocalDateTime.now();

        Order order1 = Order.create(products, registeredDateTime);
        Order order2 = Order.create(products, registeredDateTime.plusDays(1));
        Order order3 = Order.create(products, registeredDateTime.plusDays(2));

        orderRepository.saveAll(List.of(order1, order2, order3));

        //when
        List<Order> Orders = orderRepository.findOrdersBy(
                registeredDateTime, registeredDateTime.plusDays(1), OrderStatus.INIT);

        //then
        assertThat(Orders).hasSize(1);
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