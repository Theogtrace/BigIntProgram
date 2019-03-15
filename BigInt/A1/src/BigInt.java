package BigNumb;
import BigNumb.StringErrorException;
import BigNumb.BigInt;




public class BigInt 
{
	private boolean neg; // negative
	private int[] digit;//array of digits
	private int max=1000;

	//	   constructor default
	public BigInt()
	{
		neg=true;
		digit= new int[max];
	}
	//	   BigInt String
	public BigInt(String b1) throws StringErrorException 
	{

		String value = b1;

		neg = false;

		if (b1.charAt(0) == '+' || b1.charAt(0) == '-') 
		{

			if (b1.length() > 1)
			{

				value = b1.substring(1);

			} else 
			{
				throw new StringErrorException("This contains only a sign!!" + b1);

			}

			if (b1.charAt(0) == '-') //if is negative

				neg = true;

		}

		digit = new int[value.length()];

		for (int i = 0; i < value.length(); i++)
		{

			if (Character.isDigit(value.charAt(i))) 
			{
				digit[digit.length - 1 - i] = Character.digit(value.charAt(i), 10);
			} 
			else 
			{
				throw new StringErrorException("Illegal value input!" + b1);

			}

		}

	}



	public String toString()

	{
		//		   The StringBuilder class  has a length() method that returns the length of the character sequence in the builder.
		StringBuilder st = new StringBuilder();

		if (neg)

			st.append('-');


		for(int i=0; i<digit.length; i++)

		{

			st.append(digit[digit.length - 1 - i]);

		}

		return st.toString();

	}

	//	   an array to remove leading zeroes
	private int[] removeLeadingZero(int[] a)

	{

		int i = a.length - 1;

		while (i > 0 && a[i] == 0)

			i--;

		int[] c = new int[i+1];

		for (int j = 0; j < c.length; j++)

			c[j] = a[j];
		return c;

	}

	// return 0 if same, <0 if a<b, > 0 if a > b

	private int compare(int[] a, int[] b)

	{

		if (a.length != b.length)//if the length of b1 is not equal to the b2

			return a.length - b.length;

		for (int i = a.length - 1; i >= 0; i--)

		{

			if (a[i] != b[i])

				return a[i] - b[i];

		}

		return 0;

	}

	private int[] addArr(int[] a, int[] b)

	{

		int size = Math.max(a.length, b.length) + 1;

		int[] c = new int[size];

		int carry = 0;

		for (int i = 0; i < a.length || i < b.length || carry > 0; i++)

		{

			int sum = carry;

			if (i < a.length)

				sum += a[i];

			if (i < b.length)

				sum += b[i];


			carry = sum / 10;

			sum = sum % 10;

			c[i] = sum;

		}

		return removeLeadingZero(c);

	}
	// assume a >= b

	private int[] subArr(int[] a, int[] b)

	{

		int[] c = new int[a.length];

		int carry = 0;

		for (int i = 0; i < a.length || i < b.length || carry != 0; i++)

		{

			int r = carry;

			if (i < a.length)

				r += a[i];

			if (i < b.length)

				r -= b[i];

			//			carry = (r < 0 ? -1 : 0);

			if (r < 0)

				r += 10;

			c[i] = r;

		}

		return removeLeadingZero(c);

	}
	//	   adding two ints 

	public BigInt add(BigInt other) throws StringErrorException

	{

		BigInt answer = new BigInt("0");

		if (this.neg && other.neg)

		{

			answer.neg = true;

			answer.digit = addArr(this.digit, other.digit);// is going to go to this add Array

		}

		else if (!this.neg && !other.neg)// if they both are positive

		{

			answer.neg = false;

			answer.digit = addArr(this.digit, other.digit);// is going to the add array method

		}
		//Here is where it adds the b1 that is positive  and the b2 that is negative number
		else if (!neg) // positive + negative

		{
			//is comparing if the lengths have the same length or not
			if (compare(this.digit, other.digit) >= 0)

			{

				answer.neg = false;

				answer.digit = subArr(this.digit, other.digit);

			}

			else

			{

				answer.neg = true;

				answer.digit = subArr(other.digit, this.digit);

			}

		}
		//	    adding the b1 is negative and the b2 is positive

		else // negative + positive

		{

			if (compare(this.digit, other.digit) <= 0)

			{

				answer.neg = false;

				answer.digit = subArr(other.digit, this.digit);

			}

			else

			{

				answer.neg = true;

				answer.digit = subArr(digit, other.digit);

			}

		}

		return answer;

	}

	public BigInt sub(BigInt b) throws StringErrorException

	{

		BigInt answer = new BigInt("0");

		//If the numbers of b1 is positive subtracts b2 negative

		if (!neg && b.neg) // positive - negative

		{

			answer.neg = false;

			answer.digit = addArr(digit, b.digit);

		}

		else if (neg && !b.neg) // negative - positive

		{

			answer.neg = true;

			answer.digit = addArr(digit, b.digit);

		}

		else if (!neg && !b.neg) // positive - positive

		{

			if (compare(digit, b.digit) >= 0)

			{

				answer.neg = false;

				answer.digit = subArr(digit, b.digit);

			}

			else

			{

				answer.neg = true;

				answer.digit = subArr(b.digit, digit);

			}

		}

		else // negative - negative

		{

			if (compare(digit, b.digit) <= 0)

			{

				answer.neg = false;

				answer.digit = subArr(b.digit, digit);

			}

			else

			{

				answer.neg = true;

				answer.digit = subArr(digit, b.digit);

			}

		}

		return answer;

	}

	public BigInt mul(BigInt other) throws StringErrorException

	{

		BigInt answer = new BigInt("0");

		if(this.neg && other.neg)
		{
			answer.neg = false;
			answer.digit = mulArr(this.digit, other.digit);
		}
		else if (!this.neg && !other.neg)
		{
			answer.neg = false;
			answer.digit = mulArr(this.digit, other.digit);
		}
		else if (!this.neg && other.neg)
		{
			answer.neg = true;
			answer.digit = mulArr(this.digit, other.digit);
		}
		else if (this.neg && !other.neg)
		{
			answer.neg = true;
			answer.digit = mulArr(this.digit, other.digit);
		}

		return answer;


	}

	private int[] mulArr(int[] a, int[] b)
	{
		int size = (a.length + b.length);
		int len1 = a.length;
		int len2 = b.length;
		int count;
		int[] c = new int[size];
		if(len1>len2)
		{
			count = a.length;
		}
		else
		{
			count = b.length;
		}
		for(int i=0; i<len2;i++)
		{
			int carry =0;
			int sum;
			for(int j=0; j<len1;j++)
			{
				sum = c[j] + (b[i]*a[j]) + carry;
				carry = sum/10;
				sum = sum % 10;
				c[j] = sum;
				count--;
				for(int k = 0; k<count;k++)
				{
					c[k]+=0;
				}
			}
		}
		return removeLeadingZero(c);
	}


	public BigInt div(BigInt b) throws StringErrorException

	{

		BigInt r = new BigInt("0");

		int p = 1;

		for (int i = 1; i < digit.length; i++)

		{

			int times = digit[i] / p;

			for (int j = 1; j < times; j++)

				r = r.add(b);

			p /= 10;

		}

		if (neg)

			r.neg = !r.neg;



		return r;

	}

	public BigInt modulus(BigInt b) throws StringErrorException

	{

		BigInt r = new BigInt("0");

		int p = 1;

		for (int i = 1; i < digit.length; i++)

		{

			int times = digit[i] % p;

			for (int j = 1; j < times; j++)

				r = r.add(b);

			p %= 10;

		}

		if (neg)

			r.neg = !r.neg;



		return r;

	}


}
