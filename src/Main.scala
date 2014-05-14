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
        layout += new Label {
          border = Swing.EmptyBorder(0, 0, 70, 0)
          text = "Translate yourself."
        } -> South
        } 
      
      menuBar = new MenuBar {
        contents += new Menu ("Start") {
          contents += new MenuItem(Action("Study") { 
            Study.main(args)
            })
          contents += new MenuItem(Action("Translator Quiz") {
            Quiz.main(args)
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
        contents += new Menu ("My List") {
          contents += new MenuItem(Action("Manage My List") {
            CreateMyList.main(args)
          })

    }
  }
  size = new Dimension(450,300)
  centerOnScreen

  }// end of mainFrame
    
    frame.open
  }

}
