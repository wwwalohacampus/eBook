package com.eBook.mgr.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eBook.mgr.dto.PaymentDto;
import com.eBook.mgr.mapper.PaymentMapper;

@Service
public class PaymentServiceImpl implements PaymentService{

	@Autowired
	PaymentMapper paymentMapper;
	
	@Override
	public List<PaymentDto> listPayment(String setDate) throws Exception {
		// TODO Auto-generated method stub
		return paymentMapper.listPayment(setDate);
	}

	@Override
	public void insertPayment(PaymentDto dto) throws Exception {
		// TODO Auto-generated method stub
		paymentMapper.createPayment(dto);
	}

}
