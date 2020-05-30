package com.nyx.nyx;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
// static imports are frowned upon in the Java community but it saves development time
import static com.nyx.nyx.TokenIdentifier.*;

class Scanner {
	/*
	Scanner class for lexical analysis

	 */
	private final List<Token> tokens = new ArrayList<>();
	private final String instruction;

	// Variables used for the offsets of the string format of the lexeme being analysed
	private int line_number = 1;
	private int initial = 0;
	private int current = 0;

	private void evaluateTokens() {
		char character = increment();
		switch (character) {
			case ';':
				createToken(SEMICOLON);
				break;

		}
	}

	Scanner(String instruction) {
		this.instruction = instruction;
	}

	List<Token> evaluateTokens() {
		while (!endOfLine) {
			initial = current;
			evaluateTokens();
		}
		// Token(line no, lexeme, literal, type
		tokens.add(new Token(line_number, "", null, EOF));
		return tokens;
	}

	private boolean endOfLine() {
		return current >= instruction.length();
	}



}