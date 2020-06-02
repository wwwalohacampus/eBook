package com.eBook.mgr.domain;

import lombok.Data;


// 사용안함
@Data
public class Author {
	String id;								// ID
	String pw;								// PW
	String author;							// 작가명
	String realName;						// 본명
	String accountNumber;					// 계좌번호
	String residentRegistrationNumber;		// 주민번호
	String virtuousTax;						// 선인세
	String loginStatus;						// 로그인 활성/비활성
	String auth;							// 권한
	String settlementRatio;					// 정산비율
}
