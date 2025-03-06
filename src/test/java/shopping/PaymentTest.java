package shopping;

import com.superbravo.models.Cart;
import com.superbravo.models.Product;
import com.superbravo.pages.AppPage;
import io.qameta.allure.*;
import io.qameta.allure.model.Status;
import listener.ExecutionListener;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import test.AllureUtils;

import java.util.Optional;

@Severity(SeverityLevel.CRITICAL)
@Owner("Ramón Elias")
@Listeners(value = {ExecutionListener.class})
public class PaymentTest {

    public AppPage pages;
    public Cart cart = new Cart();

    @BeforeClass
    @Step("Inicio de seisón")
    void setUp(){
        pages = AppPage.getInstance();
        Allure.getLifecycle().updateFixture(fixtureResult -> fixtureResult.setName("Preconditions"));
        pages.loginPage.logIn("FDF252366Y", "Unodostres4");
        pages.homePage.waitForPagePresent();
        Allure.addByteAttachmentAsync("Página de inicio", "image/png", () -> pages.takeScreenshot());
    }

    @Test
    @Description("Crear pedido de producto (bodega) al carrito de compras, filtrando productos y entrando al detalle")
    void makePayment(){
        Allure.getLifecycle().updateTestCase(result -> result.setName("Agregar producto (bodega) al carrito"
                + " mediante el detalle de producto"));
        Product product = new Product("La Viña Vino Blanco 750 Ml", 649, 2);
        cart.addProduct(product);

        searchProduct(product.name());
        goToProductDetails(product.name());
        addProuctQuantity(product.quantity());
        addToCart(cart.totalItems());
        goToShoppingCart();
        verifyOrderConfirmation();
        verifyOrderMade();
    }

    @Test
    @Description("Crear pedido de producto (Higiene y Salud) al carrito de compras, filtrando productos y entrando al detalle")
    void makePayment1(){
        Allure.getLifecycle().updateTestCase(result -> result.setName("Agregar producto (Higiene y Salud) al carrito"
                + " mediante el detalle de producto"));
        Product product = new Product("Rexona Deo Rol Xtra Cool 50 Ml", 114, 1);
        cart.addProduct(product);

        searchProduct(product.name());
        goToProductDetails(product.name());
        addProuctQuantity(product.quantity());
        addToCart(cart.totalItems());
        goToShoppingCart();
        verifyOrderConfirmation();
        verifyOrderMade();
    }

    @Test
    @Description("Crear pedido de producto (enlatado) al carrito de compras, filtrando productos y entrando al detalle")
    void makePayment2(){
        Allure.getLifecycle().updateTestCase(result -> result.setName("Agregar producto (enlatado) al carrito"
                + " mediante el detalle de producto"));
        Product product = new Product("Celorrio Pimiento Entero Y Trozos 185 G", 89, 5);
        cart.addProduct(product);

        searchProduct(product.name());
        goToProductDetails(product.name());
        addProuctQuantity(product.quantity());
        addToCart(cart.totalItems());
        goToShoppingCart();
        verifyOrderConfirmation();
        verifyOrderMade();
    }

    @Step("Buscar producto")
    void searchProduct(String product){
        pages.homePage.searchProduct(product);
        pages.awaitProcessing(50);
        Allure.addByteAttachmentAsync("Busqueda del producto", "image/png", () -> pages.takeScreenshot());
    }

    @Step("Agregar cantidad de productos")
    void addProuctQuantity(int quantity){
        pages.productPage.tapQuantity();
        Allure.addByteAttachmentAsync("Cantidad del producto" , "image/png", () -> pages.takeScreenshot());
        pages.delay(2000);
        pages.productPage.setQuantity(quantity);
        Allure.addByteAttachmentAsync("Cantidad del producto modificada" , "image/png", () -> pages.takeScreenshot());
        pages.productPage.tapAccept();
        pages.awaitProcessing(5);
    }

    @Step("Entrar al detalle del producto")
    void goToProductDetails(String product){
        pages.homePage.goToProductDetails(product);
        pages.awaitProcessing(2);
        Allure.addByteAttachmentAsync("Detalles del producto: " + product, "image/png", () -> pages.takeScreenshot());
    }

    @Step("Agregar al carrito")
    void addToCart(int totalItems) {
        pages.productPage.addToCart();
        pages.awaitProcessing(5);
        Allure.addByteAttachmentAsync("Carrito con productos" , "image/png", () -> pages.takeScreenshot());

        int totalArticles = pages.homePage.getNumberOfArticles();
        if (totalArticles != totalItems){
            Allure.getLifecycle().updateTestCase(testCase -> testCase.setStatus(Status.FAILED));
        }
        Allure.addAttachment("Resultado", "Número de articulos agregados: " + totalArticles);
    }

    @Step("Ir al carrito y confirmar pedido")
    void goToShoppingCart(){
        pages.homePage.goToCart();
        Allure.addByteAttachmentAsync("Carrito" , "image/png", () -> pages.takeScreenshot());
        pages.cartPage.confirmOrder();
        pages.awaitProcessing(5);
    }

    @Step("Verificar confirmación del pedido")
    void verifyOrderConfirmation() {

        pages.cartPage.confirmOrder();
        double subTotal = pages.confirmationPage.getSubTotal();

        if (subTotal != cart.totalAmount()){
            Allure.getLifecycle().updateStep(step -> step.setStatus(Status.FAILED));
        }
        String subTotalValidation = "Subtotal esperado:\n" + cart.totalAmount() + "\n\nSubtotal recibido:\n" + subTotal;
        Allure.addAttachment("Validar correcto subtotal", subTotalValidation);

        double expectedTotal = pages.confirmationPage.getExpectedTotal();
        double retrievedTotal = pages.confirmationPage.getTotal();

        Allure.addByteAttachmentAsync("Confirmación del pedido" , "image/png", () -> pages.takeScreenshot());
        String totalValidation = "Total esperado:\n" + expectedTotal + "\n\nTotal recibido:\n" + retrievedTotal;

        Allure.addAttachment("Validar correcto total", totalValidation);
//        if (retrievedTotal != expectedTotal){
//            pages.basePage.goBack(2);
//            AllureUtils.failTestCase();
//        }
        pages.confirmationPage.tapMakePayment();
        pages.awaitProcessing(5);
    }

    @Step("Verificar pedido realizado")
    void verifyOrderMade(){
        Allure.addByteAttachmentAsync("Pedido realizado" , "image/png", () -> pages.takeScreenshot());
        Allure.getLifecycle().getCurrentTestCaseOrStep();
        int articles = pages.confirmationPage.getArticles();

        if (articles != cart.totalItems()) {
            Allure.getLifecycle().updateStep(step -> step.setStatus(Status.FAILED));
            Allure.getLifecycle().updateTestCase(testResult -> testResult.setStatus(Status.FAILED));
            Allure.getLifecycle().updateFixture(fixtureResult -> fixtureResult.setStatus(Status.FAILED));
        }
        String articlesValidation = "Articulos esperado:\n" + articles + "\n\nArticulos recibido:\n" + cart.totalItems();
        Allure.addAttachment("Validar correcto valor de articulos", articlesValidation);

        pages.confirmationPage.tapContinue();
        pages.awaitProcessing(5);
    }



}
