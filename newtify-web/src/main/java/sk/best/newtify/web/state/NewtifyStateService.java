package sk.best.newtify.web.state;

public class NewtifyStateService {

    private String CURRENT_ARTICLE_ID = "";

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
}
