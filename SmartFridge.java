import java.util.Scanner;
import java.util.Arrays;

//requires
//1. add item
//2. remove item
//3. display fridge
//4. enter recipe
   //4.1 make recipe
//5. remove all items
//6. enter file
//7. ezit program

//the fridge will need to eliminate 0 quantity elements to reduce crap in there
//when a file is imported then the array needs to be increased by the amount of items in the file

//max fridge size is 40 items

public class SmartFridge{
	public static void main(String[] args){
      //sets a default value
      int userOption = -1;
      //sets the value for the user to exit the program with; sets the max fridge size; sets the default fridge size
      final int PROGRAM_END = 7, MAX_SIZE = 40, DEFAULT_SIZE = 5;
      //default fridge size is 5 items
      String[] items = new String[DEFAULT_SIZE];
      String[] units = new String[DEFAULT_SIZE];
      double[] quantity = new double[DEFAULT_SIZE];
      
      Arrays.fill(items, "");
      Arrays.fill(units, "");
      
      System.out.println("Welcome to the SmartFridge.");
      
      //repeats until the user chooses to exit (when they enter 5)
      while(userOption != PROGRAM_END){
         //prints the menu of options
         System.out.println("\n=========================================");
         System.out.println("What do you want to do today?");
         
         //prints main menu
         printMenu();
         
         //gets valid input
         //sends in 7 for 7 options
         userOption = getMenuInput(7);
         
         //determines where to go depending on what the user entered
         switch(userOption){
            case 1:
               if(items.length > MAX_SIZE){
                  System.out.println("\nYour fridge is full!\nYou can't add anymore items until you take some out.");
                  break;
               }
               else{
                  //expands the arrays to make room for another item
                  if(getNullPosition(quantity) == -1){
                     items = ExpandArray.expand(items);
                     quantity = ExpandArray.expand(quantity);
                     units = ExpandArray.expand(units);
                  }
                  
                  addItem(items, quantity, units);
                  
                  Sort.selectionSort(items, quantity, units);
                  break;
               }
            case 2:
               if(checkEmpty(quantity)){
                  System.out.println("\nYour fridge is empty!\nYou need stuff in there before you can take them out.");
                  break;
               }
               else{
                  removeItem(items, quantity, units);
                  
                  items = ShrinkArray.shrink(quantity, items);
                  units = ShrinkArray.shrink(quantity, units);
                  quantity = ShrinkArray.shrink(quantity);
                  
                  Sort.selectionSort(items, quantity, units);
                  break;
               }
            case 3:
               if(checkEmpty(quantity)){
                  System.out.println("\nYour fridge is empty!");
                  break;
               }
               else{
                  printFridge(items, quantity, units);
                  break;
               }
            case 4:
               //enter recipe option
               break;
            case 5:
               //remove all items
               break;
            case 6:
               //enter file
               break;
         }//end switchcase
         
      }//end while check
      
      //these only get run when the user enters the PROGRAM_END value
      System.out.println("Thanks for using the SmartFridge!");
      System.exit(0);
      
	}//end main
   
	
	public static void printMenu(){
      //clean this later
      System.out.println("\n1. Add item to fridge");
      System.out.println("2. Remove item from fridge");
      System.out.println("3. Display all contents of the fridge");
      System.out.println("4. Enter a recipe");
      System.out.println("5. Remove ALL items");
      System.out.println("6. Enter file");
      System.out.println("7. Exit the fridge");
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
   public static int getNullPosition(double[] quantity){
      //checks entire fridge for elements with no quantity
      for(int i = 0; i < quantity.length; i++){
         //checks if the item has no quantity
         if(quantity[i] == 0){
            return i;
         }
      }
      
      return -1;
   }//end getNullPosition
   
   
   public static void addItem(String[] items, double[] quantity, String[] units){
      //these variables are for the new item being put in
      String newItem, userUnit;
      double newQuantity, convertedQuantity = 0;
      int itemPos;
      Scanner sc = new Scanner(System.in);
      
      System.out.println("What would you like to put in?");
      newItem = sc.nextLine();
      
      System.out.println("What unit are you storing this in?");
      userUnit = getValidUnit();
      
      //check if the current item is in the fridge already
      //if not add it to an empty position
      itemPos = getItemPos(items, newItem);
      
      //checks the unit matches the category of units in the fridge
      if(itemPos != -1){
         while(!doUnitsMatch(userUnit, units, itemPos)){
            System.out.println("There was a problem with the unit you selected. The item in the fridge is using " + units[itemPos] + ".");
            System.out.println("\nPlease make sure that you're storing the item in the same category of units as the fridge (eg. mg <-> g or mL <-> L)");
            userUnit = getValidUnit();
         }
      }
      
      System.out.println("And how much of it will we be storing?");
      newQuantity = getValidQuantity();
      
      if(itemPos == -1){
         items[getNullPosition(quantity)] = newItem;
         units[getNullPosition(quantity)] = userUnit;
         quantity[getNullPosition(quantity)] = newQuantity;
         
         if(userUnit.equalsIgnoreCase("")){
            System.out.println("\nYou just stored " + newQuantity + " " + newItem);
         }
         else{
            System.out.println("\nYou just stored " + newQuantity + " " + userUnit + " of " + newItem);
         }
      }
      
      else{
         //convert units here
         convertedQuantity = convertUnits(newQuantity, userUnit, itemPos, units);
         
         //new, converted quantity is set to the empty position
         quantity[itemPos] += convertedQuantity;
         
         if(userUnit.equalsIgnoreCase("")){
            System.out.println("\nYou just stored " + newQuantity + " " + newItem);
         }
         else{
            System.out.println("\nYou just stored " + newQuantity + " " + userUnit + " of " + newItem);
         }
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
   
   
   //checks if the user is entering mg <-> g, mL <-> L, lb <-> lb, quantity <-> quantity
   //NOT mg <-> L, etc.
   public static boolean doUnitsMatch(String userUnit, String[] units, int itemPos){
      String fridgeUnit = units[itemPos];
      
      //checks if the userUnit is the same as fridgeUnit
      if(userUnit.equalsIgnoreCase(fridgeUnit)){
         return true;
      }
      
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
   
   
   public static void removeItem(String[] items, double[] quantity, String[] units){
      //these variables is for the new item being put in
      String removeItem, userUnit;
      double removeQuantity, convertedQuantity = 0;
      int itemPos;
      Scanner sc = new Scanner(System.in);
      
      System.out.println("What would you like to remove?");
      removeItem = sc.nextLine();
      
      while(getItemPos(items, removeItem) == -1){
         System.out.println("That item cannot be found in the fridge.\nPlease enter a new item:");
         removeItem = sc.nextLine();
      }
      
      //finds location of the item
      itemPos = getItemPos(items, removeItem);
      
      System.out.println("What unit are you removing the item from?");
      userUnit = getValidUnit();
      
      //checks to see if the user is taking the item out in a valid unit
      while(!doUnitsMatch(userUnit, units, itemPos)){
         System.out.println("There was a problem with the unit you selected. The item in the fridge is using " + units[itemPos] + ".");
         System.out.println("\nPlease make sure that you're removing the item in the same category of units as the fridge (eg. mg <-> g or mL <-> L)");
         userUnit = getValidUnit();
      }
      
      System.out.println("And how much of it will we be removing?");
      removeQuantity = getValidQuantity();
      
      while(quantity[itemPos] < convertUnits(removeQuantity, userUnit, itemPos, units)){
         System.out.println("You're attempting to remove more than what you have.\nPlease enter a new amount to remove:");
         removeQuantity = getValidQuantity();
      }
      
      //convert units here
      convertedQuantity = convertUnits(removeQuantity, userUnit, itemPos, units);
         
      //new, converted quantity is set to the empty position
      quantity[itemPos] -= convertedQuantity;
      
      if(userUnit.equalsIgnoreCase("")){
            System.out.println("\nYou just removed " + removeQuantity + " " + removeItem);
         }
      else{
         System.out.println("\nYou just removed " + removeQuantity + " " + userUnit + " of " + removeItem);
      }
      
   }//end removeItem
   
   
   //checks if the item is already in the fridge
   //if it does, it returns the position
   //if it doesn't, it returns -1
   public static int getItemPos(String[] items, String newItem){
      for(int i = 0; i < items.length; i++){
         if(newItem.equalsIgnoreCase(items[i])){
            return i;
         }
      }
      
      return -1;
   }//end isItemPresent
   
   //checks if anything in quantity is greater than 0
   //returns true if it is empty, returns false if it isn't
   public static boolean checkEmpty(double[] quantity){
      for(int i = 0; i < quantity.length; i++){
         if(quantity[i] > 0){
            return false;
         }
      }
      
      return true;
   }
   
   //WIP - make prettier
   public static void printFridge(String[] items, double[] quantity, String[] units){
      System.out.println("Fridge contents:\n");
      for(int i = 0; i < items.length; i++){
         //will only print if the item has a quantity
         
         if(quantity[i] != 0){
            System.out.println(items[i] + " " + quantity[i] + " " + units[i]);
         }
         
      }//end for loop
   }//end printFridge
   
}//end class