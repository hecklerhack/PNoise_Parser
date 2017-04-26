package grtree ;

import java.awt.* ;
import java.awt.event.* ;

/**
*  A TreeScrollFrame is a nice frame that paints its 
*  tree.  The TreeScrollFrame uses a ScrollPane to move around 
*  to view the tree.  The tree is drawn in the center of 
*  a TreeCanvas which is added to the ScrollPane.
*/
public class TreeScrollFrame extends Frame {
   private Tree tree ;
   
   public TreeScrollFrame(Tree t) { 
      super(t.data) ;  // title of frame shows root data
      tree = t ; 
      TreeCanvas tc = new TreeCanvas(tree) ;
      this.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent w) {
            setVisible(false) ;
         }
      }) ;
      ScrollPane scrollpane = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED) ;
      scrollpane.add(tc) ;
      this.add(scrollpane) ;
      this.setSize(400,400) ;
      // WARNING -- THE ORDER OF THE FOLLOWING INSTRUCTIONS IS IMPORTANT!!!
      this.show() ;  
      this.setVisible(false) ;
      // need to get graphics for POSITIONING
      Graphics g = tc.getGraphics() ; 
      int w = tree.getTreeWidth(g) ; 
      // tc needs be big enough to hold entire tree
      tc.setSize(w,tree.getTreeHeight(g)) ;
      // Make root visible
      this.setVisible(true) ;
      scrollpane.setScrollPosition(w/2 - 200,0) ;
   }
}

