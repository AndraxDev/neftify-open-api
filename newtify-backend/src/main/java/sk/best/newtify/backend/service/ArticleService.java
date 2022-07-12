package sk.best.newtify.backend.service;

import org.springframework.stereotype.Service;
import sk.best.newtify.api.dto.ArticleDTO;
import sk.best.newtify.api.dto.CreateArticleDTO;
import sk.best.newtify.api.dto.ETopicType;
import sk.best.newtify.backend.entity.Article;
import sk.best.newtify.backend.entity.enums.TopicType;
import sk.best.newtify.backend.repository.ArticleRepository;

@Service
public class ArticleService {
    private ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public ArticleDTO createArticle(CreateArticleDTO createArticleDTO) {
        Article article = new Article();
        article.setTitle(createArticleDTO.getTitle());
        article.setShortTitle(createArticleDTO.getShortTitle());
        article.setText(createArticleDTO.getText());
        article.setCreatedAt(createArticleDTO.getCreatedAt());
        article.setAuthor(createArticleDTO.getAuthor());
        article.setTopicType(TopicType.fromValue(createArticleDTO.getTopicType().toString()));

        articleRepository.save(article);

        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setUuid(article.getUuid());
        articleDTO.setTitle(article.getTitle());
        articleDTO.setShortTitle(article.getShortTitle());
        articleDTO.setText(article.getText());
        articleDTO.setCreatedAt(article.getCreatedAt());
        articleDTO.setAuthor(article.getAuthor());
        articleDTO.setTopicType(ETopicType.fromValue(article.getTopicType().toString()));

        return articleDTO;
    }
}
