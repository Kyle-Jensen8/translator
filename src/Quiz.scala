import swing._
import swing.event._
import TabbedPane._
import BorderPanel.Position._
import scala.io.Source
import scala.util._
import scala.util.Random._
import javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE
import scala.swing.RadioMenuItem

object Quiz {
  
  case class Spanglish(spanish: String, english: String)
    
  // function that creates a Spanglish object 
  def createObj(line: String) = {
      val p = line.split("\t") // elements are separated by tabs
      val spanish = p(0) 
      val english = p(1)
      Spanglish(spanish, english)  
  }
    
  // imports text file
  val source = Source.fromFile("Spanglish.txt")
     
  // reads text file line by line
  val lines = source.getLines
    
  // puts each line into a list
  val db = lines.map(createObj).toList
    
  source.close

  def main(args: Array[String]): Unit = {
    
    val tabs = new TabbedPane {
      
      pages += new Page("Multiple Choice", new BorderPanel {
        
        var start = db.head
    
        // displays spanish word to answer
        val spanishField = new Label(db.head.spanish) 
    
        // holds english word to compare user answer with
        val englishField = new Label(db.head.english)  
        
        def check (x : Spanglish) = {
          var newWord = db(Random.nextInt(db.length))
          while(x == newWord){
            newWord = db(Random.nextInt(db.length))
          }
          newWord
        }
        
        def check2 (x : Spanglish, x2 : Spanglish ) = {
          var newWord = db(Random.nextInt(db.length))
          while(x == newWord | x2 == newWord){
            newWord = db(Random.nextInt(db.length))
          }
          newWord
        }
        
        var result = check(start)
        var result2 = check2(start, result)
        
        val questionField = new Label("What is the English translation for " + spanishField.text)
        
        var possibleAnswers = List(englishField.text, result.english, result2.english)
        // shuffles possible answers
        var shuffleList = shuffle(possibleAnswers)

        var mutex = new ButtonGroup
        var answer1 = new RadioButton{
          text = shuffleList.head  
        }
        var answer2 = new RadioButton{
          text = shuffleList.tail.head
        }   
        var answer3 = new RadioButton{
          text = shuffleList.last
        }
        var invisibleRadioButton = new RadioButton {
          this.visible = false
        }
        var radios = List(answer1, answer2, answer3, invisibleRadioButton)
        
        mutex.buttons ++= radios 
        
        mutex.select(invisibleRadioButton)
        
        val radioButtons = new BoxPanel(Orientation.Vertical) {
          contents ++= radios
        }
        
        val submitButton = new Button {
          text = "Submit Answer"
        }
        

        var dbCopy = db
        var correctTotal = 0
        
        def answerLabel = new Label {
          text = ""
          listenTo(submitButton, radioButtons)
          reactions += {
            case ButtonClicked(submitButton) =>
              // if user correctly answers question
              if(mutex.selected.get.text == englishField.text){
                correctTotal += 1
                text = ("Excellente!   " + spanishField.text + " -> " + englishField.text)
                if (dbCopy.tail.isEmpty){
                    text = ("Excellente!   " + spanishField.text + " -> " + englishField.text)
                    text = ("You got " + correctTotal + "/" + db.length )
                    submitButton.visible = false         
                }
                else {  
                dbCopy = dbCopy.tail
                start = dbCopy.head
                spanishField.text = (dbCopy.head.spanish) 
                englishField.text = (dbCopy.head.english)  

                result = check(start)
                result2 = check2(start, result)
                
                possibleAnswers = List(englishField.text, result.english, result2.english)
                shuffleList = shuffle(possibleAnswers)                
                answer1.text = shuffleList.head
                answer2.text = shuffleList.tail.head
                answer3.text = shuffleList.last
                mutex.select(invisibleRadioButton)      
                questionField.text = ("What is the English translation for " + spanishField.text)
                }
              }
              // else user incorrectly answers question
              else {
                text = ("Lo siento, your are incorrect!   " + spanishField.text + " -> " + englishField.text)
                if(dbCopy.tail.isEmpty){
                  text = ("You got " + correctTotal + "/" + db.length)
                  submitButton.visible = false
                }
                else {
                  dbCopy = dbCopy.tail
                  start = dbCopy.head
                  spanishField.text = (dbCopy.head.spanish) 
                  englishField.text = (dbCopy.head.english) 
                  
                  result = check(start)
                  result2 = check2(start, result)
                
                  possibleAnswers = List(englishField.text, result.english, result2.english)
                  shuffleList = shuffle(possibleAnswers)                
                  answer1.text = shuffleList.head
                  answer2.text = shuffleList.tail.head
                  answer3.text = shuffleList.last
                  mutex.select(invisibleRadioButton)      
                  questionField.text = ("What is the English translation for " + spanishField.text)
                  }
                }
              }
          }


          border = Swing.EmptyBorder(30, 30, 30, 30)
          layout += questionField -> North
          layout += new BorderPanel{
            border = Swing.EmptyBorder(30, 0, 30, 0)
            layout += radioButtons -> Center
            layout += answerLabel -> South
          } -> Center
          layout += submitButton -> South
      })
      
      pages += new Page("Fill In Blank", new BorderPanel {

        // displays spanish word to answer
        val spanishField = new Label(db.head.spanish) 
    
        // holds english word to compare user answer with
        val englishField = new TextField(db.head.english)   
    
        // user input field to answer question
        val userAnswerField = new TextField("")
        var correctTotal = 0

        // creates a button to check answer
        val answerButton = new Button {
          text = "Check Answer"  
        }
        
        // creates a button to restart test
        val restartButton = new Button {
          text = "Restart Test"
        }
        
        var dbCopy = db
        
        // creates a label will display if user correctly or incorrectly answered question
        // executes when answer button when clicked
        def answerLabel = new Label {
          listenTo(answerButton)
          reactions += {
            case ButtonClicked(_) =>
              // if user correctly answers question
              if(englishField.text == userAnswerField.text){
                correctTotal += 1
                text = ("Excellente!   " + spanishField.text + " -> " + englishField.text)
                if (dbCopy.tail.isEmpty){
                  text = ("You got " + correctTotal + "/" + db.length )
                  answerButton.visible = false
                }
                else {
                  dbCopy = dbCopy.tail
                  spanishField.text = dbCopy.head.spanish
                  englishField.text = dbCopy.head.english
                  userAnswerField.text = ""
                }
              }
              // else user incorrectly answers question
              else {
                text = ("Lo siento, your are incorrect!   " + spanishField.text + " -> " + englishField.text)
                if(dbCopy.tail.isEmpty){
                  text = ("You got " + correctTotal + "/" + db.length)
                  answerButton.visible = false
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
      
      // beginning of layout  
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
    })
  }
    
  val ui: Panel = new BorderPanel {
    layout(tabs) = BorderPanel.Position.Center
  }
  
  val top = new MainFrame {
    contents = ui
    title = "Translator v2.0 | Quiz"
    centerOnScreen
    size = new Dimension(450,400)
    peer.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE)
    override def closeOperation() { close() }
    }
  top.open
  }

}