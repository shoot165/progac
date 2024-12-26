package Main;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Users")
public class User {


    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String username;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Transaction> transactions  = new ArrayList<Transaction>();


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private  List<Wallet> wallets  = new ArrayList<Wallet>();

    public User() {
    }

    public User(String username) {
        this.username = username;
    }

 public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        transaction.setUser(this);
 }
 public void addWallet(Wallet wallet) {
        wallets.add(wallet);
        wallet.setUser(this);
 }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<Wallet> getWallets() {
        return wallets;
    }

    public void setWallets(List<Wallet> wallets) {
        this.wallets = wallets;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}
