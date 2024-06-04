package com.samjo.app.pay.service;

import java.sql.Date;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Component;

import com.samjo.app.pay.mapper.PayMapper;

@Component
public class ReqPaymentScheduler {
	//스케줄러
    private ThreadPoolTaskScheduler scheduler;
    
	@Autowired
	PayService setSchedulePay;
	@Autowired
	PayMapper payMapper;
	
    public void stopScheduler() {
    	//구독 취소 시 scheduler shutdown을 통해 결제 요청 멈춤
        scheduler.shutdown();
    }
 
    public void startScheduler(String customer_uid, int price, long packageId, int ctNo) {
        scheduler = new ThreadPoolTaskScheduler();
        scheduler.initialize();
        // 스케쥴러 시작
        scheduler.schedule(getRunnable(customer_uid, price, packageId,ctNo), new CronTrigger("0 10 10 15 * ?"));
        //scheduler.schedule(getRunnable(customer_uid, price, packageId,ctNo), new CronTrigger("10 0/1 * * * *"));
        //scheduler.schedule(getRunnable(customer_uid, price, packageId,ctNo), getTrigger());
        PayVO payVO = new PayVO();
   		payVO.setCustNo(customer_uid);
   		payVO.setCtNo(ctNo);
   		payVO.setServAmt(price);
   		payMapper.payUpdate(payVO);
   		payMapper.payInsert(payVO);
    }
    
    public static java.sql.Date convertFromJAVADateToSQLDate(
            java.util.Date javaDate) {
        java.sql.Date sqlDate = null;
        if (javaDate != null) {
            sqlDate = new Date(javaDate.getTime());
        }
        return sqlDate;
    }
 
    private Runnable getRunnable(String customer_uid, int price, long packageId, int ctNo){
        return () -> {
        	setSchedulePay.schedulePay(customer_uid, price, ctNo);
        	/*
        	PayVO payVO = new PayVO();
	   		payVO.setCustNo(customer_uid);
	   		payVO.setCtNo(ctNo);
	   		payVO.setServAmt(price);
	   		payMapper.payUpdate(payVO);
	   		payMapper.payInsert(payVO);
	   		*/
        };
    }
 
    private Trigger getTrigger() {
        // 작업 주기 설정 
        //return new PeriodicTrigger(1, TimeUnit.MINUTES); //1분마다
    	return new PeriodicTrigger(10, TimeUnit.SECONDS); //10초마다
    }
}
