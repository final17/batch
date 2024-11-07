package org.sparta.batch.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseCode {
    // 공통
    SUCCESS("정상 처리되었습니다."),
    INVALID_TIMEOUT("다시 시도해주세요."),
    FORBIDDEN("접근 권한이 없습니다."),
    INVALID_TIME_UNIT("TimeUnit 값을 제대로 입력해주세요."),

    // 비밀번호 형식 에러
    INVALID_PASSWORD("비밀번호는 대소문자 포함 영문 + 숫자 + 특수문자를 최소 1글자씩 포함해야하며 최소 8글자 이상이어야 합니다."),

    // 사용자
    NOT_FOUND_USER("해당 사용자는 존재하지 않습니다."),
    NOT_FOUND_STORE("해당 가게는 존재하지 않습니다."),
    NOT_FOUND_MENU("해당 메뉴는 존재하지 않습니다."),
    NOT_FOUND_RESERVATION("해당 예약은 존재하지 않습니다."),
    INVALID_USER_AUTHORITY("해당 사용자 권한은 유효하지 않습니다."),
    DUPLICATE_EMAIL("이미 존재하는 이메일입니다."),
    WRONG_EMAIL_OR_PASSWORD("이메일 혹은 비밀번호가 일치하지 않습니다."),
    WRONG_PASSWORD("비밀번호가 일치하지 않습니다."),
    DUPLICATE_NICKNAME("이미 존재하는 닉네임입니다."),

    // 알레르기
    NOT_FOUND_ALLERGY("해당 알레르기는 존재하지 않습니다."),

    // 팔로우
    CANNOT_FOLLOW_SELF("자신을 팔로우할 수 없습니다."),

    // 매니저
    MANAGER_ALREADY_EXISTS("이미 매니저가 존재합니다."),

    // 연결 이슈
    CONNECTION_ERROR("연결 오류가 발생했습니다."),

    // token
    INVALID_TOKEN("잘못된 토큰입니다."),

    // 웨이팅예약 , 예약
    DUPLICATE_RESERVATION("이미 예약되어 있습니다."),
    STORE_NOT_OPEN("해당 식당은 오픈중이지 않습니다."),
    UNAUTHORIZED_RESERVATION("본인 예약 건이 아닙니다."),
    UNAUTHORIZED_STORE_RESERVATION("본인 가게에는 예약이 불가능합니다."),
    CANCEL_FORBIDDEN("해당 예약건은 취소가 불가능합니다."),
    REFUSAL_FORBIDDEN("해당 예약건은 거절이 불가능합니다."),
    APPLY_FORBIDDEN("해당 예약건은 거절이 불가능합니다."),
    COMPLETE_FORBIDDEN("해당 예약건은 완료가 불가능합니다."),
    INVALID_RESERVATION_TIME("예약 불가능한 시간대입니다."),
    ALREADY_WAITING("이미 웨이팅 중 입니다."),
    ALREADY_DELETED_USER("회원탈퇴한 유저입니다."),
    INVALID_AMOUNT("설정한 결제 금액과 다릅니다."),
    PAYMENT_NOT_FOUND("결제정보가 없습니다."),
    INSUFFICIENT_SEAT("좌석이 부족합니다."),
    INVALID_CART("장바구니 데이터를 올바르게 입력해주시길 바랍니다."),
    EMPTY_CART("장바구니가 비어있습니다.");

    private final String message;
}