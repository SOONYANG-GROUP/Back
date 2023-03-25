package com.campuscrew.campuscrew.domain.board;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProject is a Querydsl query type for Project
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProject extends EntityPathBase<Project> {

    private static final long serialVersionUID = 1613771284L;

    public static final QProject project = new QProject("project");

    public final ListPath<Comment, QComment> comments = this.<Comment, QComment>createList("comments", Comment.class, QComment.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> createdDateTime = createDateTime("createdDateTime", java.time.LocalDateTime.class);

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath openChatUrl = createString("openChatUrl");

    public final ListPath<ParticipatedUsers, QParticipatedUsers> participatedUsers = this.<ParticipatedUsers, QParticipatedUsers>createList("participatedUsers", ParticipatedUsers.class, QParticipatedUsers.class, PathInits.DIRECT2);

    public final ListPath<Recruit, QRecruit> recruits = this.<Recruit, QRecruit>createList("recruits", Recruit.class, QRecruit.class, PathInits.DIRECT2);

    public final ListPath<Reference, QReference> references = this.<Reference, QReference>createList("references", Reference.class, QReference.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> startWithDate = createDateTime("startWithDate", java.time.LocalDateTime.class);

    public final StringPath title = createString("title");

    public final DateTimePath<java.time.LocalDateTime> updatedDateTime = createDateTime("updatedDateTime", java.time.LocalDateTime.class);

    public final StringPath voiceChatUrl = createString("voiceChatUrl");

    public QProject(String variable) {
        super(Project.class, forVariable(variable));
    }

    public QProject(Path<? extends Project> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProject(PathMetadata metadata) {
        super(Project.class, metadata);
    }

}

