package utils.dataProviders;

import com.github.dockerjava.api.model.ContainerMount;
import org.testng.annotations.DataProvider;
import utils.JsonDataReader;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class DataProviders {
    @DataProvider(name = "getLoginData")
    public Object[][] getLoginData(Method method) throws IOException {
        List<HashMap<String, String>> data = JsonDataReader.getStringJsonData(
                System.getProperty("user.dir") + JsonDataReader.getJsonPath("loginData"));

        if (method.getName().equals("LoginSuccessTest") || method.getName().equals("Logout")) {
            // Login Success + Logout
            return data.stream()
                    .filter(entry -> entry.get("expectedMessage").isEmpty())
                    .map(entry -> new Object[]{entry})
                    .toArray(Object[][]::new);
        } else if (method.getName().equals("NegativeLoginTest")) {
            // Invalid Login
            return data.stream()
                    .filter(entry -> !entry.get("expectedMessage").isEmpty())
                    .map(entry -> new Object[]{entry})
                    .toArray(Object[][]::new);
        }
        return new Object[][]{};
    }

    @DataProvider(name = "getContactData")
    public Object[][] getContactData(Method method) throws IOException {
        List<HashMap<String, String>> data = JsonDataReader.getStringJsonData(
                System.getProperty("user.dir") + JsonDataReader.getJsonPath("contactData"));
        return switch (method.getName()) {
            case "validContactFormTest" -> new Object[][]{{data.get(0)}};
            case "arabicContactFormTest" -> new Object[][]{{data.get(1)}};
            case "emptyContactFormTest" -> new Object[][]{{data.get(2)}};
            default -> new Object[][]{{new HashMap<>()}};
        };
    }

    @DataProvider(name = "getCartProducts")
    public Object[][] getCartProducts() throws IOException {
        List<HashMap<String, Object>> data = JsonDataReader.getObjectJsonData(
                System.getProperty("user.dir") + JsonDataReader.getJsonPath("cartProductsData"));
        return data.stream()
                .map(entry -> new Object[]{entry})
                .toArray(Object[][]::new);
    }

    @DataProvider(name = "placeOrderData")
    public Object[][] getPurchaseData(Method method) throws IOException {
        List<HashMap<String, String>> data = JsonDataReader.getStringJsonData(
                System.getProperty("user.dir") + JsonDataReader.getJsonPath("purchaseData"));
        return switch (method.getName()) {
            case "PlaceOrderSuccessfully", "placeOrderWithEmptyCart" -> new Object[][]{{data.get(0)}};
            case "PlaceOrderWithEmptyData" -> new Object[][]{{data.get(1)}};
            case "PlaceOrderWithInvalidData" -> new Object[][]{{data.get(2)}};
            default -> new Object[][]{{new HashMap<>()}};
        };
    }

    @DataProvider(name = "getE2eData")
    public static Object[][] getE2eData() throws IOException {
        List<HashMap<String, Object>> data = JsonDataReader.getObjectJsonData(
                System.getProperty("user.dir") + JsonDataReader.getJsonPath("e2eData"));
        return data.stream().map(entry -> new Object[]{entry}).toArray(Object[][]::new);
    }



}





//@DataProvider(name = "getFullScenarioData")
//    public Iterator<Object[]> getFullScenarioData(Method method) throws IOException {
//        List<HashMap<String, String>> loginData = JsonDataReader.getStringJsonData(
//                System.getProperty("user.dir") + JsonDataReader.getJsonPath("loginData"));
//
//        List<HashMap<String, String>> purchaseData = JsonDataReader.getStringJsonData(
//                System.getProperty("user.dir") + JsonDataReader.getJsonPath("purchaseData"));
//
//        int total = Math.min(loginData.size(), purchaseData.size());
//
//        List<Object[]> combinedData = new ArrayList<>();
//
//        for (int i = 0; i < total; i++) {
//            HashMap<String, String> login = loginData.get(i);
//            HashMap<String, String> purchase = purchaseData.get(i);
//
//            if (method.getName().equals("NegativeLoginTest")) {
//                if (!login.get("expectedMessage").isEmpty()) {
//                    combinedData.add(new Object[]{login, purchase});
//                }
//            } else {
//                combinedData.add(new Object[]{login, purchase});
//            }
//        }
//
//        return combinedData.iterator();