package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

  
public class ExcelFunctions {

private static XSSFSheet excelSheet;		
private static XSSFWorkbook excelWBook;		
private static XSSFCell cell;		
private static XSSFRow row;

public static Object[] getTableArray(String filePath,String testCaseName) throws Exception {   
	String[] data = null;
	try{
		File file = new File(filePath);
		FileInputStream inputStream = new FileInputStream(file);
		excelWBook = new XSSFWorkbook(inputStream);
		excelSheet = excelWBook.getSheetAt(0);
		int startRow = 1;
		int startCol = 0;
		int totalRows = excelSheet.getLastRowNum();
		int totalCols = excelSheet.getRow(0).getLastCellNum();

		data=new String[totalCols];
		for (int i=startRow;i<=totalRows;i++) { 
			cell = excelSheet.getRow(i).getCell(1);
			if(cell.toString().equalsIgnoreCase(testCaseName)){
				for (int j=startCol;j<=totalCols-1;j++){
					cell = excelSheet.getRow(i).getCell(j);
					data[j]=cell.getStringCellValue();
					System.out.println(data[j]);
				}
				break;
			}
		}
	}catch (FileNotFoundException e){
		System.out.println("Could not read the Excel sheet");
		e.printStackTrace();
	}catch (IOException e){
		System.out.println("Could not read the Excel sheet");
		e.printStackTrace();
	}
	return(data);	
}

}
