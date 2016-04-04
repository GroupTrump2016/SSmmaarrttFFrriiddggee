import java.util.Scanner;
import java.util.Arrays;
import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.io.PrintWriter;

public class Recipe{
   
   public static void main(String[] args) throws IOException{
      enterRecipeMenu();
   }
   
   public static void enterRecipeMenu() throws IOException{
      int userOption = -1;
      final int RETURN_TO_MAIN = 6;
      
      while(userOption != RETURN_TO_MAIN){
         //prints the menu of options
         System.out.println("\n=========================================");
         System.out.println("Recipe Submenu");
         
         //prints main menu
         printRecipeMenu();
         
         //gets valid input
         //sends in 7 for 7 options
         userOption = InputAndTools.getMenuInput(6);
         
         //determines where to go depending on what the user entered
         switch(userOption){
            case 1:
               addRecipe();
               break;
            case 2:
               break;
            case 4:
            case 5:
               
         }//end switchcase
         
      }//end while check
      
      return;
      
   }
   
   public static void printRecipeMenu() throws IOException{
      System.out.println("\n1. Add recipe");
      System.out.println("2. Delete recipe");
      System.out.println("3. Print recipe");
      System.out.println("4. See outstanding items from recipe");
      System.out.println("5. Cook recipe");
      System.out.println("6. Return to main menu");
   }
   
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
      File exportFile = new File("recipes\\" + fileName + ".txt");
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
   
}