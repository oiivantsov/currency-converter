package model;

public enum Rate {
    USD(1.0),
    GBP(0.74),
    EUR(0.92);

    private final double rateToUSD;

    Rate(double rateToUSD) {
        this.rateToUSD = rateToUSD;
    }

    public double getRateToUSD() {
        return rateToUSD;
    }

}
