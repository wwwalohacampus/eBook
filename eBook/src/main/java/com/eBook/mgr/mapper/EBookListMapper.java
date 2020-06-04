package com.eBook.mgr.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.eBook.mgr.domain.platform.Bookcube;
import com.eBook.mgr.domain.platform.Epub;
import com.eBook.mgr.domain.platform.Joara;
import com.eBook.mgr.domain.platform.Kakao;
import com.eBook.mgr.domain.platform.Mrblue;
import com.eBook.mgr.domain.platform.Munpia;
import com.eBook.mgr.domain.platform.Naver;
import com.eBook.mgr.domain.platform.Ridibooks;
import com.eBook.mgr.domain.platform.Romance;
import com.eBook.mgr.domain.platform.Tocsoda;
import com.eBook.mgr.domain.platform.Winstore;
import com.eBook.mgr.domain.platform.kyobo;

@Mapper
public interface EBookListMapper {
	public List<Bookcube> listBookcube(Date setDate) throws Exception;
	public List<Epub> listEpub(Date setDate) throws Exception;
	public List<Joara> listJoara(Date setDate) throws Exception;
	public List<Kakao> listKakao(Date setDate) throws Exception;
	public List<kyobo> listkyobo(Date setDate) throws Exception;
	public List<Mrblue> listMrblue(Date setDate) throws Exception;
	public List<Munpia> listMunpia(Date setDate) throws Exception;
	public List<Naver> listNaver(Date setDate) throws Exception;
	public List<Ridibooks> listRidibooks(Date setDate) throws Exception;
	public List<Romance> listRomance(Date setDate) throws Exception;
	public List<Tocsoda> listTocsoda(Date setDate) throws Exception;
	public List<Winstore> listWinstore(Date setDate) throws Exception;
}
