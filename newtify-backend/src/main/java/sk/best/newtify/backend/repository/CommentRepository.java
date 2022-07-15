package sk.best.newtify.backend.repository;

import org.springframework.data.repository.CrudRepository;
import sk.best.newtify.backend.entity.Comment;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, String> {
    List<Comment> findAllByUuid(String uuid);
}
