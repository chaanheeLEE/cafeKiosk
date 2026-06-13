package sample.cafekiosk.spring.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductTypeTest {

    @DisplayName("상품 타입이 재고 관련 타입인지 확인한다.")
    @Test
    void containsStockType1() {
        //given
        ProductType type = ProductType.HANDMADE;
        //when
        boolean result = ProductType.containsStockType(type);
        //then
        assertThat(result).isFalse();;
    }
    @DisplayName("상품 타입이 재고 관련 타입인지 확인한다.")
    @Test
    void containsStockType2() {
        //given
        ProductType type = ProductType.BAKERY;
        //when
        boolean result = ProductType.containsStockType(type);
        //then
        assertThat(result).isTrue();;
    }
}