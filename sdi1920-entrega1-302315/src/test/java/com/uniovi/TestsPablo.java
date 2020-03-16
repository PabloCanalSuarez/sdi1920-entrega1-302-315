package com.uniovi;

import static org.junit.Assert.assertTrue;

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
import com.uniovi.tests.pageobjects.PO_AddPostView;
import com.uniovi.tests.pageobjects.PO_FriendsUser;
import com.uniovi.tests.pageobjects.PO_HomeView;
import com.uniovi.tests.pageobjects.PO_ListUserView;
import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_NavView;
import com.uniovi.tests.pageobjects.PO_PostView;
import com.uniovi.tests.pageobjects.PO_Properties;
import com.uniovi.tests.pageobjects.PO_View;
import com.uniovi.tests.util.DataBaseAccess;
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
	@Test
	public void Prueba16() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "diego@email.com", "123456");
		
		SeleniumUtils.textoPresentePagina(driver, "no puedes enviar otra petición");
	}
	
	// Mostrar el listado de invitaciones de amistad recibidas. 
	// Comprobar con un listado que contenga varias invitaciones recibidas
	@Test
	public void Prueba17() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "clara@email.com", "123456");
		
		List<WebElement> elementos = PO_HomeView.checkElement(driver, "free", "//*[@id=\"myNavbar\"]/ul[1]/li[3]/a");
		elementos.get(0).click();
		
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr",
				PO_View.getTimeout());
		assertTrue(elementos.size() == 5);
	}
	
	// Sobre el listado de invitaciones recibidas. Hacer click en el botón/enlace de una de ellas y
	// comprobar que dicha solicitud desaparece del listado de invitaciones.
	@Test
	public void Prueba18() {		
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "clara@email.com", "123456");
		
		List<WebElement> elementos = PO_HomeView.checkElement(driver, "free", "//*[@id=\"myNavbar\"]/ul[1]/li[3]/a");
		elementos.get(0).click();
		
		elementos = PO_HomeView.checkElement(driver, "free", "//td[contains(text(), 'Diego')]/following-sibling::*/a[contains(@href, 'invitation/accept/')]");
		elementos.get(0).click();
		
		SeleniumUtils.textoNoPresentePagina(driver, "Diego");
	}

	// Visualizar al menos cuatro páginas en Español/Inglés/Español (comprobando que
	// algunas
	// de las etiquetas cambian al idioma correspondiente). Ejemplo, Página
	// principal/Opciones Principales de
	// Usuario/Listado de Usuarios.
	@Test  
	public void Prueba20() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "clara@email.com", "123456");
		
		// Check en /home
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//*[@id=\"myNavbar\"]/ul[1]/li[1]/a");
		elementos.get(0).click();
		PO_HomeView.checkChangeIdiom(driver, "btnSpanish", "btnEnglish", PO_Properties.getSPANISH(), PO_Properties.getENGLISH());
		
		// Check en /user/list
		elementos = PO_View.checkElement(driver, "id", "users-menu");
		elementos.get(0).click();
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href,'user/list')]");
		elementos.get(0).click();
		
		PO_ListUserView.checkChangeIdiom(driver, "btnSpanish", "btnEnglish", PO_Properties.getSPANISH(), PO_Properties.getENGLISH());
		
		// Check en /user/friends
		elementos = PO_View.checkElement(driver, "id", "users-menu");
		elementos.get(0).click();
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href,'user/friends')]");
		elementos.get(0).click();
		
		PO_FriendsUser.checkChangeIdiom(driver, "btnSpanish", "btnEnglish", PO_Properties.getSPANISH(), PO_Properties.getENGLISH());
		
		// Check en /post/add
		elementos = PO_View.checkElement(driver, "id", "posts-menu");
		elementos.get(0).click();
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href,'post/add')]");
		elementos.get(0).click();
		
		PO_AddPostView.checkChangeIdiom(driver, "btnSpanish", "btnEnglish", PO_Properties.getSPANISH(), PO_Properties.getENGLISH());
	}

	// Ir al formulario crear publicaciones, rellenarla con datos válidos y pulsar
	// el botón Submit.
	// Comprobar que la publicación sale en el listado de publicaciones de dicho
	// usuario.
	@Test
	public void Prueba24() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "clara@email.com", "123456");

		// Vamos a crear un post
		List<WebElement> elementos = PO_View.checkElement(driver, "id", "posts-menu");
		elementos.get(0).click();
		elementos = PO_HomeView.checkElement(driver, "free", "//a[contains(@href,'post/add')]");
		elementos.get(0).click();

		// Rellenamos los datos
		PO_PostView.fillForm(driver, "Título 1", "Aquí se escriben los contenidos.");

		// Comprobamos que se ha creado (desde la vista post/list)
		PO_View.checkElement(driver, "text", "Título 1");
	}

	// Ir al formulario de crear publicaciones, rellenarla con datos inválidos
	// (campo título vacío) y pulsar el botón Submit.
	// Comprobar que se muestra el mensaje de campo obligatorio.
	@Test
	public void Prueba25() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "clara@email.com", "123456");

		// Vamos a crear un post
		List<WebElement> elementos = PO_View.checkElement(driver, "id", "posts-menu");
		elementos.get(0).click();
		elementos = PO_HomeView.checkElement(driver, "free", "//a[contains(@href,'post/add')]");
		elementos.get(0).click();

		// Rellenamos los datos
		PO_PostView.fillForm(driver, "", "Aquí se escriben los contenidos.");

		// Comprobamos que seguimos en el formulario
		PO_View.checkElement(driver, "id", "postForm");
		PO_View.checkElement(driver, "text", "Por favor, rellene este campo");

		// Rellenamos los datos
		PO_PostView.fillForm(driver, "Título 1", "");

		// Comprobamos que seguimos en el formulario
		PO_View.checkElement(driver, "id", "postForm");
		PO_View.checkElement(driver, "text", "Por favor, rellene este campo");
	}

	// Mostrar el listado de publicaciones de un usuario y comprobar que se muestran
	// todas las que existen para dicho usuario.
	@Test
	public void Prueba26() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "enrique@email.com", "123456");

		// Vamos a crear un post
		List<WebElement> elementos = PO_View.checkElement(driver, "id", "posts-menu");
		elementos.get(0).click();
		elementos = PO_HomeView.checkElement(driver, "free", "//a[contains(@href,'post/add')]");
		elementos.get(0).click();

		// Rellenamos los datos
		PO_PostView.fillForm(driver, "Título 1", "Aquí se escriben los contenidos.");

		// Comprobamos que se ha creado (desde la vista post/list)
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr",PO_View.getTimeout());
		assertTrue(elementos.size() == 1);
		
	}
	
	// Mostrar el listado de usuarios y comprobar que se muestran todos los que existen en el sistema.
	@Test
	public void Prueba31() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "admin@email.com", "admin");
		
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

		int numberUsers = DataBaseAccess.listUsers().stream().toArray().length - 1; // -1 porque el usuario admin@email.com no se cuenta
		Assertions.assertEquals(numberUsers, usersCount);
		
		
	}

}
