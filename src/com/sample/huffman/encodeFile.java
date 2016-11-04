/* Name : Deepika Joseph
 * Wsu ID: R382U353
 * Assignment-3 Part-2
 */

package com.sample.huffman;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class encodeFile extends WriteFileWorker {

	public Map<Character, String> map;

	public encodeFile(String location, ArrayList<huffmanTple> encodings) {
		super(location);
		this.map = new HashMap<>();
		this.initMap(encodings);
	}


	private void initMap(ArrayList<huffmanTple> encodgs) {
		for (huffmanTple tple : encodgs) {
			map.put(tple.ltr, tple.showCase);
		}
	}

	protected void writeBeginningOfFile(String hdStrg) {
		for (int i = 0; i < hdStrg.length(); i += 2) {
			int intRep = Integer.parseInt(hdStrg.substring(i, i + 2), 16);
			String bin = Integer.toBinaryString(intRep);
			String padBin = huffmanImp.rightPadString(bin, NUMOFBITS_TO_WRITE);
			bytBuf += padBin;
			this.writeToFile();
		}
	}

	protected void writeEndOfFile() {
		bytBuf += map.get((char) 0x00);
		// Properly pad the
		if (bytBuf.length() != 8) {
			// determine how many bytes we'll pad by
			int numValBytes = bytBuf.length() / 8;
			bytBuf = huffmanImp.leftPadString(bytBuf, NUMOFBITS_TO_WRITE * (numValBytes + 1));
		}
		this.writeToFile();
	}

	public void writeToFile() {
		try {
			while (bytBuf.length() >= NUMOFBITS_TO_WRITE) {
				int i = Integer.parseInt(bytBuf.substring(0, NUMOFBITS_TO_WRITE), 2);
				fopSt.write(i);
				bytBuf = bytBuf.substring(NUMOFBITS_TO_WRITE);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void doWork(int currentByte) {
		bytBuf += map.get((char) currentByte);
		this.writeToFile();
	}
}
