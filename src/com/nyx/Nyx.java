package com.nyx.nyx;

// Imports
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class Nyx {
	static boolean isError = false;

	public static void main(String[] args) throws IOException {
		/*
		The main function `parses` (verb used lightly there)
		to check whether the user wants to execute a file
		or open the interactive shell
		 */
		if (args.length == 1) {
			executeScript(args[0]);
		} else if (args.length > 1) {
			System.out.println("Too many arguments!");
			System.out.println("Usage: nyx [file-name]");
		} else {
			interactiveShell();
		}
	}

	private static void interactiveShell() throws IOException {
		/*
		Provides an interactive shell to manipulate code within a terminal
		rather than having to create a script and executing it
		*/
		InputStreamReader command = new InputStreamReader(System.in);
		BufferedReader commandReader = new BufferedReader(command);

		while (true) {
			System.out.println("nyx> ");
			executeCommand(commandReader.readLine());
		}
	}

	private static void executeScript(String filePath) throws IOException {
		/*
		Function for executing a script; split into bytes and execute commands one by one
		 */
		byte[] byteCommands = Files.readAllBytes(Paths.get(filePath));
		executeCommand(new String(byteCommands, Charset.defaultCharset()));

		if (isError) System.exit(65);
	}

	private static void executeCommand(String instruction) {
		/*
		--
		 */
		Scanner scanner = new Scanner(instruction);
		List<Token> tokens = scanner.evaluateTokens();

		// Temporarily printing tokens

		for (Token token : tokens) {
			System.out.println(token);
		}
	}

	private static void reportError(String errorLocation, String errorType, int errorLine) {
		// This method is basic error handling for the time being
		System.err.println("[line "+errorLine +"] Error: " + errorLocation + ": " + errorType);
		isError = true;
	}

	static void error(String errorType, int errorLine) {
		reportError("", errorType, errorLine);
	}


}