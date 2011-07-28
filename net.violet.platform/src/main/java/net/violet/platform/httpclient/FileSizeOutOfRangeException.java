package net.violet.platform.httpclient;

public class FileSizeOutOfRangeException extends Exception {

	public FileSizeOutOfRangeException(int size) {
		super("File size is out of range : " + size);
	}

}
