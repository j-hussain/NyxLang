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
	The purpose of the class is to have methods which control the inner workings of the interpreter.
	It handles the creation of Tokens, and their evaluation
	 */
	private final List<Token> tokens = new ArrayList<>();
	private final String instruction;
	// Variables used for the offsets of the string format of the lexeme being analysed
	private int line_number = 1;
	private int initial = 0;
	private int current = 0;
	// HashMap for tying reserved words to functions
	private static final Map<String, TokenIdentifier> keywords;
	static {
		keywords = new HashMap<>();
		keywords.put("while", WHILE);
		keywords.put("var", VAR);
		keywords.put("true", TRUE);
		keywords.put("false", FALSE);
		keywords.put("super", SUPER);
		keywords.put("return", RETURN;
		keywords.put("print", PRINT);
		keywords.put("or", OR);
		keywords.put("null", NULL);
		keywords.put("if", IF);
		keywords.put("for", FOR);
		keywords.put("else", ELSE);
		keywords.put("class", CLASS);
		keywords.put("and", AND);
	}
	// A series of helper methods to aid the syntactic analysis
	private char increment() {
		// Subprocedure to move to the next character in the source file and return it.
		// Ensures evaluateTokens() doesn't run forever
		current++;
		return instruction.charAt(current-1);
	}

	private boolean endOfLine() {
		return current >= instruction.length();
	}

	private void createToken(TokenIdentifier type) {
		createToken(null, type);
	}

	private void createToken(Object literal, TokenIdentifier type) {
		String text = source.substring(initial, current);
		// Token(line no, lexeme, literal, type
		tokens.add(new Token(line_number, text, literal, type));
	}

	private void matchChar(String character) {
		if (endOfLine()) {
			return false;
		}
		if (instruction.charAt(current) != character) {
			return false;
		}
		current++;
		return true;
	}

	private void evaluateIdentifier() {
		while (isAlphaNumeric(checkChar())) {
			increment()
		}
		// We check the instruction to see if there's an identifier in the string
		// If not, we create a token as usual
		String parseInstruction = instruction.substring(initial, current)
		TokenIdentifier type = keywords.get(instruction);
		if (type == null) {
			type = IDENTIFIER;
		}
		createToken(type);
	}

	private boolean isAlphaNumeric(char c) {
		return evaluateAlpha(c) || evaluateDigit(c)
	}

	private boolean evaluateAlpha(char c) {
		return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_';
	}

	private char checkChar() {
		// Similar to the increment() method however it just peaks the next character
		// the matchChar() method is similar, but combines this lookahead function
		// with the main script
		if (endOfLine()) return '\0';
		return instruction.charAt(current);
	}

	private void evaluateString() {
		while (!endOfLine() && checkChar() != '"') {
			if (checkChar() == '\n') {
				line_number++;
			}
			increment();
		}
		if (endOfLine()) {
			Nyx.error("Unterminated string.", line_number);
			return;
		}
		increment();
		// This line removes the quotation marks from the string
		String stringContent = instruction.substring(initial+1, current-1);
		createToken(stringContent, STRING);
	}

	private boolean evaluateDigit(char x) {
		// Returns boolean expression to see if it's a digit
		return c <= '9' && c >= '0';
	}

	private void evaluateNumber() {
		while (evaluateDigit(increment())) {
			increment();
		}
		if (evaluateDigit(nextChar()) && checkChar() == '.') {
			increment();
			while (evaluateDigit(increment())) {
				increment();
			}
		}
		createToken(Double.parseDouble(instruction.substring(initial, current)), NUMBER);
	}

	private void nextChar() {
		if (current+1 >= instruction.length()) {
			return '\0';
		}
		return instruction.charAt(current+1);
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
			case '>': {
				createToken(matchChar('=') ? GREATER_THAN_OR_EQUAL : GREATER_THAN);
				break;
			}
			case '<': {
				createToken(matchChar('=') ? LESS_THAN_OR_EQUAL : LESS_THAN);
				break;
			}
			case '=': {
				createToken(matchChar("=") ? EQUAL_TO : EQUAL);
				break;
			}
			case '!': {
				createToken(matchChar('=') ? NOT_EQUAL_TO : NOT);
				break;
			}
			case '\n': {
				line_number++;
				break;
			}
			// \t is whitespace, the interpreter will ignore it
			case '\t': {
				break;
			}
			case '\r':
			case '"': {
				evaluateString();
				break;
			}
			// Handling a forward slash is somewhat different to the other characters
			// as two consecutive slashes is how the language has comments (it is the defacto
			// method of commenting after all)
			case '/' : {
				if (matchChar('/') {
					while (!endOfLine() && checkChar() != '\n') {
						increment();
				} else {
						createToken(FORWARD_SLASH);
					}}}
			case 'o': {
				if (checkChar() == 'r') {
					createToken(OR);
				}
			}
			// For characters the interpreter doesn't recognise (ie: "output@")
			default: {
				if (evaluateDigit(character)) {
					evaluateNumber();
				} else if {
					evaluateIdentifier();
				} else {
				Nyx.error(line_number, "Unexpected character.");
				}
				break;
		}
	}
	}

	Scanner(String instruction) {
		this.instruction = instruction;
	}

	List<Token> evaluateTokens() {
		while (!endOfLine()) {
			initial = current;
			evaluateTokens();
		}
		// Token(line no, lexeme, literal, type
		tokens.add(new Token(line_number, "", null, EOF));
		return tokens;
	}

}