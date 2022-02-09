package MethodReferenceEx;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class BiFunctionEx {

	public static void main(String[] args) {
		BiFunctionEx bi = new BiFunctionEx();
		BiFunction<String, String, Integer> bf = bi::getCombinedLength;

		Function<Integer, Integer> func = bi::getCombinedLength;

		chainThem(bf, func);

	}

	private static void chainThem(BiFunction<String, String, Integer> bf, Function<Integer, Integer> func) {
		bf.andThen(func).apply("Fazil", "Shaikh");
	}

	public Integer getCombinedLength(String a, String b) {
		System.out.println(a + b);
		return a.length() + b.length();
	}

	public Integer getCombinedLength(Integer str) {
		System.out.println(str);
		return str;
	}

}
