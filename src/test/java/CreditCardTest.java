import org.example.cards.*;
import org.example.cards.programs.*;
import org.example.cards.programs.cashbacks.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class CreditCardTest {
    private static final int LIMIT = 10000;

    @Test
    public void testSimpleCard() {
        CreditCard creditCard = new CreditCard(LIMIT);
        startCardTest(creditCard);
    }

    @Test
    public void bonusPointsAfterTopUpProgram() {
        List<BonusProgram> bonusPrograms = new ArrayList<>();
        BonusPointsAfterTopUp bonusPointsAfterTopUp = new BonusPointsAfterTopUp(0.005);
        bonusPrograms.add(bonusPointsAfterTopUp);

        CreditCard creditCard = new CreditCard(LIMIT, bonusPrograms);
        startCardTest(creditCard);

        assertTrue(creditCard.getInformationAboutAvailableFunds().contains("Собственные средства: 1000.0"));
        assertTrue(creditCard.getInformationAboutAvailableFunds().contains("Кредитная карта с лимитом 10000.0"));
        assertTrue(creditCard.getInformationAboutAvailableFunds().contains("Кредитные средства: 10000.0"));
        assertTrue(creditCard.getInformationAboutAvailableFunds().contains("Бонусов имеется: 0.44999999999999996"));


        creditCard.topUp(10000);
        assertTrue(creditCard.getInformationAboutAvailableFunds().contains("Собственные средства: 11000.0"));
        assertTrue(creditCard.getInformationAboutAvailableFunds().contains("Кредитные средства: 10000.0"));
        assertTrue(creditCard.getInformationAboutAvailableFunds().contains("Бонусов имеется: 0.95"));
    }

    @Test
    public void accumulationProgram() {
        List<BonusProgram> bonusPrograms = new ArrayList<>();
        Accumulation accumulation = new Accumulation(0.005);
        bonusPrograms.add(accumulation);

        CreditCard creditCard = new CreditCard(LIMIT, bonusPrograms);
        startCardTest(creditCard);

        assertTrue(creditCard.getInformationAboutAvailableFunds().contains("Собственные средства: 1000.0"));
        assertTrue(creditCard.getInformationAboutAvailableFunds().contains("Кредитная карта с лимитом 10000.0"));
        assertTrue(creditCard.getInformationAboutAvailableFunds().contains("Кредитные средства: 10000.0"));
        assertTrue(creditCard.getInformationAboutAvailableFunds().contains("Количество бонусов: 0.25"));

        creditCard.topUp(10000);
        assertTrue(creditCard.getInformationAboutAvailableFunds().contains("Количество бонусов: 0.25"));

        assertTrue(creditCard.pay(5000));

        assertTrue(creditCard.getInformationAboutAvailableFunds().contains("Количество бонусов: 0.5"));

        creditCard.topUp(10000);
        assertTrue(creditCard.pay(5000));

        assertTrue(creditCard.getInformationAboutAvailableFunds().contains("Количество бонусов: 0.75"));
    }

    @Test
    public void cashbackForDayProgram() {
        List<BonusProgram> bonusPrograms = new ArrayList<>();
        CashbackForDay cashbackForDay = new CashbackForDay(1, 10000);
        bonusPrograms.add(cashbackForDay);

        CreditCard creditCard = new CreditCard(LIMIT, bonusPrograms);
        startCardTest(creditCard);

        assertTrue(creditCard.getInformationAboutAvailableFunds().contains("Собственные средства: 1000.0"));
        assertTrue(creditCard.getInformationAboutAvailableFunds().contains("Кредитные средства: 10000.0"));
        assertTrue(creditCard.getInformationAboutAvailableFunds().contains("Сумма совершенных покупок в ныншнем периоде: 5000.0"));
        assertTrue(creditCard.getInformationAboutAvailableFunds().contains("Сумма кэшбека в ныншнем периоде: 0.0"));

        assertTrue(creditCard.pay(5000));
        assertTrue(creditCard.pay(500));

        assertTrue(creditCard.getInformationAboutAvailableFunds().contains("Собственные средства: 0.0"));
        assertTrue(creditCard.getInformationAboutAvailableFunds().contains("Кредитные средства: 5500.0"));
        assertTrue(creditCard.getInformationAboutAvailableFunds().contains("Сумма совершенных покупок в ныншнем периоде: 6000.0"));
        assertTrue(creditCard.getInformationAboutAvailableFunds().contains("Сумма кэшбека в ныншнем периоде: 0.0"));

        creditCard.topUp(20000);
        assertTrue(creditCard.pay(3000));

        assertTrue(creditCard.getInformationAboutAvailableFunds().contains("Собственные средства: 12500.0"));
        assertTrue(creditCard.getInformationAboutAvailableFunds().contains("Кредитные средства: 10000.0"));
        assertTrue(creditCard.getInformationAboutAvailableFunds().contains("Сумма совершенных покупок в ныншнем периоде: 9000.0"));
        assertTrue(creditCard.getInformationAboutAvailableFunds().contains("Сумма кэшбека в ныншнем периоде: 0.0"));

        assertTrue(creditCard.pay(5000));

        assertTrue(creditCard.getInformationAboutAvailableFunds().contains("Собственные средства: 7500.0"));
        assertTrue(creditCard.getInformationAboutAvailableFunds().contains("Кредитные средства: 10000.0"));
        assertTrue(creditCard.getInformationAboutAvailableFunds().contains("Сумма совершенных покупок в ныншнем периоде: 14000.0"));
        assertTrue(creditCard.getInformationAboutAvailableFunds().contains("Сумма кэшбека в ныншнем периоде: 50.0"));
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

        CreditCard creditCard = new CreditCard(LIMIT, bonusPrograms);
        startCardTest(creditCard);

        assertTrue(creditCard.getInformationAboutAvailableFunds().contains("Собственные средства: 1000.0"));
        assertTrue(creditCard.getInformationAboutAvailableFunds().contains("Кредитная карта с лимитом 10000.0"));
        assertTrue(creditCard.getInformationAboutAvailableFunds().contains("Кредитные средства: 10000.0"));
        assertTrue(creditCard.getInformationAboutAvailableFunds().contains("Процент кэшбека: 1.0"));
        assertTrue(creditCard.getInformationAboutAvailableFunds().contains("Сумма необходимых покупок для начисления кэшбека: 10000.0"));
        assertTrue(creditCard.getInformationAboutAvailableFunds().contains("Сумма совершенных покупок в ныншнем периоде: 5000.0"));
        assertTrue(creditCard.getInformationAboutAvailableFunds().contains("Сумма кэшбека в ныншнем периоде: 0.0"));
        assertTrue(creditCard.getInformationAboutAvailableFunds().contains("Количество бонусов: 0.25"));
        assertTrue(creditCard.getInformationAboutAvailableFunds().contains("Бонусов имеется: 0.44999999999999996"));
    }

    private void startCardTest(CreditCard creditCard) {
        /*Кредитная карта с лимитом 10 000.
        Кредитные средства: 10 000.
        Собственные средства: 0.*/
        assertEquals(LIMIT, creditCard.getCreditLimit(), 0.0);
        assertEquals(10000, creditCard.getCreditBalance(), 0.0);
        assertEquals(0, creditCard.getBalance(), 0.0);

        assertFalse(creditCard.pay(11000));

        /*После пополнения карты на 5 000:
        Кредитные средства: 10 000.
        Собственные средства: 5 000.*/
        creditCard.topUp(5000);
        assertEquals(10000, creditCard.getCreditBalance(), 0.0);
        assertEquals(5000, creditCard.getBalance(), 0.0);

        /*После оплаты на 5 000:
        Кредитные средства: 10 000.
        Собственные средства: 0.*/
        assertTrue(creditCard.pay(5000));
        assertEquals(10000, creditCard.getCreditBalance(), 0.0);
        assertEquals(0, creditCard.getBalance(), 0.0);

        /*После оплаты на 3 000:
        Кредитные средства: 7 000.
        Собственные средства: 0.*/
        assertTrue(creditCard.pay(3000));
        assertEquals(7000, creditCard.getCreditBalance(), 0.0);
        assertEquals(0, creditCard.getBalance(), 0.0);

        /*После пополнения на 2 000:
        Кредитные средства: 9 000.
        Собственные средства: 0.*/
        creditCard.topUp(2000);
        assertEquals(9000, creditCard.getCreditBalance(), 0.0);
        assertEquals(0, creditCard.getBalance(), 0.0);

        /*После пополнения на 2 000:
        Кредитные средства: 10 000.
        Собственные средства: 1 000.*/
        creditCard.topUp(2000);
        assertEquals(10000, creditCard.getCreditBalance(), 0.0);
        assertEquals(1000, creditCard.getBalance(), 0.0);
    }
}
