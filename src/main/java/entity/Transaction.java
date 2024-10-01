package entity;

import jakarta.persistence.*;

@Entity
public class Transaction {
    @Id
    @GeneratedValue
    private int id;
    @ManyToOne
    @JoinColumn(name="source_currency")
    private Currency sourceCurrency;
    @ManyToOne
    @JoinColumn(name="destination_currency")
    private Currency destinationCurrency;
    @Column(name="source_amount")
    private double sourceAmount;
    @Column(name="destination_amount")
    private double destinationAmount;

    public Transaction(Currency sourceCurrency, Currency destinationCurrency, double sourceAmount, double destinationAmount) {
        super();
        this.sourceCurrency = sourceCurrency;
        this.destinationCurrency = destinationCurrency;
        this.sourceAmount = sourceAmount;
        this.destinationAmount = destinationAmount;
    }

    public Transaction() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Currency getSourceCurrency() {
        return sourceCurrency;
    }

    public void setSourceCurrency(Currency sourceCurrency) {
        this.sourceCurrency = sourceCurrency;
    }

    public double getSourceAmount() {
        return sourceAmount;
    }

    public void setSourceAmount(double sourceAmount) {
        this.sourceAmount = sourceAmount;
    }

    public Currency getDestinationCurrency() {
        return destinationCurrency;
    }

    public void setDestinationCurrency(Currency destinationCurrency) {
        this.destinationCurrency = destinationCurrency;
    }

    public double getDestinationAmount() {
        return destinationAmount;
    }

    public void setDestinationAmount(double destinationAmount) {
        this.destinationAmount = destinationAmount;
    }
}

