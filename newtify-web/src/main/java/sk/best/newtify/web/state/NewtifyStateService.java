package sk.best.newtify.web.state;

/**
 * @author AndraxDev
 * Copyright © 2022 AndraxDev, BEST Technická univerzita Košice.
 * All rights reserved.
 */

public class NewtifyStateService {

    private String CURRENT_ARTICLE_ID = "";

    /****************************
     * CommentsState attributes
     ****************************/

    private String commentAuthor = "";
    private String commentAuthorEmail = "";
    private String commentContent = "";
    private String commentArticleId = "";
    private String commentCommentId = "";
    private long commentCreatedAt = 0;

    /**
     * Class to make current article ID accessible for whole application
     *
     * @Required by Comments API
     * @param cid (required)
     **/

    public void setCurrentArticleId(String cid) {
        CURRENT_ARTICLE_ID = cid;
    }

    public String getCurrentArticleId() {
        return CURRENT_ARTICLE_ID;
    }

    public NewtifyStateService() {

    }

    public String getCommentAuthor() {
        return commentAuthor;
    }

    public void setCommentAuthor(String commentAuthor) {
        this.commentAuthor = commentAuthor;
    }

    public String getCommentAuthorEmail() {
        return commentAuthorEmail;
    }

    public void setCommentAuthorEmail(String commentAuthorEmail) {
        this.commentAuthorEmail = commentAuthorEmail;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getCommentArticleId() {
        return commentArticleId;
    }

    public void setCommentArticleId(String commentArticleId) {
        this.commentArticleId = commentArticleId;
    }

    public String getCommentCommentId() {
        return commentCommentId;
    }

    public void setCommentCommentId(String commentCommentId) {
        this.commentCommentId = commentCommentId;
    }

    public long getCommentCreatedAt() {
        return commentCreatedAt;
    }

    public void setCommentCreatedAt(long commentCreatedAt) {
        this.commentCreatedAt = commentCreatedAt;
    }
}
