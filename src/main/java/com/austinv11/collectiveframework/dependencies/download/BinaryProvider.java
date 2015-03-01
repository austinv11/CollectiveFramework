package com.austinv11.collectiveframework.dependencies.download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

public class BinaryProvider implements IDownloadProvider {
	
	@Override
	public boolean downloadFile(String link, String downloadPath) {
		try {
			URL url = new URL(link);
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			Map<String,List<String>> header = http.getHeaderFields();
			while (isRedirected(header)) {
				link = header.get("Location").get(0);
				url = new URL(link);
				http = (HttpURLConnection) url.openConnection();
				header = http.getHeaderFields();
			}
			InputStream input = http.getInputStream();
			byte[] buffer = new byte[4096];
			int n = -1;
			OutputStream output = new FileOutputStream(new File(downloadPath));
			while ((n = input.read(buffer)) != -1) {
				output.write(buffer, 0, n);
			}
			output.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private static boolean isRedirected( Map<String,List<String>> header ) {
		for(String hv : header.get(null)) {
			if(hv.contains( " 301 " ) || hv.contains( " 302 " )) 
				return true;
		}
		return false;
	}
	
	@Override
	public EnumSet<FileType> getCapabilities() {
		return EnumSet.of(FileType.BINARY);
	}
}
