package behavioural.observerpattern;

import java.util.ArrayList;
import java.util.List;

public class Celebrity implements Subject{
	
	private List<Observer> followers = new ArrayList<Observer>();
	private String celebrity;
	
	public Celebrity(String celebrity) {
		this.celebrity=celebrity;
	}

	@Override
	public void addSubscriber(Observer o) {
		followers.add(o);
	}

	@Override
	public void removeSubscriber(Observer o) {
		followers.remove(o);
	}

	@Override
	public void notifyUpdate(String tweet) {
		followers.forEach(o->o.notification(celebrity, tweet));
	}

}
