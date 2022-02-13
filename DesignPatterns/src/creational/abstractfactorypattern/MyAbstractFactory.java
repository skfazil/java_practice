package abstractfactorypattern;

public interface MyAbstractFactory<T> {
	
	public T create(String type);

}
