package com.campuscrew.campuscrew.domain.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 1255502801L;

    public static final QUser user = new QUser("user");

    public final BooleanPath acceptAlarm = createBoolean("acceptAlarm");

    public final NumberPath<Integer> age = createNumber("age", Integer.class);

    public final ListPath<Alarm, QAlarm> alarms = this.<Alarm, QAlarm>createList("alarms", Alarm.class, QAlarm.class, PathInits.DIRECT2);

    public final ListPath<com.campuscrew.campuscrew.domain.board.Comment, com.campuscrew.campuscrew.domain.board.QComment> comments = this.<com.campuscrew.campuscrew.domain.board.Comment, com.campuscrew.campuscrew.domain.board.QComment>createList("comments", com.campuscrew.campuscrew.domain.board.Comment.class, com.campuscrew.campuscrew.domain.board.QComment.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final StringPath detailField = createString("detailField");

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<com.campuscrew.campuscrew.domain.board.ParticipatedUsers, com.campuscrew.campuscrew.domain.board.QParticipatedUsers> joinedUsers = this.<com.campuscrew.campuscrew.domain.board.ParticipatedUsers, com.campuscrew.campuscrew.domain.board.QParticipatedUsers>createList("joinedUsers", com.campuscrew.campuscrew.domain.board.ParticipatedUsers.class, com.campuscrew.campuscrew.domain.board.QParticipatedUsers.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final ListPath<com.campuscrew.campuscrew.domain.board.Project, com.campuscrew.campuscrew.domain.board.QProject> projects = this.<com.campuscrew.campuscrew.domain.board.Project, com.campuscrew.campuscrew.domain.board.QProject>createList("projects", com.campuscrew.campuscrew.domain.board.Project.class, com.campuscrew.campuscrew.domain.board.QProject.class, PathInits.DIRECT2);

    public final StringPath refreshToken = createString("refreshToken");

    public final EnumPath<Role> role = createEnum("role", Role.class);

    public final StringPath selfIntroduction = createString("selfIntroduction");

    public final StringPath shortIntroduction = createString("shortIntroduction");

    public final StringPath socialId = createString("socialId");

    public final EnumPath<SocialType> socialType = createEnum("socialType", SocialType.class);

    public final ListPath<com.campuscrew.campuscrew.domain.board.SubComment, com.campuscrew.campuscrew.domain.board.QSubComment> subComments = this.<com.campuscrew.campuscrew.domain.board.SubComment, com.campuscrew.campuscrew.domain.board.QSubComment>createList("subComments", com.campuscrew.campuscrew.domain.board.SubComment.class, com.campuscrew.campuscrew.domain.board.QSubComment.class, PathInits.DIRECT2);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

