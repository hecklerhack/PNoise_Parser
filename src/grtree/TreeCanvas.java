package grtree ;

import java.awt.* ;

/**
*  Canvas on which to paint the tree.
*  Does not work with Panel replacing Canvas.  ScrollPane needs
*  to hold a component which is not derived from Container
*  (apparently).
*  
*  The canvas needs to be as large as the tree so that a scrolling
*  container could view ALL of the tree
*
*/
public class TreeCanvas extends Canvas {
   private Tree tree ;
   
   public TreeCanvas(Tree t) { 
      tree = t ; 
      this.setBackground(Color.white) ;
   }

   public void setTree(Tree t) { tree = t ; }

   public void paint(Graphics g) {
      Dimension d = this.getSize() ;
      int w = tree.getTreeWidth(g) ;
      // center the tree over the width of the canvas
      tree.drawTree(g, (d.width - w)/2, 30) ;
   }     
}




