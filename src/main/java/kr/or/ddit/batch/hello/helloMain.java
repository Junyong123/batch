package kr.or.ddit.batch.hello;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class helloMain {
	public static void main(String[] args){
		ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("classpath:/kr/or/ddit/config/spring/context-batch.xml");
			
		// launcher lookup(DL) 방식
		JobLauncher jobLauncher = context.getBean("jobLauncher",JobLauncher.class);
		// job lookup
		Job helloJob =context.getBean("helloJob",Job.class);
		// job 실행
		//try catch
		try {
			jobLauncher.run(helloJob,new JobParameters());
		} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
				| JobParametersInvalidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// context 객체 close
		context.close();
	}
}
