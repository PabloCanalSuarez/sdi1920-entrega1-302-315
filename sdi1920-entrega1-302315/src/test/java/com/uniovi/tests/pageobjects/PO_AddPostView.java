package com.uniovi.tests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.uniovi.tests.util.SeleniumUtils;

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

	static public void checkPage(WebDriver driver, int language) {
		SeleniumUtils.EsperaCargaPagina(driver, "h2", p.getString("agregarPost.message", language), getTimeout());
		SeleniumUtils.EsperaCargaPagina(driver, "text", p.getString("post.title.message", language), getTimeout());
		SeleniumUtils.EsperaCargaPagina(driver, "text", p.getString("post.contents.message", language), getTimeout());
	}

	static public void checkChangeIdiom(WebDriver driver, String textIdiom1, String textIdiom2, int locale1,
			int locale2) {
		// Esperamos a que se cargue el saludo de bienvenida en Español
		PO_AddPostView.checkPage(driver, locale1);
		// Cambiamos a segundo idioma
		PO_AddPostView.changeIdiom(driver, textIdiom2);
		// COmprobamos que el texto de bienvenida haya cambiado a segundo idioma
		PO_AddPostView.checkPage(driver, locale2);
		// Volvemos a Español.
		PO_AddPostView.changeIdiom(driver, textIdiom1);
		// Esperamos a que se cargue el saludo de bienvenida en Español
		PO_AddPostView.checkPage(driver, locale1);
	}
}