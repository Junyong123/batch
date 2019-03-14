package kr.or.ddit.batch.hello;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

// 로직 test를 할 경우 작성
@RunWith(SpringJUnit4ClassRunner.class)
// 설정 파일 읽기
@ContextConfiguration({"classpath:kr/or/ddit/config/spring/context-batch.xml",	// sre/main/resources
					  "classpath:kr/or/ddit/config/spring/context-hello-batch-test.xml"}) // sre/test/resources
public class helloJobTest {

	@Autowired
	private JobLauncherTestUtils jobLauncher;
	
	@Test
	public void testHelloJob() throws Exception {
		/***Given***/
		
		/***When***/
		JobExecution jobExecution = jobLauncher.launchJob();
		// jobExecution에 실행결과값이 담김
		/***Then***/
		assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());

	}

}
