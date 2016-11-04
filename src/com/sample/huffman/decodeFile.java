/* Name : Deepika Joseph
 * Wsu ID: R382U353
 * Assignment-3 Part-2
 */

package com.sample.huffman;



import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class decodeFile extends WriteFileWorker {

	private Map<String, Character> map;
	private Map<Character, Integer> frequencyMap;
	private char charToShow;
	private int numOfChars;
	private int idxOfCurrent;
	private boolean scnChar;
	private char ipChar;
	private enum StateCond {
		FIRST_BYTE, DICT, ENCODED_FILE, FINISH
	};
	private StateCond currentState;

	/**
	 * Constructor for our write decoded file worker
	 * @param path the output path
	 */
	public decodeFile(String path) {
		super(path);
		this.charToShow = (char) 0x00;
		this.numOfChars = 0;
		this.idxOfCurrent = 0;
		this.scnChar = false;
		this.frequencyMap = new HashMap<>();
		this.currentState = StateCond.FIRST_BYTE;
	}
public void writeToFile() {
		try {
			fopSt.write(this.charToShow);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void readDictValues(int currentByte) {
		char currentChar = (char) currentByte;
		if (scnChar) {
			frequencyMap.put(currentChar, 0);
			this.ipChar = currentChar;
			this.scnChar = false;
		} else {
			frequencyMap.put(this.ipChar, currentByte);
			this.scnChar = true;
			idxOfCurrent++;
		}

		if (this.idxOfCurrent >= this.numOfChars) {
			ArrayList<huffmanTple> tuples = Decode.convertMapToTuples(frequencyMap);
			huffmanImp.sortHuffmanTuples(tuples);
			huffmanImp.canonizeEncodings(tuples);
			this.map = Decode.convertTuplesToLookupMap(tuples);
			System.out.println("<-----------------------------------Applying Algorithm---------------------------------------->");
			System.out.println(this.map);
			this.currentState = StateCond.ENCODED_FILE;
		}
	}


	private void handleReadFirstByte(int currentByte) {
		this.numOfChars = currentByte;
		this.scnChar = true;
		this.currentState = StateCond.DICT;
	}

	private void handleDecodeByByte(int currentByte) {
		this.bytBuf += huffmanImp.rightPadString(Integer.toBinaryString(currentByte), NUMOFBITS_TO_WRITE);
		int currentLength = 1;

		while (true) {
			try {
				String current = this.bytBuf.substring(0, currentLength);
				Character possibility = this.map.get(current);
				if (possibility != null) {
					if (possibility == '\u0000') {
						this.currentState = StateCond.FINISH;
						break;
					}
					this.bytBuf = this.bytBuf.substring(currentLength);
					this.charToShow = possibility;
					this.writeToFile();
					currentLength = 1;
				} else {
					currentLength++;
				}
			} catch (IndexOutOfBoundsException e) {
				break;
			}
		}
	}


	@Override
	public void doWork(int currentByte) {
		if (this.currentState == StateCond.FINISH) {
			return;
		} else if (this.currentState == StateCond.FIRST_BYTE) {
			this.handleReadFirstByte(currentByte);
		} else if (this.currentState == StateCond.DICT) {
			this.readDictValues(currentByte);
		} else if (this.currentState == StateCond.ENCODED_FILE) {
			this.handleDecodeByByte(currentByte);
		}
	}
}
