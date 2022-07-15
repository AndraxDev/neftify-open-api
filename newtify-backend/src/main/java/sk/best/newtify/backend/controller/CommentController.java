package sk.best.newtify.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import sk.best.newtify.api.CommentsApi;
import sk.best.newtify.api.dto.ArticleDTO;
import sk.best.newtify.api.dto.CommentsDTO;
import sk.best.newtify.api.dto.CreateArticleDTO;
import sk.best.newtify.api.dto.CreateCommentsDTO;
import sk.best.newtify.backend.service.CommentService;

import java.util.List;

@Controller
public class CommentController implements CommentsApi {

    @Autowired
    CommentService commentService;

    @Override
    public ResponseEntity<CommentsDTO> createComment(String aid, CreateCommentsDTO createCommentsDTO) {
        CommentsDTO response = commentService.createComment(createCommentsDTO, aid);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<CommentsDTO> getCommentById(String aid, String cid) {
        CommentsDTO comment = commentService.getCommentById(cid);
        return ResponseEntity.status(HttpStatus.OK).body(comment);
    }

    @Override
    public ResponseEntity<List<CommentsDTO>> getComments(String uuid) {
        System.out.println(uuid);
        List<CommentsDTO> commentsList = commentService.getComments(uuid);
        return ResponseEntity.status(HttpStatus.OK).body(commentsList);
    }

    @Override
    public ResponseEntity<Void> updateComment(String cid, String aid, CreateCommentsDTO createCommentsDTO) {
        commentService.updateComment(cid, createCommentsDTO);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteComment(String cid, String aid) {
        commentService.deleteComment(cid);
        return ResponseEntity.ok().build();
    }
}
