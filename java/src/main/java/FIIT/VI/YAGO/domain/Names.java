package FIIT.VI.YAGO.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Domain entities represent of names
 * @author mm
 *
 */
public class Names {

	/** REGEX string for $1 name and $2 language*/
	private static final String REGEX_NAME = "\"(.*)\"@(.*)\\b";
	private static final Pattern PATTERN_NAME = Pattern.compile(REGEX_NAME);

	/** Original expression*/
	private String expression;
	
	/** Language of alternative name*/
	private String language;
	
	/** Alternative name*/
	private String name;

	public Names() {
	}
	
	/**
	 * Parse names of names expression
	 * @param name names with language
	 */
	public Names(String name) {
		this.setExpression(name);
		Matcher m = PATTERN_NAME.matcher(name);
		if (m.find()) {
			this.language = m.group(2);
			this.name = m.group(1);
		}
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}
}
