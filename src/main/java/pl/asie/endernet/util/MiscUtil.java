package pl.asie.endernet.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MiscUtil {
	public static BufferedReader getBufferedReader(InputStream s) {
		return new BufferedReader(new InputStreamReader(s));
	}
}
