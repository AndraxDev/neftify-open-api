package sk.best.newtify.web.gui.provider;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import sk.best.newtify.api.dto.ArticleDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class WeatherDTO {
    private static final long serialVersionUID = 1L;

    @JsonProperty("coord")
    private Map<String, Object> coord;

    @JsonProperty("weather")
    private ArrayList<Map<String, Object>> weather;

    @JsonProperty("base")
    private String base;

    @JsonProperty("main")
    private Map<String, Object> main;

    @JsonProperty("visibility")
    private int visibility;

    @JsonProperty("wind")
    private Map<String, Object> wind;

    @JsonProperty("clouds")
    private Map<String, Object> clouds;

    @JsonProperty("dt")
    private int dt;

    @JsonProperty("sys")
    private Map<String, Object> sys;

    @JsonProperty("timezone")
    private int timezone;

    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("cod")
    private int cod;

    public WeatherDTO coord(Map<String, Object> coord) {
        this.coord = coord;
        return this;
    }

    public WeatherDTO weather(ArrayList<Map<String, Object>> weather) {
        this.weather = weather;
        return this;
    }

    public WeatherDTO base(String base) {
        this.base = base;
        return this;
    }

    public WeatherDTO main(Map<String, Object> main) {
        this.main = main;
        return this;
    }

    public WeatherDTO visibility(int visibility) {
        this.visibility = visibility;
        return this;
    }

    public WeatherDTO wind(Map<String, Object> wind) {
        this.wind = wind;
        return this;
    }

    public WeatherDTO clouds(Map<String, Object> clouds) {
        this.clouds = clouds;
        return this;
    }

    public WeatherDTO dt(int dt) {
        this.dt = dt;
        return this;
    }

    public WeatherDTO sys(Map<String, Object> sys) {
        this.sys = sys;
        return this;
    }

    public WeatherDTO timezone(int timezone) {
        this.timezone = timezone;
        return this;
    }

    public WeatherDTO id(int id) {
        this.id = id;
        return this;
    }

    public WeatherDTO name(String name) {
        this.name = name;
        return this;
    }

    public WeatherDTO cod(int cod) {
        this.cod = cod;
        return this;
    }

    @Schema(name = "coord", required = false)
    public Map<String, Object> getCoord() {
        return coord;
    }

    public void setCoord(Map<String, Object> coord) {
        this.coord = coord;
    }

    @Schema(name = "weather", required = false)
    public ArrayList<Map<String, Object>> getWeather() {
        return weather;
    }

    public void setWeather(ArrayList<Map<String, Object>> weather) {
        this.weather = weather;
    }

    @Schema(name = "base", required = false)
    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    @Schema(name = "main", required = false)
    public Map<String, Object> getMain() {
        return main;
    }

    public void setMain(Map<String, Object> main) {
        this.main = main;
    }

    @Schema(name = "wind", required = false)
    public Map<String, Object> getWind() {
        return wind;
    }

    public void setWind(Map<String, Object> wind) {
        this.wind = wind;
    }

    @Schema(name = "clouds", required = false)
    public Map<String, Object> getClouds() {
        return clouds;
    }

    public void setClouds(Map<String, Object> clouds) {
        this.clouds = clouds;
    }

    @Schema(name = "dt", required = false)
    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    @Schema(name = "sys", required = false)
    public Map<String, Object> getSys() {
        return sys;
    }

    public void setSys(Map<String, Object> sys) {
        this.sys = sys;
    }

    @Schema(name = "timezone", required = false)
    public int getTimezone() {
        return timezone;
    }

    public void setTimezone(int timezone) {
        this.timezone = timezone;
    }

    @Schema(name = "id", required = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Schema(name = "name", required = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Schema(name = "cod", required = false)
    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
