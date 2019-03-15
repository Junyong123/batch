package kr.or.ddit.yogult.batch;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;

import kr.or.ddit.yogult.model.CycleVO;
import kr.or.ddit.yogult.model.DailyVO;
											
public class YogultItemProcessor implements ItemProcessor<CycleVO, List<DailyVO>>{
	
	// spel(spring el)을 통해 jobParameter를 주입받기 위해서는 해당 bean의 스코프가 "step"이라는 특수 스코프를 지정해야 가능하다
	@Value("#{jobParameters[ym]}") // spring el
	private String ym;
	
	@Override
	public List<DailyVO> process(CycleVO cycleVO) throws Exception {
		// String ym ="201903";
		// 해당년월의 시작일자 : 1일
		// 해당년월의 종ㄹ일자 : 28일~31일
		
		// 1번 고객이 100번 제품을 2(월요일)에 3개를 먹는다
		// 1 100 2 3
		
		//  1 100 20190304  3
		//  1 100 201903011 3
		//  1 100 201903018 3
		//  1 100 201903025 3
		
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
		return dailyList;
	}

}
