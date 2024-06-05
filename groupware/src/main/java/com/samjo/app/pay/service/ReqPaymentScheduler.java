package com.samjo.app.pay.service;

import java.sql.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Component;

import com.samjo.app.ct.mapper.CtMapper;
import com.samjo.app.ct.service.CtVO;
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
	
    public void stopScheduler() {
    	//구독 취소 시 scheduler shutdown을 통해 결제 요청 멈춤
        scheduler.shutdown();
    }
 
    //public void startScheduler(String customer_uid, int price, long packageId, int ctNo) {
	public void startScheduler() {
        scheduler = new ThreadPoolTaskScheduler();
        scheduler.initialize();
        // 스케쥴러 시작
        //scheduler.schedule(getRunnable(), getTrigger());
   		//scheduler.schedule(getRunnable(customer_uid, price, packageId,ctNo), new CronTrigger("0 10 0 15 * ?"));
        scheduler.schedule(getRunnable(), new CronTrigger("30 0/1 * * * ?"));
    }
    
    public static java.sql.Date convertFromJAVADateToSQLDate(
            java.util.Date javaDate) {
        java.sql.Date sqlDate = null;
        if (javaDate != null) {
            sqlDate = new Date(javaDate.getTime());
        }
        return sqlDate;
    }
 
    //private Runnable getRunnable(String customer_uid, int price, long packageId, int ctNo){
	private Runnable getRunnable(){
        return () -> {
        	System.out.println("스케줄러 실행중...");
        	//결제 예약 처리
        	List<PayVO> payList = payMapper.selectConPay();
        	if(payList != null){
        		for(PayVO payVO: payList) {
        			payservice.schedulePay(payVO.getCustNo(), payVO.getServAmt(), payVO.getCtNo());
        		}
        	}
        	//결제대기중인 결제의 상태 조회하고 결제완료시 DB에 반영
        	List<PayVO> pList = payMapper.selectWaitPay();
        	if(pList != null) {
        		for(PayVO payVO: pList) {
        			payservice.payCheck(payVO.getMerchantUid(), payVO.getCtNo());
        		}
        	}
        };
    }
 
    private Trigger getTrigger() {
        // 작업 주기 설정 
        return new PeriodicTrigger(1, TimeUnit.MINUTES); //1분마다
    	//return new PeriodicTrigger(20, TimeUnit.SECONDS); //20초마다
    	//return new PeriodicTrigger(1, TimeUnit.DAYS); //1일마다
    }
}
