package com.campuscrew.campuscrew.domain.board;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRequiredField is a Querydsl query type for RequiredField
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRequiredField extends EntityPathBase<RequiredField> {

    private static final long serialVersionUID = -1272484010L;

    public static final QRequiredField requiredField = new QRequiredField("requiredField");

    public final StringPath developer = createString("developer");

    public final StringPath field = createString("field");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> maxRecruits = createNumber("maxRecruits", Integer.class);

    public final NumberPath<Integer> recruits = createNumber("recruits", Integer.class);

    public QRequiredField(String variable) {
        super(RequiredField.class, forVariable(variable));
    }

    public QRequiredField(Path<? extends RequiredField> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRequiredField(PathMetadata metadata) {
        super(RequiredField.class, metadata);
    }

}

