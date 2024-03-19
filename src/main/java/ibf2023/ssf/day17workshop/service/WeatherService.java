package ibf2023.ssf.day17workshop.service;

import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import ibf2023.ssf.day17workshop.model.Temperature;
import ibf2023.ssf.day17workshop.model.Weather;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class WeatherService {

    @Value("${weather.key}")
    private String apiKey;

    public static final String SEARCH_URL = "https://api.openweathermap.org/data/2.5/weather";
    // public static final String ICON_URL="https://openweathermap.org/img/wn/";

    public JsonObject searchWeather(String city){
        //Request URL = https://api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}
        String url = UriComponentsBuilder
                        .fromUriString(SEARCH_URL)
                        .queryParam("q", city.replace(" ", "+")) //with this ensure whitespace to + as url standard
                        .queryParam("appid", apiKey)
                        //.queryParam("units", "metric") by this then in celcius directly
                        .toUriString();

        // System.out.println(url);

        //GET request
        RequestEntity<Void> req = RequestEntity
                                    .get(url)
                                    .accept(MediaType.APPLICATION_JSON)
                                    .build();

        //make a call by RESTtemplate
        RestTemplate template = new RestTemplate();

        ResponseEntity<String> resp = null;

        try {
            resp = template.exchange(req, String.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        System.out.println("\nResp: "+ resp.toString() + "\n");

        JsonReader reader = Json.createReader(new StringReader(resp.getBody()));
        JsonObject weatherResp = reader.readObject();            

        return weatherResp;
    }

    public Temperature getTemp (JsonObject weatherResp){
        JsonObject main = weatherResp.getJsonObject("main");
        // System.out.println("main:" + main);

        String mainTemp = main.getValue("/temp").toString();        
        Double temp = kelvinToCelsius(Double.parseDouble(mainTemp));
        String tempInCelcius = String.format("%.1f", temp);

        Double mainFeelLikes = kelvinToCelsius(main.getJsonNumber("feels_like").doubleValue());
        String feelList = String.format("%.1f", mainFeelLikes);


        Temperature temperature = new Temperature(tempInCelcius, feelList);

        return temperature;
    }

    public List<Weather> getWeather(JsonObject weatherResp){
        List <Weather> weatherList = new LinkedList<>();
        //to copy chuk stream

        JsonArray weather = weatherResp.getJsonArray("weather");
        for (int i = 0; i < weather.size(); i ++){
            JsonObject elem = weather.get(i).asJsonObject();
            String weatherMain = elem.getString("main");
            String description = elem.getString("description");
            String icon = elem.getString("icon");
            //https://openweathermap.org/weather-conditions
            // String iconUrl = ICON_URL + icon + "@2x.png";
            String iconUrl = icon;
            Weather weatherObj = new Weather(weatherMain, description, iconUrl);
            weatherList.add(weatherObj);
        }

        // System.out.println("weatherResp:" + weatherResp);
        weatherList.forEach(System.out::println);       

        return weatherList;
    }

    public static double kelvinToCelsius(double kelvin) {
        return kelvin - 273.15;
    }
}
