package ddudooo.study.concurrent.sample1;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import org.jetbrains.annotations.NotNull;

public class SimpleThread extends Thread {

	private static final Logger logger = Logger.getLogger(SimpleThread.class.getName());

	public SimpleThread(@NotNull String name) {
		super(name);
	}

	@Override
	public void run() {
		try {
			System.out.println("Start Thread " + this.getName());
			IntStream.range(0, 10)
				.forEach(System.out::print);
			System.out.println();
			System.out.println("END Thread " + this.getName());
		} catch (Exception e) {
			Object[] logParam = {this.getName(), e.getMessage()};
			logger.log(Level.WARNING, "Thread {0} Exception {1}", logParam);
		}
	}
}