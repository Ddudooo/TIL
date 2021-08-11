package ddudooo.study.concurrent.sample2;

import java.util.stream.IntStream;

public class Application {

	public static void main(String[] args) {
		Paper paper = new Paper("init paper");
		Person[] people = new Person[10];
		IntStream.range(0, 10)
			.forEach(v -> people[v] = new Person("PERSON " + v, paper));
		for (Person person : people) {
			person.start();
		}

		try {
			Thread.sleep(20000);
			paper.read();
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}

	}
}