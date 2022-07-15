package sk.best.newtify.web.gui.view;

import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.ObjectFactory;
import sk.best.newtify.api.ArticlesApi;
import sk.best.newtify.api.dto.ArticleDTO;
import sk.best.newtify.api.dto.ETopicType;
import sk.best.newtify.web.gui.component.article.ArticlePreviewComponent;
import sk.best.newtify.web.gui.component.comments.CommentComponent;
import sk.best.newtify.web.gui.component.widget.BitcoinPriceWidgetComponent;
import sk.best.newtify.web.gui.component.widget.NameDayWidgetComponent;
import sk.best.newtify.web.gui.component.widget.WeatherWidget;
import sk.best.newtify.web.gui.layout.MainLayout;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;

/**
 * @author AndraxDev
 * Copyright © 2022 AndraxDev, BEST Technická univerzita Košice.
 * All rights reserved.
 */
@PageTitle("Music")
@Route(value = "music", layout = MainLayout.class)
public class MusicView extends FlexLayout {

    private static final long serialVersionUID = 4107656392983873277L;

    private final ArticlesApi                            articlesApi;
    private final ObjectFactory<ArticlePreviewComponent> articlePreviewObjectFactory;
    private final ObjectFactory<NameDayWidgetComponent>  nameDayWidgetComponentObjectFactory;

    private final VerticalLayout middleContent      = new VerticalLayout();
    private final VerticalLayout leftWidgetContent  = new VerticalLayout();
    private final VerticalLayout rightWidgetContent = new VerticalLayout();

    private final ObjectFactory<WeatherWidget>  weatherWidgetObjectFactory;
    private final ObjectFactory<BitcoinPriceWidgetComponent>  bitcoinPriceWidgetComponentObjectFactory;

    private List<ArticleDTO> articles = Collections.emptyList();

    public MusicView(ArticlesApi articlesApi,
                     ObjectFactory<ArticlePreviewComponent> articlePreviewObjectFactory,
                     ObjectFactory<NameDayWidgetComponent> nameDayWidgetComponentObjectFactory, ObjectFactory<WeatherWidget>  weatherWidgetObjectFactory,
                     ObjectFactory<BitcoinPriceWidgetComponent>  bitcoinPriceWidgetComponentObjectFactory) {
        this.articlesApi                         = articlesApi;
        this.articlePreviewObjectFactory         = articlePreviewObjectFactory;
        this.nameDayWidgetComponentObjectFactory = nameDayWidgetComponentObjectFactory;
        this.weatherWidgetObjectFactory = weatherWidgetObjectFactory;
        this.bitcoinPriceWidgetComponentObjectFactory = bitcoinPriceWidgetComponentObjectFactory;
    }

    @PostConstruct
    protected void init() {
        createMainPane();
        createLeftWidgetPane();
        createRightWidgetPane();

        add(leftWidgetContent, middleContent, rightWidgetContent);
    }

    private void createMainPane() {
        middleContent.removeAll();
        middleContent.setAlignItems(Alignment.CENTER);
        setFlexShrink(1, middleContent);
        setFlexGrow(2, middleContent);

        fetchArticles();
        for (ArticleDTO article : articles) {
            ArticlePreviewComponent previewComponent = articlePreviewObjectFactory.getObject();
            previewComponent.setArticle(article);
            middleContent.add(previewComponent);
        }
    }

    private void createRightWidgetPane() {
        rightWidgetContent.removeAll();
        rightWidgetContent.setAlignItems(Alignment.CENTER);
        setFlexShrink(2, rightWidgetContent);
        setFlexGrow(1, rightWidgetContent);

        WeatherWidget weatherWidget = weatherWidgetObjectFactory.getObject();
        rightWidgetContent.add(weatherWidget);
    }

    private void createLeftWidgetPane() {
        leftWidgetContent.removeAll();
        leftWidgetContent.setAlignItems(Alignment.CENTER);
        setFlexShrink(2, leftWidgetContent);
        setFlexGrow(1, leftWidgetContent);

        NameDayWidgetComponent nameDayWidget = nameDayWidgetComponentObjectFactory.getObject();
        leftWidgetContent.add(nameDayWidget);

        BitcoinPriceWidgetComponent bitcoinPriceWidget = bitcoinPriceWidgetComponentObjectFactory.getObject();
        leftWidgetContent.add(bitcoinPriceWidget);
    }

    private void fetchArticles() {
        articles = articlesApi.retrieveArticles(ETopicType.MUSIC.getValue()).getBody();
    }

}
