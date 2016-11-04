/* Name : Deepika Joseph
 * Wsu ID: R382U353
 * Assignment-3 Part-2
 */


package com.sample.huffman;

import java.util.HashMap;
import java.util.Map;

public class FrequencyMeasure implements fileReader {

	Map<Character, Integer> map;

	public FrequencyMeasure() {
		this.map = new HashMap<>();
	}

	@Override
	public void doWork(int crntByte) {
		char crntChar = (char) crntByte;

		if (map.get(crntChar) == null) {
			map.put(crntChar, 1);
		} else {
			map.put(crntChar, map.get(crntChar) + 1);
		}
	}
}
