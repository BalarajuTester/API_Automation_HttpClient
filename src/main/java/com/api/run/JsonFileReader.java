package com.api.run;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

public class JsonFileReader {
	public static String requestBuilder(String filePath) throws StreamReadException, DatabindException, IOException {

		// Specify the path to your JSON file
		File jsonFile = new File(filePath);

		// Read JSON data from a file
		String jsonData = new String(Files.readAllBytes(jsonFile.toPath()), StandardCharsets.UTF_8);
		return jsonData;

	}

}
