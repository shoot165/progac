package academy.prog.hw;


import javax.persistence.*;
import java.lang.annotation.Retention;
import java.util.Date;
import java.util.Objects;


@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;


    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false)
    String date;

    public Order() {
    }

    public Order(Client client, Product product, String date) {
        this.client = client;
        this.product = product;
        this.date = date;
    }


    public Client getClient() {
        return client;
    }

    public Product getProduct() {
        return product;
    }

    public String getDate() {
        return date;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setDate(String date) {
        this.date = date;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id && Objects.equals(client, order.client) && Objects.equals(product, order.product) && Objects.equals(date, order.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, client, product, date);
    }

    @Override
    public String toString() {
        return "Order{" +
                "client=" + client +
                ", product=" + product +
                ", date=" + date +
                '}';
    }
}
