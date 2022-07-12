package sk.best.newtify.backend.repository;

import org.springframework.data.repository.CrudRepository;
import sk.best.newtify.backend.entity.Article;

public interface ArticleRepository extends CrudRepository<Article, String> {

}
