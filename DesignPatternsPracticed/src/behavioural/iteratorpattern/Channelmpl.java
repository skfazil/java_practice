package behavioural.iteratorpattern;

import java.util.ArrayList;
import java.util.List;

public class Channelmpl implements Channel{
	
	private List<String> channels;
	private Integer currentIndex;
	
	public Channelmpl() {
		channels = new ArrayList<String>();
		currentIndex=0;
	}

	@Override
	public void addChannel(String channel) {
		channels.add(channel);
	}
	
	@Override
	public void removeChannel(String channel) {
		channels.remove(channel);
	}

	@Override
	public ChannelIterator iterator() {
		return new ChannelIteratorImpl();
	}
	
	private class ChannelIteratorImpl implements ChannelIterator{

		@Override
		public boolean hasNext() {
			return currentIndex<=(channels.size()-1);
		}

		@Override
		public String next() {
			String next = channels.get(currentIndex);
			currentIndex++;
			return next;
		}
		
	}

	

}
