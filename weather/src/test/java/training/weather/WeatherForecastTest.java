package training.weather;

import java.io.IOException;
import java.util.Date;
import org.junit.Test;



public class WeatherForecastTest{

	//creamos método prueba
	@Test
	public void unfinished_test() throws IOException {
		WeatherForecast weatherForecast = new WeatherForecast();
		String forecast = weatherForecast.getCityWeather("Barcelona", new Date());
		System.out.println(forecast);
	}
}