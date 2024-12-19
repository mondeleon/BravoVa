package shopping;

import com.superbravo.models.Cart;
import com.superbravo.models.Product;
import com.superbravo.pages.AppPage;
import io.qameta.allure.*;
import io.qameta.allure.model.Status;
import listener.CloseAndOpenListener;
import listener.ExecutionListener;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Severity(SeverityLevel.CRITICAL)
@Owner("Ramón Elias")
@Listeners(value = {ExecutionListener.class})
public class ShoppingCartTest {

    public AppPage pages;
    public Cart cart = new Cart();

    @BeforeClass
    @Step("Inicio de seisón")
    void setUp(){
        pages = AppPage.getInstance();
        Allure.getLifecycle().updateFixture( fixtureResult -> fixtureResult.setName("Preconditions"));
        pages.loginPage.logIn("FDF252366Y", "Unodostres4");
        pages.homePage.waitForPagePresent();
        Allure.addByteAttachmentAsync("Página de inicio", "image/png", () -> pages.takeScreenshot());
    }

    @Test
    @Description("Agregar producto (verduras) al carrito de compras, filtrando productos y entrando al detalle")
    void searchProductsAndAddToCart1(){
        Allure.getLifecycle().updateTestCase(result -> result.setName("Agregar producto (verduras) al carrito"
                    + " mediante el detalle de producto"));
        Product product = new Product("Ajo Importado Paq", 77.0, 4);
        cart.addProduct(product);
        searchProduct(product.name());
        goToProductDetails(product.name());
        addProuctQuantity(product.quantity());
        validateTotalPrize();
        addToCart(cart.totalItems());
        verifyShoppingCart(product);
    }

    @Test
    @Description("Agregar producto (enlatados) al carrito de compras, filtrando productos y entrando al detalle")
    void searchProductsAndAddToCart2(){
        Allure.getLifecycle().updateTestCase(result -> result.setName("Agregar producto (enlatados) al carrito"
                + " mediante el detalle de producto"));
        Product product = new Product("Celorrio Pimiento Entero Y Trozos 185 G", 89.0, 4);
        cart.addProduct(product);
        searchProduct(product.name());
        goToProductDetails(product.name());
        addProuctQuantity(product.quantity());
        validateTotalPrize();
        addToCart(cart.totalItems());
        verifyShoppingCart(product);
    }

    @Test
    @Description("Agregar producto (bodega) al carrito de compras, filtrando productos y entrando al detalle")
    void searchProductsAndAddToCart3(){
        Allure.getLifecycle().updateTestCase(result -> result.setName("Agregar producto (bodega) al carrito"
                + " mediante el detalle de producto"));
        Product product = new Product("La Viña Vino Blanco 750 Ml", 599.0, 1);
        cart.addProduct(product);
        searchProduct(product.name());
        goToProductDetails(product.name());
        addProuctQuantity(product.quantity());
        validateTotalPrize();
        addToCart(cart.totalItems());
        verifyShoppingCart(product);
    }

    @Step("Buscar producto")
    void searchProduct(String product){
        pages.homePage.searchProduct(product);
        pages.basePage.awaitLoading(5);
        Allure.addByteAttachmentAsync("Busqueda del producto", "image/png", () -> pages.takeScreenshot());
    }

    @Step("Entrar al detalle del producto")
    void goToProductDetails(String product){
        pages.homePage.goToProductDetails(product);
        pages.awaitProcessing(2);
        Allure.addByteAttachmentAsync("Detalles del producto: " + product, "image/png", () -> pages.takeScreenshot());
    }

    @Step("Agregar cantidad de productos")
    void addProuctQuantity(int quantity){
        pages.productPage.tapQuantity();
        Allure.addByteAttachmentAsync("Cantidad del producto" , "image/png", () -> pages.takeScreenshot());
        pages.productPage.setQuantity(quantity);
        Allure.addByteAttachmentAsync("Cantidad del producto modificada" , "image/png", () -> pages.takeScreenshot());
        pages.productPage.tapAccept();
        pages.basePage.awaitLoading(5);
    }

    @Step("Validar cálculo del total")
    void validateTotalPrize(){
        double expectedTotal = pages.productPage.getExpectedTotal();
        double total = pages.productPage.getTotal();
        boolean isTotalCorrect = total == expectedTotal;

        String validation = "Total esperado: " + expectedTotal + "\nTotal recibido: " + total;

        if (!isTotalCorrect){
            Allure.getLifecycle().updateStep(step -> step.setStatus(Status.FAILED));
        }

        pages.productPage.waitForPagePresent();
        Allure.addByteAttachmentAsync("Detalles del producto" , "image/png", () -> pages.takeScreenshot());
        Allure.addAttachment("Resultado", validation);
    }

    @Step("Agregar al carrito")
    void addToCart(int totalItems) {
        pages.productPage.addToCart();
        pages.awaitProcessing(5);
        Allure.addByteAttachmentAsync("Carrito con productos" , "image/png", () -> pages.takeScreenshot());

        int totalArticles = pages.homePage.getNumberOfArticles();
        if (totalArticles != totalItems){
            Allure.getLifecycle().updateStep(step -> step.setStatus(Status.FAILED));
        }
        Allure.addAttachment("Resultado", "Número de articulos agregados: " + totalArticles);
    }

    @Step("Verificar carrito")
    void verifyShoppingCart(Product product){
        pages.homePage.goToCart();
        Allure.addByteAttachmentAsync("Carrito" , "image/png", () -> pages.takeScreenshot());
        Cart cartRetrieved = pages.cartPage.getCart();

        boolean areCartsEquals = cart.equals(cartRetrieved);
        boolean areTotalEquals = pages.cartPage.getTotal() == cart.totalAmount();
        if (!areCartsEquals || !areTotalEquals){
            Allure.getLifecycle().updateStep(step -> step.setStatus(Status.FAILED));
        }
        String validation = "Carito esperado:\n" + cart + "\n\nCarrito recibido:\n" + cartRetrieved;
        Allure.addAttachment("Validar correcto agregado de productos al carrito", validation);
        pages.cartPage.deleteProduct(0);
        pages.basePage.awaitLoading(5);
        pages.basePage.goBack();
        cart.clean();
    }
}
