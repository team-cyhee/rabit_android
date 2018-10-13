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
    PENDING
}

enum class ContentType {
    USER, FOLLOW, GOAL, GOALLOG, COMMENT, FILE
}

enum class RadioStatus {
    // 활성
    ACTIVE,
    // 비활성
    INACTIVE,
    // 금지됨
    FORBIDDEN;
}