package com.eBook.mgr.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.eBook.mgr.mapper.EBookCreateMapper;
import com.eBook.mgr.mapper.EBookDeleteMapper;
import com.eBook.mgr.mapper.EBookListMapper;

@Service
public class EBookServiceImpl implements EBookService{
	
	@Autowired
	EBookCreateMapper createMapper;
	
	@Autowired
	EBookDeleteMapper deleteMapper;
	
	@Autowired
	EBookListMapper listMapper;
	
	
	
	// 등록단---------------------------------------------------------------------------
	@Override
	public void registerBookcube(Bookcube bookcube) throws Exception {
		// TODO Auto-generated method stub
		createMapper.createBookcube(bookcube);
	}

//	@Override
//	public void registerEpub(Epub epub) throws Exception {
//		// TODO Auto-generated method stub
//		createMapper.createEpub(epub);
//	}

	@Override
	public void registerJoara(Joara joara) throws Exception {
		// TODO Auto-generated method stub
		createMapper.createJoara(joara);
	}

	@Override
	public void registerKakao(Kakao kakao) throws Exception {
		// TODO Auto-generated method stub
		createMapper.createKakao(kakao);
	}

	@Override
	public void registerkyobo(kyobo kyobo) throws Exception {
		// TODO Auto-generated method stub
		createMapper.createKyobo(kyobo);
	}

	@Override
	public void registerMrblue(Mrblue mrblue) throws Exception {
		// TODO Auto-generated method stub
		createMapper.createMrblue(mrblue);
	}

	@Override
	public void registerMunpia(Munpia munpia) throws Exception {
		// TODO Auto-generated method stub
		createMapper.createMunpia(munpia);
	}

	@Override
	public void registerNaver(Naver naver) throws Exception {
		// TODO Auto-generated method stub
		createMapper.createNaver(naver);
	}

	@Override
	public void registerRidibooks(Ridibooks ridibooks) throws Exception {
		// TODO Auto-generated method stub
		createMapper.createRidibooks(ridibooks);
	}

	@Override
	public void registerRomance(Romance romance) throws Exception {
		// TODO Auto-generated method stub
		createMapper.createRomance(romance);
	}

	@Override
	public void registerTocsoda(Tocsoda tocsoda) throws Exception {
		// TODO Auto-generated method stub
		createMapper.createTocsoda(tocsoda);
	}

	@Override
	public void registerWinstore(Winstore winstore) throws Exception {
		// TODO Auto-generated method stub
		createMapper.createWinstore(winstore);
	}
	
	
	
	
	
	
	
	
	// 삭제단---------------------------------------------------------------------------------
	@Override
	public void removeBookcube(String writerId) throws Exception {
		// TODO Auto-generated method stub
		deleteMapper.deleteBookcube(writerId);
	}

//	@Override
//	public void removeEpub(String writerId) throws Exception {
//		// TODO Auto-generated method stub
//		deleteMapper.deleteEpub(writerId);
//	}

	@Override
	public void removeJoara(String writerId) throws Exception {
		// TODO Auto-generated method stub
		deleteMapper.deleteJoara(writerId);
	}

	@Override
	public void removeKakao(String writerId) throws Exception {
		// TODO Auto-generated method stub
		deleteMapper.deleteKakao(writerId);
	}

	@Override
	public void removekyobo(String writerId) throws Exception {
		// TODO Auto-generated method stub
		deleteMapper.deletekyobo(writerId);
	}

	@Override
	public void removeMrblue(String writerId) throws Exception {
		// TODO Auto-generated method stub
		deleteMapper.deleteMrblue(writerId);
	}

	@Override
	public void removeMunpia(String writerId) throws Exception {
		// TODO Auto-generated method stub
		deleteMapper.deleteMunpia(writerId);
	}

	@Override
	public void removeNaver(String writerId) throws Exception {
		// TODO Auto-generated method stub
		deleteMapper.deleteNaver(writerId);
	}

	@Override
	public void removeRidibooks(String writerId) throws Exception {
		// TODO Auto-generated method stub
		deleteMapper.deleteRidibooks(writerId);
	}

	@Override
	public void removeRomance(String writerId) throws Exception {
		// TODO Auto-generated method stub
		deleteMapper.deleteRomance(writerId);
	}

	@Override
	public void removeTocsoda(String writerId) throws Exception {
		// TODO Auto-generated method stub
		deleteMapper.deleteTocsoda(writerId);
	}

	@Override
	public void removeWinstore(String writerId) throws Exception {
		// TODO Auto-generated method stub
		deleteMapper.deleteWinstore(writerId);
	}

	
	
	
	
	
	// 조회단---------------------------------------------------------------------------
	@Override
	public List<Bookcube> listBookcube(Date setDate) throws Exception {
		// TODO Auto-generated method stub
		return listMapper.listBookcube(setDate);
	}

//	@Override
//	public List<Epub> listEpub(Date setDate) throws Exception {
//		// TODO Auto-generated method stub
//		return listMapper.listEpub(setDate);
//	}

	@Override
	public List<Joara> listJoara(Date setDate) throws Exception {
		// TODO Auto-generated method stub
		return listMapper.listJoara(setDate);
	}

	@Override
	public List<Kakao> listKakao(Date setDate) throws Exception {
		// TODO Auto-generated method stub
		return listMapper.listKakao(setDate);
	}

	@Override
	public List<kyobo> listkyobo(Date setDate) throws Exception {
		// TODO Auto-generated method stub
		return listMapper.listkyobo(setDate);
	}

	@Override
	public List<Mrblue> listMrblue(Date setDate) throws Exception {
		// TODO Auto-generated method stub
		return listMapper.listMrblue(setDate);
	}

	@Override
	public List<Munpia> listMunpia(Date setDate) throws Exception {
		// TODO Auto-generated method stub
		return listMapper.listMunpia(setDate);
	}

	@Override
	public List<Naver> listNaver(Date setDate) throws Exception {
		// TODO Auto-generated method stub
		return listMapper.listNaver(setDate);
	}

	@Override
	public List<Ridibooks> listRidibooks(Date setDate) throws Exception {
		// TODO Auto-generated method stub
		return listMapper.listRidibooks(setDate);
	}

	@Override
	public List<Romance> listRomance(Date setDate) throws Exception {
		// TODO Auto-generated method stub
		return listMapper.listRomance(setDate);
	}

	@Override
	public List<Tocsoda> listTocsoda(Date setDate) throws Exception {
		// TODO Auto-generated method stub
		return listMapper.listTocsoda(setDate);
	}

	@Override
	public List<Winstore> listWinstore(Date setDate) throws Exception {
		// TODO Auto-generated method stub
		return listMapper.listWinstore(setDate);
	}

	


}
