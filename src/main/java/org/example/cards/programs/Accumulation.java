package org.example.cards.programs;

import org.example.cards.BonusProgram;

public class Accumulation implements BonusProgram {
    private final double rate;
    private double allAccumulation;

    public Accumulation(double rate) {
        this.rate = rate;
        allAccumulation = 0;
    }

    @Override
    public void afterPay(double sum) {
        double accumulationSum = (sum / 100) * rate;
        allAccumulation += accumulationSum;
    }

    @Override
    public void afterTopUp(double sum) {}

    @Override
    public String getDescription() {
        return "Количество бонусов: " + allAccumulation + "\n";
    }

    public boolean writeOffAccumulation(double sumAccumulation) {
        if (sumAccumulation > allAccumulation)
            return false;
        else
            allAccumulation -= sumAccumulation;
        return true;
    }
}
