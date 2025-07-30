package utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.*;

public class JsonDataReader {

    public static List<HashMap<String, String>> getJsonData(String filePath) throws IOException {
        InputStream is = new FileInputStream(filePath);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(is, new TypeReference<List<HashMap<String, String>>>() {});
    }

    public static String getJsonPath(String fileName) {
        return "/src/test/resources/Data/" + fileName + ".json";
    }
}
