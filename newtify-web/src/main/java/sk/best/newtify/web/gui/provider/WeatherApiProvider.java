package sk.best.newtify.web.gui.provider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sk.best.newtify.api.dto.CommentsDTO;
import sk.best.newtify.api.dto.CreateCommentsDTO;

import java.util.Map;

/**
 * @author AndraxDev
 * Copyright © 2022 AndraxDev, BEST Technická univerzita Košice.
 * All rights reserved.
 */

@Slf4j
@Service
public class WeatherApiProvider {
    private static final String WEATHER_API_URL = "https://pro.openweathermap.org/data/2.5/weather";

    private final RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<WeatherDTO> getWeather() {
        try {
            return restTemplate.getForEntity(WEATHER_API_URL + "?q=Slovakia,sk&APPID=3299d45b446dc5cf73a215c24cbf107b", WeatherDTO.class);
        } catch (Exception e) {
            log.error("ERROR getWeather", e);
            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }
}
