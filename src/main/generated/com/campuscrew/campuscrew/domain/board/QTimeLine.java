package com.campuscrew.campuscrew.domain.board;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTimeLine is a Querydsl query type for TimeLine
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTimeLine extends EntityPathBase<TimeLine> {

    private static final long serialVersionUID = 1703362758L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTimeLine timeLine = new QTimeLine("timeLine");

    public final DateTimePath<java.time.LocalDateTime> createJobTime = createDateTime("createJobTime", java.time.LocalDateTime.class);

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QJob job;

    public final QParticipatedUsers participatedUsers;

    public final StringPath title = createString("title");

    public final DateTimePath<java.time.LocalDateTime> updateJobTime = createDateTime("updateJobTime", java.time.LocalDateTime.class);

    public final StringPath url = createString("url");

    public QTimeLine(String variable) {
        this(TimeLine.class, forVariable(variable), INITS);
    }

    public QTimeLine(Path<? extends TimeLine> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTimeLine(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTimeLine(PathMetadata metadata, PathInits inits) {
        this(TimeLine.class, metadata, inits);
    }

    public QTimeLine(Class<? extends TimeLine> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.job = inits.isInitialized("job") ? new QJob(forProperty("job"), inits.get("job")) : null;
        this.participatedUsers = inits.isInitialized("participatedUsers") ? new QParticipatedUsers(forProperty("participatedUsers"), inits.get("participatedUsers")) : null;
    }

}

