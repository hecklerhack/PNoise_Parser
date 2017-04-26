package grtree ;


import java.net.* ;
import java.io.* ;

/**
*  A TreeServer listens for a connection from a client at
*  a port (3333).  It accepts any connection and then listens
*  for a tree expression.  When it hears a tree 
*  expression it parses the expression and constructs
*  a tree, which it then puts in a TreeScrollFrame for
*  viewing.
*  @author JRFisher@CSUPomona.edu
*  @version 1
*/

public class TreeServer {
   public static void main(String[] args ) {
      try {
         ServerSocket ssoc = new ServerSocket(3333);
         while (true) {
            Socket incoming = ssoc.accept( );
            System.out.println("New Connection to TreeServer accepted.") ;
            (new TreeViewerThread(incoming)).start();
         }   
      }
      catch (Exception e) {
         System.out.println("ServerSocket problem: " + e);
      } 
   } 
}
/**
*  A TreeViewerThread handles client's requests to draw
*  Tree(s).
*/
class TreeViewerThread extends Thread {
   Socket insoc ;  
   /**
   *  Construct TreeViewerThread for Socket.
   */
   public TreeViewerThread(Socket s) {
      insoc = s ;    
   }
   
   public void run() { 
      try {
         // Read ordinary string 
         DataInputStream diStream = 
            new DataInputStream(insoc.getInputStream()) ;
         BufferedReader br =  
            new BufferedReader(new InputStreamReader(diStream)) ; // *****
         boolean more = true ;
         while (more) {
            try {
               String expr = br.readLine() ; 
               System.out.println(expr) ;
               if (expr != null)
                  new TreeScrollFrame(TreeExpression.toTree(expr)) ;
               else { 
                  more = false ;  // Do NOT wait! 
                  System.out.println("DEAD THREAD.") ; 
                  insoc.close() ;
               }
            }
            catch(Exception e) { 
               System.out.println("Error reading tree expression: " + e) ; 
               more = false ; // stop listening, no use
               // No good way to stop or destroy this Thread!!! 
               insoc.close() ;  
            }
         }
      } 
      catch(Exception e) { 
         System.out.println("Cannot make stream: " +e) ; 
      }
   }
}
