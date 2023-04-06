package com.campuscrew.campuscrew.domain.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLog is a Querydsl query type for Log
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLog extends EntityPathBase<Log> {

    private static final long serialVersionUID = -1760624002L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLog log = new QLog("log");

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<LogStatus> logStatus = createEnum("logStatus", LogStatus.class);

    public final com.campuscrew.campuscrew.domain.board.QProject project;

    public final StringPath projectName = createString("projectName");

    public final StringPath url = createString("url");

    public final QUser user;

    public QLog(String variable) {
        this(Log.class, forVariable(variable), INITS);
    }

    public QLog(Path<? extends Log> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLog(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLog(PathMetadata metadata, PathInits inits) {
        this(Log.class, metadata, inits);
    }

    public QLog(Class<? extends Log> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.project = inits.isInitialized("project") ? new com.campuscrew.campuscrew.domain.board.QProject(forProperty("project"), inits.get("project")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

