package ibf2023.ssf.day17workshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import ibf2023.ssf.day17workshop.model.Temperature;
import ibf2023.ssf.day17workshop.model.Weather;
import ibf2023.ssf.day17workshop.service.WeatherService;
import jakarta.json.JsonObject;

@Controller
public class WeatherController {
    
    @Autowired 
    WeatherService weatherService;

    @GetMapping(path = "/search")
    public ModelAndView postWeather(@ModelAttribute ("city") String city){
        ModelAndView mav = new ModelAndView("weather");
        JsonObject weatherResp = weatherService.searchWeather(city);
        if(null != weatherResp){
            List<Weather> weathers = weatherService.getWeather(weatherResp);
            Temperature temp = weatherService.getTemp(weatherResp);
    
            mav.addObject("city", city);
            mav.addObject("weathers", weathers);
            mav.addObject("temp", temp);
    
        }else{
            mav.setViewName("notfound");
        }

        return mav; 
    }
}
