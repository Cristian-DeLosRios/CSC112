//Cristian De Los Rios
package bigint;

/**
 * first class encapsulates a BigInteger, i.e. a positive or negative integer with 
 * any number of digits, which overcomes the computer storage length limitation of 
 * an integer.
 * 
 */
public class BigInteger {

	/**
	 * True if first is a negative integer
	 */
	boolean negative;
	
	/**
	 * Number of digits in first integer
	 */
	int numDigits;
	
	/**
	 * Reference to the first node of first integer's linked list representation
	 * NOTE: The linked list stores the Least Significant Digit in the FIRST node.
	 * For instance, the integer 235 would be stored as:
	 *    5 --> 3  --> 2
	 *    
	 * Insignificant digits are not stored. So the integer 00235 will be stored as:
	 *    5 --> 3 --> 2  (No zeros after the last 2)        
	 */
	DigitNode front;
	
	/**
	 * Initializes first integer to a positive number with zero digits, in second
	 * words first is the 0 (zero) valued integer.
	 */
	public BigInteger() {
		negative = false;
		numDigits = 0;
		front = null;
	}
	
	/**
	 * Parses an input integer string into a corresponding BigInteger instance.
	 * A correctly formatted integer would have an optional sign as the first 
	 * character (no sign means positive), and at least one digit character
	 * (including zero). 
	 * Examples of correct format, with corresponding values
	 *      Format     Value
	 *       +0            0
	 *       -0            0
	 *       +123        123
	 *       1023       1023
	 *       0012         12  
	 *       0             0
	 *       -123       -123
	 *       -001         -1
	 *       +000          0
	 *       
	 * Leading and trailing spaces are ignored. So "  +123  " will still parse 
	 * correctly, as +123, after ignoring leading and trailing spaces in the input
	 * string.
	 * 
	 * Spaces between digits are not ignored. So "12  345" will not parse as
	 * an integer - the input is incorrectly formatted.
	 * 
	 * An integer with value 0 will correspond to a null (empty) list - see the BigInteger
	 * constructor
	 * 
	 * @param integer Integer string that is to be parsed
	 * @return BigInteger instance that stores the input integer.
	 * @throws IllegalArgumentException If input is incorrectly formatted
	 */
	public static BigInteger parse(String integer) 
	throws IllegalArgumentException {
		
		// following line is a placeholder - compiler needs a return
		// modify it according to need
		integer = integer.trim(); //gets rid of spaces
		
		BigInteger bigInt = new BigInteger();
		
		
		if(Character.isDigit(integer.charAt(0)) || ((integer.length() >= 2) && (integer.charAt(0) == '-' || integer.charAt(0) == '+'))) 
		{
			if(integer.charAt(0)== '-') 
			{
				bigInt.negative = true;
				integer = integer.substring(1); //skip sign, save number as only number
			} 
			else if(integer.charAt(0)== '+') 
			{
				bigInt.negative = false;
				integer = integer.substring(1); //skip sign, save number as only number
			}
			else
			{
				integer = integer.substring(0);
			}
			
		}
		else 
			{
				throw new IllegalArgumentException();
			}
		
		while (integer.charAt(0) == '0' && integer.length() > 1) {
            integer = integer.substring(1);
        }
		
		for (int i = 0 ; i < integer.length(); i++) 
		{
            if (Character.isDigit(integer.charAt(i))) 
            {
                int digit = integer.charAt(i) - '0'; //to turn ascii to the number the char represents
                bigInt.front = new DigitNode(digit, bigInt.front);
                bigInt.numDigits++;
            } 
            else 
            {
                throw new IllegalArgumentException();
            }
        }
		return bigInt; 
	}
	
	/**
	 * Adds the first and second big integers, and returns the result in a NEW BigInteger object. 
	 * DOES NOT MODIFY the input big integers.
	 * 
	 * NOTE that either or both of the input big integers could be negative.
	 * (Which means this method can effectively subtract as well.)
	 * 
	 * @param first First big integer
	 * @param second Second big integer
	 * @return Result big integer
	 */
	public static BigInteger add(BigInteger first, BigInteger second) {
		BigInteger answer = new BigInteger();
		if(first == null && second == null)
		{
			return first;
		}
		
		if(first == null)
		{
			return second;
					
		}
		if(second == null) 
		{
			return first;
		}
		
		if(second.front == null || second.front == null) {
			return first;
		} 
		else if(first.front == null) {
			return second;
		}

		//if both are positive or negative
		if(first.negative == second.negative) 
		{
			answer.negative = first.negative;
			answer.front = new DigitNode(0, null);
			
			
			DigitNode firstPtr = first.front;
			DigitNode secondPtr = second.front;
			DigitNode ansPtr = answer.front;
			int carry = 0;
		
			for(; firstPtr != null && secondPtr != null; firstPtr = firstPtr.next, secondPtr = secondPtr.next) 
			{
				
				int digitSum = firstPtr.digit + secondPtr.digit + carry;
				carry = digitSum / 10; //carry over by tens
				ansPtr.digit = digitSum % 10;
				
				//if there are more numbers add a node
				if(firstPtr.next != null || secondPtr.next != null) 
				{
					ansPtr.next = new DigitNode(0,null);
					ansPtr = ansPtr.next;
				}
			}
			//go until one list is finished
			for(; firstPtr != null ; firstPtr = firstPtr.next)
			{
				int digitSum = firstPtr.digit + carry;
				carry = digitSum / 10;
				ansPtr.digit = digitSum % 10;
				if(firstPtr.next != null) 
				{
					ansPtr.next = new DigitNode(0,null);
					ansPtr = ansPtr.next;
				}
			}
			
			for(; secondPtr != null; secondPtr = secondPtr.next)
			{
				int digitSum = secondPtr.digit + carry;
				carry = digitSum / 10;
				ansPtr.digit = digitSum % 10;
				if(secondPtr.next != null) 
				{
					ansPtr.next = new DigitNode(0,null);
					ansPtr = ansPtr.next;
				}
			}
			
			//carrying the last bit over
			if(carry != 0) 
			{
				ansPtr.next = new DigitNode(carry, null);
			}
		}


		//subtraction
		else 
		{
			BigInteger greater, lesser;
			
			if(firstIsGreater(first, second)) 
			{
				greater = second;
				lesser = first;
			} else 
			{
				greater = first;
				lesser = second;
			}
			//sign is whichever is larger
			answer.negative = greater.negative;
			answer.front = new DigitNode(0, null);
			
			DigitNode gtrPtr = greater.front;
			DigitNode lsPtr = lesser.front;
			DigitNode ansPtr = answer.front;
			
			//While there are digits to subtract, do so while keeping
			boolean hasBorrowed = false; 
			boolean	borrowed = false;
			for(; gtrPtr != null && lsPtr != null; gtrPtr = gtrPtr.next, lsPtr = lsPtr.next, ansPtr = ansPtr.next) 
			{
				
				int gtrDigit = gtrPtr.digit;
				if(borrowed)
				{
					gtrDigit--;
				}
				if(gtrDigit < lsPtr.digit || gtrDigit < 0) 
				{
					gtrDigit += 10;
					hasBorrowed = true;
				}
				
				int difference = gtrDigit - lsPtr.digit;
				ansPtr.digit = difference;
				
				//as long as there are more digits, connect another node
				if(gtrPtr.next != null || lsPtr.next != null) 
				{
					ansPtr.next = new DigitNode(0,null);
				}
				borrowed = hasBorrowed;
				hasBorrowed = false;
			}
			//connect remaining numbers from the larger number
			for(; gtrPtr != null; gtrPtr = gtrPtr.next) 
			{
				int gtrDigit = gtrPtr.digit;
				if(borrowed)
					gtrDigit--;
				
				if(gtrDigit < 0) 
				{
					gtrDigit += 10;
					hasBorrowed = true;
				}
				ansPtr.digit = gtrDigit;
				
				//Only make a new node if there are more digits to copy
				if(gtrPtr.next != null) 
				{
					ansPtr.next = new DigitNode(0,null);
					ansPtr = ansPtr.next;
				}
				
				borrowed = hasBorrowed;
				hasBorrowed = false;
			}	
		}

		int count = 0, lastNonzero = 0;
		answer.numDigits = 0;
		for(DigitNode ptr = answer.front; ptr != null; ptr = ptr.next, count++)
		{
			if(ptr.digit != 0) 
			{
				lastNonzero = count;
			}
		}

		//cut the leading zeros
		count = 0;
		for(DigitNode ptr = answer.front; ptr != null; ptr = ptr.next, count++) 
		{
			answer.numDigits++;
			if(count == lastNonzero)
			{
				ptr.next = null;
				break;
			}
		}

		if(answer.numDigits == 1 && answer.front.digit == 0 || answer.numDigits == 0) 
		{
			answer.numDigits = 0;
			answer.negative = false;
			answer.front = null;
			
		}
		return answer;
		}

		private static boolean firstIsGreater(BigInteger first, BigInteger second) 
		{
			if(first.numDigits > second.numDigits)
			{
				return false;
			} else if(second.numDigits > first.numDigits) 
			{
				return true;
			} else 
			{
			DigitNode ptr1 = first.front;
			DigitNode ptr2 = second.front;
			boolean greater = false;
			while(ptr1 != null && ptr2 != null) 
			{
				if(ptr2.digit > ptr1.digit) 
				{
					greater = true;
				} else if(ptr2.digit < ptr1.digit) 
				{
					greater = false;
				}
				ptr1 = ptr1.next;
				ptr2 = ptr2.next;
			}
			return greater;
		}
		}
		
	
		/**
		 * Returns the BigInteger obtained by multiplying the first big integer
		 * with the second big integer
		 * 
		 * This method DOES NOT MODIFY either of the input big integers
		 * 
		 * @param first First big integer
		 * @param second Second big integer
		 * @return A new BigInteger which is the product of the first and second big integers
		 */
	public static BigInteger multiply(BigInteger first, BigInteger second) {
		BigInteger answer = new BigInteger();
		
		
		if(first == null ||second == null || second.front == null || first.front == null) 
		{
			answer.numDigits = 0;
			answer.negative = false;
			answer.front = null;
			return answer;
		}
		
		answer.front = new DigitNode(0, null);
		
		
		int numZeros = 0;
		for(DigitNode secondPtr = second.front; secondPtr != null; secondPtr = secondPtr.next, numZeros++) 
		{
			BigInteger scalarMultiple = new BigInteger();
			scalarMultiple.front = new DigitNode(0, null);
			DigitNode scalarPtr = scalarMultiple.front;
			
			for(int j = 0; j < numZeros; j++) {
				scalarPtr.digit = 0;
				scalarPtr.next = new DigitNode(0,null);
				scalarPtr = scalarPtr.next;
			}
			
			int carry = 0;
			for(DigitNode firstPtr = first.front; firstPtr != null; firstPtr = firstPtr.next) 
			{
				
				int product = (firstPtr.digit * secondPtr.digit) + carry;
				carry = product / 10;
				scalarPtr.digit = product % 10;
				
				if(firstPtr.next != null) 
				{
					scalarPtr.next = new DigitNode(0,null);
					scalarPtr = scalarPtr.next;
				} else 
				{
					if(carry != 0)
						scalarPtr.next = new DigitNode(carry,null);
					break;
				}
			}
			answer = answer.add(answer, scalarMultiple);
		}
		
		if(first.negative == second.negative) 
		{
			answer.negative = false;
		} else 
		{
			answer.negative = true;
		}
		
		
		int count = 0, lastNonzero = 0;
		answer.numDigits = 0;
		for(DigitNode ptr = answer.front; ptr != null; ptr = ptr.next, count++) 
		{
			if(ptr.digit != 0) 
			{
				lastNonzero = count;
			}
		}
		
		count = 0;
		for(DigitNode ptr = answer.front; ptr != null; ptr = ptr.next, count++) 
		{
			answer.numDigits++;
			if(count == lastNonzero)
			{
				ptr.next = null;
				break;
			}
		}
		
		if(answer.numDigits == 1 && answer.front.digit == 0|| answer.numDigits == 0)
		{
			answer.negative = false;
			answer.front = null;
			answer.numDigits = 0;
		}

		return answer;
		}
		
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		if (front == null) {
			return "0";
		}
		String retval = front.digit + "";
		for (DigitNode curr = front.next; curr != null; curr = curr.next) {
				retval = curr.digit + retval;
		}
		
		if (negative) {
			retval = '-' + retval;
		}
		return retval;
	}
	
}
