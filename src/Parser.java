import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.Stack;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Parser
{
	Stack<String> parse_stack;
	public Parser()
	{
		parse_stack = new Stack<String>();
	}

	//gets each token from scanner
	public void getToken(Token t)
	{
		parse_stack.push(t.getToken());
	}

//just used to test if all tokens go to parser
/*	public void printParserTokens()
	{
		while(!parse_stack.empty())
			System.out.print(parse_stack.pop());
	}*/
	
	//just used to test this if it can read the excel file
	public void lookup()
	{
		try
		{
			FileInputStream file = new FileInputStream(new File("C://test.xlsx"));		//just put excel file on the directory. I'll fix it sometime.
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheetAt(0);
			
			//Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) 
            {
                Row row = rowIterator.next();
                //For each row, iterate through all the columns
                Iterator<Cell> cellIterator = row.cellIterator();
                 
                while (cellIterator.hasNext()) 
                {
                    Cell cell = cellIterator.next();
                    //Check the cell type and format accordingly
                    switch (cell.getCellType()) 
                    {
                        case Cell.CELL_TYPE_NUMERIC:
                            System.out.print(cell.getNumericCellValue() + "\t");
                            break;
                        case Cell.CELL_TYPE_STRING:
                            System.out.print(cell.getStringCellValue() + "\t");
                            break;
                    }
                }
                System.out.println("");
            }
            file.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}