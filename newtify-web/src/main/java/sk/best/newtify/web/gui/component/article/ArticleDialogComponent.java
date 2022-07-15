package sk.best.newtify.web.gui.component.article;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.server.StreamResource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.lang.Nullable;
import sk.best.newtify.api.ArticlesApi;
import sk.best.newtify.api.CommentsApi;
import sk.best.newtify.api.dto.ArticleDTO;
import sk.best.newtify.api.dto.CommentsDTO;
import sk.best.newtify.api.dto.CreateArticleDTO;
import sk.best.newtify.api.dto.CreateCommentsDTO;
import sk.best.newtify.web.bootstrap.NewtifyWebApplication;
import sk.best.newtify.web.gui.component.comments.CommentComponent;
import sk.best.newtify.web.util.ArticleMapper;
import sk.best.newtify.web.util.CommentsMapper;

import java.io.ByteArrayInputStream;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @author Marek Urban
 * Copyright © 2022 BEST Technická univerzita Košice.
 * All rights reserved.
 */
public class ArticleDialogComponent extends Dialog {

    private static final long              serialVersionUID = -8124048656164601926L;
    private static final DateTimeFormatter DATE_FORMATTER   = DateTimeFormatter.ofPattern("d.MM.uuuu");

    private final Image    image       = new Image();
    private final H2       title       = new H2();
    private final Span     author      = new Span();
    private final Span     date        = new Span();
    private final H5       previewText = new H5();
    private final TextArea content     = new TextArea();
    private final CommentsApi commentsApi;
    private List<CommentsDTO> comments = Collections.emptyList();

    private final ObjectFactory<CommentComponent> commentsPreviewObjectFactory;

    private final VerticalLayout commentsLayout      = new VerticalLayout();
    private final VerticalLayout commentsForm      = new VerticalLayout();

    private final FormLayout formLayout          = new FormLayout();

    private final TextField nameField           = new TextField();
    private final TextField           emailField          = new TextField();
    private final TextArea            contentTextArea     = new TextArea();
    private final Binder<CommentsDTO> commentsDTOBinder   = new Binder<>();

    public ArticleDialogComponent(CommentsApi commentsApi, ObjectFactory<CommentComponent> commentsPreviewObjectFactory) {
        this.commentsApi = commentsApi;
        this.commentsPreviewObjectFactory = commentsPreviewObjectFactory;
        init();
    }

    protected void init() {
        styleImage();
        styleTitle();
        styleAuthor();
        stylePreviewText();
        styleContent();

        Span titleAndAuthor = new Span(title, author, date);
        titleAndAuthor.getStyle()
                .set("margin-left", "0.5em");

        add(image, titleAndAuthor, previewText, content, commentsForm, commentsLayout);
    }

    public void setArticle(@Nullable ArticleDTO article,
                           @Nullable byte[] imageData) {
        if (article == null) {
            clear();
            return;
        }

        if (imageData != null && imageData.length != 0) {
            setImage(imageData);
        }

        nameField.setSizeFull();
        emailField.setSizeFull();
        contentTextArea.setSizeFull();

//        commentsForm.setSizeFull();
//
//        formLayout.getStyle()
//                .set("flex-direction", "column")
//        ;

        title.setText(article.getTitle());
        previewText.setText(article.getShortTitle());
        author.setText(article.getAuthor());
        content.setValue(article.getText());
        NewtifyWebApplication.newtifyStateService.setCurrentArticleId(article.getUuid());
        System.out.println("[DEBUG] Current article ID has changed to:  " + NewtifyWebApplication.newtifyStateService.getCurrentArticleId());

        date.setText(DATE_FORMATTER.format(
                        Instant.ofEpochSecond(article.getCreatedAt())
                                .atZone(ZoneId.systemDefault())
                )
        );

        formLayout.addFormItem(nameField, "Your name");
        formLayout.addFormItem(emailField, "Your email");
        contentTextArea.setLabel("Comment");
        commentsForm.add(contentTextArea);

        commentsForm.add(formLayout);

        Button postBtn = new Button("Leave a comment", VaadinIcon.COMMENT.create());
        postBtn.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        postBtn.addClickListener(event -> {
            if (!commentsDTOBinder.validate().isOk()) {
                return;
            } else {
                CreateCommentsDTO createCommentsDTO = CommentsMapper.toCreateComment(commentsDTOBinder.getBean());
                commentsApi.createComment(NewtifyWebApplication.newtifyStateService.getCurrentArticleId(), createCommentsDTO);
                Notification successNotification = new Notification("Posted");
                successNotification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                successNotification.setPosition(Notification.Position.TOP_CENTER);
                successNotification.setDuration(5000);
                successNotification.open();
                clearCommentsForm();

                loadComments();
            }
        });

        commentsForm.add(postBtn);

        createCommentsFormBinder();

        loadComments();
    }

    public void loadComments() {
        commentsLayout.removeAll();

        fetchComments();
        int position = 0;
        for (CommentsDTO comment : comments) {
            System.out.println("+++++++++++++++++++++ COMMENTS OBJECT ++++++++++++++++++++");
            System.out.println("| [DEBUG] [UI LOADER] Position: " + position);
            System.out.println("| [DEBUG] [UI LOADER] Comment author: " + comment.getName());
            System.out.println("| [DEBUG] [UI LOADER] Comment author email: " + comment.getEmail());
            System.out.println("| [DEBUG] [UI LOADER] Comment text: " + comment.getComment());
            System.out.println("| [DEBUG] [UI LOADER] Comment ID: " + comment.getCid());

            VerticalLayout comment_block = new VerticalLayout();

            H4 commentAuthor = new H4();
            H4 commentAuthorEmail = new H4();
            H4 commentContent = new H4();
            H4 commentTimestamp = new H4();

            commentAuthor.setText(comment.getName());
            commentAuthorEmail.setText(comment.getEmail());
            commentContent.setText(comment.getComment());

            commentTimestamp.setText(DATE_FORMATTER.format(
                            Instant.ofEpochSecond(comment.getCreatedAt())
                                    .atZone(ZoneId.systemDefault())
                    )
            );

            commentAuthor.getStyle().set("padding", "0").set("margin", "4px");
            commentAuthorEmail.getStyle().set("padding", "0").set("margin", "4px");
            commentContent.getStyle().set("padding", "0").set("margin", "4px");
            commentTimestamp.getStyle().set("padding", "0").set("margin", "4px");

            comment_block.add(commentAuthor);
            comment_block.add(commentAuthorEmail);
            comment_block.add(commentContent);
            comment_block.add(commentTimestamp);
            comment_block.getStyle()
                    .set("padding-left", "16px")
                    .set("padding-right", "16px")
                    .set("padding-bottom", "16px")
                    .set("padding-top", "24px")
                    .set("margin-bottom", "8px")
                    .set("margin-top", "8px")
                    .set("background-color", "rgba(255, 255, 255, 0.1)")
                    .set("border-radius", "8px")
                    .set("width", "100%")
            ;
            commentsLayout.add(comment_block);

            position++;
        }
    }

    private void createCommentsFormBinder() {
        commentsDTOBinder.setBean(new CommentsDTO());

        commentsDTOBinder
                .forField(nameField)
                .withValidator((value, context) -> {
                    if (StringUtils.isBlank(value)) {
                        return ValidationResult.error("Please enter your name!");
                    }
                    return ValidationResult.ok();
                })
                .bind(CommentsDTO::getName, CommentsDTO::setName);

        commentsDTOBinder
                .forField(emailField)
                .withValidator(new EmailValidator("Invalid e-mail address {0}"))
                .bind(CommentsDTO::getEmail, CommentsDTO::setEmail);



        commentsDTOBinder
                .forField(contentTextArea)
                .withValidator((value, context) -> {
                    if (StringUtils.isBlank(value)) {
                        return ValidationResult.error("Content can't be empty!");
                    }
                    return ValidationResult.ok();
                })
                .bind(CommentsDTO::getComment, CommentsDTO::setComment);

        commentsDTOBinder.addValueChangeListener(event -> commentsDTOBinder.validate());
    }

    public void clearCommentsForm() {
        commentsDTOBinder.removeBean();

        nameField.clear();
        emailField.clear();
        contentTextArea.clear();
    }

    private void setImage(@Nullable byte[] imageBytes) {
        if (imageBytes == null) {
            image.removeAll();
            return;
        }

        StreamResource streamResource = new StreamResource(
                UUID.randomUUID() + ".jpeg",
                () -> new ByteArrayInputStream(imageBytes)
        );
        image.setSrc(streamResource);
    }

    private void clear() {
        title.removeAll();
        previewText.removeAll();
        author.removeAll();
        date.removeAll();
        content.clear();

        image.removeAll();
        image.setSrc("");
    }

    private void styleImage() {
        image.getStyle()
                .set("width", "100%")
                .set("height", "auto")
                .set("border-radius", "1em 1em 0 0");
    }

    private void styleTitle() {
        title.getStyle()
                .set("margin", "0");
    }

    private void styleAuthor() {
        Icon icon = VaadinIcon.USER.create();
        icon.getStyle().set("margin-left", "0.5em");
        author.add(icon);
        author.getElement().getThemeList().add("badge primary");
        author.getStyle()
                .set("margin", "0.75em 1.5em 0em auto");
    }

    private void stylePreviewText() {
        previewText.getStyle()
                .set("font-style", "italic")
                .set("color", "gray")
                .set("margin-left", "2em");
    }

    private void styleContent() {
        content.setReadOnly(true);
        content.setWidthFull();
    }

    private void fetchComments() {
        comments = commentsApi.getComments(NewtifyWebApplication.newtifyStateService.getCurrentArticleId()).getBody();
    }
}
