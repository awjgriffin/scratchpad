package selfstudy.concurrency.testobjects;

public interface RunnableSharedObject {

	void use(String threadname);
	void relinquish();
}
