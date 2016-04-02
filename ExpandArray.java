import java.util.Arrays;

//copies each respective array into an array that is one bigger to make room for another item
public class ExpandArray{
   public static String[] expandItems(String[] items){
      String[] newItems = new String[items.length + 1];
      
      System.arraycopy(items, 0, newItems, 0, items.length);
      
      return newItems;
   }
   
   public static double[] expandQuantity(double[] quantity){
      double[] newQuantity = new double[quantity.length + 1];
      
      System.arraycopy(quantity, 0, newQuantity, 0, quantity.length);
      
      return newQuantity;
   }
   
   public static String[] expandUnits(String[] units){
      String[] newUnits = new String[units.length + 1];
      
      System.arraycopy(units, 0, newUnits, 0, units.length);
      
      return newUnits;
   }
   
}//end class