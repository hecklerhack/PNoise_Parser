import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Tester {
    
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
            System.out.print(" ");
        }
        
         sc.close();
        
       
    }
    
    
}
