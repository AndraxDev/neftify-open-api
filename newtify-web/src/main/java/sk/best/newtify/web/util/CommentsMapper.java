package sk.best.newtify.web.util;

import lombok.NonNull;
import sk.best.newtify.api.dto.*;

/**
 * @author AndraxDev
 * Copyright © 2022 AndraxDev, BEST Technická univerzita Košice.
 * All rights reserved.
 */

import javax.annotation.PostConstruct;
import java.time.Instant;
public interface CommentsMapper {
    static CreateCommentsDTO toCreateComment(@NonNull CommentsDTO commentsDTO) {
        CreateCommentsDTO createCommentsDTO = new CreateCommentsDTO();
        createCommentsDTO.setCreatedAt(Instant.now().getEpochSecond());
        createCommentsDTO.setComment(commentsDTO.getComment());
        createCommentsDTO.setEmail(commentsDTO.getEmail());
        createCommentsDTO.setName(commentsDTO.getName());
        createCommentsDTO.setUuid(commentsDTO.getUuid());
        return createCommentsDTO;
    }
}
