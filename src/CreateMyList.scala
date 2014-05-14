import scala.swing._
import BorderPanel.Position._
import scala.swing.event._
import TabbedPane._
import javax.swing.ImageIcon
import javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE
import scala.io.Source

object CreateMyList {
      
	case class Spanglish(spanish: String, english: String)
    var db = List[Spanglish]()  

    
    def main(args: Array[String]) = {
	  
 
 
    val userEnglishField = new TextField("")
    val userSpanishField = new TextField("")
    
    val tabPane = new TabbedPane{
      
      ///////// start of Creating List tab ////////// 
      pages += new Page("Create My List", new BorderPanel {
      val addButton = new Button{
        text = "Add to list!"
      } 
      
      val clearButton = new Button{
        text = "Clear Your List!" 
      }
      
      val openButton = new Button {
        text = "Choose existing list"
      }
      
      val saveButton = new Button {
        text = "Save Your List"
      }
      
      var clearLabel = new Label {
        listenTo(clearButton)
        reactions += {
          case ButtonClicked(clearButton) =>
            db = List[Spanglish]() 
        } 
      }
      
     var addLabel = new Label {
        listenTo(addButton, userSpanishField, userEnglishField)
        reactions += {
          case ButtonClicked(addButton) => {
            if (userEnglishField.text.length > 0 && userSpanishField.text.length > 0) {
              val addSpan = new Spanglish(userSpanishField.text, userEnglishField.text)
              db = db:+addSpan
              userEnglishField.text = ""
              userSpanishField.text = ""
            }
          }
        }
     }     
     
    val database = new ListView(db.map(_.spanish)) {  
      listData = Seq(" ")
      listenTo(addButton, clearButton, addLabel, clearLabel, openButton, saveButton) 
      reactions += {
        case ButtonClicked(`addButton`) =>  //partial function only listen to SelectionChanged
          listData = db.map(_.spanish)
        case ButtonClicked(`clearButton`)  =>  //partial function only listen to SelectionChanged
          listData = Seq(" ")
        case ButtonClicked(`openButton`) =>
          openFile
          listData = db.map(_.spanish)
        case ButtonClicked(`saveButton`) =>
          saveFile
      	}
      
      }
    
    //start of CreateList layout
    border = Swing.EmptyBorder(0, 30, 10, 30)
    layout += new GridPanel(3,1) {
      contents += new BorderPanel{
        border = Swing.EmptyBorder(15, 0, 0, 0)
        layout += new Label {
          text = "Enter your Spanish Word  "
        } -> West 
        layout += userSpanishField -> Center
      }
      contents += new BorderPanel{
        border = Swing.EmptyBorder(0, 0, 15, 0)
        layout += new Label {
          text = "Enter your English Word  "
        } -> West
        layout += userEnglishField -> Center
        }
      contents += new GridPanel(2,2) {
        border = Swing.EmptyBorder(0,10,0,10)
        contents += addButton
        contents += clearButton
        contents += saveButton
        contents += openButton
      }
      } -> Center
      layout += new ScrollPane(database) -> South
      })
      
   ///////// start of Study List tab //////////  
   pages += new Page("Study My List", new BorderPanel {
     
    val theFrame = new BorderPanel() 
    
    val spanishField = new Label("")
    val englishField = new Label("") 
     
    val loadButton = new Button {
      text = "Load My List"
      spanishField.text = ""
      englishField.text = ""
    } 
        
    val database = new ListView(db.map(_.spanish)) {
      listData = Seq("Please load your list to view")
      listenTo(loadButton, selection) 
      reactions += {
        case ButtonClicked(_) =>  //partial function only listen to ButtonClikced
          if (db.isEmpty){ // if user does not initially create a list
            spanishField.text = " "
            englishField.text = " " 
            listData = Seq("There is no list to load")
          }
          else listData = db.map(_.spanish)  // loads list
        case event: SelectionChanged =>  //partial function only listen to SelectionChanged
          if (db.isEmpty){ // if user clears list
            spanishField.text = " "
            englishField.text = " " 
            listData = Seq("There is no list to load")
          }
          else {
          val theDB = db(selection.leadIndex)
          spanishField.text = theDB.spanish
          englishField.text = theDB.english  
          }
       }
     }  
    
      // beginning of layout for study list page
        border = Swing.EmptyBorder(10, 30, 10, 30)
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
    menuBar = new MenuBar {
        contents += new Menu("File"){
        contents += new MenuItem(Action("Open")(openFile))
        contents += new Separator 
        contents += new MenuItem(Action("Save")(saveFile)) //TODO: write saveFile
      }
    }
    contents = ui
    title = "Translator v2.0 | Managing Personal List"
    centerOnScreen
    size = new Dimension(450,400)
    peer.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE)
    override def closeOperation() { close() }
    }
    mainFrame.open
  }
	  //function used to choose and open a text file with
  //a list in it!
  def openFile {
    val chooser = new FileChooser(null)
    chooser.showOpenDialog(null)
    val src = Source.fromFile(chooser.selectedFile)  //setting src to the selected file
    val lines = src.getLines
    while (lines.hasNext){  
    val spanWord = lines.next
    val engWord = lines.next
    val use = new Spanglish(spanWord, engWord)
    db = db:+use
    }
  src.close()
  }
  
    //function used to save your current list to a text file
  def saveFile{
    val chooser = new FileChooser(null)
    chooser.showSaveDialog(null)
    val pw = new java.io.PrintWriter(chooser.selectedFile) //makes a printwriter to the selected file
    var index = 0
    while (index < db.length){ 
        var spanObj = db(index)
        var spanWord = spanObj.spanish
    	var engWord = spanObj.english
    	pw.println(spanWord)
    	pw.println(engWord)
    	index = index+1
  }
    pw.close()
  }

}
