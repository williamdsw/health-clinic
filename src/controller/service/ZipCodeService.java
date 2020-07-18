package controller.service;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import model.ZipCode;


/**
 *
 * @author william
 */
public class ZipCodeService {
    
    // FIELDS
    
    private final String BASE_URL = "http://ziptasticapi.com/";
    
    // HELPER FUNCTIONS
    
    public ZipCode search (String value) throws Exception {
        
        // Properties
        URL url = new URL(BASE_URL + value);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");
        
        // Output
        StringBuilder response = new StringBuilder();
        try (InputStreamReader input = new InputStreamReader(connection.getInputStream())) {
            try (BufferedReader reader = new BufferedReader(input)) {
                
                String line = null;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            }
        }
        
        ZipCode zipCode = new Gson().fromJson(response.toString(), ZipCode.class);
        
        return zipCode;
    }
}
