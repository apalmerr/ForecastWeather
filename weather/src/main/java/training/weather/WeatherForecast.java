package training.weather;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONObject;


public class WeatherForecast {


	//CREAMOS UN MÉTODO PUBLICO 
	//Lo creamos publico para poder instanciarlo en la clase Test
	public String getCityWeather(String city, Date datetime) throws IOException {
		
		//si la fecha es nula, CREA FECHA
		if (datetime == null) {
			datetime = new Date();
		}


		if (datetime.before(new Date(new Date().getTime() + (1000 * 60 * 60 * 24 * 6)))) {
			
			//conectamos a API
			HttpRequestFactory rf = new NetHttpTransport().createRequestFactory();
			HttpRequest req = rf
				//crea una GET solicitud para la URL
				.buildGetRequest(new GenericUrl("https://www.metaweather.com/api/location/search/?query=" + city));
			
			String r = req.execute().parseAsString();

			//creamos array JSONArray
			JSONArray array = new JSONArray(r);

			//creamos variable woe para que guarde los objetos del JSONArray 
			String woe = array.getJSONObject(0).get("woeid").toString();

			//pasamos a la variable r el id para el pronostico 
			rf = new NetHttpTransport().createRequestFactory();
			req = rf.buildGetRequest(new GenericUrl("https://www.metaweather.com/api/location/" + woe));
			r = req.execute().parseAsString();
			
			//creamos array para los resultados 
			JSONArray results = new JSONObject(r).getJSONArray("consolidated_weather");
			
			//mientras la variable i (0) sea más pequeña que el resultado (woeid), +1 a i (conseguimos pasar woe a una variable int)
			for (int i = 0; i < results.length(); i++) {
				//le asignaamos formato a (datetime) 
				//si la variable datetime es igual a la fecha a la que pertenece el pronóstico, devuelve el estado del tiempo
				if (new SimpleDateFormat("yyyy-MM-dd").format(datetime)
					.equals(results.getJSONObject(i).get("applicable_date").toString())) {
					return results.getJSONObject(i).get("weather_state_name").toString();
				}
			}
		}
		return "";
	}
}
