package com.eBook.mgr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.eBook.mgr.dto.PaymentDto;

@Mapper
public interface PaymentMapper {
	public List<PaymentDto> listPayment(String setDate) throws Exception;
	
	public void createPayment(PaymentDto dto) throws Exception;
}
