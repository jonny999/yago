package FIIT.VI.YAGO.util;

/**
 * Util class for replace 
 * @author mm
 *
 */
public class Replacer {

	/**
	 * Replace _ in string
	 * @param name string for replace
	 * @return String with replace characters
	 */
	public static String replaceCharacter(String name) {
		return name.replaceAll("_", " ");
	}
}
