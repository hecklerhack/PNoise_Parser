package grtree ;

import java.awt.* ;

public class GrtreeApplet extends java.applet.Applet {

   TreeCanvas treeCanvas ;

   public void init() {
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
      treeCanvas = new TreeCanvas(jrfisher) ;
      int w = jrfisher.getTreeWidth(getGraphics()) ; 
      // treeCanvas needs be big enough to hold entire tree
      treeCanvas.setSize(w,jrfisher.getTreeHeight(getGraphics())) ;
      add(treeCanvas) ;
   }

   public void paint(Graphics g) {
      treeCanvas.repaint() ; 
   }

}
