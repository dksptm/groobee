package com.samjo.app.pay.service;

import java.util.List;

import lombok.Data;

@Data
public class ImportResVO {
	private int total;
    private int previous;
    private int next;
    private List<PaymentListVO> list;
}
