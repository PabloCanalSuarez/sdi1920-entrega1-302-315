package com.uniovi;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
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

import com.uniovi.tests.pageobjects.PO_AddPostView;
import com.uniovi.tests.pageobjects.PO_HomeView;
import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_Properties;
import com.uniovi.tests.pageobjects.PO_RegisterView;
import com.uniovi.tests.pageobjects.PO_View;
import com.uniovi.tests.util.DataBaseAccess;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestsJesus {
	
	/*
	 *	Divided to avoid problems with the path of the executables, will merge both test suites prior to hand in
	 */
	
		static String PathFirefox65 = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
		static String Geckdriver024 = "D:\\MiUsuario\\Escritorio\\EclipseStuff\\tercero\\SDI\\Labs\\PL-SDI-Sesión5-material\\geckodriver024win64.exe";
		
		static WebDriver driver = getDriver(PathFirefox65, Geckdriver024);
		static String URL = "http://localhost:8080";

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
			PO_View.checkElement(driver, "id", "titleHome");
			
			// Delete registration
			DataBaseAccess.removeUser("myEmail@dir.com");
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
			PO_LoginView.fillForm(driver, "clara@email.com", "123456");
			
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

			int numberUsers = DataBaseAccess.listUsers()
									.stream()
									.filter( u -> u.getRole().equals( "ROLE_USER" ) )
									.filter( u -> !u.getEmail().equals("clara@email.com") )
									.toArray()
									.length;
			Assertions.assertEquals(numberUsers, usersCount);
		}
		
		
		@Test
		public void Prueba12() {
			PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
			PO_LoginView.fillForm(driver, "clara@email.com", "123456");
			
			List<WebElement> elementos = PO_View.checkElement(driver, "id", "users-menu");
			elementos.get(0).click();
			elementos = PO_View.checkElement(driver, "free", "//a[contains(@href,'user/list')]");
			elementos.get(0).click();
			
			// Click search with no searchText
			List<WebElement> searchLink = driver.findElements(By.xpath("//*[@id=\"searchForUserForm\"]/button"));
			searchLink.get(0).click();
			
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

			int numberUsers = DataBaseAccess.listUsers()
								.stream()
								.filter( u -> u.getRole().equals( "ROLE_USER" ) )
								.filter( u -> !u.getEmail().equals("clara@email.com") )
								.toArray()
								.length;
			Assertions.assertEquals(numberUsers, usersCount);
		}
		
		@Test
		public void Prueba13() {
			PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
			PO_LoginView.fillForm(driver, "admin@email.com", "admin");
			
			List<WebElement> elementos = PO_View.checkElement(driver, "id", "users-menu");
			elementos.get(0).click();
			elementos = PO_View.checkElement(driver, "free", "//a[contains(@href,'user/list')]");
			elementos.get(0).click();
			
			// Enter search text
			WebElement searchBar = driver.findElement(By.name("searchText"));
			searchBar.sendKeys("NonExistingUser$$gfgh");
			
			List<WebElement> searchLink = driver.findElements(By.xpath("//*[@id=\"searchForUserForm\"]/button"));
			searchLink.get(0).click();
			
			List<WebElement> results = driver.findElements(By.xpath("//*[@id=\"tableUsers\"]/tbody/tr"));
			int usersCount = results.size();

			Assertions.assertEquals(0, usersCount);
		}
		
		@Test
		public void Prueba14() {
			PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
			PO_LoginView.fillForm(driver, "admin@email.com", "admin");
			
			List<WebElement> elementos = PO_View.checkElement(driver, "id", "users-menu");
			elementos.get(0).click();
			elementos = PO_View.checkElement(driver, "free", "//a[contains(@href,'user/list')]");
			elementos.get(0).click();
			
			// Search By Name
			WebElement searchBar = driver.findElement(By.name("searchText"));
			searchBar.sendKeys("Clar");
			
			List<WebElement> searchLink = driver.findElements(By.xpath("//*[@id=\"searchForUserForm\"]/button"));
			searchLink.get(0).click();
			
			List<WebElement> results = driver.findElements(By.xpath("//*[@id=\"tableUsers\"]/tbody/tr"));
			int usersCount = results.size();

			Assertions.assertEquals(1, usersCount);
			
			// Search By Surname
			searchBar = driver.findElement(By.name("searchText"));
			searchBar.sendKeys("e la Cal");
			
			searchLink = driver.findElements(By.xpath("//*[@id=\"searchForUserForm\"]/button"));
			searchLink.get(0).click();
			
			results = driver.findElements(By.xpath("//*[@id=\"tableUsers\"]/tbody/tr"));
			usersCount = results.size();

			Assertions.assertEquals(1, usersCount);
			
			// Search By email
			searchBar = driver.findElement(By.name("searchText"));
			searchBar.sendKeys("diego@email.co");
			
			searchLink = driver.findElements(By.xpath("//*[@id=\"searchForUserForm\"]/button"));
			searchLink.get(0).click();
			
			results = driver.findElements(By.xpath("//*[@id=\"tableUsers\"]/tbody/tr"));
			usersCount = results.size();

			Assertions.assertEquals(1, usersCount);
		}
		
  
		@Test
		public void Prueba19() {
			DataBaseAccess.removeFriends( DataBaseAccess.getUserId("clara@email.com") );
			
			PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
			PO_LoginView.fillForm(driver, "clara@email.com", "123456");
			
			// Clara has no friends
			List<WebElement> elementos = PO_View.checkElement(driver, "id", "users-menu");
			elementos.get(0).click();
			elementos = PO_View.checkElement(driver, "free", "//a[contains(@href,'user/friends')]");
			elementos.get(0).click();
			
			List<WebElement> friends = driver.findElements(By.xpath("//*[@id=\"tableFriends\"]/tbody/tr"));
			Assertions.assertEquals(0, friends.size());
			
			// Accepts 1
			PO_HomeView.clickOption(driver, "invitation/list", "id", "tableInvitations");
			List<WebElement> friendsList = driver.findElements(By.xpath("//*[@id=\"tableInvitations\"]/tbody/tr[1]/td[3]/a"));
			friendsList.get(0).click();
			
			// has one
			elementos = PO_View.checkElement(driver, "id", "users-menu");
			elementos.get(0).click();
			elementos = PO_View.checkElement(driver, "free", "//a[contains(@href,'user/friends')]");
			elementos.get(0).click();
			
			friends = driver.findElements(By.xpath("//*[@id=\"tableFriends\"]/tbody/tr"));
			Assertions.assertEquals(1, friends.size());
			
			// Accepts another 2
			PO_HomeView.clickOption(driver, "invitation/list", "id", "tableInvitations");
			friendsList = driver.findElements(By.xpath("//*[@id=\"tableInvitations\"]/tbody/tr[1]/td[3]/a"));
			friendsList.get(0).click();
			friendsList = driver.findElements(By.xpath("//*[@id=\"tableInvitations\"]/tbody/tr[1]/td[3]/a"));
			friendsList.get(0).click();
			
			// has 3
			elementos = PO_View.checkElement(driver, "id", "users-menu");
			elementos.get(0).click();
			elementos = PO_View.checkElement(driver, "free", "//a[contains(@href,'user/friends')]");
			elementos.get(0).click();
			
			friends = driver.findElements(By.xpath("//*[@id=\"tableFriends\"]/tbody/tr"));
			Assertions.assertEquals(3, friends.size());
			
			
			// Delete invitations
			DataBaseAccess.removeFriends( DataBaseAccess.getUserId("clara@email.com") );
		}

  	@Test
		public void Prueba21() {
			driver.navigate().to(URL + "/user/list");
			// Check we are at login view
			PO_View.checkElement(driver, "id", "loginForm");
		}
  
		@Test
		public void Prueba22() {
			driver.navigate().to(URL + "/post/list");
			// Check we are at login view
			PO_View.checkElement(driver, "id", "loginForm");
			
			driver.navigate().to(URL + "/post/friends/2");
      // Check we are at login view
			PO_View.checkElement(driver, "id", "loginForm");
    }
  
		@Test
		public void Prueba23() {
			driver.navigate().to(URL + "/secret");
			
			// We are at login view
			PO_LoginView.fillForm(driver, "jesus@email.com", "123456");
			List<WebElement> h1Text = driver.findElements(By.xpath("//html/body/*[local-name() = \"h1\"]"));
			Assertions.assertEquals("HTTP Status 403 – Forbidden", h1Text.get(0).getText());
    }
		
		@Test
		public void Prueba27() {
			DataBaseAccess.removeFriends( DataBaseAccess.getUserId("clara@email.com") );
			
			PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
			PO_LoginView.fillForm(driver, "clara@email.com", "123456");
			
			// Accept Jesus as friend (as he has 2 posts)
			PO_HomeView.clickOption(driver, "invitation/list", "id", "tableInvitations");
			List<WebElement> friendsList = driver.findElements(By.xpath(
					"//*[@id=\"tableInvitations\"]/tbody/tr[td[2]/text()='jesus@email.com']/td[3]/a")
			);
			friendsList.get(0).click();
			
			// Click on friends view posts
			List<WebElement> elementos = PO_View.checkElement(driver, "id", "users-menu");
			elementos.get(0).click();
			elementos = PO_View.checkElement(driver, "free", "//a[contains(@href,'user/friends')]");
			elementos.get(0).click();
			
			List<WebElement> friends = driver.findElements(By.xpath("//*[@id=\"tableFriends\"]/tbody/tr/td[4]/a"));
			friends.get(0).click();
			
			// Count Posts
			List<WebElement> postsOfJesus = driver.findElements(By.xpath("//*[@id=\"tablePosts\"]/tbody/tr"));
			int numPostsOfJesus = DataBaseAccess.getPostsByUser("jesus@email.com");
			Assertions.assertEquals(numPostsOfJesus, postsOfJesus.size());
			
			// Delete invitations
			DataBaseAccess.removeFriends( DataBaseAccess.getUserId("clara@email.com") );
		}
		
		@Test
		public void Prueba28() {
			PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
			PO_LoginView.fillForm(driver, "clara@email.com", "123456");
			
			driver.navigate().to(URL + "/post/friends/10");

			List<WebElement> h1Text = driver.findElements(By.xpath("//html/body/*[local-name() = \"h1\"]"));
			Assertions.assertEquals("HTTP Status 403 – Forbidden", h1Text.get(0).getText());
			
			driver.navigate().to(URL + "/post/friends/2");

			h1Text = driver.findElements(By.xpath("//html/body/*[local-name() = \"h1\"]"));
			Assertions.assertEquals("HTTP Status 403 – Forbidden", h1Text.get(0).getText());
		}
		
		@Test
		public void Prueba29() {
			DataBaseAccess.removePostsByUser("clara@email.com");
			
			// Login and go to add posts
			PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
			PO_LoginView.fillForm(driver, "clara@email.com", "123456");
		
			List<WebElement> elementos = PO_View.checkElement(driver, "id", "posts-menu");
			elementos.get(0).click();
			elementos = PO_View.checkElement(driver, "free", "//a[contains(@href,'post/add')]");
			elementos.get(0).click();
			
			
			// Create a new post with a picture
			PO_AddPostView.fillForm(driver, "TitLe", "ConTenTs", URL + "/img/sample.jpg");
			
			// Auto redirect to list, where post should have a picture
			PO_View.checkElement(driver, "class", "postPicture");
			
			DataBaseAccess.removePostsByUser("clara@email.com");
		}
		
		@Test
		public void Prueba30() {
			DataBaseAccess.removePostsByUser("clara@email.com");
			
			// Login and go to add posts
			PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
			PO_LoginView.fillForm(driver, "clara@email.com", "123456");
		
			List<WebElement> elementos = PO_View.checkElement(driver, "id", "posts-menu");
			elementos.get(0).click();
			elementos = PO_View.checkElement(driver, "free", "//a[contains(@href,'post/add')]");
			elementos.get(0).click();
			
			
			// Create a new post with NO picture
			PO_AddPostView.fillForm(driver, "TitLe", "ConTenTs", null);
			
			// Auto redirect to list, where post should NO picture
			List<WebElement> picture = driver.findElements( By.xpath("//*[@id=\"tablePosts\"]/tbody/tr/td[4]") );
			Assertions.assertEquals(0, picture.size());
			
			DataBaseAccess.removePostsByUser("clara@email.com");
		}

		
		@Test
		public void Prueba32() {
			PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
			PO_LoginView.fillForm(driver, "admin@email.com", "admin");
		
			// Nos redirige a la vista con la lista
			
			// Guardamos el email del primer usuario
			String email = driver.findElement( By.xpath("//*[@id=\"tableUsers\"]/tbody/tr[1]/td[1]") ).getText(); 
			
			// Seleccionamos el primer usuario y lo borramos
			WebElement checkbox = driver.findElement( By.xpath("//*[@id=\"tableUsers\"]/tbody/tr[1]/td[6]/input") );
			checkbox.click();
			
			WebElement btnBorrar = driver.findElement( By.xpath("//*[@id=\"btnDeleteUser\"]") );
			btnBorrar.click();
			
			// Comprobamos que el email que figura es distinto
			Assert.assertNotEquals(email, driver.findElement( By.xpath("//*[@id=\"tableUsers\"]/tbody/tr[1]/td[1]") ).getText());
		}
		
		@Test
		public void Prueba33() {
			PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
			PO_LoginView.fillForm(driver, "admin@email.com", "admin");
		
			// Nos redirige a la vista con la lista
			
			//Navegamos hasta la última página
			boolean isNextPage = false;
			do {
				
				// Check If There is next page
				List<WebElement> nextPageLink = driver.findElements(By.xpath("//*[@id=\"nextPageOfList\"]/a"));
				if ( !nextPageLink.isEmpty() ) {
					nextPageLink.get(0).click();
					isNextPage = true;
				} else {
					isNextPage = false;
				}
				
			} while (isNextPage);
			
			// Guardamos el email del último usuario
			List<WebElement> users = driver.findElements(By.xpath("//*[@id=\"tableUsers\"]/tbody/tr"));
			String email = users.get( users.size()-1 ).findElement( By.xpath("//td[1]") ).getText();
			
			// Seleccionamos el último usuario y lo borramos
			WebElement checkbox = users.get( users.size()-1 ).findElement( By.xpath("//td[6]/input") );
			checkbox.click();
			
			WebElement btnBorrar = driver.findElement( By.xpath("//*[@id=\"btnDeleteUser\"]") );
			btnBorrar.click();
			
			// Comprobamos que el email que figura es distinto
			
			isNextPage = false;
			do {
				
				// Check If There is next page
				List<WebElement> nextPageLink = driver.findElements(By.xpath("//*[@id=\"nextPageOfList\"]/a"));
				if ( !nextPageLink.isEmpty() ) {
					nextPageLink.get(0).click();
					isNextPage = true;
				} else {
					isNextPage = false;
				}
				
			} while (isNextPage);
			
			users = driver.findElements(By.xpath("//*[@id=\"tableUsers\"]/tbody/tr"));
			Assert.assertNotEquals(email, users.get( users.size()-1 ).findElement( By.xpath("//td[1]") ).getText());
		}
		
		@Test
		public void Prueba34() {
			PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
			PO_LoginView.fillForm(driver, "admin@email.com", "admin");
		
			// Nos redirige a la vista con la lista
			
			// Guardamos los 3 emails
			String[] emails = new String[3];
			emails[0] = driver.findElement( By.xpath("//*[@id=\"tableUsers\"]/tbody/tr[1]/td[1]") ).getText();
			emails[1] = driver.findElement( By.xpath("//*[@id=\"tableUsers\"]/tbody/tr[2]/td[1]") ).getText();
			emails[2] = driver.findElement( By.xpath("//*[@id=\"tableUsers\"]/tbody/tr[3]/td[1]") ).getText();
			
			// Seleccionamos los usuarios y los borramos
			WebElement checkbox = driver.findElement( By.xpath("//*[@id=\"tableUsers\"]/tbody/tr[1]/td[6]/input") );
			checkbox.click();
			checkbox = driver.findElement( By.xpath("//*[@id=\"tableUsers\"]/tbody/tr[2]/td[6]/input") );
			checkbox.click();
			checkbox = driver.findElement( By.xpath("//*[@id=\"tableUsers\"]/tbody/tr[3]/td[6]/input") );
			checkbox.click();
			
			WebElement btnBorrar = driver.findElement( By.xpath("//*[@id=\"btnDeleteUser\"]") );
			btnBorrar.click();
			
			// Comprobamos que el email que figura es distinto
			Assert.assertNotEquals(emails[0], driver.findElement( By.xpath("//*[@id=\"tableUsers\"]/tbody/tr[1]/td[1]") ).getText());
			Assert.assertNotEquals(emails[1], driver.findElement( By.xpath("//*[@id=\"tableUsers\"]/tbody/tr[1]/td[1]") ).getText());
			Assert.assertNotEquals(emails[2], driver.findElement( By.xpath("//*[@id=\"tableUsers\"]/tbody/tr[1]/td[1]") ).getText());
			
			/*
			 * Buscamos en la primera, para asegurarnos de que el 2 no haya sustituido al 1 (que hay habido un shift en las posiciones)
			 */
		}
}
