package com.eBook.mgr.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EBookDeleteMapper {
	public void deleteBookcube(String idx) throws Exception;
	public void deleteEpub(String idx) throws Exception;
	public void deleteJoara(String idx) throws Exception;
	public void deleteKakao(String idx) throws Exception;
	public void deleteKyobo(String idx) throws Exception;
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
	public void allDeleteEpub() throws Exception;
	public void allDeleteJoara() throws Exception;
	public void allDeleteKakao() throws Exception;
	public void allDeleteKyobo() throws Exception;
	public void allDeleteMrblue() throws Exception;
	public void allDeleteMunpia() throws Exception;
	public void allDeleteNaver() throws Exception;
	public void allDeleteRidibooks() throws Exception;
	public void allDeleteRomance() throws Exception;
	public void allDeleteTocsoda() throws Exception;
	public void allDeleteWinstore() throws Exception;
	public void allDeleteAladin() throws Exception;
	public void allDeleteYes24() throws Exception;
}
