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
   
	
	public static void printMenu(){
		String optionPreCheck = "";
		int userOption= -1;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\nWhat do you want to do today?");
		//prints the menu of options
		
		
		//checks if the input is a valid integer
		while(sc.hasNextInt() == false){
			sc.next();
			System.out.println("Please enter a valid number:");
		}
		
		userOption = sc.nextInt();
		
		//debug output check
		System.out.println("userOption is " + userOption);
		
	}//end printMenu
	
	
	
	
}//end class