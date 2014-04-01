import java.util.Random
import java.io._
//import scala.util.Random
import scala.io.Source


object Tester {

	def main(args: Array[String]): Unit = {
					
	 val db = Map(("hola" -> "hello"),
              ("adios" -> "bye"),
              ("buenos dias" -> "good morning"),
              ("gracias" -> "thank you"),
              ("si" -> "yes"),
              ("bien" -> "good"),
              // second set if user correctly answer 100%
              ("nombre" -> "name"),
              ("feche" -> "date"),
              ("uno" -> "one"),
              ("dos" -> "two"),
              ("tres" -> "three"),
              ("buenas tarde" -> "good afternoon"))
              
    val db1 = Map((0 -> "hola"),
              (1 -> "adios"),
              (2 -> "buenos dias"),
              (3 -> "gracias"),
              (4 -> "si"),
              (5 -> "bien"),
              // second set if user correctly answer 100%
              (6 -> "nombre"),
              (7 -> "feche"),
              (8 -> "uno"),
              (9 -> "dos"),
              (10 -> "tres"),
              (11 -> "buenas tarde")
              )
              
   var goOn = true          
   var count = 0 // keeps count of how many questions you answer correct 
   var idx = 0 // index
   
   while (goOn) {
     
     for(i <- 0 to 5) {
       var ok = true
	   val question = db1(idx)
	   print("Spanish : ")
	   println(question)
	   print("English : ")
       val ln = readLine() //prompts user input
       ok = ln == (db(question))
       
       if (ok) {
         println("Excellente!\n")
	     count += 1
	     idx += 1 }
       else {
         println("Sorry, nice try. Answer is : " + (db(question)) + "\n")
         idx += 1 }
       }
     
	 //if user gets at least 6/6 correct 
	 if (count > 5 && count < 7){
	   println("********** Congratulations! You have UNLOCKED more words! **********\n")  
	 }
	 else if (count > db.size - 1){
	   println("********** Congratulations! You are now a certified bilingual spearker!!! **********\n")
	   goOn = false
	 }
	 else {
	   println("You got " + count + "/" + idx)
	   println("Study harder and try again!")
	   goOn = false
	 }
	 
   }// end of while
    
    
 }
}