import java.util.ArrayList;
public class BigInt 
{
	private char sign;
	private ArrayList<Integer> bigInt = new ArrayList<Integer>();

	public BigInt(String number)
	{
		if (number.charAt(0) == '-') 
		{ // checks to see if negative
			this.sign = '-';
			setBigInt(number.substring(1, number.length()));
		}
		else if (number.charAt(0) == '+')
		{
			this.sign = '+';
			setBigInt(number.substring(1, number.length()));
		} 
		else { // all other cases must be pos and number starts at index 0
			this.sign = '+';
			setBigInt(number.substring(0, number.length()));
		}
	}
	private void setBigInt(String number)
	{
		String num = "";
		try 
		{ 
			for (int i = 0; i < number.length(); i++)
			{
				num = "" + number.charAt(i);
				int integer = Integer.parseInt(num);
				bigInt.add(integer);
			}
			if (num.equals("")) 
			{
				throw new NumberFormatException();
			}      
		} catch (NumberFormatException e) 
		{
			System.out
			.println("Number can only contain sign than integers. Program ending");
			System.exit(0);     
		} 
	}
	public char getSign() 
	{  
		return sign;  
	}
	public String getBigInt() 
	{
		String intString = "";
		for (Integer integer : this.bigInt)
		{
			intString = intString + integer;
		}
		return intString;  
	}
	public int getBigInt(int index)
	{ 
		return bigInt.get(index);
	}
	public String toString() 
	{    
		String bigInt = getBigInt();
		for (int i = 0; i < getBigInt().length(); i++) 
		{
			if (bigInt.charAt(i) != '0') 
			{
				bigInt = bigInt.substring(i);
				break;    
			} 
		}
		if (bigInt.charAt(0) == '0'	&& bigInt.charAt(bigInt.length() - 1) == '0')
			return "0";
		if (sign == '-')
			return getSign() + bigInt;
		else
			return bigInt; 
	}


	public BigInt divideBy(BigInt bigIntToDivide) 
	{
		char sign = '+';
		String newBigInt = "";
		if (bigIntToDivide.toString().equals("0"))
		{
			System.out.println("Can not divide by zero.");
			newBigInt = "0";
			//if they equal each other
		}
		else if (this.getBigInt().equals(bigIntToDivide.getBigInt()))
		{
			if (this.getSign() == '-' && bigIntToDivide.getSign() == '+'
					|| this.getSign() == '+' && bigIntToDivide.getSign() == '-')
				sign = '-';
			newBigInt = "1";
			//if divisor is bigger it will equal 0
		} 
		else if (!isBigger(this.getBigInt(), bigIntToDivide.getBigInt())) 
		{
			newBigInt = "0";
		} else 
		{
			if (this.getSign() == '-' && bigIntToDivide.getSign() == '+'|| this.getSign() == '+' && bigIntToDivide.getSign() == '-') 
			{
				sign = '-';
			}
			newBigInt = this.division(bigIntToDivide);
		}
		BigInt bigIntToReturn = new BigInt("" + sign + newBigInt);
		return bigIntToReturn;
	}

	private String division(BigInt divisor)
	{  
		String bigIntToReturn = "";
		String compair = "";
		int index = 0;
		//loops thu dividend left to right
		while(index < this.bigInt.size()) 
		{
			compair = compair + this.getBigInt(index);
			int count = 0;
			while (!isBigger(compair, divisor.getBigInt())) 
			{   
				if (index == this.bigInt.size() - 1)
					break;
				index++;
				compair = compair + this.getBigInt(index);
				bigIntToReturn = bigIntToReturn + 0;          
			}
			//if the divisor is smaller do the math and return the div
			while (!isBigger(divisor.getBigInt(), compair)) {
				BigInt temp = new BigInt(compair);
				compair = temp.subtraction(divisor);
				count++;           }
			index++;
			bigIntToReturn = bigIntToReturn + count;    
			}
		return bigIntToReturn;   
		}
	public BigInt modulus(BigInt mod) {       if (mod.getBigInt().equals(0)) {
		System.out.println("Can not divide by zero");
		BigInt bigIntToReturn = new BigInt("0");
		return bigIntToReturn;
	} else {           BigInt result1 = this.divideBy(mod);
	BigInt result2 = result1.multiply(mod);
	BigInt bigIntToReturn = this.subtract(result2);
	bigIntToReturn.sign = '+';
	return bigIntToReturn;   }   }
	public BigInt multiply(BigInt bigIntToMultiply) {
		char sign = '+';
		String newBigInt = "";
		// assigning sign
		if (this.getSign() == '-' && bigIntToMultiply.getSign() == '+'
				|| this.getSign() == '+' && bigIntToMultiply.getSign() == '-') {
			sign = '-';   }
		if (bigIntToMultiply.getBigInt().equals("0")) {
			sign = '+';
			newBigInt = "0";
		} else if (this.getBigInt().length() <= bigIntToMultiply.getBigInt()
				.length()) {
			newBigInt = this.multiplication(bigIntToMultiply);
		} else {
			newBigInt = bigIntToMultiply.multiplication(this);
		}
		BigInt bigIntToReturn = new BigInt("" + sign + newBigInt);
		return bigIntToReturn;  
	}


	private String multiplication(BigInt bigIntToMultiply) 
	{
		int indent = 0;
		int carry = 0;
		String newString = "";
		BigInt addNumber = new BigInt("0");
		BigInt returnNumber = new BigInt("0");
		for (int i = this.getBigInt().length() - 1; i >= 0; i--) {
			newString = "";
			carry = 0;
			for (int j = bigIntToMultiply.getBigInt().length() - 1; j >= 0; j--) {
				if (j == 0) { // if your at the last number there is no carry
					int temp = (this.getBigInt(i) * bigIntToMultiply
							.getBigInt(j)) + carry;
					newString = temp + newString;
				} else {
					int temp = (this.getBigInt(i) * bigIntToMultiply
							.getBigInt(j)) + carry;
					if (temp >= 10) {
						carry = temp / 10;
						int result = temp % 10;
						newString = result + newString;
					} else {
						newString = temp + newString;
						carry = 0;
					}   }   }
			addNumber = new BigInt(newString + addTrailingZeros(indent));
			returnNumber = returnNumber.add(addNumber);
			indent++;
		}     
		return returnNumber.getBigInt(); 
	}

	public String addTrailingZeros(int amount)
	{
		String zeros = "";
		for (int i = 0; i < amount; i++) {
			zeros += "0";   }
		return zeros;  
	}

	public BigInt add(BigInt bigIntToAdd) 
	{
		char sign = '-';
		String newBigInt = "";
		// add leading zeros so numbers are same length
		if (this.bigInt.size() > bigIntToAdd.bigInt.size()) {
			generateLeadingZeros(bigIntToAdd.bigInt, this.bigInt.size());
		} else if (this.bigInt.size() < bigIntToAdd.bigInt.size()) {
			generateLeadingZeros(this.bigInt, bigIntToAdd.bigInt.size());
		}
		if (this.sign == '+' && bigIntToAdd.getSign() == '+') {
			sign = '+';
			newBigInt = addition(bigIntToAdd);
		}
		// if signs are both - then simple addition and sign is -
		if (this.sign == '-' && bigIntToAdd.getSign() == '-') {
			sign = '-';
			newBigInt = addition(bigIntToAdd);
		}
		if (this.sign == '-' && bigIntToAdd.getSign() == '+') {
			if (this.isBigger2(bigIntToAdd)) {
				sign = '-';
			} else if (!this.isBigger2(bigIntToAdd)) {
				sign = '+';
			}
			newBigInt = subtraction(bigIntToAdd);
		}
		if (this.sign == '+' && bigIntToAdd.getSign() == '-') {
			if (!this.isBigger2(bigIntToAdd)) {
				sign = '-';
			} else if (this.isBigger2(bigIntToAdd)) {
				sign = '+';
			}
			newBigInt = subtraction(bigIntToAdd);
		}
		BigInt newBig = new BigInt("" + sign + newBigInt);
		return newBig;
	}
	public BigInt subtract(BigInt bigIntToSubtract) {
		char sign = '+';
		String newBigInt = "";
		// gen leading zeros
		if (this.bigInt.size() > bigIntToSubtract.bigInt.size()) {
			generateLeadingZeros(bigIntToSubtract.bigInt, this.bigInt.size());
		} else if (this.bigInt.size() < bigIntToSubtract.bigInt.size()) {
			generateLeadingZeros(this.bigInt, bigIntToSubtract.bigInt.size());
		}

		if (this.sign == '-' && bigIntToSubtract.getSign() == '-') {
			if (this.isBigger2(bigIntToSubtract)) {
				sign = '-';
			} else if (!this.isBigger2(bigIntToSubtract)) {
				sign = '+';
			}
			newBigInt = subtraction(bigIntToSubtract);
		}
		if (this.sign == '+' && bigIntToSubtract.getSign() == '+') {
			if (this.isBigger2(bigIntToSubtract)) {
				sign = '+';
			} else if (!this.isBigger2(bigIntToSubtract)) {
				sign = '-';
			}
			newBigInt = subtraction(bigIntToSubtract);
		}
		if (this.sign == '-' && bigIntToSubtract.getSign() == '+') {
			sign = '-';
			newBigInt = addition(bigIntToSubtract);
		}
		if (this.sign == '+' && bigIntToSubtract.getSign() == '-') {
			sign = '+';
			newBigInt = addition(bigIntToSubtract);
		}
		BigInt newBig = new BigInt("" + sign + newBigInt);
		return newBig;
	}
	private ArrayList<Integer> generateLeadingZeros(
			ArrayList<Integer> generateBigInt, int length) {
		for (int i = generateBigInt.size(); i < length; i++) {
			generateBigInt.add(0, 0);
		}
		return generateBigInt;   }

	private boolean isBigger2(BigInt bigIntToCompair) {
		String num1 = this.getBigInt();
		String num2 = bigIntToCompair.getBigInt();
		return num1.compareTo(num2) > 0;   }
	private boolean isBigger(String num1, String num2) {
		if (num1.length() > num2.length()) {
			while (num1.length() > num2.length()) {
				num2 = "0" + num2;
			}   }
		if (num1.length() < num2.length()) {
			while (num1.length() < num2.length()) {
				num1 = "0" + num1;
			}   }
		return num1.compareTo(num2) > 0;   }
	private String addition(BigInt bigIntToAdd) {
		int newInt;
		String newBigInt = "";
		int remainder = 0;
		if (this.bigInt.size() > bigIntToAdd.bigInt.size()) {
			generateLeadingZeros(bigIntToAdd.bigInt, this.bigInt.size());
		} else if (this.bigInt.size() < bigIntToAdd.bigInt.size()) {
			generateLeadingZeros(this.bigInt, bigIntToAdd.bigInt.size());
		}
		for (int i = bigInt.size() - 1; i >= 0; i--) {
			if (remainder > 0) {
				newInt = this.bigInt.get(i) + bigIntToAdd.bigInt.get(i)
				+ remainder;
				remainder = 0;
			} else {
				newInt = this.bigInt.get(i) + bigIntToAdd.bigInt.get(i);
			}
			if (i > 0 && newInt >= 10) {
				remainder = 1;
				newInt = newInt - 10;   }
			newBigInt = newInt + newBigInt;   }
		return newBigInt;   }

	private String subtraction(BigInt bigIntToSubtract) {
		String num1 = this.getBigInt();
		String num2 = bigIntToSubtract.getBigInt();

		if (num1.length() > num2.length()) {
			while (num1.length() > num2.length()) {
				num2 = "0" + num2;
			}   }
		if (num1.length() < num2.length()) {
			while (num1.length() < num2.length()) {
				num1 = "0" + num1;
			}   }
		if (isBigger(num2, num1)) {
			String swap;
			swap = num1;
			num1 = num2;
			num2 = swap;
		}
		String newBigInt = "";
		int currentDigit;
		int tempSum;
		int carry = 0;

		for (int i = num1.length() - 1; i >= 0; i--)
		{
			tempSum = (num1.charAt(i) - 48 - carry) - (num2.charAt(i) - 48);
			if (tempSum < 0) { // if it is less then 0 you need to move it to
				// next
				currentDigit = 10 + tempSum;
				carry = 1;
			} else {               carry = 0;
			currentDigit = tempSum;           }
			newBigInt = (char) (currentDigit + 48) + newBigInt;       
		}
		return newBigInt;   
	}   
}