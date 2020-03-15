package com.uniovi.tests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_PostView extends PO_NavView {
	static public void fillForm(WebDriver driver, String titleF, String contentsF) {
		WebElement title = driver.findElement(By.name("title"));
		title.click();
		title.clear();
		title.sendKeys(titleF);

		WebElement contents = driver.findElement(By.name("contents"));
		contents.click();
		contents.clear();
		contents.sendKeys(contentsF);

		// Pulsar el boton de Alta.
		By boton = By.className("btn");
		driver.findElement(boton).click();
	}
}