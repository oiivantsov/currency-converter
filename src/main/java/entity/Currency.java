package entity;

import jakarta.persistence.*;

@Entity
@Table(name="Currency")
public class Currency {
    @Id
    private String code;
    private String name;
    @Column(name="exchange_rate")
    private double rate;

    public Currency(String code, String name, double rate) {
        super();
        this.code = code;
        this.name = name;
        this.rate = rate;
    }

    public Currency() {

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

}
