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
    
  val tabPane = new TabbedPane{
      
      val addButton = new Button{
        text = "Add to list!"
      } 
      val clearButton = new Button{
        text = "Clear Your List!"
      }
      
       //  val database = new ListView(db.map(_.spanish)) {
     // } 
      
      
      val addLabel = new Label {
        listenTo(addButton)
        reactions += {
          case ButtonClicked(_) =>
            if (userEnglishField.text == "" || userSpanishField == ""){
              text = "Sorry, you left one or more of the fields blank"
            }
            else {
              val addSpan = new Spanglish(userEnglishField.text, userSpanishField.text)
              db = db:+addSpan
              //testing to make sure array is being added to
              db.foreach(println)
              userEnglishField.text = ""
              userSpanishField.text = ""
            }
        }
     }
      
      val clearLabel = new Label {
        listenTo(clearButton)
        reactions += {
          case ButtonClicked(_) =>
            var dbReplacement = Array[Spanglish]()
            db = dbReplacement
            text = "Your list has been cleared."
        } 
      }
      
      pages += new Page("Creating List", new BorderPanel {
        //layout += new ScrollPane(database) -> West
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
      })
    
     
      pages += new Page("Clear List", new BorderPanel {
    	  
        layout += new GridPanel(4,1){
          border = Swing.EmptyBorder(20, 20, 50, 20)
    	    contents += clearButton
    	    contents += clearLabel
    	  } -> Center
     
     })
     
    
    }
    
    val ui: Panel = new BorderPanel {
      layout(tabPane) = BorderPanel.Position.Center
    }
    
    val mainFrame = new MainFrame {
    contents = ui
    title = "Managing Personal List"
    centerOnScreen
    size = new Dimension(450,400)
    peer.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE)
    override def closeOperation() { close() }
    }
    mainFrame.open
  }
}