package org.example.cards.programs;

import org.example.cards.BonusProgram;

public class BonusPointsAfterTopUp implements BonusProgram {
    private double rate;
    private double bonus;

    public BonusPointsAfterTopUp(double rate) {
        this.rate = rate;
    }

    @Override
    public void afterPay(double sum) {}

    @Override
    public void afterTopUp(double sum) {
        double bonusSum = (sum / 100) * rate;
        bonus += bonusSum;
    }

    public boolean writeOffBonuses(double sumBonus) {
        if (sumBonus > bonus)
            return false;
        else
            bonus -= sumBonus;
        return true;
    }

    @Override
    public String getDescription() {
        return "Бонусов имеется: " + bonus + "\n";
    }
}
