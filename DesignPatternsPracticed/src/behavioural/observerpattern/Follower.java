package behavioural.observerpattern;

public class Follower implements Observer{
	
	private String name;
	
	public Follower(String name) {
		this.name=name;
	}

	@Override
	public void notification(String celebrity, String tweet) {
		System.out.println("Hey "+name+"! "+celebrity+" just tweeted. \""+tweet+"\"");
	}

}
