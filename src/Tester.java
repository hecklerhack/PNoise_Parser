import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Hashtable;
import java.util.LinkedList;

import grtree.NPLviewer;

public class Tester {
    
    public static Hashtable<String, String> symbolTable = new Hashtable<String, String>();
    public static Parser parse;

    public static void main(String args[]) throws FileNotFoundException
    {
         File file = new File("D://sample.pn"); 
         String input = "";
         
         Interpreter inter = new Interpreter();
         File parse_tree = new File("D://parse_tree.txt");
         Scanner sc = new Scanner(file);
         parse = new Parser();
         
         while (sc.hasNextLine()) {
            input += sc.nextLine(); 
          }
         
        Tokenizer tokenizer = new Tokenizer(input);
        Tokenizer tokenoutputs = new Tokenizer(input);
        
        System.out.println("TOKENS");
        System.out.println("===============================================================");
        
        String toktok = "";
        
        while (tokenoutputs.hasNextToken()){
            toktok = toktok + "[" + tokenoutputs.nextToken().getToken() + "]";
            sc.close();
        }
        System.out.println(toktok);
        System.out.println();
        System.out.println("===============================================================");
        System.out.println();
        System.out.println("PARSING ACTION");
        System.out.println("===============================================================");
        while (tokenizer.hasNextToken()){
            Token token = tokenizer.nextToken();
            String s = token.getToken();
            System.out.println("["+s+"]");
           
            if(s.indexOf("IDENTIFIER") != -1)
            {
                addIdentifier(tokenizer, token);
            }
            parse.lookup(s);
            parse.getToken(token);
            System.out.println();
        }         
        parse.lookup("$");
         System.out.println("===============================================================");

        System.out.println();
        LinkedList<String> lparseTree = parse.getParseTree();
        String s = lparseTree.get(lparseTree.size() - 1);
        
        Interpreter interpreter = new Interpreter();
        for(String str: lparseTree)
        {
        	System.out.println(str);
        }
        
        try {
			FileWriter writer = new FileWriter(parse_tree);
			writer.write(s);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
         sc.close();
         
         NPLviewer tree_gen = null;
         if(parse.isAccepted()){
        	 tree_gen = new NPLviewer("D://parse_tree.txt");
        }
         System.out.println("=========================================================================");
         
         interpreter.traverse(s, toktok);
    }

    //adds identifier to symbol table
    public static void addIdentifier(Tokenizer tok, Token t)
    {
    	parse.lookup(t.getToken());
        Identifier id = tok.getIdentifier();
        String var = id.getIdentifierVar();
        t = tok.nextToken();
        if(tok.hasNextToken())
        {
            if(t.getType() == TokenType.ASSIGNMENT)
            {
                System.out.println("["+t.getToken()+"]");
                parse.lookup(t.getToken());
                t = tok.nextToken();
                String value = addValue(tok, t);
                symbolTable.put(var, value);
                System.out.println();
             
                return;
            }
            else if(t.getType() == TokenType.TERMINATOR)
            {
            	System.out.println("["+t.getToken()+"]");
                System.out.println();
                return;
            }
        }
    }
    ///adds value to identifier
    public static String addValue(Tokenizer tok, Token t)
    {
        //if next token is not a variable, get constant literal of identifier
        
        String value = "";
        Token valueToken = t;
        while(t.getType() != TokenType.TERMINATOR && t.getType() != TokenType.DELIM_R_PAREN && tok.hasNextToken())
        {
            //if next token is not a variable
            String literal = tok.getLiteral();
            parse.lookup(valueToken.getToken()); //try
            value += literal; 
            valueToken = t;
            t = tok.nextToken();                         
        }
        System.out.println("["+valueToken.getToken()+"]");
        System.out.println();
        System.out.println("["+t.getToken()+"]");		//prints terminator token
        if(valueToken.getType() != TokenType.STRING_CONSTANT)
        	parse.lookup(valueToken.getToken());
        parse.lookup(t.getToken());
        return value;

    }
    
   public Hashtable<String, String> getSymbolTable()
   {
       return symbolTable;
   }
    
    public static String reverse(String input){
        char[] in = input.toCharArray();
        int begin=0;
        int end=in.length-1;
        char temp;
        while(end>begin){
            temp = in[begin];
            in[begin]=in[end];
            in[end] = temp;
            end--;
            begin++;
        }
        return new String(in);
    }
}