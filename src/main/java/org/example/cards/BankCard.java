package org.example.cards;

import org.example.exceptions.IncorrectData;

import java.util.List;

public abstract class BankCard {
    private double balance;
    private List<BonusProgram> bonusPrograms;

    public BankCard() {
        balance = 0;
    }

    public BankCard(List<BonusProgram> bonusPrograms) {
        balance = 0;
        this.bonusPrograms = bonusPrograms;
    }

    public final void topUp(double sum) {
        checkSum(sum);
        balance += doTopUpOperations(sum);
        afterTopUp(sum);
    }

    public final boolean pay(double sum) {
        checkSum(sum);

        sum = doPayOperations(sum);
        if (sum > balance)
            return false;
        balance -= sum;
        afterPay(sum);
        return true;
    }

    public double getBalance() {
        return balance;
    }

    public String getBalanceInformation() {
        return "Собственные средства: " + balance;
    }

    public String getInformationAboutAvailableFunds() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("=======Информация о доступных средствах=============\n");
        stringBuilder.append(getBalanceInformation() + "\n");
        if (getCardDescription() != null)
            stringBuilder.append(getCardDescription() + "\n");

        if (bonusPrograms != null) {
            stringBuilder.append("Информация по бонусным программам: " + "\n");
            for (BonusProgram bonusProgram : bonusPrograms) {
                stringBuilder.append(bonusProgram.getDescription());
            }
        }

        stringBuilder.append("====================================================");

        return stringBuilder.toString();
    }

    abstract protected String getCardDescription();

    abstract protected double doTopUpOperations(double sum);

    abstract protected double doPayOperations(double sum);

    public void checkSum(double sum){
        if (sum < 0)
            throw new IncorrectData("Сумма отрицательная!");
    }

    private void afterTopUp(double sum) {
        if (bonusPrograms != null) {
            for (BonusProgram bonusProgram : bonusPrograms) {
                bonusProgram.afterTopUp(sum);
            }
        }
    }

    private void afterPay(double sum) {
        if (bonusPrograms != null) {
            for (BonusProgram bonusProgram : bonusPrograms) {
                bonusProgram.afterPay(sum);
            }
        }
    }
}
