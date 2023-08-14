package org.example.cards;

public interface BonusProgram {
    void afterPay(double sum);
    void afterTopUp(double sum);
    String getDescription();
}
