package hello;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.task.TaskRejectedException;

public class Application {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		testVoid();
		//testReturn();
		
	}
	
	
	// 測試無返回結果
	private static void testVoid() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AsyncTaskConfig.class);
		AsyncTaskService asyncTaskService = context.getBean(AsyncTaskService.class);
		// 建立了20個執行緒
		for (int i = 1; i <= 20; i++  ) {
		asyncTaskService.executeAsyncTask(i);
		}
		context.close();
	}
	
	
	// 測試有返回結果
	private static void testReturn() throws InterruptedException, ExecutionException {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AsyncTaskConfig.class);
		AsyncTaskService asyncTaskService = context.getBean(AsyncTaskService.class);
		List<Future<String>> lstFuture = new ArrayList<Future<String>>();// 存放所有的執行緒，用於獲取結果
		// 建立100個執行緒
		for (int i = 1; i <= 100; i++  ) {
		while (true) {
		try {
			// 執行緒池超過最大執行緒數時，會丟擲TaskRejectedException，則等待1s，直到不丟擲異常為止
			Future<String> future = asyncTaskService.asyncInvokeReturnFuture(i);
			lstFuture.add(future);
			break;
		} catch (TaskRejectedException e) {
			System.out.println("執行緒池滿，等待1S。");
			Thread.sleep(1000);
		}
		}
		}
		// 獲取值。get是阻塞式，等待當前執行緒完成才返回值
		for (Future<String> future : lstFuture) {
			System.out.println(future.get());
		}
		context.close();
	}

}
