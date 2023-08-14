package org.example.cards.programs.cashbacks;

import java.time.LocalDate;

public class CashbackForDay extends Cashback {

    private LocalDate date;

    public CashbackForDay(double rate, double needToSpendForCashback) {
        super(rate, needToSpendForCashback);
        date = LocalDate.now();
    }

    @Override
    protected boolean isPurchaseSumActual() {
        if (!date.equals(LocalDate.now())) {
            date = LocalDate.now();
            return false;
        }
        return true;
    }

    @Override
    protected LocalDate WhenWillCurrentCashbackPeriodEnd() {
        return LocalDate.now().minusDays(1);
    }
}
