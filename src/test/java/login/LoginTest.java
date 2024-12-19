package login;

import com.superbravo.pages.AppPage;
import io.qameta.allure.*;
import io.qameta.allure.model.Status;
import listener.CloseAndOpenListener;
import listener.ExecutionListener;
import org.testng.annotations.*;

@Severity(SeverityLevel.CRITICAL)
@Owner("Ramón Elias")
@Listeners(value = {ExecutionListener.class, CloseAndOpenListener.class})
public class LoginTest {

    public AppPage pages;

    @BeforeClass
    @Step("Abrir la aplicación")
    void setUp(){
        pages = AppPage.getInstance();
    }

    @Test(priority = 3)
    @Description("Inicio de sesión con credenciales correctas.")
    void logIn() {
        Allure.getLifecycle().updateTestCase(result -> result.setName("Log in credenciales correctas"));
        goToLogIn();
        setUser("FDF252366Y");
        setPassword("Unodostres4");
        skipBiometry();
        validateCorrectLogin();
    }

    @Test(priority = 4)
    @Description("Inicio de sesión con el usuario ya vinculado y credenciales correctas.")
    void logInUserLinked() {
        Allure.getLifecycle().updateTestCase(result -> result.setName("Log in usuario vinculado y credenciales correctas"));
        setPassword("Unodostres4");
        validateCorrectLogin();
    }

    @Test(priority = 1)
    @Description("Inicio de sesión con usuario incorrecto.")
    void logInIncorrectUser() {
        Allure.getLifecycle().updateTestCase(result -> result.setName("Log in usuario incorrecto"));
        goToLogIn();
        setUser("qaautomation");
        setPassword("Unodostres4");
        validateIncorrectLogin();
    }

    @Test(priority = 2)
    @Description("Inicio de sesión con contraseña incorrecta.")
    void logInIncorrectPassword() {
        Allure.getLifecycle().updateTestCase(result -> result.setName("Log in contraseña incorrecta"));
        goToLogIn();
        setUser("FDF252366Y");
        setPassword("1234567");
        validateIncorrectLogin();
    }


    @Step("Pantalla de bienvenida")
    public void goToLogIn(){
        pages.loginPage.waitForPagePresent();
        Allure.addByteAttachmentAsync("¡Bienvenido a BravoVa!", "image/png", () -> pages.takeScreenshot());
        if (!pages.loginPage.isUserRegistered()){
            pages.loginPage.tapLogin();
        }
    }

    @Step("Colocar usuario")
    public void setUser(String user){
        if (!pages.loginPage.isUserLogged()){
            pages.loginPage.setUser(user);
        }
    }

    @Step("Colocar contraseña")
    public void setPassword(String password){
        pages.loginPage.setPassword(password);
        Allure.addByteAttachmentAsync("Pantalla de inicio de sesión", "image/png", () -> pages.takeScreenshot());
        pages.loginPage.tapLogin();
        pages.awaitProcessing(3);
    }

    @Step("Saltar biómetría")
    public void skipBiometry() {
        Allure.addByteAttachmentAsync("Acceso biómetrico", "image/png", () -> pages.takeScreenshot());
        pages.loginPage.skipBiometry();
        pages.awaitProcessing(3);
    }

    @Step("Validar correcto inicio de sesión")
    public void validateCorrectLogin() {
        Allure.addByteAttachmentAsync("Home page", "image/png", () -> pages.takeScreenshot());
        boolean isHomePagePresent = pages.homePage.isPagePresent();
        if (!isHomePagePresent){
            Allure.getLifecycle().updateStep(stepResult -> stepResult.setStatus(Status.FAILED));
        }
    }

    @Step("Validar incorrecto inicio de sesión")
    public void validateIncorrectLogin() {
        Allure.addByteAttachmentAsync("Usuario o contraseña incorrecta", "image/png", () -> pages.takeScreenshot());
        boolean isHomePagePresent = pages.homePage.isPagePresent();
        if (isHomePagePresent){
            Allure.getLifecycle().updateStep(stepResult -> stepResult.setStatus(Status.FAILED));
        }
    }


}
