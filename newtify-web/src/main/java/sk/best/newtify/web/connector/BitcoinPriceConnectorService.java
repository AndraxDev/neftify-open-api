package sk.best.newtify.web.connector;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.CastUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@Slf4j
@Service
public class BitcoinPriceConnectorService {
    private static final String QUERY_URL = "https://api.coinpaprika.com/v1/tickers/btc-bitcoin";

    private static final String QUOTES_NODE = "quotes";
    private static final String USD_NODE = "USD";
    private static final String PRICE_NODE = "price";

    private static final int ROUNDING_PLACES = 2;

    public BigDecimal retrieveBitcoinPrice(){
        try{
            RestTemplate template = new RestTemplate();
            ResponseEntity<Map<String, Object>> priceResponseEntity =
                    CastUtils.cast(template.getForEntity(QUERY_URL, Map.class));
            Map<String, Object> retrievedData = priceResponseEntity.getBody();
            System.out.println(this.getClass().getName());

            Map<String, Object> quotes = (Map<String, Object>) retrievedData.get(QUOTES_NODE);
            Map<String, Object> usd = (Map<String, Object>) quotes.get(USD_NODE);

            BigDecimal price = BigDecimal.valueOf((double) usd.get(PRICE_NODE));

            price = price.setScale(ROUNDING_PLACES, RoundingMode.HALF_UP);

            return price;
        } catch(Exception e){
            System.out.println("Error while receiving price!");
            e.printStackTrace();
            return null;
        }

    }
}