package com.uniovi;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;

import com.uniovi.repositories.UsersRepository;
import com.uniovi.tests.pageobjects.PO_HomeView;
import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_Properties;
import com.uniovi.tests.pageobjects.PO_RegisterView;
import com.uniovi.tests.pageobjects.PO_View;
import com.uniovi.tests.util.DataBaseAccess;
import com.uniovi.tests.util.SeleniumUtils;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestsJesus {
	
	/*
	 *	Divided to avoid problems with the path of the executables, will merge both test suites prior to hand in
	 */
	
		static String PathFirefox65 = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
		static String Geckdriver024 = "D:\\MiUsuario\\Escritorio\\EclipseStuff\\tercero\\SDI\\Labs\\PL-SDI-Sesión5-material\\geckodriver024win64.exe";
		
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
		
		@Test
		public void Prueba1() {
			// Vamos al formulario de registro
			PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
			
			PO_RegisterView.fillForm(driver, "myEmail@dir.com", "Josefo", "Perez", "1234", "1234");
			
			// We are in the correct view
			PO_View.checkElement(driver, "text", "Esta es una zona privada la web");
			System.err.println("TODO: Change element checked for test to pass");
		}
		
		@Test
		public void Prueba2() {
			// Vamos al formulario de registro
			PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
			
			PO_RegisterView.fillForm(driver, "", "a", "a", "1234", "1234");
			PO_View.checkElement(driver, "id", "registerForm");
			
			PO_RegisterView.fillForm(driver, "a", "", "a", "1234", "1234");
			PO_View.checkElement(driver, "id", "registerForm");
			
			PO_RegisterView.fillForm(driver, "a", "a", "", "1234", "1234");
			PO_View.checkElement(driver, "id", "registerForm");
			
			PO_RegisterView.fillForm(driver, "a", "a", "a", "", "");
			PO_View.checkElement(driver, "id", "registerForm");
		}
		
		@Test
		public void Prueba3() {
			PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
			
			PO_RegisterView.fillForm(driver, "another", "Josefo", "Perez", "1234", "5678");
			PO_RegisterView.checkKey(driver, "Error.signup.passwordConfirm.coincidence", PO_Properties.getSPANISH());
		}
		

		@Test
		public void Prueba4() {
			PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
			
			PO_RegisterView.fillForm(driver, "admin@email.com", "Josefo", "Perez", "1234", "1234");
			PO_RegisterView.checkKey(driver, "Error.signup.email.duplicate", PO_Properties.getSPANISH());
		}
		
		
		@Test
		public void Prueba11() {
			PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
			PO_LoginView.fillForm(driver, "admin@email.com", "123456");
			
			List<WebElement> elementos = PO_View.checkElement(driver, "id", "users-menu");
			elementos.get(0).click();
			elementos = PO_View.checkElement(driver, "free", "//a[contains(@href,'user/list')]");
			elementos.get(0).click();
			
			int usersCount = 0;
			boolean isNextPage = false;
			do {
				
				List<WebElement> users = driver.findElements(By.xpath("//*[@id=\"tableUsers\"]/tbody/tr"));
				usersCount += users.size();
				
				// Check If There is next page
				List<WebElement> nextPageLink = driver.findElements(By.xpath("//*[@id=\"nextPageOfList\"]/a"));
				if ( !nextPageLink.isEmpty() ) {
					nextPageLink.get(0).click();
					isNextPage = true;
				} else {
					isNextPage = false;
				}
				
			} while (isNextPage); // nextPageOfList
			
			//DataBaseAccess.listUsers().forEach( u -> u.getEmail());
			Assertions.assertEquals(5, usersCount);
		}
}
