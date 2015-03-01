package com.austinv11.collectiveframework.dependencies.download;

import com.austinv11.collectiveframework.utils.StringUtils;
import com.austinv11.collectiveframework.utils.WebUtils;

import java.io.FileWriter;
import java.util.EnumSet;

/**
 * General plain text download provider
 */
public class PlainTextProvider implements IDownloadProvider {
	
	@Override
	public boolean downloadFile(String url, String downloadPath) {
		try {
			String download = StringUtils.stringFromList(WebUtils.readURL(url));
			FileWriter writer = new FileWriter(downloadPath);
			writer.write(download);
			writer.close();
			writer.flush();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public EnumSet<FileType> getCapabilities() {
		return EnumSet.of(FileType.PLAIN_TEXT);
	}
}
