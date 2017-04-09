import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Hashtable;

public class Tester {
    
    public static Hashtable<String, String> symbolTable = new Hashtable<String, String>();

    public static void main(String args[]) throws FileNotFoundException
    {
         File file = new File("sample.pn");
         String input = "";
        
         Scanner sc = new Scanner(file);
         
         while (sc.hasNextLine()) {
            input += sc.nextLine(); 
          }
         
        Tokenizer tokenizer = new Tokenizer(input);
        
        while (tokenizer.hasNextToken()){
            System.out.print(tokenizer.nextToken().getToken());
            if(tokenizer.nextToken().getToken().indexOf("IDENTIFIER") != -1)
            {
                addIdentifier(tokenizer, tokenizer.nextToken());
            }
            System.out.print(" ");
        }
        
         sc.close();
        
       
    }
    //adds identifier to symbol table
    public static void addIdentifier(Tokenizer tok, Token t)
    {

        Identifier id = tok.getIdentifier();
        String var = id.getIdentifierVar();
        while(tok.hasNextToken())
        {
            if(t.getType() == TokenType.ASSIGNMENT)
            {
                System.out.print(tok.nextToken().getToken());
                System.out.print(" ");
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
        while(t.getType() != TokenType.TERMINATOR && tok.hasNextToken())
        {
            //if next token is not a variable
            String literal = tok.getLiteral();
            value += literal;                           //future to do: check if value is double (3.97 for example)
        }
        return value;

    }
    
    
}
