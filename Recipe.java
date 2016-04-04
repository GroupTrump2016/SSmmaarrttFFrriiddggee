import java.util.Scanner;
import java.util.Arrays;
import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.io.PrintWriter;

public class Recipe{
   
   public static void enterRecipeMenu(String[] items, double[] quantity, String[] units) throws IOException{
      int userOption = -1;
      final int RETURN_TO_MAIN = 6;
      
      while(userOption != RETURN_TO_MAIN){
         //prints the menu of options
         System.out.println("\n=========================================");
         System.out.println("Recipe Submenu");
         
         //prints main menu
         printRecipeMenu();
         
         //gets valid input
         //sends in 6 for 6 options
         userOption = InputAndTools.getMenuInput(6);
         
         //determines where to go depending on what the user entered
         switch(userOption){
            case 1:
               addRecipe();
               break;
            case 2:
               deleteRecipe();
               break;
            case 3:
               printRecipe();
               break;
            case 4:
               getMissingItems(items, quantity, units, 1);
               break;
            case 5:
               getMissingItems(items, quantity,units, 2);
               break;
         }//end switchcase
         
      }//end while check
      
      return;
      
   }
   
   //prints the menu for the options
   public static void printRecipeMenu() throws IOException{
      System.out.println("\n1. Add recipe");
      System.out.println("2. Delete recipe");
      System.out.println("3. Display recipe");
      System.out.println("4. See outstanding items from recipe");
      System.out.println("5. Cook recipe");
      System.out.println("6. Return to main menu");
   }
   
   //add a recipe
   public static void addRecipe() throws IOException{
      Scanner sc = new Scanner(System.in);
      System.out.println("Please type a filename (no extension) for the recipe:");
      String fileName = sc.next();
      boolean doneEntering = false;
      
      String currentItem, currentUnit;
      double currentQuantity;
      
      //creates a recipe folder if one doesn't exist yet
      File dir = new File("recipes");
      if(!dir.exists()){
         dir.mkdir();
      }
      
      //if the file exists with that name, the program will not create a new file
      File exportFile = new File("recipes//" + fileName + ".txt");
      if(exportFile.exists()){ 
         System.out.println("A recipe under the name of " + fileName + ".txt already exists.");
         System.out.println("Do you want to override it?");
         
         if(!InputAndTools.yesOrNo()){
            return;
         }
      }
      
      PrintWriter pw = new PrintWriter(exportFile);
      
      //repeats until the user is done entering the last item
      while(!doneEntering){
         System.out.println("Please enter the name of the item:");
         currentItem = InputAndTools.getItemName();
         
         System.out.println("Please enter the unit of the item:");
         currentUnit = InputAndTools.getValidUnit();
         
         System.out.println("Please enter the quantity of the item:");
         currentQuantity = InputAndTools.getValidQuantity();
            
         pw.println(currentItem + ", " + currentQuantity + " " + currentUnit);
         System.out.println("\nJust stored " + currentItem + " " + currentQuantity + " " + currentUnit);
         
         System.out.println("\nAre you done entering the recipe?");
         doneEntering = InputAndTools.yesOrNo();
      }
      
      pw.close();
      
   }
   
   //deletes recipes
   public static void deleteRecipe(){
      Scanner sc = new Scanner(System.in);
      
      System.out.println("Please type a filename (no extension) for the recipe you want to delete:");
      String fileName = sc.next();
      
      System.out.println("Are you sure you want to delete " + fileName + ".txt?");
      if(!InputAndTools.yesOrNo()){
         return;
      }
      
      File exportFile = new File("recipes//" + fileName + ".txt");
      
      //checks if the recipe exists
      if(!exportFile.exists()){
         System.out.println("That recipe does not exist.");
         return;
      }
      
      //attempts file deletion here
      exportFile.delete();
      
      //checks if successful or not
      if(!exportFile.exists()){
         System.out.println("Recipe successfully deleted.");
         return;
      }
      else{
         System.out.println("There was a problem and the recipe could not be deleted.");
         return;
      }
      
   }
   
   //prints recipe
   public static void printRecipe() throws IOException{
      Scanner sc = new Scanner(System.in);
      
      System.out.println("Please enter the file name of a recipe you want to print:");
      String fileName = sc.next();
      
      File importFile = new File("recipes//" + fileName + ".txt");
      
      //checks if the file exists in the root directory
      if(!importFile.exists()){
         System.out.println("\nThat file does not exist.");
         return;
      }
      
      Scanner fileSc = new Scanner(importFile);
      
      //keeps scanning the next line until it reaches the end
      try{
         while(fileSc.hasNextLine()){
            System.out.println(fileSc.nextLine());
         }
      }
      catch(NoSuchElementException e){
         System.out.println("\nEnd of file.");
      }
      
      fileSc.close();
      
   }
   
   //gets the outstanding items
   public static void getMissingItems(String[] items, double[] quantity, String[] units, int option) throws IOException{
      Scanner sc = new Scanner(System.in);
      
      if(option == 1){
         System.out.println("Please enter the file name of a recipe you want to check from:");
      }
      else{
         System.out.println("Please enter the file name of a recipe you want to make:");
      }
      String fileName = sc.next();
      
      File importFile = new File("recipes//" + fileName + ".txt");
      
      //checks if the file exists in the root directory
      if(!importFile.exists()){
         System.out.println("\nThat file does not exist.");
         return;
      }
      
      Scanner fileSc = new Scanner(importFile);
      
      File exportFile = new File("recipes//" + fileName + "_missingitems.txt");
      PrintWriter pw = new PrintWriter(exportFile);
      
      //keeps scanning the next line until it reaches the end
      try{
         importFile(items, quantity, units, fileSc, exportFile, pw, option);
      }
      catch(NoSuchElementException e){
         System.out.println("\nEnd of file.");
      }
      
      fileSc.close();
      
      if(option == 1){
         if(exportFile.exists()){
            System.out.println("\nShopping list created.");
         }
         else{
            System.out.println("\nThere was a problem and the list could not be created.");
         }
      }
      
   }
   
   
   public static void importFile(String[] items, double[] quantity, String[] units, Scanner fileSc, File exportFile, PrintWriter pw, int option) throws IOException{
      String newItem = "", newUnit = "";
      double newQuantity, convertedQuantity;
      String currentLine = "";
      
      while(fileSc.hasNextLine()){
         currentLine = fileSc.nextLine();
         Scanner line = new Scanner(currentLine);
         
         newUnit = "";
         
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
         int itemPos = InputAndTools.getItemPos(items, newItem);
         
         //checks the unit matches the category of units in the fridge
         if(itemPos != -1){
            if(!InputAndTools.doUnitsMatch(newUnit, units, itemPos)){
               System.out.println("\nThe item, " + newItem + ", is currently being stored in a different unit.");
               System.out.println("Please change it before attempting again.");
            }
         }
         
         if(option == 1){
            //prints to the missingitems file the missing item
            if(itemPos == -1){
               pw.println(newItem + ", " + newQuantity + " " + newUnit);
            }
            
            else{
               //convert units here
               convertedQuantity = InputAndTools.convertUnits(newQuantity, newUnit, itemPos, units);
               
               //checks if the item should be added to the list according to the units (will export full value if units don't match)
               if(quantity[itemPos] < newQuantity && InputAndTools.doUnitsMatch(newUnit, units, itemPos)){
                  pw.println(newItem + ", " + (newQuantity - quantity[itemPos]) + " " + newUnit);
               }
               else if(quantity[itemPos] < newQuantity && !InputAndTools.doUnitsMatch(newUnit, units, itemPos)){
                  pw.println(newItem + ", " + newQuantity + " " + newUnit);
               }
            }
         }
         
         else{
            if(itemPos == -1){
               if(newUnit.equalsIgnoreCase(""))
                  System.out.println("\nYou are missing " + newQuantity + " " + newItem);
               else
                  System.out.println("\nYou are missing " + newQuantity + " " + newUnit + " of " + newItem);
            }
            
            else{
               //convert units here
               convertedQuantity = InputAndTools.convertUnits(newQuantity, newUnit, itemPos, units);
               
               if(quantity[itemPos] >= newQuantity && InputAndTools.doUnitsMatch(newUnit, units, itemPos)){
                  quantity[itemPos] -= newQuantity;
                  System.out.println("Dispensed " + newQuantity + " of " + newItem);
               }
               
               //checks if the item should be added to the list according to the units (will export full value if units don't match)
               else if(quantity[itemPos] < newQuantity && InputAndTools.doUnitsMatch(newUnit, units, itemPos)){
                  System.out.println("\nYou are missing " + (newQuantity - quantity[itemPos]) + " of " + newItem);
               }
               
               else if(!InputAndTools.doUnitsMatch(newUnit, units, itemPos)){
                  System.out.println("\nYou are missing " + newQuantity + " " + newUnit + " of " + newItem);
               }
            }
            
         }
      
      }
      
      pw.close();
   }
   
}