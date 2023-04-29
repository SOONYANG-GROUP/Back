package com.campuscrew.campuscrew.domain.board;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QJob is a Querydsl query type for Job
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QJob extends EntityPathBase<Job> {

    private static final long serialVersionUID = -645253064L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QJob job = new QJob("job");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath jobDescription = createString("jobDescription");

    public final StringPath jobTitle = createString("jobTitle");

    public final QProject project;

    public final DateTimePath<java.time.LocalDateTime> startJobDate = createDateTime("startJobDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> updateJobDate = createDateTime("updateJobDate", java.time.LocalDateTime.class);

    public QJob(String variable) {
        this(Job.class, forVariable(variable), INITS);
    }

    public QJob(Path<? extends Job> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QJob(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QJob(PathMetadata metadata, PathInits inits) {
        this(Job.class, metadata, inits);
    }

    public QJob(Class<? extends Job> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.project = inits.isInitialized("project") ? new QProject(forProperty("project"), inits.get("project")) : null;
    }

}

