// Translator tester

object Tester {

	def main(args: Array[String]): Unit = {
	
	 // Spanish to English database
	 val db = Map(
	          // first set
	          ("hola" -> "hello"),
              ("adios" -> "bye"),
              ("buenos dias" -> "good morning"),
              ("gracias" -> "thank you"),
              ("si" -> "yes"),
              ("bien" -> "good"),
              // second set (if user correctly answer 100%)
              ("nombre" -> "name"),
              ("feche" -> "date"),
              ("uno" -> "one"),
              ("dos" -> "two"),
              ("tres" -> "three"),
              ("buenas tarde" -> "good afternoon"))
              
    val db1 = Map(
              // first set
              (0 -> "hola"),
              (1 -> "adios"),
              (2 -> "buenos dias"),
              (3 -> "gracias"),
              (4 -> "si"),
              (5 -> "bien"),
              // second set (if user correctly answer 100%)
              (6 -> "nombre"),
              (7 -> "feche"),
              (8 -> "uno"),
              (9 -> "dos"),
              (10 -> "tres"),
              (11 -> "buenas tarde")
              )
              
   var goOn = true        
   var count = 0 // keeps count of how many questions you answer correct 
   var key = 0 // used as key for db1 database
   
   while (goOn) {
     
     // each set contains 6 questions
     for(i <- 0 to 5) { 
       
       var ok = true
	   val question = db1(key) // returns associated value of key in db1 database
	   print("Spanish : ")
	   println(question)
	   print("English : ")
       val ln = readLine() // prompts user input
       ok = ln == (db(question)) //checks user's answer
       
       if (ok) {
         println("Excellente!\n")
	     count += 1
	     key += 1 }
       else {
         println("Sorry, nice try. Answer is : " + (db(question)) + "\n")
         key += 1 }
       }
     
	   // if user gets 6/6 correct continues with more words
	   if (count > 5 && count < 7){
	     println("********** Congratulations! You have UNLOCKED more words! **********\n")  
	   }
	   // if user gets 12/12 correct, end translator
	   else if (count > db.size - 1){
	     println("********** Congratulations! You are now a certified bilingual spearker!!! **********\n")
	     goOn = false
	   }
	   // if user gets at least 1 wrong, end translator
	   else {
	     println("You got " + count + "/" + key)
	     println("Study harder and try again!")
	     goOn = false
	     }
	   
       }// end of while 
	 }//end of main
	}