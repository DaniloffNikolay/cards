import org.example.cards.*;
import org.example.cards.programs.*;
import org.example.cards.programs.cashbacks.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class DebitCardTest {
    @Test
    public void simpleCard() {
        DebitCard debitCard = new DebitCard();
        startCardTest(debitCard);
    }

    @Test
    public void bonusPointsAfterTopUpProgram() {
        List<BonusProgram> bonusPrograms = new ArrayList<>();
        BonusPointsAfterTopUp bonusPointsAfterTopUp = new BonusPointsAfterTopUp(0.005);
        bonusPrograms.add(bonusPointsAfterTopUp);

        DebitCard debitCard = new DebitCard(bonusPrograms);
        startCardTest(debitCard);

        assertTrue(debitCard.getInformationAboutAvailableFunds().contains("Бонусов имеется: 0.05"));
        debitCard.topUp(10000);
        assertTrue(debitCard.getInformationAboutAvailableFunds().contains("Бонусов имеется: 0.55"));
    }

    @Test
    public void accumulationProgram() {
        List<BonusProgram> bonusPrograms = new ArrayList<>();
        Accumulation accumulation = new Accumulation(0.005);
        bonusPrograms.add(accumulation);

        DebitCard debitCard = new DebitCard(bonusPrograms);
        startCardTest(debitCard);

        assertTrue(debitCard.getInformationAboutAvailableFunds().contains("Количество бонусов: 0.025"));
        debitCard.pay(10000);
        assertTrue(debitCard.getInformationAboutAvailableFunds().contains("Количество бонусов: 0.025"));

        debitCard.topUp(10000);
        debitCard.pay(10000);
        assertTrue(debitCard.getInformationAboutAvailableFunds().contains("Количество бонусов: 0.525"));
    }

    @Test
    public void cashbackForDayProgram() {
        List<BonusProgram> bonusPrograms = new ArrayList<>();
        CashbackForDay cashbackForDay = new CashbackForDay(1, 10000);
        bonusPrograms.add(cashbackForDay);

        DebitCard debitCard = new DebitCard(bonusPrograms);
        startCardTest(debitCard);

        assertTrue(debitCard.getInformationAboutAvailableFunds().contains("Процент кэшбека: 1.0"));
        assertTrue(debitCard.getInformationAboutAvailableFunds().contains("Сумма необходимых покупок для начисления кэшбека: 10000.0"));
        assertTrue(debitCard.getInformationAboutAvailableFunds().contains("Сумма совершенных покупок в ныншнем периоде: 500.0"));
        assertTrue(debitCard.getInformationAboutAvailableFunds().contains("Сумма кэшбека в ныншнем периоде: 0.0"));

        debitCard.topUp(5000);
        assertTrue(debitCard.pay(5000));

        assertTrue(debitCard.getInformationAboutAvailableFunds().contains("Сумма совершенных покупок в ныншнем периоде: 5500.0"));
        assertTrue(debitCard.getInformationAboutAvailableFunds().contains("Сумма кэшбека в ныншнем периоде: 0.0"));

        debitCard.topUp(5000);
        assertTrue(debitCard.pay(5000));

        assertTrue(debitCard.getInformationAboutAvailableFunds().contains("Сумма совершенных покупок в ныншнем периоде: 10500.0"));
        assertTrue(debitCard.getInformationAboutAvailableFunds().contains("Сумма кэшбека в ныншнем периоде: 50.0"));

        debitCard.topUp(5000);
        assertTrue(debitCard.pay(5000));

        assertTrue(debitCard.getInformationAboutAvailableFunds().contains("Сумма совершенных покупок в ныншнем периоде: 15500.0"));
        assertTrue(debitCard.getInformationAboutAvailableFunds().contains("Сумма кэшбека в ныншнем периоде: 100.0"));
    }

    @Test
    public void allPrograms() {
        CashbackForDay cashbackForDay = new CashbackForDay(1, 10000);
        Accumulation accumulation = new Accumulation(0.005);
        BonusPointsAfterTopUp bonusPointsAfterTopUp = new BonusPointsAfterTopUp(0.005);

        List<BonusProgram> bonusPrograms = new ArrayList<>();
        bonusPrograms.add(cashbackForDay);
        bonusPrograms.add(accumulation);
        bonusPrograms.add(bonusPointsAfterTopUp);

        DebitCard debitCard = new DebitCard(bonusPrograms);
        startCardTest(debitCard);

        assertTrue(debitCard.getInformationAboutAvailableFunds().contains("Собственные средства: 500.0"));
        assertTrue(debitCard.getInformationAboutAvailableFunds().contains("Процент кэшбека: 1.0"));
        assertTrue(debitCard.getInformationAboutAvailableFunds().contains("Сумма необходимых покупок для начисления кэшбека: 10000.0"));
        assertTrue(debitCard.getInformationAboutAvailableFunds().contains("Сумма совершенных покупок в ныншнем периоде: 500.0"));
        assertTrue(debitCard.getInformationAboutAvailableFunds().contains("Сумма кэшбека в ныншнем периоде: 0.0"));
        assertTrue(debitCard.getInformationAboutAvailableFunds().contains("Количество бонусов: 0.025"));
        assertTrue(debitCard.getInformationAboutAvailableFunds().contains("Бонусов имеется: 0.05"));
    }

    private void startCardTest(DebitCard debitCard) {
        assertEquals(0, debitCard.getBalance(), 0.0);
        assertFalse(debitCard.pay(500));

        debitCard.topUp(1000);
        assertEquals(1000, debitCard.getBalance(), 0.0);

        assertTrue(debitCard.pay(500));
        assertEquals(500, debitCard.getBalance(), 0.0);
    }
}
