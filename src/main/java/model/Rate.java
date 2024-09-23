package model;

public enum Rate {
    USD(1.0),
    GBP(0.75),
    EUR(0.90);

    private final double rateToUSD;

    Rate(double rateToUSD) {
        this.rateToUSD = rateToUSD;
    }

    public double getRateToUSD() {
        return rateToUSD;
    }

}
