package utils.DataProvider;

import org.testng.annotations.DataProvider;
import utils.JsonDataReader;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class ContactDataProvider {
    @DataProvider(name = "getContactData")
    public Object[][] getContactData() throws IOException {
        List<HashMap<String, String>> data = JsonDataReader.getStringJsonData(
                System.getProperty("user.dir") + JsonDataReader.getJsonPath("contactData"));

        return data.stream()
                .map(entry -> new Object[]{entry})
                .toArray(Object[][]::new);
    }
}
