package com.api.utils;

import java.util.Iterator;
import java.util.LinkedHashMap;
import org.testng.annotations.DataProvider;
import org.testng.asserts.SoftAssert;

public class Base {
	
	protected SoftAssert assertion = new SoftAssert();;
	
	 String DATA_PROVIDE_CSV;

	
	 public  void setFilepath(String filepath) {
	        DATA_PROVIDE_CSV = filepath;
	    }
	 
	@DataProvider(name = "data", parallel = false)
	public Iterator<Object[]> getData() {

		// Define a mapping between entity types and their corresponding classes
		LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
		entityClazzMap.put("Entity", Entity.class);
		Iterator<Object[]> testData = SpreadSheetUtils.getTestData(this.getClass(), entityClazzMap, DATA_PROVIDE_CSV, 0);
		return testData;
		
	}

}
