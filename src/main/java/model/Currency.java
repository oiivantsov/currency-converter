package model;

// model, which contains the abbreviation, name, and conversion rate to a fixed currency (e.g., USD).
public class Currency {
    private CurrencyCode code;
    private double amount;

    public Currency(CurrencyCode code, double amount) {
        this.code = code;
        this.amount = amount;
    }

    public CurrencyCode getCode() {
        return code;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double convertTo(CurrencyCode targetCurrency) {
        Rate fromRate = Rate.valueOf(this.code.name());
        Rate toRate = Rate.valueOf(targetCurrency.name());
        double amountInUSD = this.amount / fromRate.getRateToUSD();
        return amountInUSD * toRate.getRateToUSD();
    }

    @Override
    public String toString() {
        return String.format("%.2f %s", amount, code);
    }

    public static void main(String[] args) {
        Currency myMoney = new Currency(CurrencyCode.USD, 100);
        System.out.println("Original: " + myMoney);

        double convertedAmount = myMoney.convertTo(CurrencyCode.EUR);
        System.out.println("Converted to EUR: " + String.format("%.2f EUR", convertedAmount));
    }
}
