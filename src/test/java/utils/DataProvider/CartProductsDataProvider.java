package utils.DataProvider;

import org.testng.annotations.DataProvider;
import utils.JsonDataReader;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class CartProductsDataProvider {
    @DataProvider(name = "getCartProducts")
    public Object[][] getCartProducts() throws IOException {
        List<HashMap<String, Object>> data = JsonDataReader.getObjectJsonData(
                System.getProperty("user.dir") + JsonDataReader.getJsonPath("cartProductsData"));

        return data.stream()
                .map(entry -> new Object[]{entry})
                .toArray(Object[][]::new);
    }
}
