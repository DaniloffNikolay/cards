package org.example.cards.programs.cashbacks;

import org.example.cards.BankCard;
import org.example.cards.BonusProgram;

import java.time.LocalDate;

public abstract class Cashback implements BonusProgram {
    private final double rate;
    private final double needToSpendForCashback;
    private double purchaseSum;
    private double cashbackSum;

    public Cashback(double rate, double needToSpendForCashback) {
        this.rate = rate;
        this.needToSpendForCashback = needToSpendForCashback;
        purchaseSum = 0;
        cashbackSum = 0;
    }

    @Override
    public void afterPay(double sum) {
        if (!isPurchaseSumActual())
            purchaseSum = 0;
        purchaseSum += sum;

        if (needToSpendForCashback <= purchaseSum) {
            addCashbackSum(sum);
        }
    }

    @Override
    public void afterTopUp(double sum) {}

    private double getCashbackSumFromPurchaseSum(double purchaseSum) {
        return (purchaseSum / 100) * rate;
    }

    private void addCashbackSum(double sum) {
        this.cashbackSum += getCashbackSumFromPurchaseSum(sum);
    }

    public void transferCashbackToCard(BankCard bankCard) {
        bankCard.topUp(cashbackSum);
        cashbackSum = 0;
    }

    protected abstract boolean isPurchaseSumActual();

    protected abstract LocalDate WhenWillCurrentCashbackPeriodEnd();

    @Override
    public String getDescription() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Процент кэшбека: " + rate + "\n");
        stringBuilder.append("Сумма необходимых покупок для начисления кэшбека: " + needToSpendForCashback + "\n");
        stringBuilder.append("Сумма совершенных покупок в ныншнем периоде: " + purchaseSum + "\n");
        stringBuilder.append("Сумма кэшбека в ныншнем периоде: " + cashbackSum + "\n");
        stringBuilder.append("Ныншний период закончится: " + WhenWillCurrentCashbackPeriodEnd().toString() + "\n");

        return stringBuilder.toString();
    }
}
