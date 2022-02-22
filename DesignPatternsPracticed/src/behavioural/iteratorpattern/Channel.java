package behavioural.iteratorpattern;

public interface Channel {
	
	void addChannel(String channel);
	
	void removeChannel(String channel);
	
	ChannelIterator iterator();

}
