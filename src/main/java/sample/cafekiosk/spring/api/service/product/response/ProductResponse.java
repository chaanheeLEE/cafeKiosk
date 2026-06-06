package sample.cafekiosk.spring.api.service.product.response;

import lombok.Builder;
import lombok.Getter;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductSellingType;
import sample.cafekiosk.spring.domain.product.ProductType;

@Getter
public class ProductResponse {

    private Long id;
    private String productNumber;
    private ProductType type;
    private ProductSellingType sellingType;
    private String name;
    private int price;

    @Builder
    private ProductResponse(Long id, String name, int price, String productNumber, ProductSellingType sellingType, ProductType type) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.productNumber = productNumber;
        this.sellingType = sellingType;
        this.type = type;
    }

    public static ProductResponse of(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .productNumber(product.getProductNumber())
                .sellingType(product.getSellingType())
                .type(product.getType())
                .build();
    }
}
