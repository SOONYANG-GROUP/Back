package com.campuscrew.campuscrew.domain.board;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QJoinedUser is a Querydsl query type for JoinedUser
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QJoinedUser extends EntityPathBase<JoinedUser> {

    private static final long serialVersionUID = 1986088409L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QJoinedUser joinedUser = new QJoinedUser("joinedUser");

    public final StringPath id = createString("id");

    public final QProject project;

    public final EnumPath<JoinUserStatus> status = createEnum("status", JoinUserStatus.class);

    public final com.campuscrew.campuscrew.domain.user.QUser user;

    public QJoinedUser(String variable) {
        this(JoinedUser.class, forVariable(variable), INITS);
    }

    public QJoinedUser(Path<? extends JoinedUser> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QJoinedUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QJoinedUser(PathMetadata metadata, PathInits inits) {
        this(JoinedUser.class, metadata, inits);
    }

    public QJoinedUser(Class<? extends JoinedUser> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.project = inits.isInitialized("project") ? new QProject(forProperty("project")) : null;
        this.user = inits.isInitialized("user") ? new com.campuscrew.campuscrew.domain.user.QUser(forProperty("user")) : null;
    }

}

