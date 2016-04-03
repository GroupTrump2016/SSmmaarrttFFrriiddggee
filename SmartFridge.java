import java.util.Scanner;
import java.util.Arrays;
import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.io.PrintWriter;

//requires
//1. add item
//2. remove item
//3. display fridge
//4. enter recipe
   //4.1 make recipe
//5. remove all items
//6. enter file
//7. ezit program

//when a file is imported then the array needs to be increased by the amount of items in the file

public class SmartFridge{
	public static void main(String[] args) throws IOException{
      //sets a default value
      int userOption = -1;
      //sets the value for the user to exit the program with; sets the max fridge size; sets the default fridge size
      final int PROGRAM_END = 7, MAX_SIZE = 40, DEFAULT_SIZE = 5;
      //default fridge size is 5 items
      String[] items = new String[DEFAULT_SIZE];
      String[] units = new String[DEFAULT_SIZE];
      double[] quantity = new double[DEFAULT_SIZE];
      
      //replaces null elements with blanks
      Arrays.fill(items, "");
      Arrays.fill(units, "");
      
      //the try-catch is testing if there was an argument passed in
      if(args.length > 0){
         String fileName = args[0];
         System.out.println("Argument detected!");
         File importFile = new File(fileName + ".txt");
         
         //checks if the argument exists in the root directory
         if(!importFile.exists()){
            System.out.println("\nThe file that was attempted to be passed in does not exist.");
         }
         else{
            Scanner fileSc = new Scanner(importFile);
            
            //keeps scanning the next line until it reaches the end
            try{
               while(items.length < MAX_SIZE){
                  items = ExpandArray.expand(items);
                  quantity = ExpandArray.expand(quantity);
                  units = ExpandArray.expand(units);
                  
                  importFile(items, quantity, units, MAX_SIZE, fileSc);
               }//end while
            }
            catch(NoSuchElementException e){
               System.out.println("\nEnd of file");
            }
            
            Sort.selectionSort(items, quantity, units);
            
            fileSc.close();
         }
      }
      
      System.out.println("\nWelcome to the SmartFridge.");
      
      //repeats until the user chooses to exit (when they enter PROGRAM_END)
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
            case 1://adds item
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
            case 2://removes item
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
            case 3://prints fridge
               if(checkEmpty(quantity)){
                  System.out.println("\nYour fridge is empty!");
                  break;
               }
               else{
                  printFridge(items, quantity, units);
                  break;
               }
            case 4://enter recipe option
               
               break;
            case 5://remove all items
               System.out.println("Are you sure you want to remove ALL the items in the fridge?");
               
               //calls method to remove items (or not if they pick no)
               if(yesOrNo()){
                  items = ShrinkArray.clear(items, DEFAULT_SIZE);
                  units = ShrinkArray.clear(units, DEFAULT_SIZE);
                  quantity = ShrinkArray.clear(quantity, DEFAULT_SIZE);
                  
                  System.out.println("\nRemoved!");
               }
               else{
                  System.out.println("\nDid not remove.");
               }
               
               
               break;
            case 6://imports file
               //all this stuff is here instead of a method because the arrays need to expand to accomodate the new items
               //creating a new array in a method will require additional method calls to save the new location of the array so that main can use it again
               //all those new method calls is LESS friendly on resources
               Scanner input = new Scanner(System.in);
               
               System.out.println("Please enter the file name of a .txt file to import from:");
               String fileName = input.next();
               
               File importFile = new File(fileName + ".txt");
               
               //checks if the file exists in the root directory
               if(!importFile.exists()){
                  System.out.println("\nThat file does not exist.");
                  break;
               }
               
               Scanner fileSc = new Scanner(importFile);
               
               //keeps scanning the next line until it reaches the end
               try{
                  while(items.length < MAX_SIZE){
                     items = ExpandArray.expand(items);
                     quantity = ExpandArray.expand(quantity);
                     units = ExpandArray.expand(units);
                     
                     importFile(items, quantity, units, MAX_SIZE, fileSc);
                  }//end while
               }
               catch(NoSuchElementException e){
                  System.out.println("\nEnd of file");
               }
               
               Sort.selectionSort(items, quantity, units);
               
               fileSc.close();
               
               break;
         }//end switchcase
         
      }//end while check
      
      //these only get run when the user enters the PROGRAM_END value
      //must ask if user wants to save fridge contents into a file
      System.out.println("Would you like to save the current contents of the fridge?");
      if(yesOrNo()){
         exportFridge(items, quantity, units);
      }
      
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
	
   
   
   public static void exportFridge(String[] items, double[] quantity, String[] units) throws IOException{
      Scanner sc = new Scanner(System.in);
      System.out.println("Please type a filename (no extension) for the exported list:");
      String fileName = sc.next();
      sc.close();
      
      //if the file exists with that name, the program will not create a new file
      File exportFile = new File(fileName + ".txt");
      if(exportFile.exists()){ 
         System.out.println("The file " + fileName + ".txt already exists and a new file cannot be created.");
         System.exit(1);
      }
      
      //only exports quantities that are greater than 0
      PrintWriter pw = new PrintWriter(exportFile);
      for(int i = 0; i < quantity.length; i++){
         if(quantity[i] != 0){
            pw.println(items[i] + ", " + quantity[i] + " " + units[i]);
         }
      }
      
      pw.close();
   }
   
   public static boolean yesOrNo(){
      Scanner sc = new Scanner(System.in);
      boolean validInput = false;
      char input = 'A';
      String check;
      
      //gets a valid Y or N from the user
      while(!validInput){
         System.out.println("Please enter Y (Yes) or N (No):");
         check = sc.next();
         
         while(check.length() > 1){
            System.out.println("Please enter Y (Yes) or N (No):");
            check = sc.next();
         }
         
         input = check.charAt(0);
         
         //checks if the value inputted is Y or N
         if(input == 'Y' || input == 'y'){
            validInput = true;
            return true;
         }
         
         else if(input == 'N' || input == 'n'){
            validInput = true;
            return false;
         }
      }
      
      return false;
   }
   
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
      double convertedQuantity = 0;
      Scanner sc = new Scanner(System.in);
                  
      System.out.println("What would you like to put in?");
      String newItem = sc.nextLine();
                  
      System.out.println("What unit are you storing this in?");
      String userUnit = getValidUnit();
                  
      //check if the current item is in the fridge already
      //if not add it to an empty position
      int itemPos = getItemPos(items, newItem);
                  
      //checks the unit matches the category of units in the fridge
      if(itemPos != -1){
         while(!doUnitsMatch(userUnit, units, itemPos)){
            System.out.println("There was a problem with the unit you selected. The item in the fridge is using " + units[itemPos] + ".");
            System.out.println("\nPlease make sure that you're storing the item in the same category of units as the fridge (eg. mg <-> g or mL <-> L)");
            userUnit = getValidUnit();
         }
      }
      
      System.out.println("And how much of it will we be storing?");
      double newQuantity = getValidQuantity();
      
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
   
   
   public static String getValidUnit(){
      //checks if the user entered mL, L, g, mg, lb, no units, ignoring uppercase
      boolean valid = false;
      String userUnit = "";
      Scanner sc = new Scanner(System.in);
      
      System.out.println("Valid units = mL, L, g, mg, lb, or leave blank for quantity (eg. 3 Potatoes):");
      userUnit = sc.nextLine();
      
      //need a check to see if the user is entering valid units
      while(!valid){
         if(userUnit.equalsIgnoreCase("mL")){
            valid = true;
         }
         
         else if(userUnit.equalsIgnoreCase("L")){
            valid = true;
         }
         
         else if(userUnit.equalsIgnoreCase("g")){
            valid = true;
         }
         
         else if(userUnit.equalsIgnoreCase("mg")){
            valid = true;
         }
         
         else if(userUnit.equalsIgnoreCase("lb")){
            valid = true;
         }
         
         else if(userUnit.equalsIgnoreCase("")){
            valid = true;
         }
         
         else{
            System.out.println("Please enter valid units.");
            System.out.println("Valid units = mL, L, g, mg, lb, or leave blank for quantity (eg. 3 Potatoes):");
            userUnit = sc.nextLine();
         }
      }
      
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
   
   
   public static void importFile(String[] items, double[] quantity, String[] units, int MAX_SIZE, Scanner fileSc) throws IOException{
      String newItem = "", newUnit;
      double newQuantity, convertedQuantity;
      String currentLine = "";
      
      
      newUnit = "";
      
      currentLine = fileSc.nextLine();
      Scanner line = new Scanner(currentLine);
         
      //retrieves item name, concatenates items with multiple words (eg. orange juice)
      newItem = line.next();
      while(!line.hasNextDouble()){
         newItem += " " + line.next();
      }
         
      //removes the comma from the item string
      newItem = newItem.substring(0, newItem.length() - 1);
         
      newQuantity = line.nextDouble();
         
      //checks to see if there are units or nothing (as in 4 tomatoes)
      try{
         newUnit = line.next();
      }
      catch(NoSuchElementException e){
         //nothing happens, it just catches the exception
      }
      
      //check if the current item is in the fridge already
      //if not add it to an empty position
      int itemPos = getItemPos(items, newItem);
      
      //checks the unit matches the category of units in the fridge
      if(itemPos != -1){
         if(!doUnitsMatch(newUnit, units, itemPos)){
            System.out.println("The item is currently being stored in a different unit.");
            System.out.println("Please change it before attempting again.");
         }
      }
         
      //actually adds the item to the arrays here
      if(itemPos == -1){
         items[getNullPosition(quantity)] = newItem;
         units[getNullPosition(quantity)] = newUnit;
         quantity[getNullPosition(quantity)] = newQuantity;
         
         if(newUnit.equalsIgnoreCase("")){
            System.out.println("\nYou just stored " + newQuantity + " " + newItem);
         }
         else{
            System.out.println("\nYou just stored " + newQuantity + " " + newUnit + " of " + newItem);
         }
      }
         
      else{
         //convert units here
         convertedQuantity = convertUnits(newQuantity, newUnit, itemPos, units);
         
         //new, converted quantity is set to the empty position
         quantity[itemPos] += convertedQuantity;
         
         if(newUnit.equalsIgnoreCase("")){
            System.out.println("\nYou just stored " + newQuantity + " " + newItem);
         }
         else{
            System.out.println("\nYou just stored " + newQuantity + " " + newUnit + " of " + newItem);
         }
      }
      
      if(items.length == MAX_SIZE){
         System.out.println("\nThe fridge is full!\nThe last item that was added was " + newItem);
      }
      
      
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