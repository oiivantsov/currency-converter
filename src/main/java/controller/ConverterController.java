package controller;

import model.Currency;
import model.CurrencyCode;
import view.ConverterUI;

public class ConverterController {
    ConverterUI converterUI;
    Currency currency;

    public ConverterController(ConverterUI converterUI) {
        this.converterUI = converterUI;
    }

    public void convert() {
        CurrencyCode from = converterUI.getFrom();
        CurrencyCode to = converterUI.getTo();
        double amount = converterUI.getAmount();
        currency = new Currency(from, amount);
        double result = currency.convertTo(to);
        converterUI.showResult(result);
    }

}
