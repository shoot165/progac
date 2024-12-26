package Main;


import javax.persistence.*;

@Entity
@Table(name = "Transactions")
public class Transaction {

    @Id
    @GeneratedValue
    private long id;

    private String type;
    private double amount;
    private String currency;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Transaction(String type, double amount, String currency) {
        this.type = type;
        this.amount = amount;
        this.currency = currency;
    }

    public Transaction() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", user=" + user +
                '}';
    }
}
