package com.austinv11.collectiveframework.dependencies.download;

/**
 * These represent the abilities of a download provider
 */
public enum FileType {
	BINARY, PLAIN_TEXT, OTHER;
	
	private String type;
	
	/**
	 * Gets a filetype enum for one not listed
	 * @param otherType File type
	 * @return The OTHER file type with additional data
	 */
	public static FileType getFileTypeForOther(String otherType) {
		FileType t = FileType.OTHER;
		t.type = otherType;
		return t;
	}
	
	@Override
	public String toString() {
		if (this.equals(OTHER))
			return type;
		return super.toString();
	}
}
