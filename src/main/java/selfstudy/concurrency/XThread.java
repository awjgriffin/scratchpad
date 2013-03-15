package selfstudy.concurrency;

public class XThread extends Thread {

	public XThread(Runnable target) {
		super(target);
	}

	@Override
	public String toString() {
		return this.getName() + ", " + this.getState().toString();
	}
}
