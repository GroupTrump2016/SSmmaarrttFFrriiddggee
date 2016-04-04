import java.util.Scanner;

//contains a collection of tools needed for SmartFridge
//it's all here to reduce clutter on the main class
public class InputAndTools{
   
   //bypasses issues with scanners with sc.next() and sc.nextLine() right after
   public static String getItemName(){
      Scanner sc = new Scanner(System.in);
      
      String name = sc.nextLine();
      
      return name;
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
   
   //gets a yes or no from the user
   public static boolean yesOrNo(){
      Scanner sc = new Scanner(System.in);
      boolean validInput = false;
      char input = 'A';
      String check;
      
      //gets a valid char Y or N from the user
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
      
   }//end getValidUnit
   
   
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
   
   
   //converts units to the unit in the fridge
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
}