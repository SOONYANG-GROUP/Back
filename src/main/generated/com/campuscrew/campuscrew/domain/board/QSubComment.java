package com.campuscrew.campuscrew.domain.board;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSubComment is a Querydsl query type for SubComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSubComment extends EntityPathBase<SubComment> {

    private static final long serialVersionUID = -564743388L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSubComment subComment1 = new QSubComment("subComment1");

    public final QComment comment;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath subComment = createString("subComment");

    public final com.campuscrew.campuscrew.domain.user.QUser user;

    public QSubComment(String variable) {
        this(SubComment.class, forVariable(variable), INITS);
    }

    public QSubComment(Path<? extends SubComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSubComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSubComment(PathMetadata metadata, PathInits inits) {
        this(SubComment.class, metadata, inits);
    }

    public QSubComment(Class<? extends SubComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.comment = inits.isInitialized("comment") ? new QComment(forProperty("comment"), inits.get("comment")) : null;
        this.user = inits.isInitialized("user") ? new com.campuscrew.campuscrew.domain.user.QUser(forProperty("user")) : null;
    }

}

