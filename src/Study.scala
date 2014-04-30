
import scala.swing._
import BorderPanel.Position._ //for borderpanel location
import scala.io.Source // for opening files
import scala.swing.event._ //for case events
import TabbedPane._
import javax.swing.ImageIcon // for importing image

object Study extends SimpleSwingApplication{
  
  
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
      
      def displayAnswer {
        if(dbCopy.isEmpty){
              // does nothing, stops it from going out of bounds
            }
            else {
            spanishField.text =  dbCopy.head.spanish
            }
        }

      val answerButton = new Button{
        text = "See Spanish Translation"
          listenTo(this)
          reactions += {
          case ButtonClicked(_) | EditDone(_) =>
            displayAnswer
        }         
      }      
 
      def imgPanel = new BorderPanel{
        layout += myImage -> Center
        listenTo(nextButton)
        reactions += {
          case ButtonClicked(_) | EditDone(_) =>
            changeImage
        }
        preferredSize = new Dimension(100,100)
       }
      
      def changeImage {
        if(dbCopy.length == 1){
              nextButton.text = "Restart Study Images"  
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
      
        // beginning of layout for study image page
        layout += imgPanel -> Center
        layout += new GridPanel(3,1) {
          border = Swing.EmptyBorder(0, 20, 10, 20)
          contents += spanishField 
          contents += answerButton 
          contents += nextButton 
        } -> South
           

    }) // end of study images page
    
    pages += new Page("More Pages?", new BorderPanel {
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

