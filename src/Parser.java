import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.Stack;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Parser
{
	Stack<String> parse_stack;
	private int state = 0;			//initialize state to 0
	private int numTokens = 54; 	//total of 57 tokens in the parsing table
	private int numStates = 291;	//total of 279 states in the parsing table
	Stack<Integer> state_stack;			//stack of states visited
	private int totalGoto = 21;
	public Parser()
	{
		parse_stack = new Stack<String>();
		state_stack = new Stack<Integer>();
		state_stack.push(state);
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
	
	public void lookup(String token)
	{
		if(token.indexOf("IDENTIFIER") != -1)
			token = "IDENTIFIER";
		
		if(token.indexOf("INTEGER_CONSTANT") != -1)
			token = "INTEGER_CONSTANT";
		
		if(token.indexOf("STRING_CONSTANT") != -1)
			token = "STRING_CONSTANT";
		
		try
		{
			FileInputStream file = new FileInputStream(new File("C://Parsing Table.xlsx"));		//just put excel file on the directory. I'll fix it sometime.
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheetAt(1);		//get parsing table
			XSSFCell cell = null;
			for (int columnIndex = 1; columnIndex<=numTokens; columnIndex++){	    
			        cell = sheet.getRow(0).getCell(columnIndex);
			        if(token.equals(cell.toString()))
			        {
			            cell = sheet.getRow(state + 1).getCell(columnIndex);
			            System.out.println(cell.toString());
			            break;
			            
			        }
			}
			String st = cell.toString();
			String num = "";
			if(st.length() > 2)
				num = st.substring(1, st.length());			//extracts the number
			else if(st.length() == 2)
				num  = st.charAt(1) + "";
	            if(st.indexOf("s") != -1)			//shift
			     {
			        state = Integer.parseInt(num);
			        state_stack.push(state);
			        parse_stack.push(token);
			     }
			     else if(st.indexOf("r") != -1)	//reduce
			     {
			        //pop stack, reduce to a certain rule
			         XSSFSheet rule_sheet = workbook.getSheetAt(2);		//get CFG rules
			         int row = Integer.parseInt(num);   	
			         cell = rule_sheet.getRow(row).getCell(0);   	
			         System.out.println(cell.toString());   	   	
			            	
			            	XSSFCell cell2 = rule_sheet.getRow(row).getCell(2);			//get total tokens to pop in stack
			            	String sTotalPop = cell2.getRawValue();
			            	int nTotalPop = Integer.parseInt(sTotalPop);
			            	for(int counter = 0; counter < nTotalPop; counter++)
			            	{
			            		parse_stack.pop();
			            		state_stack.pop();
			            	}
			            	parse_stack.push(cell.toString());
			            	
			            	state = state_stack.peek();
			            	System.out.println("state: " + state);
			            	//goto 
			            	String top = parse_stack.peek(); 
			            	for(int columnIndex = 1; columnIndex <= totalGoto; columnIndex++)
			            	{
			            		cell = sheet.getRow(0).getCell(numTokens + columnIndex);
			            		if(top.equals(cell.toString()))
			            		{
			            			cell = sheet.getRow(state + 1).getCell(columnIndex + numTokens);
			            			state = Integer.parseInt(cell.getRawValue());
			            			state_stack.push(state);
			            			System.out.println("goto: " + state);
			            			break;
			            		}
			            	}
			            	lookup(token);
			     }     	      
            file.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}