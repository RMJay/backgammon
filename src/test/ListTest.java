package test;

import java.util.ArrayList;
import java.util.List;

public class ListTest {

	public static void main(String[] args) {
		
		List<String> words = new ArrayList<String>();
				
		words.add("hello");
		words.add("my");
		words.add("name");
		words.add("is");
		words.add("robert");
		words.add("muckle-jones");
		
		String firstElement = words.remove(0);
		
		for(String word:words){
			System.out.print(word + " ");
		}
		System.out.println();
		System.out.println(firstElement);

	}

}
