package com.austinv11.collectiveframework.utils;

/**
 * Enum class for minecraft color codes
 */
public enum Colors {
	
	BLACK("0"),
	DARK_BLUE("1"),
	DARK_GREEN("2"),
	DARK_AQUA("3"),
	DARK_RED("4"),
	DARK_PURPLE("5"),
	GOLD("6"),
	GRAY("7"),
	DARK_GRAY("8"),
	BLUE("9"),
	GREEN("a"),
	AQUA("b"),
	RED("c"),
	LIGHT_PURPLE("d"),
	YELLOW("e"),
	WHITE("f"),
	MAGIC("k"),
	BOLD("l"),
	STRIKETHROUGH("m"),
	UNDERLINE("n"),
	ITALIC("o"),
	RESET("r");
	
	public String code;
	
	public static final char[] COLOR_CHARS = new char[]{'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f','k','l','m','n','o','r'};
	public static final char COLOR_CODE_CHAR = '§';
	
	Colors(String code) {
		this.code = code;
	}
	
	@Override
	public String toString() {
		return COLOR_CODE_CHAR+code;
	}
	
	/**
	 * Replaces the specified character with the real color character in a string (if it returns a valid color)
	 * @param colorChar The alternate color character
	 * @param string The string
	 * @return The modified string
	 */
	public static String replaceAlternateColorChar(char colorChar, String string) {
		char[] stringCharArray = string.toCharArray();
		for (int i = 0; i < stringCharArray.length; i++) {
			char c = stringCharArray[i];
			if (c == colorChar)
				if (stringCharArray.length > i+1) {
					int index = ArrayUtils.indexOf(COLOR_CHARS, stringCharArray[i+1]);
					if (index != -1)
						stringCharArray[i] = COLOR_CODE_CHAR;
				}
		}
		return String.valueOf(stringCharArray);
	}
	
	/**
	 * Replaces '&' with the real color character in a string (if it returns a valid color)
	 * @param string The string
	 * @return The modified string
	 */
	public static String replaceAlternateColorChar(String string) {
		return replaceAlternateColorChar('&', string);
	}
}
