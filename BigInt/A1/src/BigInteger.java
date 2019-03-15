package BigNumb;

public class BigInteger 
{
	public static String checkSignWithRelational(int bigInt1, int bigInt2)
	{
		if( bigInt1 < 0){
			return "negative";
		}else {
			return "positive";
		}
	}
	BigInteger(long init)
	{
		java.lang.String bigInt1 = null;
		Long.parseLong(bigInt1);
	}
	BigInteger String (String init){
		return null; 
	}

	private static int intLenght(int bigInt) {

		return Integer.toString(bigInt).length();
	}

	private static int[] intToArray(int bigInt, int bigIntLength, int arrayLength) {

		int array[] = new int[arrayLength ]; 
		for (int i = 0; i < arrayLength ; i++) {
			array[i] = ( i<bigIntLength ?
					getDigitAtIndex(bigInt, bigIntLength - i -1) :0 ); 
		}
		return array;
	}
	static String add(int bigInt1, int bigInt2) {
		//Find array length
		int length1 = intLenght(bigInt1);
		int length2 = intLenght(bigInt2);
		int arrayLength = Math.max(length1, length2);


		int array1[] = intToArray(bigInt1, length1, arrayLength);
		int array2[] = intToArray(bigInt2, length2, arrayLength);


		return add(array1, array2);
	}


	private static String add(int[] array1, int[] array2) {
		int carry=0;
		int addArray[] = new int[array1.length + 1];


		for (int i = 0; i < array1.length; i++) {
			addArray[i] = (array1[i] + array2[i] + carry) % 10 ; 
			carry = (array1[i] + array2[i] + carry) / 10; 
		}
		addArray[array1.length] = carry;
		return arrayToString(addArray);
	}

	private static int getDigitAtIndex(int longint,int index){        
		return Integer.parseInt(Integer.toString(longint).substring(index, index+1)); 
	}
	private static String arrayToString(int[] addArray) {
		String add = "";
		boolean firstNonZero = false; 
		for (int i = addArray.length-1; i >= 0 ; i--) {  

			if(!firstNonZero && (addArray[i]==0)){ 
				continue;
			} else{
				firstNonZero=true;
			}
			add += addArray[i];
			if((i%3 ==0)&&i!=0){ add +=",";}  //formatting
		}
		String sumStr = add.length()==0?"0":add; 
		return sumStr;
	}
	public static String sub(int bigInt1, int bigInt2) {


		int length1 = intLenght(bigInt1);
		int length2 = intLenght(bigInt2);
		int arrayLength = Math.max(length1, length2);


		int array1[] = intToArray(bigInt1, length1, arrayLength);
		int array2[] = intToArray(bigInt2, length2, arrayLength);


		return sub(array1, array2);
	}
	private static String sub(int[] array1, int[] array2) {
		int carry=0;
		int sub[] = new int[array1.length + 1];


		for (int i = 0; i < array1.length; i++) {
			sub[i] = (array1[i] - array2[i] + carry) % 10 ; //sum digits + carry; then extract last digit
			carry = (array1[i] - array2[i] + carry) / 10; //Compute carry
		}
		sub[array1.length] = carry;
		return arrayToString(sub);
	}
	public static String mul(int bigInt1, int bigInt2) {
		int length1 = intLenght(bigInt1), length2 = intLenght(bigInt2), length = Math.max(length1, length2);        
		int array1[] = intToArray(bigInt1, length1, length); int array2[] = intToArray(bigInt2, length2, length);
		return mul(array1, array2);
	}
	private static String mul(int[] array1, int[] array2) {
		int product[] = new int[array1.length + array2.length];
		for(int i=0; i<array1.length; i++){        
			for(int j=0; j<array2.length; j++){ 

				int prod = array1[i] * array2[j];       
				int prodLength = intLenght(prod);
				int prodAsArray[] =  intToArray(prod, prodLength, prodLength); 


				for (int k =0; k < prodAsArray.length; k++) {
					product[i+j+k] += prodAsArray[k];


					int currentValue = product[i+j+k];
					if(currentValue>9){
						product[i+j+k] = 0;                
						int curValueLength = intLenght(currentValue);
						int curValueAsArray[] = intToArray(currentValue, curValueLength, curValueLength);
						for (int l = 0; l < curValueAsArray.length; l++) {
							product[i+j+k+l] += curValueAsArray[l];
						}
					}
				}      
			}
		}
		return arrayToString(product);
	}

	public static int div(int bigInt1, int bigInt2) {
		if ( bigInt2 == 0){
			throw new ArithmeticException("Division by 0 is undefined:" + bigInt1+ "/" + bigInt2);
		}
		int sign = 1;
		if(bigInt1 < 0) {
			bigInt1 = -bigInt1;
			sign = -sign;
		}
		if (bigInt2 < 0){
			bigInt2 = -bigInt2;
			sign = -sign;

		}
		int result  =0;
		while (bigInt1 >= 0){
			bigInt1 -= bigInt2;
			result++;
		}
		return (result - 1) * sign;
	}

	public static String check(String bigInt1, String bigInt2){
		int difference;
		StringBuilder first = new StringBuilder(bigInt1);
		StringBuilder second = new StringBuilder(bigInt2);

		if(bigInt1.length()> bigInt2.length()){
			difference = bigInt1.length() - bigInt2.length();
			for(int x = difference; x > 0; x--){
				second.insert(0,"0");

			}
			bigInt2 = second.toString();
			return bigInt2;

		}else {
			difference = bigInt2.length() - bigInt1.length();
			for (int x = difference; x> 0; x--)
			{
				first.insert(0, "0");
			}
			bigInt1 = first.toString();
			return bigInt1;
		}
	}
	public static int mod(int bigInt1, int bigInt2){
		int res = bigInt1 % bigInt2;
		return (res);

	}

	public static void main(String[] args) 
	{

		int bigInt1 = Integer.parseInt("12");
		int bigInt2 = Integer.parseInt("2");
		System.out.println(bigInt1+" + "+bigInt2+" = "+add(bigInt1, bigInt2));
		System.out.println(bigInt1+" - "+bigInt2+" = "+sub(bigInt1, bigInt2));
		System.out.println(bigInt1+" * "+bigInt2+" = "+mul(bigInt1, bigInt2));
		System.out.println(bigInt1+" / "+bigInt2+" = "+div(bigInt1, bigInt2));
		System.out.println(bigInt1+" % "+bigInt2+" = "+mod(bigInt1, bigInt2));
	}
}
