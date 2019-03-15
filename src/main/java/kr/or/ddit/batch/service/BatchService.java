package kr.or.ddit.batch.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.or.ddit.batch.dao.IBatchDao;
import kr.or.ddit.batch.model.BatchVO;
import kr.or.ddit.yogult.dao.IYogultDao;

@Service
public class BatchService implements IBatchService{
	
	@Resource(name="batchDao")
	private IBatchDao batchDao;
	
	@Resource(name="yogultDao")
	private IYogultDao yogultDao;
	
	// 배치 아이디 획득
	// 배치 정보를 생성 - > 배치 정보 등록
	// 일실적 생성
	// 배치 정상완료로 업데이트
	
	@Override
	public int dailyBatchYm(String ym) {
		int bid = batchDao.getBatchBic();
		BatchVO batchVO = new BatchVO(bid,"01","01");
		
		batchDao.createBatch(batchVO);
		// 일실적 생성
		int insertCnt = yogultDao.dailyBatchYm(ym);
		
		batchVO.setSt("02");
		batchDao.updateBatch(batchVO);
		
		return insertCnt;
	}
	
	
}
