
import scala.swing._
import BorderPanel.Position._
import scala.swing.event._
import javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE

object Quiz  {
  
    def main(args: Array[String]) = {

    case class Spanglish(spanish: String, english: String)
    
    var db = Array[Spanglish](
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
    
    val spanishField = new Label(db.head.spanish)    
    val englishField = new TextField(db.head.english)    
    val userAnswerField = new TextField("")
    var correctTotal = 0
  
    def top = new MainFrame {
            
      val answerButton = new Button {
              text = "Check Answer"  
            }      
       
      var dbCopy = db
      
      def answerLabel = new Label {
              listenTo(answerButton)
              reactions += {
                case ButtonClicked(_) =>
                  if(englishField.text == userAnswerField.text){
                    text = ("Excellente!   " + spanishField.text + " -> " + englishField.text)
                    if (dbCopy.tail.isEmpty){
                      text = ("You got " + correctTotal + "/" + db.length )
                    }
                    else {
                      correctTotal += 1
                      dbCopy = dbCopy.tail
                      spanishField.text = dbCopy.head.spanish
                      englishField.text = dbCopy.head.english
                      userAnswerField.text = ""
                    }
                  }
                  else {
                    text = ("Lo siento, your are incorrect!   " + spanishField.text + " -> " + englishField.text)
                    if(dbCopy.tail.isEmpty){
                      text = ("You got " + correctTotal + "/" + db.length)
                    }
                    else {
                      dbCopy = dbCopy.tail
                      spanishField.text = dbCopy.head.spanish
                      englishField.text = dbCopy.head.english
                      userAnswerField.text = ""
                    }
                  }
              }
            }
      
      title = "Translator - Quiz"
      contents = new BorderPanel {
        layout += new GridPanel(5,1) {
          border = Swing.EmptyBorder(20, 20, 50, 20)
          contents += new Label {
            text = "Translate the Spanish word into English: "
          }
          contents += spanishField
          contents += new BorderPanel {
            border = Swing.EmptyBorder(15, 0, 15, 0)
            layout += new Label{
              text = "My Answer: "
            } -> West
            layout += userAnswerField -> Center
          }
          contents += answerLabel
          contents += new GridPanel(1,1) {
            border = Swing.EmptyBorder(10, 40, 10, 40)
            contents += answerButton  
          }
        } -> Center
      }
            
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