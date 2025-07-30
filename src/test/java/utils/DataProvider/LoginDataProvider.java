package utils.DataProvider;

import org.testng.annotations.DataProvider;
import utils.JsonDataReader;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class LoginDataProvider {
    @DataProvider(name = "getValidData")
    public Object[][] getValidData() throws IOException {
        List<HashMap<String, String>> data = JsonDataReader.getStringJsonData(
                System.getProperty("user.dir") + JsonDataReader.getJsonPath("loginData"));

        return data.stream()
                .filter(entry -> entry.get("expectedMessage").isEmpty())
                .map(entry -> new Object[]{entry})
                .toArray(Object[][]::new);
    }

    @DataProvider(name = "getInvalidData")
    public Object[][] getInvalidData() throws IOException {
        List<HashMap<String, String>> data = JsonDataReader.getStringJsonData(
                System.getProperty("user.dir") + JsonDataReader.getJsonPath("loginData"));

        return data.stream()
                .filter(entry -> !entry.get("expectedMessage").isEmpty())
                .map(entry -> new Object[]{entry})
                .toArray(Object[][]::new);
    }
}
