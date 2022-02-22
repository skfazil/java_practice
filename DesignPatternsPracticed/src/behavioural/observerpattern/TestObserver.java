package behavioural.observerpattern;

public class TestObserver {
	
	public static void main(String[] args) {
		Subject dhoni = new Celebrity("Dhoni");
		
		Observer follower1 = new Follower("Fazil");
		Observer follower2 = new Follower("Nasru");
		Observer follower3 = new Follower("Ali");
		
		dhoni.addSubscriber(follower1);
		dhoni.addSubscriber(follower2);
		dhoni.addSubscriber(follower3);
		
		dhoni.notifyUpdate("Travelling to Ranchi with my Daughter");
	}

}
