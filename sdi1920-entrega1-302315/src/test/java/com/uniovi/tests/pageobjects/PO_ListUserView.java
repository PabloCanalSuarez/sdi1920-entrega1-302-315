package com.uniovi.tests.pageobjects;

import org.openqa.selenium.WebDriver;

import com.uniovi.tests.util.SeleniumUtils;

public class PO_ListUserView extends PO_NavView {
	static public void checkPage(WebDriver driver, int language) {
		SeleniumUtils.EsperaCargaPagina(driver, "h2", p.getString("user.message", language), getTimeout());
		SeleniumUtils.EsperaCargaPagina(driver, "text", p.getString("userSub.message", language), getTimeout());
		SeleniumUtils.EsperaCargaPagina(driver, "text", p.getString("userName.message", language), getTimeout());
		SeleniumUtils.EsperaCargaPagina(driver, "text", p.getString("userSurname.message", language), getTimeout());
	}

	static public void checkChangeIdiom(WebDriver driver, String textIdiom1, String textIdiom2, int locale1,
			int locale2) {
		// Esperamos a que se cargue el saludo de bienvenida en Español
		PO_ListUserView.checkPage(driver, locale1);
		// Cambiamos a segundo idioma
		PO_ListUserView.changeIdiom(driver, textIdiom2);
		// COmprobamos que el texto de bienvenida haya cambiado a segundo idioma
		PO_ListUserView.checkPage(driver, locale2);
		// Volvemos a Español.
		PO_ListUserView.changeIdiom(driver, textIdiom1);
		// Esperamos a que se cargue el saludo de bienvenida en Español
		PO_ListUserView.checkPage(driver, locale1);
	}

}
