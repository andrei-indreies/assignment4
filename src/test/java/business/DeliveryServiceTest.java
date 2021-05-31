package business;

import com.foodmanagement.business.impl.DeliveryService;
import com.foodmanagement.business.impl.UserService;
import com.foodmanagement.business.model.criteria.ReportCriteria;
import com.foodmanagement.business.model.criteria.SearchCriteria;
import com.foodmanagement.business.model.menu.BaseProduct;
import com.foodmanagement.business.model.menu.MenuItem;
import com.foodmanagement.business.model.menu.Order;
import com.foodmanagement.business.model.user.Role;
import com.foodmanagement.business.model.user.User;
import com.foodmanagement.data.Serializator;
import com.foodmanagement.presentation.EmployeeUI;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.*;

public class DeliveryServiceTest {

    @Before
    public void init() {
        DeliveryService.menuMap = new HashMap<>();
        DeliveryService.idToNameMap = new HashMap<>();
        DeliveryService.orderListMap = new HashMap<>();
    }

    @Test
    public void Should_ImportProductsFromFile() {
        // Given
        DeliveryService deliveryService = new DeliveryService();

        // When
        deliveryService.importProducts("products.csv");

        // Then
        assertEquals(10, DeliveryService.menuMap.size());
        assertEquals(10, DeliveryService.idToNameMap.size());
    }

    @Test
    public void Should_CreateProduct() {
        // Given
        DeliveryService deliveryService = new DeliveryService();
        BaseProduct baseProduct = BaseProduct.builder()
                .title("aaaa")
                .price(10.2)
                .calories(100)
                .proteins(50)
                .fats(60)
                .rating(10)
                .fats(40)
                .build();

        // When
        deliveryService.createProduct(baseProduct);

        // Then
        assertNotNull(DeliveryService.menuMap.get("aaaa"));
    }

    @Test
    public void Should_DeleteProduct() {
        // Given
        DeliveryService deliveryService = new DeliveryService();

        BaseProduct baseProduct = BaseProduct.builder()
                .title("aaaa")
                .price(10.2)
                .calories(100)
                .proteins(50)
                .fats(60)
                .rating(10)
                .fats(40)
                .build();

        MenuItem item = deliveryService.createProduct(baseProduct);

        // When
        deliveryService.deleteProduct(item.getId());

        // Then
        assertNull(DeliveryService.menuMap.get("aaaa"));
        assertNull(DeliveryService.idToNameMap.get(item.getId()));
    }

    @Test
    public void Should_UpdateProduct() {
        // Given
        DeliveryService deliveryService = new DeliveryService();
        BaseProduct baseProduct = BaseProduct.builder()
                .title("aaaa")
                .price(10.2)
                .calories(100)
                .proteins(50)
                .fats(60)
                .rating(10)
                .build();

        MenuItem item = deliveryService.createProduct(baseProduct);

        BaseProduct newProduct = BaseProduct.builder()
                .id(item.getId())
                .title("aaaa")
                .price(11)
                .calories(150)
                .proteins(70)
                .fats(60)
                .rating(8)
                .build();

        // When
        deliveryService.updateProduct(newProduct);

        // Then
        assertEquals(newProduct.getPrice(), DeliveryService.menuMap.get("aaaa").getPrice(), 0);
        assertEquals(newProduct.getCalories(), ((BaseProduct) DeliveryService.menuMap.get("aaaa")).getCalories());
        assertEquals(newProduct.getProteins(), ((BaseProduct) DeliveryService.menuMap.get("aaaa")).getProteins());
        assertEquals(newProduct.getFats(), ((BaseProduct) DeliveryService.menuMap.get("aaaa")).getFats());
        assertEquals(newProduct.getRating(), ((BaseProduct) DeliveryService.menuMap.get("aaaa")).getRating(), 0);
    }

    @Test
    public void Should_GenerateIntervalReport() {
        // Given
        DeliveryService deliveryService = new DeliveryService();
        BaseProduct baseProduct = BaseProduct.builder()
                .title("aaaa")
                .price(10.2)
                .calories(100)
                .proteins(50)
                .fats(60)
                .rating(10)
                .build();

        deliveryService.createProduct(baseProduct);
        UUID clientId = UUID.randomUUID();

        deliveryService.createOrder(clientId, Collections.singletonList(baseProduct));

        ReportCriteria reportCriteria = ReportCriteria.builder()
                .startHour(1)
                .endHour(23)
                .build();

        // When
        deliveryService.generateReport(reportCriteria);
    }

    @Test
    public void Should_GenerateFrequencyReport() {
        // Given
        DeliveryService deliveryService = new DeliveryService();
        BaseProduct baseProduct = BaseProduct.builder()
                .title("aaaa")
                .price(10.2)
                .calories(100)
                .proteins(50)
                .fats(60)
                .rating(10)
                .build();
        BaseProduct baseProduct1 = BaseProduct.builder()
                .title("bbb")
                .price(10.2)
                .calories(100)
                .proteins(50)
                .fats(60)
                .rating(10)
                .build();

        deliveryService.createProduct(baseProduct);
        UUID clientId = UUID.randomUUID();

        deliveryService.createOrder(clientId, Collections.singletonList(baseProduct));
        deliveryService.createOrder(clientId, Arrays.asList(baseProduct, baseProduct1));

        ReportCriteria reportCriteria = ReportCriteria.builder()
                .numberOfTimes(2)
                .build();

        // When
        deliveryService.generateReport(reportCriteria);
    }

    @Test
    public void Should_GenerateClientsReport() {
        // Given
        DeliveryService deliveryService = new DeliveryService();
        UserService userService = new UserService();
        BaseProduct baseProduct = BaseProduct.builder()
                .title("aaaa")
                .price(10.2)
                .calories(100)
                .proteins(50)
                .fats(60)
                .rating(10)
                .build();
        BaseProduct baseProduct1 = BaseProduct.builder()
                .title("bbb")
                .price(10.2)
                .calories(100)
                .proteins(50)
                .fats(60)
                .rating(10)
                .build();

        deliveryService.createProduct(baseProduct);

        User user = userService.registerUser("client", "client", Role.CLIENT);

        deliveryService.createOrder(user.getId(), Collections.singletonList(baseProduct));
        deliveryService.createOrder(user.getId(), Arrays.asList(baseProduct, baseProduct1));

        ReportCriteria reportCriteria = ReportCriteria.builder()
                .clientOrdersNumber(2)
                .orderAmount(10)
                .build();

        // When
        deliveryService.generateReport(reportCriteria);
    }


    @Test
    public void Should_GenerateSpecificDayReport() {
        // Given
        DeliveryService deliveryService = new DeliveryService();
        UserService userService = new UserService();
        BaseProduct baseProduct = BaseProduct.builder()
                .title("aaaa")
                .price(10.2)
                .calories(100)
                .proteins(50)
                .fats(60)
                .rating(10)
                .build();
        BaseProduct baseProduct1 = BaseProduct.builder()
                .title("bbb")
                .price(10.2)
                .calories(100)
                .proteins(50)
                .fats(60)
                .rating(10)
                .build();

        deliveryService.createProduct(baseProduct);

        User user = userService.registerUser("client", "client", Role.CLIENT);

        deliveryService.createOrder(user.getId(), Collections.singletonList(baseProduct));
        deliveryService.createOrder(user.getId(), Arrays.asList(baseProduct, baseProduct1));

        ReportCriteria reportCriteria = ReportCriteria.builder()
                .orderDate(LocalDateTime.now())
                .build();

        // When
        deliveryService.generateReport(reportCriteria);
    }

    @Test
    public void Should_RetrieveProducts() {
        // Given
        DeliveryService deliveryService = new DeliveryService();
        BaseProduct baseProduct = BaseProduct.builder()
                .title("aaaa")
                .price(10.2)
                .calories(100)
                .proteins(50)
                .fats(60)
                .rating(10)
                .build();
        BaseProduct baseProduct1 = BaseProduct.builder()
                .title("bbb")
                .price(10.2)
                .calories(100)
                .proteins(50)
                .fats(60)
                .rating(10)
                .build();

        deliveryService.createProduct(baseProduct);
        deliveryService.createProduct(baseProduct1);

        // When
        Map<String, MenuItem> menuItemMap = deliveryService.getProducts();

        // Then
        assertEquals(2, menuItemMap.size());
    }

    @Test
    public void Should_RetrieveFilteredProductsByTitle() {
        // Given
        DeliveryService deliveryService = new DeliveryService();
        BaseProduct baseProduct = BaseProduct.builder()
                .title("aaaa")
                .price(10.2)
                .calories(100)
                .proteins(50)
                .fats(60)
                .rating(10)
                .build();
        BaseProduct baseProduct1 = BaseProduct.builder()
                .title("bbb")
                .price(10.2)
                .calories(100)
                .proteins(50)
                .fats(60)
                .rating(10)
                .build();

        deliveryService.createProduct(baseProduct);
        deliveryService.createProduct(baseProduct1);

        SearchCriteria searchCriteria = SearchCriteria.builder()
                .keyword("bbb")
                .build();

        // When
        List<MenuItem> filteredProducts = deliveryService.getFilteredProducts(searchCriteria);

        // Then
        assertEquals(1, filteredProducts.size());
    }

    @Test
    public void Should_RetrieveFilteredProductsByCalories() {
        // Given
        DeliveryService deliveryService = new DeliveryService();
        BaseProduct baseProduct = BaseProduct.builder()
                .title("aaaa")
                .price(10.2)
                .calories(100)
                .proteins(50)
                .fats(60)
                .rating(10)
                .build();
        BaseProduct baseProduct1 = BaseProduct.builder()
                .title("bbb")
                .price(10.2)
                .calories(100)
                .proteins(50)
                .fats(60)
                .rating(10)
                .build();

        deliveryService.createProduct(baseProduct);
        deliveryService.createProduct(baseProduct1);

        SearchCriteria searchCriteria = SearchCriteria.builder()
                .calories(100)
                .build();

        // When
        List<MenuItem> filteredProducts = deliveryService.getFilteredProducts(searchCriteria);

        // Then
        assertEquals(2, filteredProducts.size());
    }

    @Test
    public void Should_RetrieveFilteredProductsByCaloriesAndTitle() {
        // Given
        DeliveryService deliveryService = new DeliveryService();
        BaseProduct baseProduct = BaseProduct.builder()
                .title("aaaa")
                .price(10.2)
                .calories(100)
                .proteins(50)
                .fats(60)
                .rating(10)
                .build();
        BaseProduct baseProduct1 = BaseProduct.builder()
                .title("bbb")
                .price(10.2)
                .calories(100)
                .proteins(50)
                .fats(60)
                .rating(10)
                .build();

        deliveryService.createProduct(baseProduct);
        deliveryService.createProduct(baseProduct1);

        SearchCriteria searchCriteria = SearchCriteria.builder()
                .calories(100)
                .keyword("bbb")
                .build();

        // When
        List<MenuItem> filteredProducts = deliveryService.getFilteredProducts(searchCriteria);

        // Then
        assertEquals(1, filteredProducts.size());
    }

    @Test
    public void Should_CreateOrder() {
        // Given
        DeliveryService deliveryService = new DeliveryService();
        new EmployeeUI(deliveryService.getSubject());

        BaseProduct baseProduct = BaseProduct.builder()
                .title("aaaa")
                .price(10.2)
                .calories(100)
                .proteins(50)
                .fats(60)
                .rating(10)
                .build();

        deliveryService.createProduct(baseProduct);
        UUID clientId = UUID.randomUUID();

        // When
        Order order = deliveryService.createOrder(clientId, Collections.singletonList(baseProduct));

        // Then
        assertEquals(clientId, order.getClientId());
    }
}
