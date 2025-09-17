package shop.domain.items;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import shop.domain.Category;
import shop.exception.NotEnoughStockException;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter
@Setter
public class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity) {
        int realStock = this.stockQuantity - quantity;
        if (realStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = realStock;
    }
}
