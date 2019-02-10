package ru.otus.java;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.BasicConfigurator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ru.otus.java.atm.Atm;
import ru.otus.java.atm.Banknote;
import ru.otus.java.atm.Values;
import ru.otus.java.exception.NotAllowedRequestException;

public class Work7Test {

  private Atm atm;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  public Work7Test() {
    BasicConfigurator.configure();
  }

  @Before
  public void init() {
    atm = new Atm();
    atm.put(Arrays.asList(new Banknote(Values.FIVE_THOUSAND), new Banknote(Values.FIVE_THOUSAND),
        new Banknote(Values.THOUSAND), new Banknote(Values.TWO_HUNDRED), new Banknote(Values.FIFTY),
        new Banknote(Values.FIFTY), new Banknote(Values.FIFTY), new Banknote(Values.FIFTY)));
  }

  @Test
  public void testGetSuccess() throws Exception {
    long totalAmount = atm.printRestAmount();
    List<Banknote> result = atm.get(1250);

    Assert.assertNotNull("Expected non null result but it is", result);
    Assert.assertFalse("Expected non empty result but it is", result.isEmpty());
    Assert.assertEquals(
        "Expected restAmount=" + (totalAmount - 1250) + " but actual=" + atm.printRestAmount(),
        totalAmount - 1250, atm.printRestAmount());

    Map<Values, List<Banknote>> groups = result.stream()
        .collect(Collectors.groupingBy(item -> item.getValue()));
    Assert.assertTrue("Expected number of groups=2 but actual=" + groups.size(),
        groups.size() == 3);

    Assert.assertNotNull("Expected non null THOUSAND group but it is", groups.get(Values.THOUSAND));
    Assert.assertNotNull("Expected non null TWO_HUNDRED group but it is",
        groups.get(Values.TWO_HUNDRED));
    Assert.assertNotNull("Expected non null FIFTY group but it is", groups.get(Values.FIFTY));
    Assert.assertTrue(
        "Expected THOUSAND group size=1 but actual=" + groups.get(Values.THOUSAND).size(),
        groups.get(Values.THOUSAND).size() == 1);

    Assert.assertTrue(
        "Expected FIFTY group size=1 but actual=" + groups.get(Values.TWO_HUNDRED).size(),
        groups.get(Values.TWO_HUNDRED).size() == 1);

    Assert.assertTrue("Expected FIFTY group size=1 but actual=" + groups.get(Values.FIFTY).size(),
        groups.get(Values.FIFTY).size() == 1);
  }

  @Test
  public void testPutSuccess() {
    long totalAmount = atm.printRestAmount();
    atm.put(new Banknote(Values.FIVE_HUNDRED));
    Assert.assertEquals(
        "Expected restAmount=" + (totalAmount + Values.FIVE_HUNDRED.getValue()) + " but actual="
            + atm.printRestAmount(),
        totalAmount + Values.FIVE_HUNDRED.getValue(), atm.printRestAmount());
  }

  @Test
  public void testGetFailByMultiplicity() {
    thrown.expect(NotAllowedRequestException.class);
    thrown.expectMessage(Atm.INVALID_AMOUNT);
    atm.get(123);
  }

  @Test
  public void testGetFailByLackOfBanknotes() {
    thrown.expect(NotAllowedRequestException.class);
    thrown.expectMessage(Atm.INVALID_AMOUNT);
    atm.get(1500);
  }

  @Test
  public void testGetFailByAmountExceeding() {
    thrown.expect(NotAllowedRequestException.class);
    thrown.expectMessage(Atm.NOT_ENOUGH_MONEY);
    atm.get(150000);
  }
}
