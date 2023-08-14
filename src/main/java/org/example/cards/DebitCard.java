package org.example.cards;

import java.util.List;

public class DebitCard extends BankCard {
    public DebitCard() {
        super();
    }

    public DebitCard(List<BonusProgram> bonusPrograms) {
        super(bonusPrograms);
    }

    @Override
    protected double doTopUpOperations(double sum) {
        return sum;
    }

    @Override
    protected double doPayOperations(double sum) {
        return sum;
    }

    @Override
    protected String getCardDescription() {
        return null;
    }
}
