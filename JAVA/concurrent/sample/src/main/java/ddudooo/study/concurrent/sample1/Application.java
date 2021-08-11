package ddudooo.study.concurrent.sample1;

import java.util.stream.IntStream;

public class Application {

	public static void main(String[] args) {
		SimpleThread[] threads = new SimpleThread[10];
		IntStream.range(0, 10)
			.forEach(v -> {
				threads[v] = new SimpleThread(v + "번 쓰레드");
			});
		for (SimpleThread thread : threads) {
			thread.start();
		}
	}
}