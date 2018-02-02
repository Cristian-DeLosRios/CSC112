package poly;

import java.io.IOException;
import java.util.Scanner;
//save
/**
 * This class implements evaluate, add and multiply for polynomials.
 * 
 * @author runb-cs112
 *
 */
public class Polynomial {
	
	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage format
	 * of the polynomial is:
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * with the guarantee that degrees will be in descending order. For example:
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * which represents the polynomial:
	 * <pre>
	 *      4*x^5 - 2*x^3 + 2*x + 3 
	 * </pre>
	 * 
	 * @param sc Scanner from which a polynomial is to be read
	 * @throws IOException If there is any input error in reading the polynomial
	 * @return The polynomial linked list (front node) constructed from coefficients and
	 *         degrees read from scanner
	 */
	public static Node read(Scanner sc) 
	throws IOException {
		Node poly = null;
		while (sc.hasNextLine()) {
			Scanner scLine = new Scanner(sc.nextLine());
			poly = new Node(scLine.nextFloat(), scLine.nextInt(), poly);
			scLine.close();
		}
		return poly;
	}
	
	/**
	 * Returns the sum of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list
	 * @return A new polynomial which is the sum of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	
	
	//should make a sort nodes methods, declare as private
	
	
	private static Node Sort(Node poly){
		
		//from highest degree to smallest degree
		
			
		Node Ordered = new Node(0,0,null);
			
		//int low = Integer.MAX_VALUE;
		//int high = Integer.MIN_VALUE;
		// Node temp = new Node(0, 0, null);
		// temp.next = new Node(tempCo, tempDegree,null);
		int high = -1;
		boolean run == true;
		Do{
		for(node current = poly; current!= null; current = poly.next){ //find highest degree, add it to new list, then find the next one
			if (current.term.degree > high)
				high = current.term.degree;
				
				
			//if (current.term.degree < current.next.term.degree)
			//	current = poly;
				
		}
		for(node current = poly; current!= null; current = poly.next){
			if(current.term.degree == high)
				Ordered.next = new Node(current.term.coeff, 			current.term.degree, null);
			poly = poly.next;
		}
		while(run == true)}
		return Ordered;
	}
	
	private static Node Remove(Node input){
		
		//while(){ }//.next =
			
		
		
		
		//return nodes with the term removed
		return null;
	}
	public static Node add(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/
		
		int length = 0;
		Node temp = new Node(0, 0, null);
		
		for (Node list1 = poly1; list1 != null; list1 = poly1.next) {
			for (Node list2 = poly2; list2 != null; list2 = poly2.next) {
				
				if(list1.term.degree == list2.term.degree){
					
					float tempCo =	list1.term.coeff + list2.term.coeff;  //check if 0
					int tempDegree = list1.term.degree;
					poly1 = poly1.next;
					poly2 = poly2.next;
					
					if (tempCo == 0){
						//remove, then continue
						continue;
					}
					
					 //temp.term = new Term(tempCo, tempDegree);
					 temp.next = new Node(tempCo, tempDegree,null);
					 length +=1;
				}
			}
		}
			
		if(poly1 != null)	
			temp.next = poly1.next;
		if(poly2 != null)
			temp.next = poly2.next;
			
				
		if(length ==0)
			return 0;
		else if(length == 1)
			return temp;
		else
			return null  // return sort(temp);	
		
		//return sorted array. 
		return null;
					 
					

				//remove added terms and add the unequal degree at the end	
				
		}
	

	/**
	 * Returns the product of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list)
	 * @return A new polynomial which is the product of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node multiply(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		return null;
	}
		
	/**
	 * Evaluates a polynomial at a given value.
	 * 
	 * @param poly Polynomial (front of linked list) to be evaluated
	 * @param x Value at which evaluation is to be done
	 * @return Value of polynomial p at x
	 */
	public static float evaluate(Node poly, float x) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		return 0;
	}
	
	/**
	 * Returns string representation of a polynomial
	 * 
	 * @param poly Polynomial (front of linked list)
	 * @return String representation, in descending order of degrees
	 */
	public static String toString(Node poly) {
		if (poly == null) {
			return "0";
		} 
		
		String retval = poly.term.toString();
		for (Node current = poly.next ; current != null ;
		current = current.next) {
			retval = current.term.toString() + " + " + retval;
		}
		return retval;
	}	
}
