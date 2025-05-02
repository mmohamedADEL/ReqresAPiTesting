package utilities.helper;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;

public class DataUtility {
    //TODO : Generate random data
    //random name
    public static String getRandomName() {
        String[] names = {"John", "Jane", "Alice", "Bob", "Charlie", "David", "Eve", "Frank", "Grace", "Hannah"};
        int randomIndex = (int) (Math.random() * names.length);
        return names[randomIndex];
    }
    //TODO : random job
    public static String getRandomJob() {
        String[] jobs = {"Software Engineer", "Data Scientist", "Product Manager", "Designer", "QA Engineer", "DevOps Engineer", "Business Analyst", "System Administrator", "Network Engineer", "Technical Writer"};
        int randomIndex = (int) (Math.random() * jobs.length);
        return jobs[randomIndex];
    }
    //TODO : read from json file
    private final static String TEST_DATA_PATH = "src/test/resources/";
    public static String getJsonData(String filename, String... keys) {
        InputStream inputStream = DataUtility.class.getClassLoader().getResourceAsStream(filename + ".json");

        if (inputStream == null) {
            throw new RuntimeException("file is not found : " + filename + ".json");
        }

        JsonElement jsonElement = JsonParser.parseReader(new InputStreamReader(inputStream));
        JsonObject currentObject = jsonElement.getAsJsonObject();

        for (int i = 0; i < keys.length - 1; i++) {
            if (currentObject.has(keys[i])) {
                currentObject = currentObject.getAsJsonObject(keys[i]);
            } else {
                throw new RuntimeException("parent key is not found: " + keys[i]);
            }
        }

        String lastKey = keys[keys.length - 1];
        if (currentObject.has(lastKey)) {
            return currentObject.get(lastKey).getAsString();
        } else {
            throw new RuntimeException("sub key is not found " + lastKey);
        }
    }

}
