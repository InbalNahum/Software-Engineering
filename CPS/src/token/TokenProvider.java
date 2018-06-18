package token;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import common.CpsGlobals;


public class TokenProvider {

	public int getCommunicateToken() throws IOException {
		String filePath = getClass().getResource(CpsGlobals.tokenFile).getPath();
		Scanner scanner = new Scanner(new File(filePath));
        int token = scanner.nextInt();
		scanner.close();
		int nextToken = token+1;
		if(token == Integer.MAX_VALUE) {
			nextToken = 1;
		}
		PrintWriter pw = new PrintWriter(filePath);
		pw.print("");
		pw.printf("%d", nextToken);
		pw.close();
		return token;
	}
}

