package grtree ;

import java.util.* ;

/**
*  <pre>
*  Here is a tree expression:
*  "[a # [[b # [[c # [[d # []]]],[e # []]]],[f # []],[g # [[h # []]]]]]"  
*
*   It 'represents' the following tree:
*
*            a
*         /  |  \
*        b   f   g
*      /   \     |
*     c     e    h
*     |
*     d
*
*  It is assuemed that some other program will correctly 
*  generate the tree expression.  
*
*  </pre>
*/

public class TreeExpression {

   /**
   *  <pre>
   *  Return the root name of a 
   *  tree expression. 
   *       "[<root> # [<children>]]" --> "<root>"
   *  <pre>
   */
   private static String findRoot(String xpr) {
      String s = xpr.trim() ;
      // use index of "#"
      return s.trim().substring(1,s.indexOf("#")) ;
   }

   /**
   *  <pre>
   *  Input "[root,[TE1,TE2,...,TEn###"
   *  Return "TE1,TE2,...,TEn###"
   *  where the TEk are (sub)tree expressions.
   *  <pre>
   */
   private static String findChildren(String xpr) {
      String s = xpr.trim() ;
      // find where 2nd "["
      int lpcount = 0 ;
      int where = -1 ;
      for(int i = 0 ; i < s.length() ; i++) 
         if (s.charAt(i) == '[') { 
            lpcount++ ; 
            if (lpcount == 2) { 
              where = i ;
              break ;
            }
         }
      return s.substring(where+1,s.length()) ;
   }
       
   /**
   *  Return the Tree represented by this tree 
   *  expression.
   */
   public static Tree toTree(String treeExpression) {
      if (treeExpression.trim().equals("[]")) 
         return null ;
      else { 
         Tree T = new Tree(findRoot(treeExpression)) ;
         String children = findChildren(treeExpression) ;
         int length = children.length() ;
         if (length == 0) return T ;
         else { 
            int balance = 0 ; 
            int begin = 0 ;
            for(int k = 0 ; k < length ; k++) {
                switch(children.charAt(k)) {
                  case '[' : 
                     balance++ ;
                     break ;
                  case ']' :
                     balance-- ;
                     if(balance == 0) {
                        Tree t = toTree(children.substring(begin,k+1)) ; 
                        T.addChild(t) ;
                     }
                     break ;
                  case ',' : 
                     if(balance == 0) begin = k+1 ;
                     break ;  
               }
            }                     
            return T ;
         }
      }
   }

   public static void main(String[] args) {
      String s = "[r([a,1],(b,d,e,t))# []]" ;
      System.out.println(findRoot(s)) ; 
   

      //String s = "[a,[[b,[[c,[[d,[]]]],[e,[[i,[]]]]]],[f,[]],[g,[[h,[]]]]]]" ; 
      //String s = "[d(1)|d(_h121)|d(2),[[d(1),[[a(1),[[~b(1),[[~d(1),[]]]],[c(1),[[~c(2),[[~a(2),[[~d(2),[]]]],[~b(2),[[~d(2),[]]]]]]]]]]]]]]" ; 
      //String s = "[a,[ [b,[]] , [c,[]] ]]" ;
      //System.out.println(s) ; 
      //System.out.println(getRoot(s)) ; 
      //System.out.println(getChildren(s)) ; 
      //System.out.println(getTree(s).toString()) ;
      //TreeScrollFrame f = new TreeScrollFrame(getTree(s)) ;
   }

}

