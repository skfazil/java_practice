package behavioural.iteratorpattern;

public class TestIterator {
	
	public static void main(String[] args) {
		Channel channel = new Channelmpl();
		
		channel.addChannel("NDTV");
		channel.addChannel("MAA");
		channel.addChannel("ETV");
		channel.addChannel("NTV");
		channel.addChannel("STARPLUS");
		
		channel.removeChannel("ETV");
		
		ChannelIterator itr = channel.iterator();
		
		while(itr.hasNext()) {
			System.out.println(itr.next());
		}
	}

}
