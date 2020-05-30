package com.nyx.nyx;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
// static imports are frowned upon in the Java community but it saves development time
import static com.nyx.nyx.TokenIdentifier.*;

class Scanner {
	/*
	docstring here
	 */
	private final List<Token> tokens = new ArrayList<>();
	private final String source;

	// Variables used for the offsets of the string format of the lexeme being analysed
	private int line_number = 1;
	private int initial = 0;
	private int current = 0;
}