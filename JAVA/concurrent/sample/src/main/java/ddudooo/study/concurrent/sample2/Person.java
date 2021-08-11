package ddudooo.study.concurrent.sample2;

public class Person extends Thread {

	private final Paper paper;

	public Person(String name,
		Paper paper) {
		super(name);
		this.paper = paper;
	}

	@Override
	public void run() {
		try {
			System.out.println("THREAD START " + this.getName());
			paper.write(this.getName() + " WRITE PAPER");
			paper.read();
			System.out.println("THREAD END " + this.getName());
		} catch (Exception e) {
			System.out.println("THREAD TERMINATED BY " + e.getMessage());
		}
	}
}