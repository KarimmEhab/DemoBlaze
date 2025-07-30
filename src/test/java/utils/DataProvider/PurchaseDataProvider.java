package utils.DataProvider;

import org.testng.annotations.DataProvider;
import utils.JsonDataReader;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

public class PurchaseDataProvider {
    @DataProvider(name = "placeOrderData")
    public Object[][] getPurchaseData(Method method) throws IOException {
        List<HashMap<String, String>> data = JsonDataReader.getStringJsonData(
                System.getProperty("user.dir") + JsonDataReader.getJsonPath("purchaseData")
        );
        return switch (method.getName()) {
            case "testPlaceOrder", "placeOrderWithEmptyCart" -> new Object[][]{{data.get(0)}};
            case "PlaceOrderWithEmptyData" -> new Object[][]{{data.get(1)}};
            case "PlaceOrderWithInvalidData" -> new Object[][]{{data.get(2)}};
            default -> new Object[][]{{new HashMap<>()}};
        };
    }
}
