package grtree ;   // "graphic" tree

import java.awt.* ;
import java.awt.event.* ;
import java.util.* ;

/**
*  Tree is a class representing a kind of generalized tree
*  that the user wishes to draw on a graphics plane or 
*  print on some page (or graphics plane).
*
*  A tree of this kind has data and any number of children.  
*  The data is simply a String and the children is a Vector
*  of Tree's.
*  
*  A Tree can draw itself on any appropriate graphics plane
*  using its drawTree method.  A Tree can print itself at 
*  TTY (or elsewhere) via its toString method.
*
*  @author jrfisher@csupomona.edu
*  @version proto2
*/
public class Tree {
   /**
   * A Tree has data and children.
   */
   public String data ;
   public Vector children ;

   // other fields for drawing
   private Font font = new Font("monospaced",Font.PLAIN,11) ;
   private Color background = Color.lightGray ;
   private Color leafColor = Color.green ;
   private Color fontColor = Color.black ;
   private Color edgeColor = Color.darkGray ;

   /**
   *  Construct a Tree using (simple) String.
   */
   public Tree(String s) {
      data = s ;
      children = new Vector() ;
   }

   /**
   *  Add a child to this tree.
   */
   public void addChild(Tree t) {
      if (t != null) 
         children.addElement(t) ;
      // else do nothing ;
   }


   /**
   *  Remove a child from this tree.
   */
   public void removeChild(Tree t) {
      children.removeElement(t) ;
   }

   /**
   *  Is this a leaf?
   */
   public boolean isLeaf() {
      return (children.size() == 0) ;
   }

   /** 
   *  .toString() 
   *  .toString(int)
   *  Generate a String representation for the entire tree
   *  This is the familiar cascaded (tabbed tree) form.
   */
   public String toString() {
      return toString(0) ;
   }
   public String toString(int tab) {
      String result = "" ; 
      // calculate (String) tabs first
      for(int i = 0 ; i <= tab-1 ; i ++) result += "  " ;  
      result += "|-" + data + "\n" ;
      for(int j = 0 ; j <= children.size()-1 ; j++)  
         result += ((Tree)(children.elementAt(j))).toString(tab+1) ;
      return result ; 
   }

   /**
   *  Return TreeExpression for this tree.
   *
   *  NOT YET IMPLEMENTED.
   */
   public String toTreeExpression() {
      return null ;
   }


   /**
   *  How wide/high is the root node of this tree when drawn?
   */
   public int getNodeWidth(Graphics g) {
      // get the width of this node
      g.setFont(font) ;
      FontMetrics fm =  g.getFontMetrics(font) ;
      int w = fm.stringWidth(this.data) ;
      int margin = fm.stringWidth(" ") ;
      return 2*margin + w ;
   }
   public int getNodeHeight(Graphics g) {
      // get the height of this node
      g.setFont(font) ;
      FontMetrics fm =  g.getFontMetrics(font) ;
      int h = fm.getHeight() ;
      return 2*h ;
   }

   public void drawNode(Graphics g, int x, int y) {
      g.setFont(this.font) ;
      FontMetrics fm =  g.getFontMetrics(this.font) ; 
      if (isLeaf()) g.setColor(leafColor) ;
      else g.setColor(this.background) ;
      int width = fm.stringWidth(this.data) ;
      int margin = fm.stringWidth(" ") ;
      int height = fm.getHeight() ;
      g.fill3DRect(x,y,2*margin + width, 2*height,true) ;
      g.setColor(this.fontColor) ;
      g.drawString(this.data, x + margin, y + (int)((1.3)*height)) ;
   }

   // graphical estimate of tree width (as drawn)
   public int getTreeWidth(Graphics g) {
      int w = this.getNodeWidth(g) + 10 ;
      int wc = 0 ;
      if (children.size() == 0) return w ;
      else {
         for(int i = 0 ; i < children.size() ; i++) 
            wc += ((Tree)children.elementAt(i)).getTreeWidth(g) ;
         return Math.max(w,wc) ;
      }
   }

   // graphical estimate of tree height (as drawn)
   public int getTreeHeight(Graphics g) {
      int h = 2*this.getNodeHeight(g) ;
      if (children.size() == 0) return h ;
      else {
         int h1 = ((Tree)children.elementAt(0)).getTreeHeight(g) ;
         for(int i = 1 ; i < children.size() ; i++) { 
            int h2 = ((Tree)children.elementAt(i)).getTreeHeight(g) ;
            if (h2 > h1) h1 = h2 ;
         }
         return h+h1 ;
      }
   }

   /**
   *  drawTree draws the tree in the Graphics
   *  plane using upper-left corner x,y (for box
   *  containing the tree).
   */
   public void drawTree(Graphics g, int x, int y) {
      int nw = this.getNodeWidth(g) ;
      int tw = this.getTreeWidth(g) ;
      int h = this.getNodeHeight(g) ; 
      this.drawNode(g,x + (tw - nw)/2,y) ;
      if (children.size() > 0) { 
         int dx = x ; // start at left
         for(int k = 0 ; k < children.size() ; k++) {
            Tree t = (Tree)(children.elementAt(k)) ; 
            int cw = t.getTreeWidth(g) ;
            // draw the edge
            g.setColor(this.edgeColor) ;
            g.drawLine(x+tw/2,y+h, dx+cw/2,y+2*h) ;
            // draw child tree ...
            t.drawTree(g,dx,y+2*h) ;
            // move over to right on drawing pane
            dx += cw ;
         }
      }
   }

   // for testing methods and constructor ...
   public static void main(String[] args) {
      Tree jrfisher = new Tree("jrfisher") ;
      Tree www = new Tree("www") ;
      Tree java_topics = new Tree("java_topics") ;
      Tree linkedlist = new Tree("linkedlist") ;
      Tree lldoc = new Tree("doc") ;
      Tree tree = new Tree("tree") ;
      Tree treedoc = new Tree("doc") ;
      Tree complex = new Tree("complex") ;
      Tree complexdoc = new Tree("doc") ;
      Tree xsb = new Tree("xsb") ;
      Tree logic = new Tree("logic") ;
      jrfisher.addChild(www) ;
      jrfisher.addChild(xsb) ;
      www.addChild(java_topics) ;
      java_topics.addChild(complex) ;
      java_topics.addChild(linkedlist) ;
      java_topics.addChild(tree) ;
      complex.addChild(complexdoc) ;
      linkedlist.addChild(lldoc) ;
      tree.addChild(treedoc) ;
      xsb.addChild(logic) ;
      // command-line output
      System.out.println(jrfisher.toString()) ;
      // graphical output
      new TreeScrollFrame(jrfisher) ;
   }
}

