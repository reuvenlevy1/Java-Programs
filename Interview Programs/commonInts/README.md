/**
 * Returns an array of common integers between two sorted arrays.
 * This is done in the following way:
 * 1. One while loop incrementing indexes of either array depending on if 
 * the value of one array is less than the value of the other. 
 * 
 * Assumption:
 * 1. Integers would be positive.
 * 
 * The following are requirements for both arrays:
 * 1. Sorted in ascending order.
 * 2. Do not contain any duplicate numbers.
 *  
 * Contains the following features:
 * 1. Uses Selection Sort to sort any array into ascending order. 
 * 2. Uses LinkedHashMap to remove duplicates and maintain order of array.
 * 3. Uses HashSet to automatically sort and remove duplicates of array.
 * 
 * @author Reuven
 */
