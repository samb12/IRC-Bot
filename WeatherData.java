
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class WeatherData {
	
	
	// Calls the open weather map API and returns a JSON with weather info for the location
	public JSONObject getWeatherData(String zipCode) {

		//weather API generated for access when creating an account on openweathermap.com
		
          String weatherApiKey = "095ae8ad2731e03d0c7fd0e7ccba5586";
		//Declaring a JSONObject to be returned
		JSONObject weatherJson = null;

		try {

			//API URL
			URL weatherUrl = new URL(
					"http://api.openweathermap.org/data/2.5/weather?zip=" + zipCode + ",us&APPID=" + weatherApiKey);

			//Opening a connection
			HttpURLConnection conn = (HttpURLConnection) weatherUrl.openConnection();

			//Setting request method API return content type
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			//Check for http response error codes
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Connection Failed. " + "The error code is HTTP:" + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String output = "";
			StringBuffer apiResponse = new StringBuffer();

			//Read the response from API request line by line and save it as a apiResopnse 
			while ((output = br.readLine()) != null) {
				apiResponse.append(output);
			}

			//Closing connection and buffered reader when done
			br.close();
			conn.disconnect();

			//Converting the API returned text(JSON) into a JSON object
			weatherJson = new JSONObject(apiResponse.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

		//returns weather data as a JSON object
		return weatherJson;
	}
}
