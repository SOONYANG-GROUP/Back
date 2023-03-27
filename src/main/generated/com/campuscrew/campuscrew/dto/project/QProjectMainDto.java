package com.campuscrew.campuscrew.dto.project;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.campuscrew.campuscrew.dto.project.QProjectMainDto is a Querydsl Projection type for ProjectMainDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QProjectMainDto extends ConstructorExpression<ProjectMainDto> {

    private static final long serialVersionUID = 2088627358L;

    public QProjectMainDto(com.querydsl.core.types.Expression<? extends java.util.List<RecruitUserDto>> recruitUserDtos, com.querydsl.core.types.Expression<? extends java.util.List<ReferenceDto>> referenceDtos, com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<java.time.LocalDateTime> createDateTime, com.querydsl.core.types.Expression<String> title, com.querydsl.core.types.Expression<String> description) {
        super(ProjectMainDto.class, new Class<?>[]{java.util.List.class, java.util.List.class, long.class, java.time.LocalDateTime.class, String.class, String.class}, recruitUserDtos, referenceDtos, id, createDateTime, title, description);
    }

}

