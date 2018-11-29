package com.cyhee.android.rabit.model

enum class ContentStatus {
    // 활성
    ACTIVE,
    // 비활성
    INACTIVE,
    // 금지됨
    FORBIDDEN,
    // 삭제됨
    DELETED,
    // 게시 보류중
    PENDING,
    // 신고됨
    REPORTED
}

enum class ContentType(val korean: String) {
    USER("유저"), FOLLOW("팔로우"), GOAL("래빗"), GOALLOG("캐럿"), COMMENT("댓글"), FILE("파일"), LIKE("응원")
}

enum class RadioStatus {
    // 활성
    ACTIVE,
    // 비활성
    INACTIVE,
    // 금지됨
    FORBIDDEN;
}

enum class ReportType {
    /* 비방글 */
    INSULT,
    /* 음란물 */
    PORN,
    /* 부적절한 컨탠츠 */
    INAPT,
    /* 기타 */
    ETC;
}