/* Name : Deepika Joseph
 * Wsu ID: R382U353
 * Assignment-3 Part-2
 */


package com.sample.huffman;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
public abstract class WriteFileWorker implements fileReader {
	private File file;
	protected FileOutputStream fopSt;
	public String bytBuf;
	public static final int NUMOFBITS_TO_WRITE = 8;

	public WriteFileWorker(String path) {
		this.file = new File(path);
		this.bytBuf = "";
		this.initFile();
	}

	private void initFile() {
		try {
			this.fopSt = new FileOutputStream(this.file);
			if (!file.exists()) {
				file.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public abstract void writeToFile();
}
