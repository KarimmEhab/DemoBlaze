package utils.dataProviders;

import org.testng.annotations.DataProvider;
import utils.JsonDataReader;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class e2eDataProvider {
    @DataProvider(name = "getOrderData")
    public static Object[][] getOrderData() throws IOException {
        List<HashMap<String, Object>> data = JsonDataReader.getObjectJsonData(
                System.getProperty("user.dir") + JsonDataReader.getJsonPath("e2eData"));
        return data.stream().map(entry -> new Object[]{entry}).toArray(Object[][]::new);
    }
}
