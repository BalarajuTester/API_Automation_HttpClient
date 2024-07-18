package com.api.requests;

import java.io.IOException;

//import org.apache.http.ParseException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.api.run.Assertion;
import com.api.run.RestAPI;
import com.api.utils.Base;
import com.api.utils.Entity;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonNode;

public class Pen extends Base{
	
	String filePath="/API_Auto/src/test/resources/API Resources/data/Pendata.xlsx";

	
	@BeforeClass
    public void setFilePath() {
        setFilepath(filePath);
    }

	@Test(dataProvider = "data", groups = { "p1" }, description = "book details")
	public void test(Entity entity) throws StreamReadException, DatabindException, IOException {
		System.out.println("fgcfvgvgvg");
		Assertion response = new Assertion(RestAPI.executeAPI(entity));

		if (entity.getid() != 0) {
			assertion.assertEquals(response.element("id"), entity.getid(), " Id is not correct ");
		}

		if (entity.getbookName() != null) {
			assertion.assertEquals(response.element("bookName"), entity.getbookName(),
					" Book Name is not correct ");
		}

		if (entity.getbookAuthor() != null) {
			assertion.assertEquals(response.element("bookAuthor"), entity.getbookAuthor(),
					" Book Author name is not correct ");
		}

		if (entity.getbookPrice() != 0) {
			assertion.assertEquals(response.element("bookPrice"), entity.getbookPrice(),
					" Book Price is not correct ");
		}
		if (entity.geterrorMessage() != null) {
			assertion.assertNotNull(response.element("data.violations[0].message"), " Message is null ");
		}
		
		assertion.assertAll();
	}

}
