package com.eBook.mgr.service;

import java.util.List;

import com.eBook.mgr.domain.Author;
import com.eBook.mgr.domain.Member;
import com.eBook.mgr.dto.AuthorListDto;

public interface MemberService {
	public void registerMember(Member member) throws Exception;
	public void registerAuthor(Author author) throws Exception;
	
	public List<AuthorListDto> list() throws Exception;
	
	public Member read(String userId) throws Exception;
}
