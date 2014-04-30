import swing._
import javax.swing.ImageIcon // for importing image
import BorderPanel.Position._ //for borderpanel location

object Main {
  
  def main(args: Array[String]) = {
    val frame = new MainFrame {
      title = "Translator v2.0"
      
      //reads in image
      val myImage = new Label { 
        icon = new ImageIcon("src/images/translator.gif") //starts looking in main folder
      } 
         
      contents = new BorderPanel {
        layout += myImage -> Center
        } 
      
      menuBar = new MenuBar {
        contents += new Menu ("Translator") {
          contents += new MenuItem(Action("Translator Quiz") { 
            Quiz.main(args)
            })
          contents += new MenuItem(Action("Study") {
            Study.main(args)
            })
          contents += new Separator
          contents += new MenuItem(Action("Translator Ver 1") {
            Translator.main(args)
            })
          contents += new Separator
          contents += new MenuItem(Action("Exit") {
            sys.exit(0)
            })
          } 
        contents += new Menu ("Create") {
          contents += new MenuItem("View My List") { }
          contents += new Separator
          contents += new MenuItem("Add To My List") { }
          contents += new MenuItem("Delete From My List") { }
          contents += new MenuItem("Clear My List") { }
          contents += new Separator
          contents += new MenuItem("Begin Learning My List")
    }
  }
  size = new Dimension(450,300)
  centerOnScreen

  }// end of mainFrame
    
    frame.open
  }

}
