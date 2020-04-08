package entity;

import javax.persistence.*;

@Entity
@Table(name = "Wallet")
public class Wallet {
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    private Currency currency;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @Column
    private int value;

    public Wallet() {
    }

    public Wallet(Currency currency, User user, int value) {
        this.currency = currency;
        this.user = user;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "id=" + id +
                ", currency=" + currency +
                ", user=" + user +
                ", value=" + value +
                '}';
    }
}
