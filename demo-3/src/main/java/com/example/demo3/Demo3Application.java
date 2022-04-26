package com.example.demo3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Demo3Application {

	public static void main(String[] args) {
		//BinarySearchImpl binarySearch = new BinarySearchImpl(new BubbleSortAlgorithm());
		//System.out.println(result);

		ApplicationContext applicationContext =
				SpringApplication.run(Demo3Application.class, args);
		BinarySearchImpl binarySearch =
				applicationContext.getBean(BinarySearchImpl.class);
		int result =
				binarySearch.binarySearch(new int[] {12, 4, 6}, 3);

		System.out.println(result);

	}

}
