/* Program name: ExpandArray
 * Name and ID: Marty Yung 100256573, Youta Wu 100259493
 * Date: April 8, 2016
 * Lab: Project
 * Course: CPSC 1150 - Sec. 5
 */
 
 /*	This program is capable of expanding an array by one size and copying the old contents into the new one. 
      SmartFridge uses this when new items are added.
      
      This program uses method overloading.
 */

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
   
   //this method is for the quantity
   public static double[] expand(double[] quantity){
      double[] newQuantity = new double[quantity.length + 1];
      
      System.arraycopy(quantity, 0, newQuantity, 0, quantity.length);
      
      return newQuantity;
   }
   
}//end class