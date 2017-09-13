package com.htsec.init;

import com.htsec.beans.Pet;
//import com.htsec.dao.PetMapper;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(
				new String[] { "applicationContext-myBatis.xml" });
		//PetMapper petMapperImpl = (PetMapper) appContext.getBean("petMapper");
		// Printing pets data
		//List<Pet> pets = petMapperImpl.getAllPetsDate();
		//System.out.println("--- pets ---" + pets.size());
	}

}
