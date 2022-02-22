package behavioural.observerpattern;

public interface Subject {
	
	void addSubscriber(Observer o);

	void removeSubscriber(Observer o);
	
	void notifyUpdate(String tweet);

}
