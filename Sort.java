public class Sort{
   public static void selectionSort(String[] items, double[] quantity, String[] units){
      //loops for entire length of array
      for(int i = 0; i < items.length - 1; i++){
         int minPos = i;
         for(int j = i + 1; j < items.length; j++){
            //checks if the element at i+1 is smaller than the pos at the min. pos.            
            if(items[j].compareToIgnoreCase(items[minPos]) < 0){
               minPos = j;
            }
            
         }
         
         swap(items, quantity, units, i, minPos);
         
      }//end for
      
   }//end selectionSort
   
   public static void swap(String[] items, double[] quantity, String[] units, int i, int j){
      String tempItem, tempUnit;
      double tempQuantity;
      
      //swaps item name
      tempItem = items[i];
      items[i] = items[j];
      items[j] = tempItem;
      
      //swaps quantity
      tempQuantity = quantity[i];
      quantity[i] = quantity[j];
      quantity[j] = tempQuantity;
      
      //swaps units
      tempUnit = units[i];
      units[i] = units[j];
      units[j] = tempUnit;
      
      
   }//end swap

}