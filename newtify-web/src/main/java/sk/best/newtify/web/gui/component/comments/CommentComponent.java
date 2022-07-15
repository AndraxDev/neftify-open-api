package sk.best.newtify.web.gui.component.comments;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import sk.best.newtify.api.dto.ArticleDTO;
import sk.best.newtify.api.dto.CommentsDTO;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CommentComponent extends Composite<VerticalLayout> {
    private static final long              serialVersionUID = -8229157675764936894L;
    private static final DateTimeFormatter DATE_FORMATTER   = DateTimeFormatter.ofPattern("d.MM.uuuu");

    private final H4 commentAuthor = new H4();
    private final H4 commentAuthorEmail = new H4();
    private final H4 commentContent = new H4();
    private final H4 commentTimestamp = new H4();

    private CommentsDTO comments;

    public CommentComponent() {

    }

    public void setComment(@Nullable CommentsDTO comment) {
        this.comments = comment;
        if (comment == null) {
            clear();
            return;
        }


        commentAuthor.setText(comment.getName());
        commentAuthorEmail.setText(comment.getEmail());
        commentContent.setText(comment.getComment());

        commentTimestamp.setText(DATE_FORMATTER.format(
                        Instant.ofEpochSecond(comment.getCreatedAt())
                                .atZone(ZoneId.systemDefault())
                )
        );
    }

    private void clear() {
        commentAuthor.removeAll();
        commentAuthorEmail.removeAll();
        commentContent.removeAll();
        commentTimestamp.removeAll();
    }
}
