/* Program name: SmartFridge
 * Name and ID: Marty Yung 100256573, Youta Wu 100259493
 * Date: April 8, 2016
 * Lab: Project
 * Course: CPSC 1150 - Sec. 5
 */
 
 /*	This program creates a fridge that can store items, the quantities, and the units of the quantities. The SmartFridge
      is capable of adding/removing items, creating and cooking recipes, and saving the contents for consistent use. 
      
      Arguments passed in must be in the /contents/ folder.
 */

import java.util.*;
import java.io.*;

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
      
      //checks to see if an argument was passed in
      if(args.length > 0){
         String fileName = args[0];
         System.out.println("Argument detected!");
         File importFile = new File("contents//" + fileName + ".txt");
         
         //checks if the argument exists in the /contents/ directory
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
            
            if(items.length >= MAX_SIZE){
               System.out.println("\nYour fridge is full!\nYou can't add anymore items until you take some out.");
            }
            
            Sort.selectionSort(items, quantity, units);
            
            fileSc.close();
         }
      }
      
      System.out.println("\nWelcome to the SmartFridge.");
      
      //repeats until the user chooses to exit (when they enter PROGRAM_END)
      while(userOption != PROGRAM_END){
         System.out.println("\n=========================================");
         System.out.println("What do you want to do today?");
         
         //prints main menu
         printMenu();
         
         //gets valid menu input
         //sends in 7 for 7 options
         userOption = InputAndTools.getMenuInput(7);
         
         //determines where to go depending on what the user entered
         switch(userOption){
            case 1://adds item
               if(items.length > MAX_SIZE){
                  System.out.println("\nYour fridge is full!\nYou can't add anymore items until you take some out.");
                  break;
               }
               else{
                  //expands the arrays to make room for another item
                  if(InputAndTools.getNullPosition(quantity) == -1){
                     items = ExpandArray.expand(items);
                     quantity = ExpandArray.expand(quantity);
                     units = ExpandArray.expand(units);
                  }
                  
                  //actually adds the item here
                  addItem(items, quantity, units);
                  
                  //sorts the arrays 
                  Sort.selectionSort(items, quantity, units);
                  break;
               }
            case 2://removes item
               //first checks if the fridge is empty
               if(InputAndTools.checkEmpty(quantity)){
                  System.out.println("\nYour fridge is empty!\nYou need stuff in there before you can take them out.");
                  break;
               }
               else{
                  //removes item
                  removeItem(items, quantity, units);
                  
                  //shinks the array
                  items = ShrinkArray.shrink(quantity, items);
                  units = ShrinkArray.shrink(quantity, units);
                  quantity = ShrinkArray.shrink(quantity);
                  
                  //sorts the array
                  Sort.selectionSort(items, quantity, units);
                  break;
               }
            case 3://prints fridge
               //if the fridge is empty then it will notify user
               if(InputAndTools.checkEmpty(quantity)){
                  System.out.println("\nYour fridge is empty!");
                  break;
               }
               //if it's not empty then the contents will be printed
               else{
                  printFridge(items, quantity, units);
                  break;
               }
            case 4://enter recipe option
               Recipe.enterRecipeMenu(items, quantity, units);
            
               //shinks the array because the user might have removed items from the fridge
               items = ShrinkArray.shrink(quantity, items);
               units = ShrinkArray.shrink(quantity, units);
               quantity = ShrinkArray.shrink(quantity);
               
               //sorts the array
               Sort.selectionSort(items, quantity, units);
               break;
            case 5://remove all items
               if(InputAndTools.checkEmpty(quantity)){
                  System.out.println("\nYour fridge is empty!\nYou need stuff in there before you can take them out.");
                  break;
               }
               
               //prompts before doing it
               System.out.println("Are you sure you want to remove ALL the items in the fridge?");
               
               //calls method to remove items (or not if they pick no)
               if(InputAndTools.yesOrNo()){
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
               
               File importFile = new File("contents//" + fileName + ".txt");
               
               //checks if the file exists in the contents directory
               if(!importFile.exists()){
                  System.out.println("\nThat file does not exist.");
                  break;
               }
               
               Scanner fileSc = new Scanner(importFile);
               
               //keeps scanning the next line until it reaches the end
               try{
                  while(items.length < MAX_SIZE){
                     //expands array to make room for a new item
                     items = ExpandArray.expand(items);
                     quantity = ExpandArray.expand(quantity);
                     units = ExpandArray.expand(units);
                     
                     importFile(items, quantity, units, MAX_SIZE, fileSc);
                     
                     //cleans up arrays after a new item is inserted
                     items = ShrinkArray.shrink(quantity, items);
                     units = ShrinkArray.shrink(quantity, units);
                     quantity = ShrinkArray.shrink(quantity);
                  }//end while
                  
                  if(items.length >= MAX_SIZE){
                     System.out.println("\nYour fridge is full!\nYou can't add anymore items until you take some out.");
                  }
               }
               catch(NoSuchElementException e){
                  System.out.println("\nEnd of file.");
               }
               
               Sort.selectionSort(items, quantity, units);
               
               fileSc.close();
               
               break;
         }//end switchcase
         
      }//end while check
      
      //these only get run when the user enters the PROGRAM_END value
      //must ask if user wants to save fridge contents into a file
      System.out.println("Would you like to save the current contents of the fridge?");
      
      if(InputAndTools.yesOrNo()){
         exportFridge(items, quantity, units);
      }
      
      System.out.println("\nThanks for using the SmartFridge!");
      System.exit(0);
      
	}//end main
   
	
	public static void printMenu(){
      System.out.println("\n1. Add item to fridge");
      System.out.println("2. Remove item from fridge");
      System.out.println("3. Display all contents of the fridge");
      System.out.println("4. Recipe Submenu");
      System.out.println("5. Remove ALL items");
      System.out.println("6. Enter file");
      System.out.println("7. Exit the fridge");
	}//end printMenu
   
   
   public static void exportFridge(String[] items, double[] quantity, String[] units) throws IOException{
      Scanner sc = new Scanner(System.in);
      
      System.out.println("Please type a filename (no extension) for the exported list:");
      String fileName = sc.next();
      sc.close();
      
      //creates a contents folder if one doesn't exist yet
      File dir = new File("contents");
      if(!dir.exists()){
         dir.mkdir();
      }
      
      //if the file exists with that name, the program will not create a new file
      File exportFile = new File("contents//" + fileName + ".txt");
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
   
   
   public static void addItem(String[] items, double[] quantity, String[] units){
      //these variables are for the new item being put in
      double convertedQuantity = 0;
      Scanner sc = new Scanner(System.in);
                  
      System.out.println("What would you like to put in?");
      String newItem = sc.nextLine();
                  
      System.out.println("What unit are you storing this in?");
      String userUnit = InputAndTools.getValidUnit();
                  
      //check if the current item is in the fridge already
      //if not add it to an empty position
      int itemPos = InputAndTools.getItemPos(items, newItem);
                  
      //checks the unit matches the category of units in the fridge
      if(itemPos != -1){
         while(!InputAndTools.doUnitsMatch(userUnit, units, itemPos)){
            System.out.println("There was a problem with the unit you selected. The item in the fridge is using " + units[itemPos] + ".");
            System.out.println("\nPlease make sure that you're storing the item in the same category of units as the fridge (eg. mg <-> g or mL <-> L)");
            userUnit = InputAndTools.getValidUnit();
         }
      }
      
      System.out.println("And how much of it will we be storing?");
      double newQuantity = InputAndTools.getValidQuantity();
      
      if(itemPos == -1){
         items[InputAndTools.getNullPosition(quantity)] = newItem;
         units[InputAndTools.getNullPosition(quantity)] = userUnit;
         quantity[InputAndTools.getNullPosition(quantity)] = newQuantity;
         
         if(userUnit.equalsIgnoreCase("")){
            System.out.println("\nYou just stored " + newQuantity + " " + newItem);
         }
         else{
            System.out.println("\nYou just stored " + newQuantity + " " + userUnit + " of " + newItem);
         }
      }
      
      else{
         //convert units here
         convertedQuantity = InputAndTools.convertUnits(newQuantity, userUnit, itemPos, units);
         
         //new, converted quantity is set to the empty position
         quantity[itemPos] += convertedQuantity;
         
         //prints a gramatically correct message according to the unit
         if(userUnit.equalsIgnoreCase("")){
            System.out.println("\nYou just stored " + newQuantity + " " + newItem);
         }
         else{
            System.out.println("\nYou just stored " + newQuantity + " " + userUnit + " of " + newItem);
         }
      }
      
   }//end addItem
   
   
   public static void removeItem(String[] items, double[] quantity, String[] units){
      //these variables is for the new item being put in
      String removeItem, userUnit;
      double removeQuantity, convertedQuantity = 0;
      int itemPos;
      Scanner sc = new Scanner(System.in);
      
      System.out.println("What would you like to remove?");
      removeItem = sc.nextLine();
      
      while(InputAndTools.getItemPos(items, removeItem) == -1){
         System.out.println("That item cannot be found in the fridge.\nPlease enter a new item:");
         removeItem = sc.nextLine();
      }
      
      //finds location of the item
      itemPos = InputAndTools.getItemPos(items, removeItem);
      
      System.out.println("What unit are you removing the item from?");
      userUnit = InputAndTools.getValidUnit();
      
      //checks to see if the user is taking the item out in a valid unit
      while(!InputAndTools.doUnitsMatch(userUnit, units, itemPos)){
         System.out.println("There was a problem with the unit you selected. The item in the fridge is using " + units[itemPos] + ".");
         System.out.println("\nPlease make sure that you're removing the item in the same category of units as the fridge (eg. mg <-> g or mL <-> L)");
         userUnit = InputAndTools.getValidUnit();
      }
      
      System.out.println("And how much of it will we be removing?");
      removeQuantity = InputAndTools.getValidQuantity();
      
      while(quantity[itemPos] < InputAndTools.convertUnits(removeQuantity, userUnit, itemPos, units)){
         System.out.println("You're attempting to remove more than what you have.\nPlease enter a new amount to remove:");
         removeQuantity = InputAndTools.getValidQuantity();
      }
      
      //convert units here
      convertedQuantity = InputAndTools.convertUnits(removeQuantity, userUnit, itemPos, units);
         
      //new, converted quantity is set to the empty position
      quantity[itemPos] -= convertedQuantity;
      
      if(userUnit.equalsIgnoreCase("")){
         System.out.println("\nYou just removed " + removeQuantity + " " + removeItem);
      }
      else{
         System.out.println("\nYou just removed " + removeQuantity + " " + userUnit + " of " + removeItem);
      }
      
   }//end removeItem
   
   
   public static void importFile(String[] items, double[] quantity, String[] units, int MAX_SIZE, Scanner fileSc) throws IOException{
      String newItem = "", newUnit;
      double newQuantity, convertedQuantity;
      String currentLine = "";
      
      //sets the default unit to blank
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
      int itemPos = InputAndTools.getItemPos(items, newItem);
      
      //checks the unit matches the category of units in the fridge
      if(itemPos != -1){
         if(!InputAndTools.doUnitsMatch(newUnit, units, itemPos)){
            System.out.println("\nThe item, " + newItem + ", is currently being stored in a different unit.");
            System.out.println("Please change it before attempting again.");
         }
      }
         
      //actually adds the item to the arrays here
      //does this if it cannot find the item in the fridge/array
      if(itemPos == -1){
         items[InputAndTools.getNullPosition(quantity)] = newItem;
         units[InputAndTools.getNullPosition(quantity)] = newUnit;
         quantity[InputAndTools.getNullPosition(quantity)] = newQuantity;
         
         if(newUnit.equalsIgnoreCase("")){
            System.out.println("\nYou just stored " + newQuantity + " " + newItem);
         }
         else{
            System.out.println("\nYou just stored " + newQuantity + " " + newUnit + " of " + newItem);
         }
      }
         
      else{
         //convert units here
         convertedQuantity = InputAndTools.convertUnits(newQuantity, newUnit, itemPos, units);
         
         //new, converted quantity is set to the empty position
         quantity[itemPos] += convertedQuantity;
         
         //prints a gramatically correct message according to the unit
         if(newUnit.equalsIgnoreCase("")){
            System.out.println("\nYou just stored " + newQuantity + " " + newItem);
         }
         else{
            System.out.println("\nYou just stored " + newQuantity + " " + newUnit + " of " + newItem);
         }
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