package com.arsh.twitter.utilities;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class AppendedObjectOutputStream extends ObjectOutputStream{

	public AppendedObjectOutputStream(OutputStream out) throws IOException {
		super(out);
		// TODO Auto-generated constructor stub
	}
	@Override
	  protected void writeStreamHeader() throws IOException {
	    reset();
	 }
	

}
