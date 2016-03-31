import java.util.Scanner;

//default units:
//litres
//kilograms
//pounds
//units (4 tomatoes)

//requires
//1. add item
//2. remove item
//3. display fridge
//4. enter recipe
   //4.1 make recipe
//5. exit program
//6. enter file

//will add items in positions where it is null
//then the fridge will need to eliminate null elements to reduce crap in there
//so when addItem is called you need to copy the old array to a new one +1 size
   //when the new array is created then the null elements can get removed
//when a file is imported then the array needs to be increased by the amount of items in the file

public class SmartFridge{
	public static void main(String[] args){
      //sets a default value
      int userOption = -1;
      final int PROGRAM_END = 5;
      //default fridge size is 5 items
      String[] items = new String[5];
      double[] quantity = new double[5];
      
      System.out.println("Welcome to the SmartFridge.");
      
      //repeats until the user chooses to exit (when they enter 5)
      while(userOption != PROGRAM_END){
         //prints the menu of options
         System.out.println("\nWhat do you want to do today?");
         
         //prints main menu
         printMenu();
         
         //gets valid input
         //sends in 5 for 5 options
         userOption = getMenuInput(5);
         
         //determines where to go depending on what the user entered
         switch(userOption){
            case 1:
               addItem(items, quantity);
               break;
            case 2:
               removeItem(items, quantity);
               break;
            case 3:
               printFridge(items, quantity);
               break;
            case 4:
               //enter recipe option
               break;
         }//end switchcase
         
      }//end while check
      
      //these only get run when the user enters 5
      System.out.println("Thanks for using the SmartFridge!");
      System.exit(0);
      
	}//end main
   
	
	public static void printMenu(){
      //clean this later
      System.out.println("\n1. Add item to fridge");
      System.out.println("2. Remove item from fridge");
      System.out.println("3. Display all contents of the fridge");
      System.out.println("4. Enter a recipe");
      System.out.println("5. Exit the fridge");
	}//end printMenu
	
   
   //options is the number of options the menu should have
   //can be reused for submenus (if needed)
	public static int getMenuInput(int options){
      Scanner sc = new Scanner(System.in);
      int input = -1;
      boolean validInput = false;
      
      System.out.println("Select a menu item from 1 to " + options + ":");
      
      while(validInput == false){
         //checks if the scanner holds an int
         while(sc.hasNextInt() == false){
            sc.next();
            System.out.println("Please enter a valid menu item (1-" + options + ")");
         }
         
         input = sc.nextInt();
         
         //checks if the value inputted meets range
         if(input > 0 && input <= options){
            validInput = true;
            return input;
         }
         
         System.out.println("Please enter a valid menu item (1-" + options + ")");
      }//end validInput while
      
      //will only get here by some magic error
      return -1;
   }//end getInput
	
   
   
   //WIP
   public static double getValidQuantity(){
      double quantity = -1;
      //checks if the user entered a double and not a string or something
      
      return quantity;
   }//end getValidQuantity
   
   //finds the first position where the element is null so that a new item can be put in there
   //WIP
   public static int getNullPosition(){
      int position = -1;
      
      return position;
   }//end getNullPosition
   
   //WIP
   public static void addItem(String[] items, double[] quantity){
      //these variables is for the new item being put in
      String newItem, userUnit;
      double newQuantity;
      Scanner sc = new Scanner(System.in);
      
      System.out.println("What would you like to put in?");
      newItem = sc.nextLine();
      
      System.out.println("What unit are you storing this in?");
      userUnit = getValidUnit();
      
      System.out.println("And how much of it will we be storing?");
      newQuantity = getValidQuantity();
      
      
      System.out.println("You just stored " + newQuantity + userUnit + " of " + newItem);
      
   }//end addItem
   
   //WIP
   public static String getValidUnit(){
      //checks if the user entered mL, L, g, mg, lb, no units, ignoring uppercase
      boolean valid = false;
      String userUnit = "";
      Scanner sc = new Scanner(System.in);
      
      System.out.println("Valid units = mL, L, g, mg, lb, quantity:");
      userUnit = sc.nextLine();
      
      //need a check to see if the user is entering valid units
      
      return userUnit;
      
   }//end getValidUnits
   
   //WIP
   public static double convertUnits(double quantity, String userUnit){
      double converted = -1;
      
      //converts units here to our set units
      
      //default units:
      //litres
      //kilograms
      //pounds
      //units (4 tomatoes)
      
      if(userUnit == "mL"){
         converted = quantity / 1000;
      }
      
      return converted;
   }
   
   //WIP
   public static void removeItem(String[] items, double[] quantity){
      System.out.println("What would you like to remove?");
   }
   
   //WIP - as in make it prettier
   public static void printFridge(String[] items, double[] quantity){
      for(int i = 0; i < items.length; i++){
         //checks if the item is null or actually has something
         if(items[i] != null){
            //make this prettier later
            System.out.println(items[i] + " | " + quantity[i]);
         }
      }//end for loop
   }//end printFridge
   
}//end class