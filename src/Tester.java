import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Hashtable;
import java.util.Set;
import java.util.Iterator;

public class Tester {
    
    public static Hashtable<String, String> symbolTable = new Hashtable<String, String>();
    public static Parser parse;

    public static void main(String args[]) throws FileNotFoundException
    {
         File file = new File("C://sample.pn"); //just put the file on the directory. I'll fix it sometime.
         String input = "";
        
         Scanner sc = new Scanner(file);
         parse = new Parser();
         
         while (sc.hasNextLine()) {
            input += sc.nextLine(); 
          }
         
        Tokenizer tokenizer = new Tokenizer(input);
        
        while (tokenizer.hasNextToken()){
            Token token = tokenizer.nextToken();
            String s = token.getToken();
            System.out.println("["+s+"]");
           
            if(s.indexOf("IDENTIFIER") != -1)
            {
                addIdentifier(tokenizer, token);
            }
            parse.lookup(s);
            System.out.print(" ");
            parse.getToken(token);
        }
        parse.lookup("$");
  /*      Set<String> keys = symbolTable.keySet();        
        Iterator<String> itr = keys.iterator();*/

     //   parse.printParserTokens();
        System.out.println();
    //    parse.lookup();
         sc.close();
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
                System.out.print("["+t.getToken()+"]");
                parse.lookup(t.getToken());
                t = tok.nextToken();
                String value = addValue(tok, t);
                symbolTable.put(var, value);
      //          System.out.print("["+t.getToken()+"]");
             
                return;
            }
            else if(t.getType() == TokenType.TERMINATOR)
            {
            	System.out.print("["+t.getToken()+"]");
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
            value += literal; 
            valueToken = t;
            t = tok.nextToken();                         
        }
        System.out.println("["+valueToken.getToken()+"]");
        System.out.println("["+t.getToken()+"]");		//prints terminator token
        parse.lookup(valueToken.getToken());
        parse.lookup(t.getToken());
        return value;

    }
}
