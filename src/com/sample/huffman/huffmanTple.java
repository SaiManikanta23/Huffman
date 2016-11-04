/* Name : Deepika Joseph
 * Wsu ID: R382U353
 * Assignment-3 Part-2
 */

package com.sample.huffman;

public class huffmanTple {

	public char ltr;
	public String showCase;

	public huffmanTple(char ltr, String showCase) {
		this.ltr = ltr;
		this.showCase = showCase;
	}

	@Override
	public String toString() {
		return this.ltr + " => " + this.showCase;
	}
}
