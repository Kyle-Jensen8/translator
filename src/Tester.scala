import java.util.Random

object Tester {

	def main(args: Array[String]): Unit = {
			val a = new Definition
					a.englishWord = "House"
					a.spanishWord = "Casa"
					println(a.englishWord + a.spanishWord) 
					
	 val db = Map(("hola" -> "hello"),
              ("adios" -> "goodbye"),
              ("mucho gusta" -> "nice to meet you"),
              ("gracias" -> "thank you"),
              ("de nada" -> "you're welcome"),
              ("bien" -> "good"))
              
    val db1 = Map((1 -> "hola"),
              (2 -> "adios"),
              (3 -> "mucho gusta"),
              (4 -> "gracias"),
              (5 -> "de nada"),
              (6 -> "bien"))              
              
     val db2 = Map((1 -> "hello"),
              (2 -> "goodbye"),
              (3 -> "nice to meet you"),
              (4 -> "thank you"),
              (5 -> "you're welcome"),
              (6 -> "good"))
    
    println(db)          
              
    // get value at index -> (map(index))
    println(db("hola"))
    
    // checks to see if index is contained in Map -> (map contains index)
    println(db contains "la casa")
    
    // adds to Map -> (map + (index -> val))
    val newDb = (db + ("buenos dias" -> "good morning"))
    println(newDb)
    
    val rand = new Random(System.currentTimeMillis());
    val random_index = rand.nextInt(db2.size);
    val result = db2(random_index);
    
    println(result)
    println(Map() ++ db.map(_.swap))
    println(db)
				
	}
}