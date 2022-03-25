package Algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
class Developer {
	private int age;

	public Developer(int age) {
		this.age = age;
	}

	void setAge(int age) {
		this.age = age;
	}

	public int getAge() {
		return age;
	}
}
public class ComparatorSnippet{
	public static void main(String... args)throws Exception{
		ArrayList<Developer> listDevs = new ArrayList<Developer>();
//		Developer[] devs = Collections.sort(listDevs, new Comparator<Developer>() {
//			@Override
//			public int compare(Developer o1, Developer o2) {
//				return o1.getAge() - o2.getAge();
//			}
//		});
	}
}