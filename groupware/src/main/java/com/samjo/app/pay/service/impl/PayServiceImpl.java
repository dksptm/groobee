package com.samjo.app.pay.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.samjo.app.common.service.SearchVO;
import com.samjo.app.ct.mapper.CtMapper;
import com.samjo.app.ct.service.CtService;
import com.samjo.app.ct.service.CtVO;
import com.samjo.app.pay.mapper.PayMapper;
import com.samjo.app.pay.service.GetTokenVO;
import com.samjo.app.pay.service.ImportResVO;
import com.samjo.app.pay.service.PayService;
import com.samjo.app.pay.service.PayVO;

@Service
public class PayServiceImpl implements PayService{

	@Autowired
	CtService ctservice;
	@Autowired
	PayMapper payMapper;
	@Autowired
	CtMapper ctMapper;
	@Autowired
	StringEncryptor jasyptStringEncryptor;

	//결제 전체조회
	@Override
	public List<PayVO> payList(SearchVO searchVO) {
		return payMapper.selectPayAll(searchVO);
	}

	//결제 페이징
	@Override
	public int count(SearchVO searchVO) {
		return payMapper.payCount(searchVO);
	}

	//결제처리
	@Override
	public String getToken() {
		
		RestTemplate restTemplate = new RestTemplate();
	
		//서버로 요청할 Header
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    
	    Map<String, Object> map = new HashMap<>();
	    map.put("imp_key", jasyptStringEncryptor.decrypt("kO2u4ZT4ezy1/lww239rOWpXniHeS5N4s74PoEX9uMZ3Ia4CLAHtx0g2zlP8L0ew9DJydXw2psQ5VrUr6uS5kA=="));
	    map.put("imp_secret", jasyptStringEncryptor.decrypt("gomT3MCD8uUZYf6ZxS91qaTBBIdEpN+WKq441/VywG9LXE2giwyNqFaLIIjSdq3cevOr4UEGNUv5z9YOT61kV4mdQGEGyUpW1dXlUIOGHH29PpoxmGcctLocmTC1MrAoM+b/9rm1j3JPJKbzR7x2tou4NP3RnKLgwwEbINukN74="));
	   
	    Gson var = new Gson();
	    String json=var.toJson(map);
		//서버로 요청할 Body
	   
	    HttpEntity<String> entity = new HttpEntity<>(json,headers);
		return restTemplate.postForObject("https://api.iamport.kr/users/getToken", entity, String.class);
	}

	//최초정기결제 예약 및 DB생성
	@Override
	public String firstPay(String customer_uid, int ctNo, long merchant_uid) {
		CtVO ctVO = new CtVO();
		ctVO.setCtNo(ctNo);
		ctVO.setPayCheck(1);
		ctVO.setCustName("SYSDATE");
		ctservice.ctPayCheck(ctVO);

		PayVO fPayVO = new PayVO();
		fPayVO.setCtNo(ctNo);
		fPayVO.setCustNo(customer_uid);
		fPayVO.setMerchantUid(merchant_uid);
		payMapper.fstPayInsert(fPayVO);
		
		CtVO pCtVO = ctMapper.ctInfo(ctNo);
		int price = pCtVO.getCtAmt();
		
		String token = getToken();
		long timestamp = 0;
		Calendar cal = Calendar.getInstance();
		Date payDay;
		cal.add(Calendar.MINUTE, +1);
		payDay = cal.getTime();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.KOREA);
		String date = sdf.format(payDay);
		try {
			Date stp = sdf.parse(date);
			timestamp = stp.getTime()/1000;
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		Gson str = new Gson(); 
		token = token.substring(token.indexOf("response") +10); 
		token = token.substring(0, token.length() - 1);
		GetTokenVO vo = str.fromJson(token, GetTokenVO.class);
		String access_token = vo.getAccess_token();
		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(access_token);
		 
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("merchant_uid", timestamp);
		jsonObject.addProperty("schedule_at", timestamp);
		jsonObject.addProperty("amount", price);
		
		JsonArray jsonArr = new JsonArray();
		 
		jsonArr.add(jsonObject); JsonObject reqJson = new JsonObject();
		 
		reqJson.addProperty("customer_uid", customer_uid); 
		reqJson.add("schedules",jsonArr);
		String json = str.toJson(reqJson); 
		HttpEntity<String> entity = new HttpEntity<>(json, headers);
		
		String response = restTemplate.postForObject("https://api.iamport.kr/subscribe/payments/schedule", entity, String.class);
		System.out.println("최초 결제등록 API응답 : " + response);

		PayVO payVO = new PayVO();
		payVO.setCustNo(customer_uid);
		payVO.setCtNo(ctNo);
		payVO.setServAmt(price);
		payVO.setPayExpcDt(payDay);
		payVO.setMerchantUid(timestamp);
		int result = payMapper.payInsert(payVO);
		
		if(result == 1) {
			Timer m_timer = new Timer();
			TimerTask m_task = new TimerTask() {
				@Override
				public void run() {
					List<PayVO> pList = payMapper.selectWaitPay();
			    	if(pList != null) {
			    		for(PayVO payVO: pList) {
			    			String result = payResultCheck(payVO.getMerchantUid(), payVO.getCtNo());
			    			if(result.equals("UPDATE")) {
			    				if(ctMapper.selectCtPayCheck(payVO.getCtNo()) == 1) {
			    					schedulePay(payVO.getCustNo(), payVO.getCtNo());
			    				};
			    			};
			    		};
			    	};
				}
			};
			m_timer.schedule(m_task, 61000);
		}
		return response;
	}
	
	//정기결제 예약 및 DB생성
	@Override
	public String schedulePay(String customer_uid, int ctNo) {
		CtVO pCtVO = ctMapper.ctInfo(ctNo);
		int price = pCtVO.getCtAmt();
		Date ctPayDay = pCtVO.getCtPayDt();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.KOREA);
		SimpleDateFormat sdfM = new SimpleDateFormat("yyyy/MM/", Locale.KOREA);
		SimpleDateFormat sdfD = new SimpleDateFormat("dd HH:mm", Locale.KOREA);
		
		String token = getToken();
		long timestamp = 0;
		Calendar cal = Calendar.getInstance();
		Date payDay;
		cal.add(Calendar.MONTH, +1);
		payDay = cal.getTime();
		String date = "";
		if(ctPayDay != null) {
			date = sdfM.format(payDay) + sdfD.format(ctPayDay);
		}else {
			date = sdf.format(payDay);
		}
		System.out.println("DATE : "+ date);
		
		try {
			Date stp = sdf.parse(date);
			timestamp = stp.getTime()/1000;
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		Gson str = new Gson(); 
		token = token.substring(token.indexOf("response") +10); 
		token = token.substring(0, token.length() - 1);
		GetTokenVO vo = str.fromJson(token, GetTokenVO.class);
		String access_token = vo.getAccess_token();
		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(access_token);
		 
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("merchant_uid", timestamp);
		jsonObject.addProperty("schedule_at", timestamp);
		jsonObject.addProperty("amount", price);
		
		JsonArray jsonArr = new JsonArray();
		 
		jsonArr.add(jsonObject); JsonObject reqJson = new JsonObject();
		 
		reqJson.addProperty("customer_uid", customer_uid); 
		reqJson.add("schedules",jsonArr);
		String json = str.toJson(reqJson); 
		HttpEntity<String> entity = new HttpEntity<>(json, headers);
		
		String response = restTemplate.postForObject("https://api.iamport.kr/subscribe/payments/schedule", entity, String.class);
	 	PayVO payVO = new PayVO();
		payVO.setCustNo(customer_uid);
		payVO.setCtNo(ctNo);
		payVO.setServAmt(price);
		payVO.setPayExpcDt(payDay);
		payVO.setMerchantUid(timestamp);
		payMapper.payInsert(payVO);
		System.out.println("정기결제 API응답 : " + response);
		return response;
	}

	//예약된 결제건 입금여부 확인
	@Override
	public String payResultCheck(long merchantUid, int ctNo) {
		String token = getToken();
		System.out.println("token : " + token);
		
		Gson str = new Gson(); 
		token = token.substring(token.indexOf("response") +10); 
		token = token.substring(0, token.length() - 1);
		GetTokenVO vo = str.fromJson(token, GetTokenVO.class);
		String access_token = vo.getAccess_token();
		System.out.println("access_token : " + access_token);

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(access_token);
		
		Gson var = new Gson();
		String json = var.toJson("");
		HttpEntity<String> entity = new HttpEntity<>(json, headers);
		
		String url = "https://api.iamport.kr/subscribe/payments/schedule/";
		String fullUrl = url + merchantUid;
		String list = restTemplate.exchange(fullUrl, HttpMethod.GET, entity, String.class).getBody();
		
		list = list.substring(list.indexOf("payment_status\":\"") + 17);
		list = list.substring(0, 4);
		String result = "NOT UPDATE";
		if(list.equals("paid")){
			PayVO payVO = new PayVO();
			payVO.setCtNo(ctNo);
			payVO.setMerchantUid(merchantUid);
			payMapper.payUpdate(payVO);
			result = "UPDATE";
		}
		return result;
	}
	
	//결제 취소
	@Override
	public String cancelPay(int ctNo, String customer_uid) {
		CtVO ctVO = new CtVO();
		ctVO.setCtNo(ctNo);
		ctVO.setPayCheck(0);
		ctVO.setCustName("NULL");
		ctservice.ctPayCheck(ctVO);
		
		String token = getToken();
		
		Gson str = new Gson(); 
		token = token.substring(token.indexOf("response") +10); 
		token = token.substring(0, token.length() - 1);
		GetTokenVO vo = str.fromJson(token, GetTokenVO.class);
		String access_token = vo.getAccess_token();
		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(access_token);

		JsonObject reqJson = new JsonObject();
		 
		reqJson.addProperty("customer_uid", customer_uid); 
		String json = str.toJson(reqJson); 
		HttpEntity<String> entity = new HttpEntity<>(json, headers);
		
		String response = restTemplate.postForObject("https://api.iamport.kr/subscribe/payments/unschedule", entity, String.class);
		payMapper.cancelPay(ctNo);
		return "";
	}
	
	@Override
	public List<PayVO> custPayList(SearchVO searchVO) {
		return payMapper.selectCustPayAll(searchVO);
	}
	
	@Override
	public int custCount(String custNo) {
		return payMapper.custPayCount(custNo);
	}
	
	//이하 삭제예정 ========================================================================================================================
	//dateStamp처리
	@Override
	public long dateStamp(String dateString) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date date = sdf.parse(dateString); 
		long unixTimestamp = date.getTime() / 1000; 
		return unixTimestamp;
	}
	
	//정기결제 내역조회
	@Override
	public String payLList() throws ParseException {
		String token = getToken();
		System.out.println("token : " + token);
		
		Gson str = new Gson(); 
		token = token.substring(token.indexOf("response") +10); 
		token = token.substring(0, token.length() - 1);
		GetTokenVO vo = str.fromJson(token, GetTokenVO.class);
		String access_token = vo.getAccess_token();
		System.out.println("access_token : " + access_token);

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(access_token);

		long a = dateStamp("2024/06/01");
		long b = dateStamp("2024/06/04");
		Map<String, Object> map = new HashMap<>();
		map.put("schedule_from", a);
		map.put("schedule_to", b);
		map.put("limit", "10");

		Gson var = new Gson();
		String json = var.toJson(map);
		System.out.println(json);
		HttpEntity<String> entity = new HttpEntity<>(json, headers);
		
		String url = "https://api.iamport.kr/subscribe/payments/schedule";
		String queryParams = String.format("?schedule_from=%d&schedule_to=%d&limit=%s", a, b, "10");
		String fullUrl = url + queryParams;
		//String list = restTemplate.getForObject(fullUrl, String.class);
		String list = restTemplate.exchange(fullUrl, HttpMethod.GET, entity, String.class).getBody();
		System.out.println(list);
		int startIdx = list.indexOf("\"response\"");
	    startIdx = list.indexOf("{", startIdx); 
	    int endIdx = list.lastIndexOf("}") + 1;
	    String responseJson = list.substring(startIdx, endIdx);
	    System.out.println("test : " + responseJson);
		
		Gson gson = new Gson();
		ImportResVO resVO = gson.fromJson(responseJson, ImportResVO.class);
		System.out.println(resVO);
		return list; 
	}


}
