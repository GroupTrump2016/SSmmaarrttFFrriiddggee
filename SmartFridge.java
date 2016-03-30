import java.util.Scanner;

//default units:
//litres
//kilograms
//pounds
//units (4 tomatoes)

public class SmartFridge{
	public static void main(String[] args){
      //sets a default value
      int userOption = -1;
      
      while(userOption != 5){
         System.out.println("Welcome to the SmartFridge.");
         
         //prints the menu of options
         System.out.println("\nWhat do you want to do today?");
         
         //prints main menu
         printMenu();
         
         //gets valid input
         //sends in 5 for 5 options
         userOption = getInput(5);
         
         //determines where to go depending on what the user entered
         switch(userOption){
            case 1:
               addItem();
               break;
            case 2:
               removeItem();
               break;
            case 3:
               break;
            case 4:
               break;
         }//end switchcase
         
      }//end while check
      
      //these only get run when the user enters 5
      System.out.println("Bye!");
      System.exit(0);
      
	}//end main
   
   //requires
   //1. add item
   //2. remove item
   //3. display fridge
   //4. enter recipe
      //4.1 make recipe
   //5. exit program
   
	
	public static void printMenu(){		
      //clean this later
      System.out.println("\n1. Add item to fridge");
      System.out.println("2. Remove item from fridge");
      System.out.println("3. Display all contents of the fridge");
      System.out.println("4. Enter a recipe");
      System.out.println("5. Exit the fridge");
	}//end printMenu
	
   
   //options is the number of options the menu should have
	public static int getInput(int options){
      Scanner sc = new Scanner(System.in);
      int input = -1;
      boolean validInput = false;
      
      System.out.println("Select a menu from 1 to " + options + ":");
      
      while(validInput == false){
         //checks if the scanner holds an int
         while(sc.hasNextInt() == false){
            sc.next();
            System.out.println("Please enter a valid menu (1-" + options + ")");
         }
         
         input = sc.nextInt();
         
         //checks if the value inputted meets range
         if(input > 0 && input <= options){
            validInput = true;
            return input;
         }
         
         System.out.println("Please enter a valid menu (1-" + options + ")");
      }//end validInput while
      
      return -1;
   }//end getInput
	
   
   public static void addItem(){
      String newItem;
      double quantity;
      
      System.out.println("What would you like to put in?");
      
      return;
   }
   
   
   public static void removeItem(){
      System.out.println("What would you like to remove?");
      return;
   }
   
}//end class