package com.samjo.app.pay.service;

import lombok.Data;

@Data
public class GetTokenVO {
	private String access_token;
	private long now;
	private long expired_at;
}
