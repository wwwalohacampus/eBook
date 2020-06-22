package com.eBook.mgr.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EBookDeleteMapper {
	public void deleteBookcube(String idx) throws Exception;
	//public void deleteEpub(String writerId) throws Exception;
	public void deleteJoara(String idx) throws Exception;
	public void deleteKakao(String idx) throws Exception;
	public void deletekyobo(String idx) throws Exception;
	public void deleteMrblue(String idx) throws Exception;
	public void deleteMunpia(String idx) throws Exception;
	public void deleteNaver(String idx) throws Exception;
	public void deleteRidibooks(String idx) throws Exception;
	public void deleteRomance(String idx) throws Exception;
	public void deleteTocsoda(String idx) throws Exception;
	public void deleteWinstore(String idx) throws Exception;
	public void deleteAladin(String idx) throws Exception;
	public void deleteYes24(String idx) throws Exception;
	
	public void allDeleteBookcube() throws Exception;
}
