package com.curator.oeuvre;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OeuvreApplication {

	public static void main(String[] args) {
		SpringApplication.run(OeuvreApplication.class, args);

		long heapSize = Runtime.getRuntime().totalMemory();
		System.out.println("heapSize(M) = " +  heapSize / (1024 * 1024) + "MB");

	}
}
