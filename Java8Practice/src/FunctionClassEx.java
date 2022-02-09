import java.util.function.Function;

public class FunctionClassEx {
	public static void main(String[] args) {
		Function<Integer, String> func = msg -> msg.toString();
		String result = func.apply(100);
		System.out.println(result);
	}

}
