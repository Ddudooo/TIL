package ddudooo.study.concurrent.sample2;

public class Paper {

	private volatile String text;

	public Paper(String text) {
		this.text = text;
	}

	public synchronized void write(String text) {
		this.text += "\n" + text;
	}

	public void read() {
		try {
			System.out.println(text);
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}
	}
}