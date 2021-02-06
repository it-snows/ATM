package com.company.tests;

import org.junit.Assert;
import org.junit.Test;

import com.company.BankAccount;

public class BankAccountTests {

	@Test
	public void unable_to_take_money() {
		var bankAccount = new BankAccount("", 50, null);
		var result = bankAccount.takeMoney(100);
		Assert.assertFalse(result);
	}

	@Test
	public void can_take_money_b50_20() {
		var bankAccount = new BankAccount("", 50, null);
		var result = bankAccount.takeMoney(20);
		Assert.assertTrue(result);
		Assert.assertEquals(30d == bankAccount.getBalance(), 0);
	}
}
