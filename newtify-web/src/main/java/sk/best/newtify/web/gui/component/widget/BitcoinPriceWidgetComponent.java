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
import sk.best.newtify.web.connector.BitcoinPriceConnectorService;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Calendar;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class BitcoinPriceWidgetComponent extends FlexLayout {
    private static final long serialVersionUID = 54375439023642304L;

    private final BitcoinPriceConnectorService connectorService;

    public BitcoinPriceWidgetComponent(BitcoinPriceConnectorService connectorService) {
        this.connectorService = connectorService;
    }

    @PostConstruct
    private void init(){
        BigDecimal price = connectorService.retrieveBitcoinPrice();

        createWidgetHeader();
        createPriceLabel(price);

        this.getStyle()
                .set("background", "var(--lumo-contrast-10pct)")
                .set("border-radius", "1em");
        this.setFlexDirection(FlexDirection.COLUMN);
        this.setAlignItems(Alignment.CENTER);
        this.setWidthFull();
    }


    private void createWidgetHeader(){
        Icon icon = VaadinIcon.LINE_CHART.create();

        icon.setSize("5em");
        icon.setColor("var(--lumo-contrast-color)");

        this.add(icon);

        H3 widgetTitle = new H3("Bitcoin price is:");

        this.add(widgetTitle);
    }

    private void createPriceLabel(BigDecimal price){
        String message = price == null ? "Unknown" : price.toPlainString() + " USD/BTC";

        H4 priceLabel = new H4(message);
        this.add(priceLabel);
    }
}
