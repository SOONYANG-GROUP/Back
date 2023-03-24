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

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProject project = new QProject("project");

    public final ListPath<Comment, QComment> comments = this.<Comment, QComment>createList("comments", Comment.class, QComment.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> createdDateTime = createDateTime("createdDateTime", java.time.LocalDateTime.class);

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<JoinedUser, QJoinedUser> joinedUsers = this.<JoinedUser, QJoinedUser>createList("joinedUsers", JoinedUser.class, QJoinedUser.class, PathInits.DIRECT2);

    public final StringPath openChatUrl = createString("openChatUrl");

    public final ListPath<Reference, QReference> references = this.<Reference, QReference>createList("references", Reference.class, QReference.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> startWithDate = createDateTime("startWithDate", java.time.LocalDateTime.class);

    public final StringPath title = createString("title");

    public final DateTimePath<java.time.LocalDateTime> updatedDateTime = createDateTime("updatedDateTime", java.time.LocalDateTime.class);

    public final com.campuscrew.campuscrew.domain.user.QUser user;

    public final StringPath voiceChatUrl = createString("voiceChatUrl");

    public QProject(String variable) {
        this(Project.class, forVariable(variable), INITS);
    }

    public QProject(Path<? extends Project> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProject(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProject(PathMetadata metadata, PathInits inits) {
        this(Project.class, metadata, inits);
    }

    public QProject(Class<? extends Project> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.campuscrew.campuscrew.domain.user.QUser(forProperty("user")) : null;
    }

}

