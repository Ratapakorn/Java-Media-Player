package studiplayer.audio;

public class NotPlayableException extends Exception {

	public NotPlayableException() 
	{
		
	}
	
	public NotPlayableException(String pathname, String msg) {
		super("Pathname: " + pathname + " Messages: " + msg);
	}

	public NotPlayableException(String pathname, Throwable cause) {
		super("Pathname: " + pathname + " Cause: " + cause);
	}

	public NotPlayableException(String pathname, String msg, Throwable cause) {
		super("Path: " + pathname + " Messages: " + msg, cause);
	}
}
