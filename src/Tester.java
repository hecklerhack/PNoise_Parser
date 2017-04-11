import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Hashtable;
import java.util.Set;
import java.util.Iterator;

public class Tester {
    
    public static Hashtable<String, String> symbolTable = new Hashtable<String, String>();

    public static void main(String args[]) throws FileNotFoundException
    {
         File file = new File("C://sample.pn");
         String input = "";
        
         Scanner sc = new Scanner(file);
         Parser parse = new Parser();
         
         while (sc.hasNextLine()) {
            input += sc.nextLine(); 
          }
         
        Tokenizer tokenizer = new Tokenizer(input);
        
        while (tokenizer.hasNextToken()){
            Token token = tokenizer.nextToken();
            String s = token.getToken();
            System.out.print(s);
            if(s.indexOf("IDENTIFIER") != -1)
            {
                addIdentifier(tokenizer, token);
            }
            
            System.out.print(" ");
            parse.getToken(token);
        }
  /*      Set<String> keys = symbolTable.keySet();        
        Iterator<String> itr = keys.iterator();*/

     //   parse.printParserTokens();
        parse.lookup();
         sc.close();
    }

    //adds identifier to symbol table
    public static void addIdentifier(Tokenizer tok, Token t)
    {

        Identifier id = tok.getIdentifier();
        String var = id.getIdentifierVar();
        t = tok.nextToken();
        if(tok.hasNextToken())
        {
            if(t.getType() == TokenType.ASSIGNMENT)
            {
                System.out.print(t.getToken());
                System.out.print(" ");
                t = tok.nextToken();
                String value = addValue(tok, t);
                symbolTable.put(var, value);
                return;
            }
            else if(t.getType() == TokenType.TERMINATOR)
            {
                return;
            }
        }
    }
    ///adds value to identifier
    public static String addValue(Tokenizer tok, Token t)
    {
        //if next token is not a variable, get constant literal of identifier
        
        String value = "";
        while((t.getType() != TokenType.TERMINATOR && t.getType() != TokenType.DELIM_R_PAREN) && tok.hasNextToken())
        {
            //if next token is not a variable
            String literal = tok.getLiteral();
            value += literal; 
            t = tok.nextToken();                         
        }
        return value;

    }
    //future to do: check if value is double (3.97 for example)
}
