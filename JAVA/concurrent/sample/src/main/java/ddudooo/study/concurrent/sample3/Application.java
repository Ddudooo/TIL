package ddudooo.study.concurrent.sample3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Application {

	public static void main(String[] args) {
		ExecutorService executorService = Executors.newFixedThreadPool(5);

		for (int i = 0; i < 10; i++) {
			Runnable thread = () -> {
				ThreadPoolExecutor executor = (ThreadPoolExecutor) executorService;
				int poolSize = executor.getPoolSize();
				String name = Thread.currentThread().getName();
				System.out.printf("[풀 사이즈 %d] %s \n", poolSize, name);
			};

			executorService.execute(thread);

			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		executorService.shutdown();
	}
}