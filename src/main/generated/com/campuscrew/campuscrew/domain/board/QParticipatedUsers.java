package com.campuscrew.campuscrew.domain.board;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QParticipatedUsers is a Querydsl query type for ParticipatedUsers
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QParticipatedUsers extends EntityPathBase<ParticipatedUsers> {

    private static final long serialVersionUID = 422704797L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QParticipatedUsers participatedUsers = new QParticipatedUsers("participatedUsers");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QProject project;

    public final EnumPath<ParticipatedStatus> status = createEnum("status", ParticipatedStatus.class);

    public final com.campuscrew.campuscrew.domain.user.QUser user;

    public QParticipatedUsers(String variable) {
        this(ParticipatedUsers.class, forVariable(variable), INITS);
    }

    public QParticipatedUsers(Path<? extends ParticipatedUsers> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QParticipatedUsers(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QParticipatedUsers(PathMetadata metadata, PathInits inits) {
        this(ParticipatedUsers.class, metadata, inits);
    }

    public QParticipatedUsers(Class<? extends ParticipatedUsers> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.project = inits.isInitialized("project") ? new QProject(forProperty("project"), inits.get("project")) : null;
        this.user = inits.isInitialized("user") ? new com.campuscrew.campuscrew.domain.user.QUser(forProperty("user")) : null;
    }

}

