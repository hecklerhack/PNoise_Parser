import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Stack;
import java.util.StringTokenizer;

public class Interpreter {
    
    public Interpreter(){}
    
    
    public void traverse(String pt, String tok){
        //flavor code
        Stack stack = new Stack();
        String s[] = new String[200];
        Hashtable<String, String> sTable = new Hashtable<String, String>();
        String gg = "";
        
        StringBuilder sb = new StringBuilder(gg);
        
        for (int i = 0; i < sb.length(); i++){
        char c = sb.charAt(i);        
        
        if(c == '['){
            stack.push(c);
        }else if (c == ']'){
            stack.pop();
            sb.deleteCharAt(i);
        }else if(c == '#'){
            s[i] = sb.substring(sb.indexOf("[") + 1, i);
            sb.deleteCharAt(sb.indexOf("["));
        }
            
        }
        
        //bullshit time
        String pt2 = tok.replace('[', ' ');
        String pt3 = pt2.replace(']', ' ');
        String pt4 = pt3.replace('#', ' ');
        String pt5 = pt4.replace(',', ' ');
        String pt6 = pt5.replace('"', ' ');
        System.out.println("");
        
        String ids[] = new String[100];
        StringTokenizer t = new StringTokenizer(pt6);
        String tokens ="";
        System.out.println(pt6);
        Tester ts = new Tester();
        Hashtable<String, String> value = ts.getSymbolTable();
        
        System.out.println("==========Actual CLI===========");
        System.out.println("");
        while(t.hasMoreTokens())//kadugaan lvl 69000
        {
           tokens = t.nextToken();
           //System.out.println(tokens);
           
           //TYPE CHECKING
           if(tokens.equals("DATA_TYPE_INT") && t.nextToken().equals("UNDERSCORE") && !t.nextToken().equals("MAIN")){
               //System.out.println(tokens);
               tokens = t.nextToken();
               tokens = t.nextToken();
               tokens = t.nextToken();
               tokens = t.nextToken();
               String number = tokens.toString();
               //System.out.println(number);
               if(number.contains(".")){
                   System.out.println("Error, Type Mismatch");
                   break;
               }
           }//mano mano AF
           
           
           if(tokens.equals("PRINT")){
               tokens = t.nextToken();//delim_l_paren
               tokens = t.nextToken();//string constant or format int
               if(tokens.equals("STRING_CONSTANT")){
               tokens = t.nextToken();
               System.out.println(tokens);
               }else if(tokens.equals("FORMAT_INT")){
                   tokens = t.nextToken();
                   tokens = t.nextToken();
                   tokens = t.nextToken();
                   String val = value.get(tokens);
                   System.out.println(val);
               }
           }
           
           if(tokens.equals("IF")){
               tokens = t.nextToken();
               tokens = t.nextToken();
               tokens = t.nextToken();
               String val = value.get(tokens);
               
               
           }
           
           
           
           
        }        
        
    }//end Traverse 
   
    
}
