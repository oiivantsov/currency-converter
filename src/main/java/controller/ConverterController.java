package controller;

import dao.CurrencyDao;
import entity.Currency;
import view.ConverterUI;

import java.util.List;

public class ConverterController {
    ConverterUI converterUI;
    CurrencyDao currencyDao = new CurrencyDao();

    public ConverterController(ConverterUI converterUI) {
        this.converterUI = converterUI;
    }

    public void initializeUI() {
        // Populate combo boxes in the view with currency codes
        List<String> codes = currencyDao.findAllCodes();
        converterUI.updateCurrencyComboBoxes(codes);
    }

    public void convert() {
        String codeFrom = converterUI.getFrom();
        String codeTo = converterUI.getTo();
        double amount = converterUI.getAmount();

        if (codeFrom == null || codeTo == null) {
            converterUI.showError("Please select both currencies.");
            return;
        }

        Currency currencyFrom = currencyDao.find(codeFrom);
        Currency currencyTo = currencyDao.find(codeTo);

        if (currencyFrom == null || currencyTo == null) {
            converterUI.showError("Error retrieving currency rates.");
            return;
        }

        double rateFrom = currencyFrom.getRate();
        double rateTo = currencyTo.getRate();

        if (rateFrom == 0 || rateTo == 0) {
            converterUI.showError("Error retrieving currency rates.");
            return;
        }

        double amountInUSD = amount / rateFrom;
        double result = amountInUSD * rateTo;

        converterUI.showResult(result);
    }

    public void updateCurrency(String code, String name, double rate) {
        Currency currency = new Currency(code, name, rate);
        currencyDao.update(currency);
        initializeUI(); // Update combo boxes with new currency data
    }

    public void deleteCurrency(String code) {
        Currency currency = currencyDao.find(code);
        if (currency != null) {
            currencyDao.delete(currency);
            initializeUI(); // Update combo boxes with new currency data
        } else {
            converterUI.showError("Currency with code " + code + " not found.");
        }
    }

    public Currency getCurrency(String code) {
        return currencyDao.find(code);
    }
}
