import java.util.Arrays;

//finds the length the new array should be
//then copies the old elements into the new one

//uses method overloading to determine which method to use
public class ShrinkArray{
   
   public static String[] shrink(double[] quantity, String[] stringArr){
      int numNonEmpty = getNumNonEmpty(quantity), newPos = 0;
      String[] newStringArr = new String[numNonEmpty];
      
      //checks each position in the array for 0 quantity
      for(int i = 0; i < quantity.length; i++){
         if(quantity[i] != 0){
            newStringArr[newPos] = stringArr[i];
            newPos ++;
         }
      }
      
      return newStringArr;
   }
   
   
   public static double[] shrink(double[] quantity){
      int numNonEmpty = getNumNonEmpty(quantity), newPos = 0;
      double[] newQuantity = new double[numNonEmpty];
      
      //checks each position in the array for 0 quantity
      for(int i = 0; i < quantity.length; i++){
         if(quantity[i] != 0){
            newQuantity[newPos] = quantity[i];
            newPos ++;
         }
      }
      
      return newQuantity;
   }
   
   //gets the number of non empty quantities so that the shrink method can create a new array of that size
   public static int getNumNonEmpty(double[] quantity){
      int numNonEmpty = 0;
      
      //checks the array for how many non-zero quantity items there are
      for(int i = 0; i < quantity.length; i++){
         if(quantity[i] > 0){
            numNonEmpty ++;
         }
      }
      
      return numNonEmpty;
   }
   
   
   //the clear methods will create a new array of the default size
   //uses method overloading
   public static String[] clear(String[] stringArr, int DEFAULT_SIZE){
      String[] newStringArr = new String[DEFAULT_SIZE];
      Arrays.fill(newStringArr, "");
      
      return newStringArr;
   }
   
   public static double[] clear(double[] quantity, int DEFAULT_SIZE){
      double[] newQuantity = new double[DEFAULT_SIZE];
      
      return newQuantity;
   }
   

   
}//end class