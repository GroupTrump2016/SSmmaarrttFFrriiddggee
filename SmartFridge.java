import java.util.Scanner;

public class SmartFridge{
	public static void main(String[] args){
		System.out.println("Welcome to the SmartFridge.");
		
		printMenu();
		
		
	}//end main
	
	
   
   //requires
   //1. add item
   //2. remove item
   //3. display fridge
   //4. enter recipe
      //4.1 make recipe
   //5. exit program
   
	
	public static void printMenu(){
		String optionPreCheck = "";
		int userOption= -1;
		Scanner sc = new Scanner(System.in);
		
      //prints the menu of options
		System.out.println("\nWhat do you want to do today?");
		
      //gets valid input
      //sends in 5 for 5 options
      getInput(5);
		
	}//end printMenu
	
	public static int getInput(int options){
      Scanner sc = new Scanner(System.in);
      int input = -1;
      boolean validInput = false;
      
      System.out.println("Select a menu from 1 to " + options + ":");
      
      while(validInput == false){
         //checks if the scanner holds an int
         while(sc.hasNextInt() == false){
            sc.next();
            System.out.println("Please enter a valid menu (1-" + option + ")");
         }
         
         input = sc.nextInt();
         
         //checks if the value inputted meets range
         if(input >= 0 && input <=6){
            validInput = true;
            return input;
         }
         
         System.out.println("Please enter a valid column number (0-6).");
      }//end validInput while
      
      return -1;
   }
	
	
}//end class