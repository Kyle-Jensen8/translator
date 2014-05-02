import scala.swing._
import BorderPanel.Position._
import scala.swing.event._
import TabbedPane._
import javax.swing.ImageIcon
import javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE


object CreateMyList {
  
  def main(args: Array[String]) = {
   
    case class Spanglish(spanish: String, english: String)
    var db = Array[Spanglish]() 
   
    
    val userEnglishField = new TextField("")
    val userSpanishField = new TextField("")
    
    def mainFrame = new MainFrame {
      title = "Creating List"
     
      val addButton = new Button{
        text = "Add to list!"
      } 
      
     def addLabel = new Label {
        listenTo(addButton)
        reactions += {
          case ButtonClicked(_) =>
            if (userEnglishField.text == "" || userSpanishField == ""){
              text = "Sorry, you left one or more of the fields blank"
            }
            else { //make sure this works!!!!
              val addSpan = new Spanglish(userEnglishField.text, userSpanishField.text)
              db:+addSpan
              userEnglishField.text = ""
              userSpanishField.text = ""
            }
        }
     }
      
    
          contents = new BorderPanel {
        layout += new GridPanel(5,1) {
          border = Swing.EmptyBorder(20, 20, 50, 20)
          contents += new BorderPanel{
          layout += new Label {
            text = "Enter your Spanish Word   "
          } -> West 
          layout += userSpanishField -> Center
          }
          
          contents += new BorderPanel{
          layout += new Label {
            text = "Enter your English Word    "
          } -> West
          layout += userEnglishField -> Center
          }
          contents += new GridPanel(1,1) {
            contents += addButton
          }
          contents += addLabel
        } -> Center
      }
    centerOnScreen
    size = new Dimension(450,400)
    peer.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE)
    override def closeOperation() { close() }
    }
    mainFrame.open
  }
}