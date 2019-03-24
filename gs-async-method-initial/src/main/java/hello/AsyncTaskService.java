package hello;

import java.util.Random;
import java.util.concurrent.Future;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

@Service
//執行緒執行任務類
public class AsyncTaskService {
	
	Random random = new Random();// 預設構造方法
	@Async
	// 表明是非同步方法
	// 無返回值
	public void executeAsyncTask(Integer i) {
		System.out.println("執行非同步任務：" + i);
	}
	
	/**
	* 異常呼叫返回Future
	* 
	* @param i
	* @return
	* @throws InterruptedException
	*/
	@Async
	public Future<String> asyncInvokeReturnFuture(int i) throws InterruptedException {
		System.out.println("input is " +  i);
		Thread.sleep(1000 * random.nextInt(i));
		// Future接收返回值，這裡是String型別，可以指明其他型別
		Future<String> future = new AsyncResult<String>("success:" + i);
	    return future;
	}
}
