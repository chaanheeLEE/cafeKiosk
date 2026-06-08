package sample.cafekiosk.spring.domain.product;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.cafekiosk.spring.domain.BaseEntity;

@Entity
@Getter
@NoArgsConstructor
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productNumber;

    @Enumerated(EnumType.STRING)
    private ProductType type;

    @Enumerated(EnumType.STRING)
    private ProductSellingType sellingType;

    private String name;
    private int price;


    @Builder
    public Product(String name, int price, String productNumber, ProductSellingType sellingType, ProductType type) {
        this.name = name;
        this.price = price;
        this.productNumber = productNumber;
        this.sellingType = sellingType;
        this.type = type;
    }
}
