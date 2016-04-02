import java.util.Arrays;

//copies each respective array into an array that is one bigger to make room for another item
//uses method overloading to determine which method to use
public class ExpandArray{
   //this method can take in both item name and unit name
   public static String[] expand(String[] stringArr){
      String[] newStringArr = new String[stringArr.length + 1];
      
      //replaces nulls with blanks
      Arrays.fill(newStringArr, "");
      
      System.arraycopy(stringArr, 0, newStringArr, 0, stringArr.length);
      
      return newStringArr;
   }
   
   public static double[] expand(double[] quantity){
      double[] newQuantity = new double[quantity.length + 1];
      
      System.arraycopy(quantity, 0, newQuantity, 0, quantity.length);
      
      return newQuantity;
   }
   
}//end class