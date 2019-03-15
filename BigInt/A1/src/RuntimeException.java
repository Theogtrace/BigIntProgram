package BigNumb;

public class StringErrorException extends RuntimeException
{
		public StringErrorException() 
		{
			super("");
		}

		public StringErrorException(String message) {
			super(message);
		}


}
