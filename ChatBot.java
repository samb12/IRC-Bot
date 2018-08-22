import java.util.Date;
//pircBot is a framework for writing IRC(Internet Relay chat) bot.
import org.jibble.pircbot.PircBot;
//JSON Array is used to create Convert JSON text into JSON object and vice versa.
//It is also used to access the value for the key pair by calling get methods.
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ChatBot extends PircBot {

	public ChatBot() {
		this.setName("Sujan");
	}

	// This onMessage method overrides the method present in PircBot abstract
	// class.When an user sends message to the channel,
	// this method is called.This method can also run in multiple channel and
	// replies to the same channel according as the logic
	// inside the method.
	@Override
	public void onMessage(String channel, String sender, String login, String hostname, String message) {

		// Declaring a string to send and initializing it
		String messageToSend = "";

		// Logic for message Hi or Hello
		if (message.equalsIgnoreCase("Hi") || message.equalsIgnoreCase("hello")) {
			messageToSend = "Hello " + sender
					+ "! Type weather with zipcode for weather update or time for current time." + 
					"Type movie followed by movie name for movie details.";

			sendMessage(channel, messageToSend);
		}

		// Logic for message time
		else if (message.equalsIgnoreCase("time")) {

			Date date = new Date();
			String time = date.toString();

			messageToSend = sender + ", The current time is " + time;

			sendMessage(channel, messageToSend);
		}

		// Logic for message joke
		else if (message.equalsIgnoreCase("joke")) {

			messageToSend = "Why did the programmer quit his job?..... Because he didn't get Arrays!";

			sendMessage(channel, messageToSend);
		} else if (message.equalsIgnoreCase("help")) {
			messageToSend = "Please enter weather followed by Zipcode for weather update or time for the current time or movie movieName for movie details";

			sendMessage(channel, messageToSend);
		}
		// Logic to return weather, input message format --> weather zipcode
		else if (message.contains("weather ")) {

			// Extracting zipcode from string weather zipcode
			String zipCode = message.replace("weather ", "");

			// Creating an instance of WeatherdData class
			WeatherData wd = new WeatherData();

			// Calling a method in WeatherData class to return a JSON object with weather
			// information
			JSONObject wdJson = wd.getWeatherData(zipCode);

			// Manipulating the JSON object with weather data to get the name of city,
			// weather description,
			// Minimum and Maximum temperatures and converting temperatures from K to F
			try {
				String city = wdJson.getString("name");

				JSONArray weather = wdJson.getJSONArray("weather");
				JSONObject weather0 = weather.getJSONObject(0);

				String weatherDesc = weather0.getString("description");

				JSONObject temp = wdJson.getJSONObject("main");

				int temp_max = (int) Math.round((1.8 * (temp.getInt("temp_max") - 273)) + 32);
				int temp_min = (int) Math.round((1.8 * (temp.getInt("temp_min") - 273)) + 32);

				String max_temp = Integer.toString(temp_max);
				String min_temp = Integer.toString(temp_min);

				messageToSend = "The weather in " + city + " is going to be " + weatherDesc + " with a high of "
						+ max_temp + "F and a low of " + min_temp + "F";

			} catch (JSONException e) {
				e.printStackTrace();
				messageToSend = "Unable to parse response. JSONException occured :(";
			} catch (RuntimeException re) {
				re.printStackTrace();
				messageToSend = "Unable to parse response. RuntimeException occured :(";
			}

			sendMessage(channel, messageToSend);
		}

		else if (message.contains("movie")) {

			// Extracting movie name from string movie movie_name
			//message.replace replaces the "movie" with the movie name entered in the message.
			String movieName = message.replace("movie ", "");
			if (movieName.contains(" ")) {
				movieName = movieName.replace(" ", "+");
			}

			// Creating an instance of MovieInfo class
			MovieInfo mi = new MovieInfo();

			// Calling a method in MovieInfo class to return a JSON object with movie
			// details
			JSONObject miJson = mi.getMovieData(movieName);

			// Manipulating the JSON object with movie info to get movie details

			try {
				// the getString method is called passing the key to get the title and other details of the movie.
				String movieTitle = miJson.getString("Title");
				String releaseDate = miJson.getString("Released");
				String runTime = miJson.getString("Runtime");
				String director = miJson.getString("Director");
				String synopsis = miJson.getString("Plot");
				String earnings = miJson.getString("BoxOffice");
				String imdbRating = miJson.getString("imdbRating");

				messageToSend = "The movie "+ movieTitle + " was directed by " + director + " and was released on "
						+ releaseDate + ". It was " + runTime + " long and earned " + earnings
						+ " in BoxOffice. The current IMDB rating for this movie is " + imdbRating
						+ " and the one liner summary of the plot is \"" + synopsis + "\"";
             //when no Json object is returned by the API for the message.
			} catch (JSONException e) {
				e.printStackTrace();
				messageToSend = "Unable to parse response. JSONException occured :(. Please donot include spaces if the name of the movie is more than 1 word";
			} catch (RuntimeException re) {
				re.printStackTrace();
				messageToSend = "Unable to parse response. RuntimeException occured :(";
			}

			sendMessage(channel, messageToSend);

		}

		// Logic for other messages
		else {
			messageToSend = "I am sorry, I can only check time, tell a joke, check weather or give you the movie details for now";
			sendMessage(channel, messageToSend);
			sendMessage(channel, "To Check a weather, please type weather zipcode");
			sendMessage(channel, "To get movie details, please type movie movieName(noSpaces in between the movie name)");
		}
	}

}
