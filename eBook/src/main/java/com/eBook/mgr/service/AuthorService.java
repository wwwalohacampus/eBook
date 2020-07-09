package com.eBook.mgr.service;

import com.eBook.mgr.domain.Author;
import com.eBook.mgr.dto.AuthorListDto;

public interface AuthorService {
	
	public AuthorListDto read(String writerId) throws Exception;
	
}
