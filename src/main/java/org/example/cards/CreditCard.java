package org.example.cards;

import java.util.List;

public class CreditCard extends BankCard {

    private double creditLimit;
    private double creditBalance;

    public CreditCard(double creditLimit) {
        this.creditLimit = creditLimit;
        creditBalance = creditLimit;
    }

    public CreditCard(double creditLimit, List<BonusProgram> bonusPrograms) {
        super(bonusPrograms);
        this.creditLimit = creditLimit;
        creditBalance = creditLimit;
    }

    @Override
    protected double doTopUpOperations(double sum) {
        creditBalance += sum;

        double difference = 0;
        if (creditBalance > creditLimit) {
            difference = creditBalance - creditLimit;
            creditBalance = creditLimit;
        }

        return difference;
    }

    @Override
    protected double doPayOperations(double sum) {
        double balance = getBalance();
        if (sum <= balance)
            return sum;
        else {
            double tempSum = sum - balance;
            if (tempSum > creditBalance)
                return sum;
            else {
                creditBalance -= tempSum;
                return balance;
            }
        }
    }

    public double getCreditLimit() {
        return creditLimit;
    }

    public double getCreditBalance() {
        return creditBalance;
    }

    @Override
    protected String getCardDescription() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Кредитная карта с лимитом " + creditLimit + "\n");
        stringBuilder.append(getCreditBalanceInformation());

        return stringBuilder.toString();
    }

    public String getCreditBalanceInformation() {
        return "Кредитные средства: " + creditBalance;
    }
}
