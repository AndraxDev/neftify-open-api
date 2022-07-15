package sk.best.newtify.web.gui.component.widget;

import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sk.best.newtify.api.NamedaysApi;
import sk.best.newtify.api.dto.NameDayDTO;
import sk.best.newtify.web.gui.provider.WeatherApiProvider;
import sk.best.newtify.web.gui.provider.WeatherDTO;

import javax.annotation.PostConstruct;
import java.util.Calendar;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class WeatherWidget extends FlexLayout {
    private static final long              serialVersionUID    = -1463717502029647659L;

    WeatherApiProvider weatherApiProvider;

    public WeatherWidget(WeatherApiProvider weatherApiProvider) {
        this.weatherApiProvider = weatherApiProvider;
    }

    @PostConstruct
    private void init() {
        WeatherDTO data = weatherApiProvider.getWeather().getBody();

        System.out.println("[WEATHER API] ++++++++++++++ WEATHER ++++++++++++++");
        System.out.println("[WEATHER API] Weather: " + data.getWeather());

        createWidgetIcon();
        createWeatherView(data);

        this.getStyle()
                .set("background", "var(--lumo-contrast-10pct)")
                .set("border-radius", "1em");
        this.setFlexDirection(FlexDirection.COLUMN);
        this.setAlignItems(Alignment.CENTER);
        this.setWidthFull();
    }

    private void createWidgetIcon() {
        Icon calendarIcon = VaadinIcon.SUN_RISE.create();
        calendarIcon.setSize("5em");
        calendarIcon.setColor("var(--lumo-contrast-color)");

        this.add(calendarIcon);
    }

    private void createWeatherView(WeatherDTO weatherDTO) {
        H4 wt = new H4("Weather");
        H3 cloudness = new H3(weatherDTO != null ? "Cloud: " + weatherDTO.getWeather().get(0).get("main").toString() : "Unknown");
        H3 temperature = new H3(weatherDTO != null ? "Temperature: " + String.format("%.1f", (double) weatherDTO.getMain().get("temp") - 273) + "°C" : "Unknown");
        H3 humidity = new H3(weatherDTO != null ? "Humidity: " + weatherDTO.getMain().get("humidity").toString() : "Unknown");
        this.add(wt, cloudness, temperature, humidity);
    }
}
