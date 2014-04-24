import swing._
import javax.imageio.ImageIO //for read
import java.io.File // to create new File
import BorderPanel.Position._ //for borderpanel location

object Main {
  
  def main(args: Array[String]): Unit = {
    val frame = new MainFrame {
      title = "Translator v2.0"
      
      //reads in image
      val myImage = ImageIO.read(new File("src/images/translator.gif"))//starts looking in main folder
      val imgPanel = new Panel {
      override def paint(g:Graphics2D){
      g.drawImage(myImage,20,55,null)
      }
      }  
        
      contents = new BorderPanel {
        layout += imgPanel -> Center
        } 
      
      menuBar = new MenuBar {
        contents += new Menu ("Translator") {
          contents += new MenuItem(Action("Translator Quiz") { 
            Translator.main(args)
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
  size = new Dimension(400,325)
  centerOnScreen

  }// end of mainFrame
    
    frame.open
  }

}
