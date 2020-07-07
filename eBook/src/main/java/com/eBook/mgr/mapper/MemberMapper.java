package com.eBook.mgr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.eBook.mgr.domain.Author;
import com.eBook.mgr.domain.Member;
import com.eBook.mgr.domain.MemberAuth;
import com.eBook.mgr.dto.AuthorListDto;

@Mapper
public interface MemberMapper {
	public void createMember(Member member) throws Exception;
	public void createAuthor(Author author) throws Exception;

	public void createAuth(MemberAuth memberAuth);
	
	public Member readByUserId(String userId);
	
	public List<AuthorListDto> list() throws Exception;
	
	public Member read(String userId) throws Exception;
	
	public void deleteMember(String writerId) throws Exception;
}