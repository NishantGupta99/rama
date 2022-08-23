package com.rama.data;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for Greeting App.
 */

public class AppTest {
  Greeting greeting;

  @Before
  public void setUp() {
    greeting = new Greeting();
  }

  @Test
  public void TestHello() {

    String result = greeting.sayHello();
    assertEquals(result, "Hello");

  }

  @Test
  public void TestHi() {
    String result = greeting.sayHi();
    assertEquals(result, "Hi");
  }

  @Test
  public void TestWelcome() {
    String result = greeting.sayWelcome();
    assertEquals(result, "Welcome");
  }

  @Test
  public void TestThanks() {
    String result = greeting.sayThanks();
    assertEquals(result, "Thanks");
  }
}
