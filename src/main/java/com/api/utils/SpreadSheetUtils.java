package com.api.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SpreadSheetUtils {

	static String entityType;

	public static Iterator<Object[]> getTestData(Class<?> class1, LinkedHashMap<String, Class<?>> entityClazzMap,
			String dataProvideCsv, int index) {

		List<Object[]> testDataList = new ArrayList<>();

		try {
			FileInputStream file = new FileInputStream(dataProvideCsv);

			Workbook workbook = new XSSFWorkbook(file);

			Sheet sheet = workbook.getSheetAt(index);

			for (int i = 0; i < sheet.getPhysicalNumberOfRows() - 1; i++) {
				Row row = sheet.getRow(0);
				Cell cell = row.getCell(0); // Assuming column 0 contains entityType
				if (cell != null) {
					if (cell != null && cell.getCellType() == CellType.STRING) {
						entityType = cell.getStringCellValue();
					}
					Class<?> entityClass = entityClazzMap.get(entityType);
					if (entityClass != null) {
						// Create an instance of the corresponding class
						Constructor<?> constructor = entityClass.getDeclaredConstructor();
						Object entityInstance = constructor.newInstance();

						// Iterate through the cells in the row to populate class variables
						for (int j = 1; j < row.getLastCellNum(); j++) {
							Row row1 = sheet.getRow(i + 1);
							Cell cell1 = row1.getCell(j);
							String fieldName = sheet.getRow(0).getCell(j).getStringCellValue(); // Assuming row 0
																								// contains field names
							Field field = entityClass.getDeclaredField(fieldName);
							field.setAccessible(true);

							if (cell1 != null && cell1.getCellType() == CellType.STRING) {
								field.set(entityInstance, cell1.getStringCellValue());
							} else if (cell1 != null && cell1.getCellType() == CellType.NUMERIC) {
								if (field.getType() == int.class || field.getType() == Integer.class) {
									field.set(entityInstance, (int) cell1.getNumericCellValue());
								} else if (field.getType() == double.class || field.getType() == Double.class) {
									field.set(entityInstance, cell1.getNumericCellValue());
								}
							}
						}
						testDataList.add(new Object[] { entityInstance });
					}
				}
			}

			workbook.close();
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Iterator<Object[]> td = testDataList.iterator();
		List<Entity> entityList = new ArrayList<>();
		List<Object[]> data = new ArrayList<>();

		while (td.hasNext()) {
			Object[] testData = td.next();

			// checks whether the testData array has at least one element &
			// whether the first element of the array is an instance of the Entity class
			if (testData.length > 0 && testData[0] instanceof Entity) {

				// casts the first element of the testData array to an Entity object and assigns
				// it to the variable entity
				Entity entity = (Entity) testData[0];

				// add the object entity to the list
				entityList.add(entity);
			}
		}

		for (Entity entity : entityList) {
			if (entity.getExecutable().equalsIgnoreCase("y")) {
				data.add(new Object[] { entity });
			}
		}
		return data.iterator();
	}
}
