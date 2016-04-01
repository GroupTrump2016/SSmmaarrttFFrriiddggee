import java.util.Scanner;

//requires
//1. add item
//2. remove item
//3. display fridge
//4. enter recipe
   //4.1 make recipe
//5. exit program
//6. enter file

//will add items in positions where it is null
//then the fridge will need to eliminate 0 quantity elements to reduce crap in there
//so when addItem is called you need to copy the old array to a new one +1 size
   //when the new array is created then the null elements can get removed
//when a file is imported then the array needs to be increased by the amount of items in the file

//max fridge size is 40 items

public class SmartFridge{
	public static void main(String[] args){
      //sets a default value
      int userOption = -1;
      //sets the value for the user to exit the program with
      final int PROGRAM_END = 5;
      //default fridge size is 5 items
      String[] items = new String[5];
      String[] units = new String[5];
      double[] quantity = new double[5];
      
      System.out.println("Welcome to the SmartFridge.");
      
      //repeats until the user chooses to exit (when they enter 5)
      while(userOption != PROGRAM_END){
         //prints the menu of options
         System.out.println("\n=========================================");
         System.out.println("What do you want to do today?");
         
         //prints main menu
         printMenu();
         
         //gets valid input
         //sends in 5 for 5 options
         userOption = getMenuInput(5);
         
         //determines where to go depending on what the user entered
         switch(userOption){
            case 1:
               addItem(items, quantity, units);
               break;
            case 2:
               removeItem(items, quantity, units);
               break;
            case 3:
               printFridge(items, quantity, units);
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
      
      while(!validInput){
         //checks if the scanner holds an int
         while(!sc.hasNextInt()){
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
	
   
   //gets a double from the user + checks that it is a double and not a string or something
   public static double getValidQuantity(){
      Scanner sc = new Scanner(System.in);
      double quantity = -1;
      
      while(!sc.hasNextDouble()){
         sc.next();
         System.out.println("Please enter a valid quantity:");
      }
      
      quantity = sc.nextDouble();
      
      return quantity;
   }//end getValidQuantity
   
   
   //finds the first position where the quanitity is 0 so that a new item can be put in there
   //WIP
   public static int getNullPosition(double[] quantity){
      //checks entire fridge for elements with no quantity
      for(int i = 0; i < quantity.length; i++){
         //checks if the item has no quantity
         if(quantity[i] == 0){
            return i;
         }
      }//end for loop
      
      //means fridge is full
      return -1;
   }//end getNullPosition
   
   //WIP
   public static void addItem(String[] items, double[] quantity, String[] units){
      //these variables is for the new item being put in
      String newItem, userUnit;
      double newQuantity, convertedQuantity = 0;
      int itemPos;
      boolean validUnit;
      Scanner sc = new Scanner(System.in);
      
      System.out.println("What would you like to put in?");
      newItem = sc.nextLine();
      
      System.out.println("What unit are you storing this in?");
      userUnit = getValidUnit();
      
      System.out.println("And how much of it will we be storing?");
      newQuantity = getValidQuantity();
      
      //check if the current item is in the fridge already
      //if not add it to an empty position
      itemPos = isItemPresent(items, newItem);
      
      if(itemPos == -1){
         items[getNullPosition(quantity)] = newItem;
         units[getNullPosition(quantity)] = userUnit;
         quantity[getNullPosition(quantity)] = newQuantity;
         
         System.out.println("\nYou just stored " + newQuantity + " " + userUnit + " of " + newItem);
      }
      
      else{
         while(!doUnitsMatch(userUnit, units, itemPos)){
            System.out.println("There was a problem with the unit you selected.");
            System.out.println("Please make sure that you're storing the item in the same category of units as the fridge (eg. mg <-> g or mL <-> L)");
            userUnit = getValidUnit();
         }
         
         //convert units here
         convertedQuantity = convertUnits(newQuantity, userUnit, itemPos, units);
         
         //new, converted quantity is set to the empty position
         quantity[itemPos] += convertedQuantity;
         
         System.out.println("\nYou just stored " + newQuantity + " " + userUnit + " of " + newItem);
      }
      
   }//end addItem
   
   
   //WIP
   public static String getValidUnit(){
      //checks if the user entered mL, L, g, mg, lb, no units, ignoring uppercase
      boolean valid = false;
      String userUnit = "";
      Scanner sc = new Scanner(System.in);
      
      System.out.println("Valid units = mL, L, g, mg, lb, or leave blank for quantity (eg. 3 Potatoes):");
      userUnit = sc.nextLine();
      
      //need a check to see if the user is entering valid units
      
      return userUnit;
      
   }//end getValidUnits
   
   
   //checks if the user is entering metric <-> metric or imperial <-> imperial
   //NOT metric <-> imperial
   public static boolean doUnitsMatch(String userUnit, String[] units, int itemPos){
      String fridgeUnit = units[itemPos];
      
      //checks mL <-> L
      if(userUnit.equalsIgnoreCase("mL") && fridgeUnit.equalsIgnoreCase("L")){
         return true;
      }
      else if(userUnit.equalsIgnoreCase("L") && fridgeUnit.equalsIgnoreCase("mL")){
         return true;
      }
      
      //checks mg <-> g
      if(userUnit.equalsIgnoreCase("mg") && fridgeUnit.equalsIgnoreCase("g")){
         return true;
      }
      else if(userUnit.equalsIgnoreCase("g") && fridgeUnit.equalsIgnoreCase("mg")){
         return true;
      }
      
      //checks lb
      if(userUnit.equalsIgnoreCase("lb") && fridgeUnit.equalsIgnoreCase("lb")){
         return true;
      }
      
      //checks quantity
      if(userUnit.equalsIgnoreCase("") && fridgeUnit.equalsIgnoreCase("")){
         return true;
      }
      
      return false;
   }
   
      
   //WIP
   public static double convertUnits(double quantity, String userUnit, int itemPos, String[] units){
      double converted = -1;
      String fridgeUnit;
      
      //find the unit being used at emptyPos
      fridgeUnit = units[itemPos];
      
      //checks if the userUnit is the same as fridgeUnit
      if(userUnit.equalsIgnoreCase(fridgeUnit)){
         converted = quantity;
      }
      
      //converts mL <-> L
      if(userUnit.equalsIgnoreCase("mL") && fridgeUnit.equalsIgnoreCase("L")){
         converted = quantity / 1000;
      }
      else if(userUnit.equalsIgnoreCase("L") && fridgeUnit.equalsIgnoreCase("mL")){
         converted = quantity * 1000;
      }
      
      //converts mg <-> g 
      if(userUnit.equalsIgnoreCase("mg") && fridgeUnit.equalsIgnoreCase("g")){
         converted = quantity / 1000;
      }
      else if(userUnit.equalsIgnoreCase("g") && fridgeUnit.equalsIgnoreCase("mg")){
         converted = quantity * 1000;
      }
      
      //returns the lb
      if(userUnit.equalsIgnoreCase("lb") && fridgeUnit.equalsIgnoreCase("lb")){
         converted = quantity;
      }
      
      //returns the quantity
      if(userUnit.equalsIgnoreCase("") && fridgeUnit.equalsIgnoreCase("")){
         converted = quantity;
      }
      
      return converted;
   }//end convertUnits
   
   
   //WIP
   public static void removeItem(String[] items, double[] quantity, String[] units){
      System.out.println("What would you like to remove?");
   }
   
   
   //checks if the item is already in the fridge
   //if it does, it returns the position
   //if it doesn't, it returns -1
   public static int isItemPresent(String[] items, String newItem){
      for(int i = 0; i < items.length; i++){
         if(newItem.equalsIgnoreCase(items[i])){
            return i;
         }
      }
      
      return -1;
   }//end isItemPresent
   
   
   //WIP - as in make it prettier
   public static void printFridge(String[] items, double[] quantity, String[] units){
      System.out.println("Fridge contents:\n");
      for(int i = 0; i < items.length; i++){
         //checks if the item is null or actually has something
         if(quantity[i] != 0){
            //make this prettier later
            System.out.println(items[i] + " " + quantity[i] + " " + units[i]);
         }
      }//end for loop
   }//end printFridge
   
}//end class