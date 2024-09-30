package controller;

import dao.CurrencyDao;
import view.ConverterUI;

public class ConverterController {
    ConverterUI converterUI;
    CurrencyDao currencyDao = new CurrencyDao();

    public ConverterController(ConverterUI converterUI) {
        this.converterUI = converterUI;
    }

    public void convert() {
        String codeFrom = converterUI.getFrom();
        String codeTo = converterUI.getTo();
        double amount = converterUI.getAmount();

        if (codeFrom == null || codeTo == null) {
            converterUI.showError("Please select both currencies.");
            return;
        }

        double rateFrom = currencyDao.getRateByCode(codeFrom);
        double rateTo = currencyDao.getRateByCode(codeTo);

        if (rateFrom == 0 || rateTo == 0) {
            converterUI.showError("Error retrieving currency rates.");
            return;
        }

        double amountInUSD = amount / rateFrom;
        double result = amountInUSD * rateTo;

        converterUI.showResult(result);
    }
}
