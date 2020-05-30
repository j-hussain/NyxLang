package com.nyx.nyx;

class Token {
	/*
	Class to handle tokens and their attributes; some variable names are self-explanatory.
	Lexeme -> a collection of characters representing something of significance to the language (ie variable)
	This class helps with the error tracking as well
	 */
	final int line_number;
	final String lexeme;
	final Object literal;
	final TokenIdentifier type;

	/*
	Certain Token implementations consider the length of the lexeme, and the offset from the beginning of the
	source file to the beginning of the lexeme.
	 */

	Token(int line_number, String lexeme, Object literal, TokenIdentifier type) {
		this.line_number = line_number;
		this.lexeme = lexeme;
		this.literal = literal;
		this.type = type;
	}

	public String asString() {
		return type + " " + lexeme + " " + literal;
	}
}