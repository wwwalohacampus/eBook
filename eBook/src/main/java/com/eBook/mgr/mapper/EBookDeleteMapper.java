package com.eBook.mgr.mapper;

public interface EBookDeleteMapper {
	public void deleteBookcube(String writerId) throws Exception;
	public void deleteEpub(String writerId) throws Exception;
	public void deleteJoara(String writerId) throws Exception;
	public void deleteKakao(String writerId) throws Exception;
	public void deletekyobo(String writerId) throws Exception;
	public void deleteMrblue(String writerId) throws Exception;
	public void deleteMunpia(String writerId) throws Exception;
	public void deleteNaver(String writerId) throws Exception;
	public void deleteRidibooks(String writerId) throws Exception;
	public void deleteRomance(String writerId) throws Exception;
	public void deleteTocsoda(String writerId) throws Exception;
	public void deleteWinstore(String writerId) throws Exception;
}
