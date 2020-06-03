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

	// The next few methods before evaluateToken() are helper methods to aid the syntactic analysis
	private char increment() {
		// Subprocedure to move to the next character in the source file and return it.
		current++;
		return instruction.charAt(current-1);
	}

	private void createToken(TokenIdentifier type) {
		createToken(null, type);
	}

	private void createToken(Object literal, TokenIdentifier type) {
		String text = source.substring(intial, current);
		// Token(line no, lexeme, literal, type
		tokens.add(new Token(line_number, text, literal, type));
	}

	private void evaluateTokens() {
		/*
		This subprocedure considers the character of the lexeme, and creates relevant tokens based on
		 */
		char character = increment();
		switch (character) {
			case ';': {
				createToken(SEMICOLON);
				break;
			}
			case '.': {
				createToken(DOT);
				break;
			}
			case ',': {
				createToken(COMMA);
				break;
			}
			case '+': {
				createToken(PLUS);
				break;
			}
			case '-': {
				createToken(MINUS);
				break;
			}
			case '(': {
				createToken(OPEN_BRACKET);
				break;
			}
			case ')': {
				createToken(CLOSE_BRACKET);
				break;
			}
			case '{': {
				createToken(OPEN_BRACE);
				break;
			}
			case '}': {
				createToken(CLOSE_BRACE);
				break;
			}
			case '*': {
				createToken(ASTERISK);
				break;
			}
			default:
				Nyx.error(line_number, "Unexpected character.");
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