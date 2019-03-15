package kr.or.ddit.yogult.batch;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.yogult.model.CycleVO;
import kr.or.ddit.yogult.model.DailyVO;

public class DateTest {
	
	private Logger logger = LoggerFactory.getLogger(DateTest.class);

	@Test
	public void test() throws ParseException {
		/***Given***/
		String ym ="201903";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		
		/***When***/
		Date stDate = sdf.parse(ym + "01"); // 시작일자
		Calendar cal = Calendar.getInstance();
		cal.setTime(stDate);
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		Date edDate = sdf.parse(ym+lastDay);
		
		/***Then***/
		assertEquals("20190301",sdf.format(stDate));
		assertEquals("20190331",sdf.format(edDate));

	}
	
	@Test
	public void testday() throws ParseException{
		String ym ="201903";
		CycleVO cycleVO = new CycleVO();
		cycleVO.setCid(1);
		cycleVO.setPid(100);
		cycleVO.setDay(2);
		cycleVO.setCnt(2);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date stDate = sdf.parse(ym+"01");
		Calendar cal = Calendar.getInstance();
		
		cal.setTime(stDate);
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		Date edDate = sdf.parse(ym + lastDay);
		
		// 시작일자, 종료일자
		List<DailyVO> dailyList = new ArrayList<DailyVO>();
		while(cal.getTimeInMillis() <= edDate.getTime()){
			int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
			
			// 애음주기 요일이랑 cal 날짜요일이 같으면 일실적으로 생성할 대상일자
			if(dayOfWeek == cycleVO.getDay()){
				DailyVO dailyVO = new DailyVO();
				dailyVO.setCid(cycleVO.getCid());
				dailyVO.setPid(cycleVO.getPid());
				dailyVO.setDt(sdf.format(cal.getTime()));
				dailyVO.setCnt(cycleVO.getCnt());
				dailyList.add(dailyVO);
			}
			cal.add(Calendar.DAY_OF_MONTH, 1);
		}
		logger.debug("dailyList : {}", dailyList);
	}
}
