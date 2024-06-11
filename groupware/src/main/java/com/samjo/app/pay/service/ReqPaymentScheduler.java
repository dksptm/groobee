package com.samjo.app.pay.service;

import java.sql.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Component;

import com.samjo.app.ct.mapper.CtMapper;
import com.samjo.app.pay.mapper.PayMapper;

@Component
public class ReqPaymentScheduler {
	//스케줄러
    private ThreadPoolTaskScheduler scheduler;
    
	@Autowired
	PayService payservice;
	@Autowired
	PayMapper payMapper;
	@Autowired
	CtMapper ctMapper;
	
	@Scheduled(cron = "0 0 5 * * ?") // 매일 5시 실행 세팅
	public void run() {
		//결제대기중인 결제의 상태 조회하고 결제완료시 DB에 반영
    	List<PayVO> pList = payMapper.selectWaitPay();
    	if(pList != null) {
    		for(PayVO payVO: pList) {
    			String result = payservice.payResultCheck(payVO.getMerchantUid(), payVO.getCtNo());
    			if(result.equals("UPDATE")) {
    				if(ctMapper.selectCtPayCheck(payVO.getCtNo()) == 1) {
    					payservice.schedulePay(payVO.getCustNo(), payVO.getCtNo());
    				};
    			};
    		};
    	};
	}
	
    public void stopScheduler() {
    	//구독 취소 시 scheduler shutdown을 통해 결제 요청 멈춤
        scheduler.shutdown();
    }
 
	public void startScheduler() {
        scheduler = new ThreadPoolTaskScheduler();
        scheduler.initialize();
        // 스케쥴러 시작
        scheduler.schedule(getRunnable(), getTrigger()); //1분마다 실행 세팅
        //scheduler.schedule(getRunnable(), new CronTrigger("0 0 5 * * ?")); //매일 5시 실행 세팅
    }
    
    public static java.sql.Date convertFromJAVADateToSQLDate(
            java.util.Date javaDate) {
        java.sql.Date sqlDate = null;
        if (javaDate != null) {
            sqlDate = new Date(javaDate.getTime());
        }
        return sqlDate;
    }
 
	private Runnable getRunnable(){
        return () -> {
        	//결제대기중인 결제의 상태 조회하고 결제완료시 DB에 반영
        	List<PayVO> pList = payMapper.selectWaitPay();
        	if(pList != null) {
        		for(PayVO payVO: pList) {
        			String result = payservice.payResultCheck(payVO.getMerchantUid(), payVO.getCtNo());
        			if(result.equals("UPDATE")) {
        				if(ctMapper.selectCtPayCheck(payVO.getCtNo()) == 1) {
        					payservice.schedulePay(payVO.getCustNo(), payVO.getCtNo());
        				};
        			};
        		};
        	};
        };
    }
 
    private Trigger getTrigger() {
        // 작업 주기 설정 
        return new PeriodicTrigger(1, TimeUnit.MINUTES); //1분마다
    	//return new PeriodicTrigger(20, TimeUnit.SECONDS); //20초마다
    	//return new PeriodicTrigger(1, TimeUnit.DAYS); //1일마다
    }
}
