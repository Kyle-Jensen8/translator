
import scala.swing._
import BorderPanel.Position._ //for borderpanel location
import scala.io.Source // for opening files
import scala.swing.event._ //for case events
import TabbedPane._
import javax.imageio.ImageIO //for read
import java.io.File //to create new File

object Study {
  
   def main(args: Array[String]): Unit = {
  
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
      
      var db2 = Array[Spanglish](
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
    val englishField = new Label("")
    val locField = new Label("")
          
    val database = new ListView(db2.map(_.english)) {
      listenTo(selection) 
      reactions += {
        case event: SelectionChanged =>  //partial function only listen to SelectionChanged
          val theDB = db2(selection.leadIndex)
          spanishField.text = theDB.spanish
          englishField.text = theDB.english
          locField.text = theDB.loc
          
          val myImage = ImageIO.read(new File(locField.text))
          val imgPanel = new Label {
            override def paint(g:Graphics2D){
            g.drawImage(myImage,100,20,null)
            }
            preferredSize = new Dimension(400,250)
            } 
          
          
          def frame = new Frame {
            title = "Translator - Images"
              
            val answerButton = new Button {
              text = "Spanish Translation"  
            }
            val answerLabel = new Label {
              listenTo(answerButton)
              reactions += {
                case ButtonClicked(_) | EditDone(_) => text = spanishField.text
              }
            }
            val label = new Label {
              text = englishField.text
            }
            
            contents = new BorderPanel {
              layout += imgPanel -> North
              layout += new GridPanel(2,1) {
                border = Swing.EmptyBorder(0, 50, 10, 50)
                contents += answerLabel
                contents += answerButton
            } -> Center
            }
            centerOnScreen
        
              // this allows the user to close window without exiting app
              import javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE
              peer.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE)
              override def closeOperation() { close() }
            }
          frame.open
          }
      }
      
      //beginning of study images layout
        layout += new BorderPanel {
          border = Swing.EmptyBorder(10, 50, 30, 50)
          layout += new Label ("Please select a word to view") -> North
          layout += new ScrollPane(database) -> Center
        } -> Center        

    }) // end of study images page
    
    pages += new Page("More Pages?", new BorderPanel {
      layout += new Label("Under Construction") -> Center
    })
  }
  
  val ui: Panel = new BorderPanel {
    layout(tabs) = BorderPanel.Position.Center
  }
   
  
  val frame = new MainFrame { 
    title = "Translator v2.0 | Study"
    contents = ui
    centerOnScreen
    
    size = new Dimension(450,300)   
      
    // this allows the user to close window without exiting app
    import javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE
    peer.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE)
    override def closeOperation() { close() }
    }
  frame.open
   }
}

