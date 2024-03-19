package ibf2023.ssf.day17workshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ibf2023.ssf.day17workshop.service.WeatherService;
import jakarta.json.JsonObject;

@SpringBootApplication
public class Day17WorkshopApplication implements CommandLineRunner {

	@Autowired
	WeatherService weatherService;

	public static void main(String[] args) {
		SpringApplication.run(Day17WorkshopApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// JsonObject resp = weatherService.searchWeather("Singapore");
		// System.out.println(resp.toString());
		// System.out.println(weatherService.getTemp(resp));
		// System.out.println(weatherService.getWeather(resp).toString());
		// System.exit(0);
	}

}
