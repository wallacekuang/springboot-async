package hello;

import java.util.concurrent.Executor;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@ComponentScan("hello")
@EnableAsync
// 執行緒配置類
public class AsyncTaskConfig implements AsyncConfigurer {
	
	// ThredPoolTaskExcutor的處理流程
	// 當池子大小小於corePoolSize，就新建執行緒，並處理請求
	// 當池子大小等於corePoolSize，把請求放入workQueue中，池子裡的空閒執行緒就去workQueue中取任務並處理
	// 當workQueue放不下任務時，就新建執行緒入池，並處理請求，如果池子大小撐到了maximumPoolSize，就用RejectedExecutionHandler來做拒絕處理
	// 當池子的執行緒數大於corePoolSize時，多餘的執行緒會等待keepAliveTime長時間，如果無請求可處理就自行銷燬
	@Override
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(5);// 最小執行緒數
		taskExecutor.setMaxPoolSize(10);// 最大執行緒數
		taskExecutor.setQueueCapacity(25);// 等待佇列
		taskExecutor.initialize();
		return taskExecutor;
	}
	
	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return null;
	}

}
