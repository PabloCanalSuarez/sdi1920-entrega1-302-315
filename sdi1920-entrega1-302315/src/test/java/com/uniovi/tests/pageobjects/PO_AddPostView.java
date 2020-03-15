package com.uniovi.tests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_AddPostView extends PO_NavView {
	static public void fillForm(WebDriver driver, String titlep, String contentsp, String filenamep) {
		WebElement title = driver.findElement(By.name("title"));
		title.click();
		title.clear();
		title.sendKeys(titlep);

		WebElement content = driver.findElement(By.name("contents"));
		content.click();
		content.clear();
		content.sendKeys(contentsp);

		if (filenamep != null) {
			WebElement picture = driver.findElement(By.name("picture"));
			picture.sendKeys(filenamep);
		}
		
		// Pulsar el boton de Alta.
		By boton = By.className("btn");
		driver.findElement(boton).click();
	}
}