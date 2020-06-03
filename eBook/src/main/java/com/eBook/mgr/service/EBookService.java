package com.eBook.mgr.service;

import java.util.Date;
import java.util.List;

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

public interface EBookService {
	
	// 등록 로직 ----------------------------------------------------------------------
	public void registerBookcube(Bookcube bookcube) throws Exception;
	public void registerEpub(Epub epub) throws Exception;
	public void registerJoara(Joara joara) throws Exception;
	public void registerKakao(Kakao kakao) throws Exception;
	public void registerkyobo(kyobo kyobo) throws Exception;
	public void registerMrblue(Mrblue mrblue) throws Exception;
	public void registerMunpia(Munpia munpia) throws Exception;
	public void registerNaver(Naver naver) throws Exception;
	public void registerRidibooks(Ridibooks ridibooks) throws Exception;
	public void registerRomance(Romance romance) throws Exception;
	public void registerTocsoda(Tocsoda tocsoda) throws Exception;
	public void registerWinstore(Winstore winstore) throws Exception;
	
	
	
	
	
	// 삭제 로직 ---------------------------------------------------------------------
	public void removeBookcube(String writerId) throws Exception;
	public void removeEpub(String writerId) throws Exception;
	public void removeJoara(String writerId) throws Exception;
	public void removeKakao(String writerId) throws Exception;
	public void removekyobo(String writerId) throws Exception;
	public void removeMrblue(String writerId) throws Exception;
	public void removeMunpia(String writerId) throws Exception;
	public void removeNaver(String writerId) throws Exception;
	public void removeRidibooks(String writerId) throws Exception;
	public void removeRomance(String writerId) throws Exception;
	public void removeTocsoda(String writerId) throws Exception;
	public void removeWinstore(String writerId) throws Exception;
	
	
	
	
	
	
	// 조회 로직 ----------------------------------------------------------------------
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

