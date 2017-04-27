import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.LinkedList;
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
	private int numTokens = 53; 	//total of 57 tokens in the parsing table
	private int numStates = 349;	//total of 279 states in the parsing table
	Stack<Integer> state_stack;			//stack of states visited
	LinkedList<String> parse_tree;
	private int totalGoto = 21;
	private String literal;
	private boolean accept = false;
	private int error_counter = 0;
	
	public Parser()
	{
		parse_stack = new Stack<String>();
		state_stack = new Stack<Integer>();
		state_stack.push(state);
		parse_tree = new LinkedList<String>();
	}

	//gets each token from scanner
	public void getToken(Token t)
	{
		parse_stack.push(t.getToken());
	}
	
	public void lookup(String token)
	{
		if(token.indexOf("IDENTIFIER") != -1)
		{
			literal = token.substring(token.indexOf(",") + 1);
			token = "IDENTIFIER";
		}
		
		if(token.indexOf("INTEGER_CONSTANT") != -1)
		{
			literal = token.substring(token.indexOf(",") + 1);
			token = "INTEGER_CONSTANT";
		}
		
		if(token.indexOf("STRING_CONSTANT") != -1)
		{
			literal = token.substring(token.indexOf(",") + 1);
			token = "STRING_CONSTANT";
		}
		
		if(token.equals("TRUE") || token.equals("FALSE"))
		{
			literal = token;
		}
		
		if(token.indexOf("RELATIONAL_OPE") != -1)
		{
			literal = token.substring(token.indexOf(",") + 1);
			token = "RELATIONAL_OPE";
		}
		
		try
		{
			FileInputStream file = new FileInputStream(new File("D:\\Parsing-Table.xlsx"));    //just put excel file on the directory. I'll fix it sometime.
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheetAt(1);		//get parsing table
			XSSFCell cell = null;
                        XSSFCell cfgcell = null;
			for (int columnIndex = 1; columnIndex<=numTokens; columnIndex++){	    
			        cell = sheet.getRow(0).getCell(columnIndex);
			        if(token.equals(cell.toString()))
			        {
			            cell = sheet.getRow(state + 1).getCell(columnIndex);
                        if(cell.toString().contains("s"))
                            System.out.println("SHIFT " + cell.toString());
                        else if(cell.toString().contains("r"))
                        {
                        	System.out.println("Lookahead: " + token.toString());
                            System.out.print("REDUCE " + cell.toString() + ":");
                        }
                        else if(cell.toString().indexOf("acc") != -1)
                        {
                            System.out.println("ACCEPT");
                            accept = true;
                        }
                        else
                        {
                        	System.out.println("ERROR: " + token + " not expected");
                        	error_counter++;
                        }
                               
			            break;
			            
			        }
			}
			String st = cell.toString();
			String num = "";
			if(st.length() > 2)
				num = st.substring(1, st.length());	//extracts the number
			else if(st.length() == 2)
				num  = st.charAt(1) + "";
			
                 if(st.indexOf("s") != -1)			//shift
			     {
			        state = Integer.parseInt(num);
			        state_stack.push(state);
			        parse_stack.push(token);
			        
			        ReservedWord rw = new ReservedWord();
			        String str = "";
			        if(rw.getLexeme(token) != null)
			        {
			        	str = rw.getLexeme(token);
			        }
			        else
			        	str = literal;
			        parse_tree.addLast("[" + token +"#[["+str+"#[]]]],");
			     }
			     else if(st.indexOf("r") != -1)	//reduce
			     {
			        //pop stack, reduce to a certain rule
			         XSSFSheet rule_sheet = workbook.getSheetAt(2);		//get CFG rules
			         int row = Integer.parseInt(num);   	
			         cell = rule_sheet.getRow(row).getCell(0);
                     cfgcell = rule_sheet.getRow(row).getCell(1);     
			         //System.out.println(cell.toString());   
                     System.out.println(" " + cfgcell.toString());   
			            	
			            	XSSFCell cell2 = rule_sheet.getRow(row).getCell(2);	//get total tokens to pop in stack
			            	String sTotalPop = cell2.getRawValue();
			            	int nTotalPop = Integer.parseInt(sTotalPop);
			            	
			            	String s = "[" + cell.toString() +"#[";
			            	
			            	for(int counter = nTotalPop; counter > 0; counter--)
			            	{
			            		parse_stack.pop();
			            		state_stack.pop();
			            		s += parse_tree.get(parse_tree.size() - counter);
			            		parse_tree.remove(parse_tree.size() - counter);
			            	}
			            	s = s.substring(0, s.length() - 1);
			            	s += "]],";
			            	parse_tree.addLast(s);
			            	
			            	parse_stack.push(cell.toString());
			            	
			            	state = state_stack.peek();
			            	System.out.println("state: " + state);
			            	//goto 
			            	String top = parse_stack.peek(); 
                       //     System.out.println("top: " + top);
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
	
	public LinkedList<String> getParseTree()
	{
		return parse_tree;
	}
	
	public boolean isAccepted()
	{
		return accept;
	}
}