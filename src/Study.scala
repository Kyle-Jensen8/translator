
import scala.swing._
import BorderPanel.Position._
import scala.swing.event._
import TabbedPane._
import javax.swing.ImageIcon
import javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE

object Study {
  
  def main(args: Array[String]) = {
  
  val tabs = new TabbedPane {
    
    pages += new Page("Study List", new BorderPanel {
      
      case class Spanglish(spanish: String, english: String)
      
      var db1 = Array[Spanglish](
        Spanglish("hola", "hello"),
        Spanglish("adios", "bye"),
        Spanglish("gracias", "thank you"),
        Spanglish("de nada", "welcome"),
        Spanglish("bien", "good"),
        Spanglish("malo", "bad"), 
        Spanglish("numero", "number"),
        Spanglish("uno", "one"),
        Spanglish("dos", "two"),
        Spanglish("tres", "three"),
        Spanglish("buenos dias", "good morning"),
        Spanglish("buenas tardes", "good afternoon"),
        Spanglish("buenas noches", "good evening"),
        Spanglish("el padre", "father"),
        Spanglish("la madre", "mother"),
        Spanglish("el hermano", "brother"),
        Spanglish("la hermana", "sister"),
        Spanglish("el tio", "uncle"),
        Spanglish("el primo", "cousin"),
        Spanglish("la familia", "family")
        )
        
    val spanishField = new Label("")
    val englishField = new Label("")    
        
    val database = new ListView(db1.map(_.spanish)) {
      listenTo(selection) 
      reactions += {
        case event: SelectionChanged =>  //partial function only listen to SelectionChanged
          val theDB = db1(selection.leadIndex)
          spanishField.text = theDB.spanish
          englishField.text = theDB.english
          }
      }    
    
      // beginning of layout for study list page
        border = Swing.EmptyBorder(10, 10, 10, 10)
        layout += new ScrollPane(database) -> West
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
    }) // end of study list page
    
   pages += new Page("Study Images", new BorderPanel {
      
      case class Spanglish(spanish: String, english: String, loc: String)
    
      var db = Array[Spanglish](
        Spanglish("uno", "one", "src/images/uno.jpg"),
        Spanglish("dos", "two", "src/images/dos.jpg"),
        Spanglish("tres", "three", "src/images/tres.jpg"),
        Spanglish("el padre", "father", "src/images/padre.jpg"),
        Spanglish("la madre", "mother", "src/images/madre.jpg"),
        Spanglish("el hermano", "brother", "src/images/hermano.jpg"),
        Spanglish("la hermana", "sister", "src/images/hermana.jpg"),
        Spanglish("el tio", "uncle", "src/images/tio.jpg"),
        Spanglish("el primo", "cousin", "src/images/primo.jpg")
        )
    
      val spanishField = new Label("")    
      val englishField = new TextField("")  
      val locField = new Label (db.head.loc)
      var dbCopy = db 
  
      // reads in new Images
      val myImage = new Label { 
        icon = new ImageIcon(locField.text)
      }      
      
      // Displays Spanish word that relates to image
      def displayAnswer {
        if(dbCopy.isEmpty){
            spanishField.text = ""
            }
            else {
            spanishField.text =  dbCopy.head.spanish
            }
        }

      val answerButton = new Button{
        text = "Answer"
          listenTo(this)
          reactions += {
          case ButtonClicked(_) =>
            displayAnswer
        }         
      }      
 
      // Loads images to panel depending on what button (next or restart) is clicked.
      def imgPanel = new BorderPanel{
        layout += myImage -> Center
        
        listenTo(restartButton, nextButton)
        reactions += {
          case ButtonClicked(`restartButton`) =>
            restartImage
          case ButtonClicked(`nextButton`) =>  
            changeImage
        }     
        preferredSize = new Dimension(100,100)
       }
      
      // Function reloads next image to view when nextButton is clicked
      def changeImage {
        if(dbCopy.length == 1){
          spanishField.text = "End of Tutorial"  
            }
        else {
            dbCopy = dbCopy.tail
            locField.text = dbCopy.head.loc
            myImage.icon  = new ImageIcon(locField.text)
            spanishField.text =  "" //clear answer
            }
      }
      
      val nextButton = new Button{
        text = "Next Image"
      }
      
      // Function reloads images to view when restartButton is clicked
      def restartImage {
        listenTo(restartButton)
        reactions += {
          case ButtonClicked(_) =>
            dbCopy = db
            locField.text = dbCopy.head.loc
            myImage.icon  = new ImageIcon(locField.text)
            spanishField.text =  "" //clear answer
        }
      }
      
      val restartButton = new Button{
        text = "Restart Tutorial"
          listenTo(this)
          reactions += {
          case ButtonClicked(_) =>
            restartImage
        }
      }
      
        // beginning of layout for study image page
        layout += imgPanel -> Center
        layout += new GridPanel(2,1) {
          border = Swing.EmptyBorder(0, 20, 10, 20)
          contents += spanishField
          contents += new GridPanel(1,3) {
            contents += restartButton
            contents += answerButton 
            contents += nextButton 
          }
        } -> South
           

    }) // end of study images page
    
    pages += new Page("More Pages ?", new BorderPanel {
      layout += new Label("Under Construction") -> Center
    })
  }
  
  val ui: Panel = new BorderPanel {
    layout(tabs) = BorderPanel.Position.Center
  }
   
  
  val top = new MainFrame { 
    title = "Translator v2.0 | Study"
    contents = ui
    centerOnScreen
    
    size = new Dimension(450,400)   
      
    // this allows the user to close window without exiting app
    import javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE
    peer.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE)
    override def closeOperation() { close() }
    }
  top.open
  }
}

