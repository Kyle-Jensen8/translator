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
      
      ///////// start of Creating List tab ////////// 
      pages += new Page("Creating List", new BorderPanel {
      val addButton = new Button{
        text = "Add to list!"
      } 
      
      val clearButton = new Button{
        text = "Clear Your List!" 
      }    
      
      def clearLabel = new Label {
        listenTo(clearButton)
        reactions += {
          case ButtonClicked(clearButton) =>
            db = Array[Spanglish]() 
            text = "Clear Your List!"
        } 
      }
      
     def addLabel = new Label {
        listenTo(addButton, userSpanishField, userEnglishField)
        reactions += {
          case ButtonClicked(addButton) => {
            if (userEnglishField.text.length > 0 || userSpanishField.text.length > 0) {
              val addSpan = new Spanglish(userSpanishField.text, userEnglishField.text)
              db = db:+addSpan
              userEnglishField.text = ""
              userSpanishField.text = ""
              text = ""
            }
            else text = "Sorry, you left one or more of the fields blank"
          }
        }
     }       
      
     def displayLabel = new Label {
        listenTo(addButton, clearButton)
        reactions += {
          case ButtonClicked(addButton) => 
            addLabel
          case ButtonClicked(clearButton) =>
            clearLabel
        }
     }      
     
    val database = new ListView(db.map(_.spanish)) {  
      listenTo(addButton, clearButton, addLabel, clearLabel) 
      reactions += {
        case ButtonClicked(`addButton`) =>  //partial function only listen to SelectionChanged
          listData = db.map(_.spanish)
        case ButtonClicked(`clearButton`)  =>  //partial function only listen to SelectionChanged
          listData = db.map(_.spanish)
          }
      }
    
    //start of CreateList layout
    border = Swing.EmptyBorder(20, 30, 20, 30)
    layout += new GridPanel(4,1) {
      contents += new BorderPanel{
        layout += new Label {
          text = "Enter your Spanish Word  "
        } -> West 
        layout += userSpanishField -> Center
      }
      contents += new BorderPanel{
        layout += new Label {
          text = "Enter your English Word  "
        } -> West
        layout += userEnglishField -> Center
        }
      contents += new GridPanel(1,2) {
        border = Swing.EmptyBorder(4,10,4,10)
        contents += addButton
        contents += clearButton
      }
      contents += displayLabel
      } -> Center
      layout += new ScrollPane(database) -> South
      })
      
   ///////// start of Study List tab //////////  
   pages += new Page("Study List", new BorderPanel {
     
    val theFrame = new BorderPanel() 
    
    val spanishField = new Label("")
    val englishField = new Label("") 
     
    val loadButton = new Button {
      text = "Load My List"
    } 
        
    val database = new ListView(db.map(_.spanish)) {
      listenTo(loadButton, selection) 
      reactions += {
        case ButtonClicked(_) =>  //partial function only listen to ButtonClikced
          if (db.isEmpty){
            listData = db.map(_.spanish)
            spanishField.text = ""
            englishField.text = ""  
          }
          else listData = db.map(_.spanish)  
        case event: SelectionChanged =>  //partial function only listen to SelectionChanged
          val theDB = db(selection.leadIndex)
          spanishField.text = theDB.spanish
          englishField.text = theDB.english  
       }
     }  
    
      // beginning of layout for study list page
        border = Swing.EmptyBorder(10, 10, 10, 10)
        layout += new BorderPanel {
          layout += new GridPanel(3,1){
            contents += new Label("Please select a Spanish word to translate.")
            contents += new BorderPanel{
              border = Swing.EmptyBorder(10, 50, 10, 50)
              layout += new Label("Spanish: ") -> West
              layout += spanishField -> Center
             }
            contents += new BorderPanel{
              border = Swing.EmptyBorder(10, 50, 10, 50)
              layout += new Label("English: ") -> West
              layout += englishField -> Center
            }           
          } -> North
        } -> Center 
        layout += new BorderPanel {
          layout += new GridPanel (1,1){
            border = Swing.EmptyBorder(10, 50, 10, 50)
            contents += loadButton
          } -> North
          layout += new ScrollPane(database) {
          }-> Center
        } -> South
        
    }) // end of study list page      
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