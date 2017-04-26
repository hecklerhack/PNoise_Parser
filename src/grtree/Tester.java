package grtree;
import java.net.* ;
import java.io.* ;

public class Tester {

   public static void main(String[] args) {
      String texpr1 = "[a,[ [b,[]] , [c,[]] ]]" ;
      String texpr2 = "[d(1)|d(_h121)|d(2),[[d(1),[[a(1),[[~b(1),[[~d(1),[]]]],[c(1),[[~c(2),[[~a(2),[[~d(2),[]]]],[~b(2),[[~d(2),[]]]]]]]]]]]]]]" ; 
      String texpr3 = "[(d(1)  ';'  d(2)),[[d(1),[[a(1),[[~b(1),[[~d(1),[]]]],[c(1),[[~c(2),[[~a(2),[[~d(2),[]]]],[~b(2),[[~d(2),[]]]]]]]]]]]]]]" ; 

      try {
         Socket s = new Socket("127.0.0.1",3333) ;
         PrintStream out = new PrintStream(new DataOutputStream(s.getOutputStream())) ;
         out.println(texpr1) ;
         out.flush() ;
         out.println(texpr2) ;
         out.flush() ;
         out.println(texpr3) ;
         out.flush() ;
         out.close() ;
      }
      catch(Exception e) {
         System.out.println("Trouble sending tree: " + e) ;
      }
   }
}