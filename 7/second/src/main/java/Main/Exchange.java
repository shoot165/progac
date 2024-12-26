package Main;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Exchange {

@Id
@GeneratedValue
private long id;
Double usd;
Double eur;
Double uah;


@OneToMany(mappedBy = "exchange")
private List<Wallet> walletList = new ArrayList<Wallet>();


    public Exchange() {
    }

    public Exchange(Double usd, Double eur, Double uan) {
        this.usd = usd;
        this.eur = eur;
        this.uah = this.uah;
    }

    public void addWallet(Wallet wallet) {
        walletList.add(wallet);
        wallet.setExchange(this);
    }

    public Double getUah() {
        return uah;
    }

    public void setUah(Double uan) {
        this.uah = uan;
    }

    public Double getEur() {
        return eur;
    }

    public void setEur(Double eur) {
        this.eur = eur;
    }

    public Double getUsd() {
        return usd;
    }

    public void setUsd(Double usd) {
        this.usd = usd;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Exchange{" +
                "usd=" + usd +
                ", eur=" + eur +
                ", uan=" + uah +
                '}';
    }
}
