package Main;


import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Table(name = "Wallets")
public class Wallet {

    @Id
    @GeneratedValue
private long id;


    private String currency;
    private BigDecimal amount;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToOne
    @JoinColumn(name = "exchange")
private Exchange exchange;

    public Wallet(String currency, Double amount) {
        this.currency = currency;
        this.amount =  BigDecimal.valueOf(amount).setScale(2, RoundingMode.HALF_UP);
    }

    public Wallet() {}


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getAmount() {
        return amount.doubleValue();
    }

    public void setAmount(Double amount) {
        this.amount = BigDecimal.valueOf(amount).setScale(2, RoundingMode.HALF_UP);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Exchange getExchange() {
        return exchange;
    }

    public void setExchange(Exchange exchange) {
        this.exchange = exchange;
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "id=" + id +
                ", currency='" + currency + '\'' +
                ", amount=" + amount +
                '}';
    }
}
