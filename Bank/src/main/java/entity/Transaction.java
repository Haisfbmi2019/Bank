package entity;

import javax.persistence.*;

@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Wallet walletFrom;
    @ManyToOne
    private Wallet walletTo;
    @Column
    private int amount;

    public Transaction() {
    }

    public Transaction(Wallet walletFrom, Wallet walletTo, int amount) {
        this.walletFrom = walletFrom;
        this.walletTo = walletTo;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Wallet getWalletFrom() {
        return walletFrom;
    }

    public void setWalletFrom(Wallet walletFrom) {
        this.walletFrom = walletFrom;
    }

    public Wallet getWalletTo() {
        return walletTo;
    }

    public void setWalletTo(Wallet walletTo) {
        this.walletTo = walletTo;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", walletFrom=" + walletFrom +
                ", walletTo=" + walletTo +
                ", amount=" + amount +
                '}';
    }
}
