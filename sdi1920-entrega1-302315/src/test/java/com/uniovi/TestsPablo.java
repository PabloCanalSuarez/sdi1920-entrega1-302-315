package com.uniovi;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;

import com.uniovi.repositories.UsersRepository;
import com.uniovi.tests.pageobjects.PO_HomeView;
import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_NavView;
import com.uniovi.tests.pageobjects.PO_View;
import com.uniovi.tests.util.SeleniumUtils;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestsPablo {

	/*
	 * Divided to avoid problems with the path of the executables, will merge both
	 * test suites prior to hand in
	 */

	static String PathFirefox65 = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
	static String Geckdriver024 = "C:\\Users\\kendo\\Downloads\\PL-SDI-Sesión5-material\\PL-SDI-Sesión5-material\\geckodriver024win64.exe";

	static WebDriver driver = getDriver(PathFirefox65, Geckdriver024);
	static String URL = "http://localhost:8080";

	@Autowired
	private UsersRepository usersRepository;

	public static WebDriver getDriver(String PathFirefox, String Geckdriver) {
		System.setProperty("webdriver.firefox.bin", PathFirefox);
		System.setProperty("webdriver.gecko.driver", Geckdriver);
		WebDriver driver = new FirefoxDriver();
		return driver;
	}

	// Antes de cada prueba se navega al URL home de la aplicaciónn
	@Before
	public void setUp() {
		driver.navigate().to(URL);
	}

	// Después de cada prueba se borran las cookies del navegador
	@After
	public void tearDown() {
		driver.manage().deleteAllCookies();
	}

	// Antes de la primera prueba
	@BeforeClass
	static public void begin() {
	}

	// Al finalizar la última prueba
	@AfterClass
	static public void end() {
		// Cerramos el navegador al finalizar las pruebas
		driver.quit();
	}

	// Inicio de sesión con datos válidos (administrador).
	@Test
	public void Prueba5() {
		// Vamos al formulario de login
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "admin@email.com", "admin");
		// Comprobamos estar en la zona correcta
		PO_View.checkElement(driver, "text", "Los usuarios que actualmente figuran en el sistema son los siguientes:");
	}

	// Inicio de sesión con datos válidos (usuario estándar).
	@Test
	public void Prueba6() {
		// Vamos al formulario de login
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "diego@email.com", "123456");
		// Comprobamos estar en la zona correcta
		PO_View.checkElement(driver, "text", "Los usuarios que actualmente figuran en el sistema son los siguientes:");
	}

	// Inicio de sesión con datos inválidos
	// (usuario estándar, campo email y contraseña vacíos).
	@Test
	public void Prueba7() {
		// Vamos al formulario de login
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "", "");
		// Comprobamos estar en la zona correcta
		PO_View.checkElement(driver, "text", "Identifícate");
	}

	// Inicio de sesión con datos válidos
	// (usuario estándar, email existente, pero contraseña incorrecta).
	@Test
	public void Prueba8() {
		// Vamos al formulario de login
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "admin@email.com", "contraseñaIncorrecta");
		// Comprobamos estar en la zona correcta
		PO_View.checkElement(driver, "text", "Your email or password is invalid.");
	}

	// Hacer click en la opción de salir de sesión y comprobar que se redirige
	// a la página de inicio de sesión (Login).
	@Test
	public void Prueba9() {
		// Vamos al formulario de login
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "admin@email.com", "admin");
		PO_NavView.clickOption(driver, "logout", "class", "btn btn-primary");
		// Comprobamos estar en la zona correcta
		PO_View.checkElement(driver, "text", "Identifícate");
		PO_View.checkElement(driver, "text", "You have been logged out successfully.");
	}

	// Comprobar que el botón cerrar sesión no está visible si el usuario no está
	// autenticado.
	@Test
	public void Prueba10() {
		SeleniumUtils.textoNoPresentePagina(driver, "Desconectar");
	}

	// Desde el listado de usuarios de la aplicación, enviar una invitación de amistad a un usuario.
	// Comprobar que la solicitud de amistad aparece en el listado de invitaciones (punto siguiente).
	@Test
	public void Prueba15() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "diego@email.com", "123456");

		List<WebElement> elementos = PO_HomeView.checkElement(driver, "free", "//td[contains(text(), 'jesus@email.com')]/following-sibling::*/a[contains(@href, 'invitation/send/')]");
		elementos.get(0).click();
		
		PO_NavView.clickOption(driver, "logout", "class", "btn btn-primary");
		
		// Comprobamos haber enviado la invitacion
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "jesus@email.com", "123456");
		
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href,'invitation/list')]");
		elementos.get(0).click();
		
		PO_View.checkElement(driver, "text", "Tus Invitaciones");
		PO_View.checkElement(driver, "text", "Diego");
	}

	// Desde el listado de usuarios de la aplicación, enviar una invitación de amistad a un usuario al
	// que ya le habíamos enviado la invitación previamente. No debería dejarnos enviar la invitación, se podría
	// ocultar el botón de enviar invitación o notificar que ya había sido enviada previamente.
//	@Test
//	public void Prueba16() {
//		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
//		PO_LoginView.fillForm(driver, "diego@email.com", "123456");
//
//		List<WebElement> elementos = PO_HomeView.checkElement(driver, "free", "//td[contains(text(), 'clara@email.com')]/following-sibling::*/a[contains(@href, 'invitation/send/')]");
//		elementos.get(0).click();
//		
//		PO_View.checkElement(driver, "text", "Tus Invitaciones");
//		
//		// Comprobamos haber enviado la invitacion
//		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
//		PO_LoginView.fillForm(driver, "jesus@email.com", "123456");
//		
//		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href,'invitation/list')]");
//		elementos.get(0).click();
//		
//		PO_View.checkElement(driver, "text", "Tus Invitaciones");
//		PO_View.checkElement(driver, "text", "Diego");
//	}

}