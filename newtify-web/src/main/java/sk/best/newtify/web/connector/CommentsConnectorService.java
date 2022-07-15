package sk.best.newtify.web.connector;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sk.best.newtify.api.ArticlesApi;
import sk.best.newtify.api.CommentsApi;
import sk.best.newtify.api.dto.ArticleDTO;
import sk.best.newtify.api.dto.CommentsDTO;
import sk.best.newtify.api.dto.CreateArticleDTO;
import sk.best.newtify.api.dto.CreateCommentsDTO;

import java.net.URI;
import java.util.Collections;
import java.util.List;

/**
 * @author AndraxDev
 * Copyright © 2022 AndraxDev, BEST Technická univerzita Košice.
 * All rights reserved.
 */

@Slf4j
@Service
public class CommentsConnectorService implements CommentsApi {

    private static final String COMMENTS_API_URL = "http://localhost:8081/v1/comments";

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * POST /v1/comments/{aid}
     * This is the endpoint which will create Comment object
     *
     * @param aid               (required)
     * @param createCommentsDTO Data model for comments creation (required)
     * @return Hey, everything went well (status code 201)
     */
    @Override
    public ResponseEntity<CommentsDTO> createComment(String aid, CreateCommentsDTO createCommentsDTO) {
        try {
            return restTemplate.postForEntity(COMMENTS_API_URL + "/" + aid, createCommentsDTO, CommentsDTO.class);
        } catch (Exception e) {
            log.error("ERROR createComment", e);
            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }

    /**
     * DELETE /v1/comments/{aid}/{cid}
     * Endpoint which can be used to delete article resource specified by articleUuid
     *
     * @param cid Comment resource identifier (required)
     * @param aid Article resource identifier (required)
     * @return Comment was successfully deleted (status code 200)
     * or Comment not found (status code 404)
     */
    @Override
    public ResponseEntity<Void> deleteComment(String cid, String aid) {
        try {
            restTemplate.delete(COMMENTS_API_URL + "/" + aid + "/" + cid);
            return ResponseEntity
                    .ok()
                    .build();
        } catch (Exception e) {
            log.error("ERROR deleteComment", e);
            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }

    /**
     * GET /v1/comments/{aid}/{cid}
     * Fetch comment with specified id
     *
     * @param aid (required)
     * @param cid (required)
     * @return returns comment with specified id (status code 200)
     */
    @Override
    public ResponseEntity<CommentsDTO> getCommentById(String aid, String cid) {
        try {
            return restTemplate.getForEntity(COMMENTS_API_URL + "/" + aid + "/" + cid, CommentsDTO.class);
        } catch (Exception e) {
            log.error("ERROR getCommentById", e);
            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }

    /**
     * GET /v1/comments/{aid}
     * Fetch comments for specified article
     *
     * @param aid (required)
     * @return returns detail of comments (status code 200)
     */
    @Override
    public ResponseEntity<List<CommentsDTO>> getComments(String aid) {
        try {
            ResponseEntity<CommentsDTO[]> commentsResponse;
            if (aid == null) {
                commentsResponse = restTemplate.getForEntity(COMMENTS_API_URL, CommentsDTO[].class);
            } else {
                commentsResponse = restTemplate.getForEntity(COMMENTS_API_URL + "/" + aid, CommentsDTO[].class);
            }

            System.out.println("[DEBUG] Getting comments for article with ID: " + aid);

            CommentsDTO[] data = commentsResponse.getBody();
            if (data == null) {
                return ResponseEntity.ok(Collections.emptyList());
            }

            int position = 0;
            for (CommentsDTO d: data) {
                System.out.println("+++++++++++++++++++++ COMMENTS OBJECT ++++++++++++++++++++");
                System.out.println("| [DEBUG] Position: " + Integer.toString(position));
                System.out.println("| [DEBUG] Comment author: " + d.getName());
                System.out.println("| [DEBUG] Comment author email: " + d.getEmail());
                System.out.println("| [DEBUG] Comment text: " + d.getComment());
                System.out.println("| [DEBUG] Comment ID: " + d.getCid());
                position++;
            }

            if (position == 0) {
                System.out.println("[DEBUG] This post does not have any comments");
            }

            return ResponseEntity
                    .status(commentsResponse.getStatusCode())
                    .body(List.of(data));
        } catch (Exception e) {
            log.error("ERROR getComments", e);
            return ResponseEntity
                    .internalServerError()
                    .body(Collections.emptyList());
        }
    }

    /**
     * PUT /v1/comments/{aid}/{cid}
     * Endpoint to update already existing comment specified by commentsUuid
     *
     * @param cid               Comment resource identifier (required)
     * @param aid               Article resource identifier (required)
     * @param createCommentsDTO Data model with properties which are required for comment update (required)
     * @return Comment was successfully updated (status code 200)
     * or Comment not found (status code 404)
     */
    @Override
    public ResponseEntity<Void> updateComment(String cid, String aid, CreateCommentsDTO createCommentsDTO) {
        try {
            restTemplate.put(URI.create(COMMENTS_API_URL + "/" + aid + "/" + cid), createCommentsDTO);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("ERROR updateComment", e);
            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }
}
