package com.campuscrew.campuscrew.domain.board;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRecruit is a Querydsl query type for Recruit
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecruit extends EntityPathBase<Recruit> {

    private static final long serialVersionUID = -1289195975L;

    public static final QRecruit recruit = new QRecruit("recruit");

    public final NumberPath<Integer> currentRecruit = createNumber("currentRecruit", Integer.class);

    public final StringPath developer = createString("developer");

    public final StringPath field = createString("field");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> maxRecruit = createNumber("maxRecruit", Integer.class);

    public QRecruit(String variable) {
        super(Recruit.class, forVariable(variable));
    }

    public QRecruit(Path<? extends Recruit> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRecruit(PathMetadata metadata) {
        super(Recruit.class, metadata);
    }

}

