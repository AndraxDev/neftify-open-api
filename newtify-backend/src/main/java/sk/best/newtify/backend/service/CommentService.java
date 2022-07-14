package sk.best.newtify.backend.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sk.best.newtify.api.dto.*;
import sk.best.newtify.backend.entity.Article;
import sk.best.newtify.backend.entity.Comment;
import sk.best.newtify.backend.entity.enums.TopicType;
import sk.best.newtify.backend.repository.ArticleRepository;
import sk.best.newtify.backend.repository.CommentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public CommentsDTO createComment(CreateCommentsDTO createCommentsDTO, String articleId) {
        Comment comment = new Comment();
        comment.setName(createCommentsDTO.getName());
        comment.setEmail(createCommentsDTO.getEmail());
        comment.setComment(createCommentsDTO.getComment());
        comment.setCreatedAt(createCommentsDTO.getCreatedAt());
        comment.setUuid(articleId);

        commentRepository.save(comment);

        CommentsDTO commentsDTO = new CommentsDTO();
        commentsDTO.setCid(comment.getCid());
        commentsDTO.setUuid(comment.getUuid());
        commentsDTO.setName(comment.getName());
        commentsDTO.setEmail(comment.getEmail());
        commentsDTO.setComment(comment.getComment());
        commentsDTO.setCreatedAt(comment.getCreatedAt());

        return commentsDTO;
    }

    public CommentsDTO getCommentById(String cid) {
        Optional<Comment> commentOpt = commentRepository.findById(cid);
        if (commentOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Comment comment = commentOpt.get();

        CommentsDTO commentsDTO = new CommentsDTO();
        commentsDTO.setCid(comment.getCid());
        commentsDTO.setUuid(comment.getUuid());
        commentsDTO.setName(comment.getName());
        commentsDTO.setEmail(comment.getEmail());
        commentsDTO.setComment(comment.getComment());
        commentsDTO.setCreatedAt(comment.getCreatedAt());
        return commentsDTO;
    }

    public List<CommentsDTO> getComments(String uuid) {
        List<Comment> comments = new ArrayList<>();

        if (uuid != null && !uuid.isEmpty()) {
            comments = commentRepository.findAllByUuid(uuid);
        } else {
            System.out.println("uuid object is null or empty");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Article with this ID was not found");
        }

        List<CommentsDTO> response = new ArrayList<>();

        for (Comment comment : comments) {
            CommentsDTO commentsDTO = new CommentsDTO();
            commentsDTO.setCid(comment.getCid());
            commentsDTO.setUuid(comment.getUuid());
            commentsDTO.setName(comment.getName());
            commentsDTO.setEmail(comment.getEmail());
            commentsDTO.setComment(comment.getComment());
            commentsDTO.setCreatedAt(comment.getCreatedAt());
            response.add(commentsDTO);
        }

        return response;
    }

    public void deleteComment(String cid) {
        commentRepository.deleteById(cid);
    }

    public void updateComment(String cid, CreateCommentsDTO createCommentsDTO) {
        Optional<Comment> commentOpt = commentRepository.findById(cid);
        if (commentOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment was not found");
        }

        Comment comment = commentOpt.get();
        comment.setUuid(createCommentsDTO.getUuid() != null ? createCommentsDTO.getUuid() : comment.getUuid());
        comment.setName(createCommentsDTO.getName() != null ? createCommentsDTO.getName() : comment.getName());
        comment.setEmail(createCommentsDTO.getEmail() != null ? createCommentsDTO.getEmail() : comment.getEmail());
        comment.setComment(createCommentsDTO.getComment() != null ? createCommentsDTO.getComment() : comment.getComment());
        comment.setCreatedAt(createCommentsDTO.getCreatedAt() != null ? createCommentsDTO.getCreatedAt() : comment.getCreatedAt());
        commentRepository.save(comment);
    }
}
