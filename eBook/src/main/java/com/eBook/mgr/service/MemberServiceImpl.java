package com.eBook.mgr.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eBook.mgr.domain.Member;
import com.eBook.mgr.domain.MemberAuth;
import com.eBook.mgr.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService {
	
	private static final Logger log = LoggerFactory.getLogger(MemberServiceImpl.class);
	
	@Autowired
	private MemberMapper mapper;

	@Override
	public void register(Member member) throws Exception {
		mapper.create(member);
		
		String auth = "";
		if( member.getAuthNumber() == 0 )
			auth = "ROLE_USER";
		if( member.getAuthNumber() == 1 )
			auth = "ROLE_ADMIN";
		
		// member 테이블에 삽인한 후, idx 조회
		member = mapper.read(member.getId());
		
		MemberAuth memberAuth = new MemberAuth();
		memberAuth.setIdx(member.getIdx());
		memberAuth.setAuth(auth);

		log.info("id : " + member.getId());
		log.info("작가명 : " + member.getAuthor());
		log.info("본명 : " + member.getRealName());
		log.info("권한 : " + memberAuth.getAuth());
		
		
		mapper.createAuth(memberAuth);
	}

	@Override
	public List<Member> list() throws Exception {
		return mapper.list();
	}

	@Override
	public Member read(String userId) throws Exception {
		return mapper.read(userId);
	}
	
	
}
