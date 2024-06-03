package com.samjo.app.pay.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.samjo.app.pay.mapper.PayMapper;
import com.samjo.app.pay.service.GetTokenVO;
import com.samjo.app.pay.service.ImportResVO;
import com.samjo.app.pay.service.PayService;
import com.samjo.app.pay.service.PayVO;

@Service
public class PayServiceImpl implements PayService{

	@Autowired
	PayMapper payMapper;
	@Value("{app.data.impkey}")
	private String impKey;
	@Value("{app.data.imps}")
	private String impS;

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

	/*결제처리 테스트*/
	@Override
	public String getToken() {
		
		RestTemplate restTemplate = new RestTemplate();
	
		//서버로 요청할 Header
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    
	    Map<String, Object> map = new HashMap<>();
	    String test1 = impKey;
	    String test2 = impS;
	    System.out.println("@@@@@"+ test1);
	    System.out.println("@@@@@"+ test2);
	    map.put("imp_key", "6512613888230017");
	    map.put("imp_secret", "DOSk0YxWmmuak1WEwZBaduB0nrExkAujYf6NwTKBrJDB5w3ktJQHCiAyiyMfLe9lVz3tZki5Rk99yOuX");
	   
	    Gson var = new Gson();
	    String json=var.toJson(map);
		//서버로 요청할 Body
	   
	    HttpEntity<String> entity = new HttpEntity<>(json,headers);
		return restTemplate.postForObject("https://api.iamport.kr/users/getToken", entity, String.class);
	}

	//정기결제처리 테스트-삭제예정
	@Override
	public String requestSubPay() {
		String token = getToken();
		Gson str = new Gson();
		token = token.substring(token.indexOf("response") + 10);
		token = token.substring(0, token.length() - 1);

		GetTokenVO vo = str.fromJson(token, GetTokenVO.class);

		String access_token = vo.getAccess_token();
		System.out.println("access_token : "+access_token);

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(access_token);

		Map<String, Object> map = new HashMap<>();
		map.put("customer_uid", "24");
		map.put("merchant_uid", "162443471100");
		map.put("amount", "10000");
		map.put("name", "test05");

		Gson var = new Gson();
		String json = var.toJson(map);
		System.out.println(json);
		HttpEntity<String> entity = new HttpEntity<>(json, headers);
		
		return restTemplate.postForObject("https://api.iamport.kr/subscribe/payments/again", entity, String.class);
	}

	//정기결제 처리
	@Override
	public String schedulePay(String customer_uid, int price) {
		String token = getToken();
		System.out.println("token : " + token);
		long timestamp = 0;
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.KOREA);
		cal.add(Calendar.MINUTE, +1);
		String date = sdf.format(cal.getTime());
		try {
			Date stp = sdf.parse(date);
			timestamp = stp.getTime()/1000;
			System.out.println(timestamp);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
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
		 
		 JsonObject jsonObject = new JsonObject();
		 jsonObject.addProperty("merchant_uid", timestamp);
		 jsonObject.addProperty("schedule_at", timestamp + 10);
		 jsonObject.addProperty("amount", price);
		 
		 JsonArray jsonArr = new JsonArray();
		 
		 jsonArr.add(jsonObject); JsonObject reqJson = new JsonObject();
		 
		 reqJson.addProperty("customer_uid", customer_uid); 
		 reqJson.add("schedules",jsonArr);
		 String json = str.toJson(reqJson); 
		 System.out.println(json);
		 HttpEntity<String> entity = new HttpEntity<>(json, headers);
		 System.out.println("entity : " + entity);
		 try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		 String response = restTemplate.postForObject("https://api.iamport.kr/subscribe/payments/schedule", entity, String.class);
		 System.out.println("API응답 : " + response);
		 return response;
	}

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
