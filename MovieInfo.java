import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class MovieInfo {

	// Calls the open OMDB API and returns a JSON with Movie info
	public JSONObject getMovieData(String movieName) {

		// OMDB API generated for access when creating an account on
		// OMDB Api 
		String omdbApiKey = "3683055f";

		// Declaring a JSONObject to be returned
		JSONObject movieJson = null;

		try {
             //create JSON Reader
			// API URL
			URL omdbUrl = new URL("http://www.omdbapi.com/?t=" + movieName + "&apikey=" + omdbApiKey);

			// Opening a connection to make request to the API server
			//openConnection returns an instance of HttpURLConnection.
			HttpURLConnection conn = (HttpURLConnection) omdbUrl.openConnection();

			// Setting request method API return content type
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json"); // params key and value

			// Check for http response error codes
			//checks if the output is received from the API
			//getResponsecode returns int value 200 for an authorized connection.
			if (conn.getResponseCode() != 200) {
				//display error message with the code if the connection cannot be made.
				throw new RuntimeException("Connection Failed. " + "The error code is HTTP:" + conn.getResponseCode());
			}
            //Buffer reader is used instead of scanner to avoid error related to reading new line as well as for efficiency.
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String output = "";
			//stringBuffer is like a string but it can be modified. ie the sequence or length of the character in the 
			//string buffer can be modified thats why string buffer is used instead of the string.
			StringBuffer apiResponse = new StringBuffer();

			// Read the response from API request line by line and save it as a apiResopnse
			//readline reads the string from the buffer reader and throws exception if any error occurs during reading
			while ((output = br.readLine()) != null) {
				//save the output to the api response stringBuffer.
				apiResponse.append(output);
			}

			// Closing connection and buffered reader when done
			br.close();
			conn.disconnect();

			// Converting the API returned text(JSON) into a JSON object
			movieJson = new JSONObject(apiResponse.toString());

		} catch (Exception e) {
			//printStackTrace is used for easier debugging of the error.
			//Since it not only shows where the error occurred but also displays destination affected by the error.
			e.printStackTrace();
		}

		// returns weather data as a JSON object
		return movieJson;
	}

}
