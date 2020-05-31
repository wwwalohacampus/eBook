package com.eBook.mgr.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.eBook.mgr.domain.platform.Bookcube;

@Mapper
public interface EBookMapper {
	public void bookcubeCreate(Bookcube bookcube) throws Exception;
	public void epubCreate(Bookcube bookcube) throws Exception;
	public void joaraCreate(Bookcube bookcube) throws Exception;
	public void kakaoCreate(Bookcube bookcube) throws Exception;
	public void kyoboCreate(Bookcube bookcube) throws Exception;
	public void MrblueCreate(Bookcube bookcube) throws Exception;
	public void MunpiaCreate(Bookcube bookcube) throws Exception;
	public void naverCreate(Bookcube bookcube) throws Exception;
	public void ridibooksCreate(Bookcube bookcube) throws Exception;
	public void romanceCreate(Bookcube bookcube) throws Exception;
	public void tocsodaCreate(Bookcube bookcube) throws Exception;
	public void winstoreCreate(Bookcube bookcube) throws Exception;
}
