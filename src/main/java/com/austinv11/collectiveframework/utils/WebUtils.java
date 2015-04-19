package com.austinv11.collectiveframework.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class for interacting with teh interwebz
 */
public class WebUtils {
	
	/**
	 * Method for reading the contents of a web page
	 * @param url The url of the web page
	 * @return Contents, each list entry represents a new line
	 * @throws IOException
	 */
	public static List<String> readURL(String url) throws IOException {
		return readURL(url, 5000);
	}
	
	/**
	 * Method for reading the contents of a web page
	 * @param url The url of the web page
	 * @param timeout The connection timeout
	 * @return Contents, each list entry represents a new line
	 * @throws IOException
	 */
	public static List<String> readURL(String url, int timeout) throws IOException {
		if (!url.contains("http://") && !url.contains("https://"))
			url = "http://"+url;
		URL input = new URL(url);
		URLConnection connection = input.openConnection();
		connection.setConnectTimeout(timeout);
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		connection.connect();
		String temp;
		List<String> read = new ArrayList<String>();
		while ((temp = in.readLine()) != null)
			read.add(temp);
		in.close();
		return read;
	}
	
	private static List<String> request(String url, int timeout, String method, Map header) throws IOException {
		if (!url.contains("http://") && !url.contains("https://"))
			url = "http://"+url;
		URL input = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) input.openConnection();
		connection.setConnectTimeout(timeout);
		connection.setRequestMethod(method);
		if (header != null) {
			for (Object key : header.keySet())
				connection.setRequestProperty(String.valueOf(key), String.valueOf(header.get(key)));
		}
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		connection.connect();
		String temp;
		List<String> read = new ArrayList<String>();
		while ((temp = in.readLine()) != null)
			read.add(temp);
		in.close();
		return read;
	}
	
	/**
	 * Method to make a POST request to the specified url
	 * @param url URL to make the request on
	 * @param timeout The request timeout
	 * @param header The request header (can be null)
	 * @return Contents, each list entry represents a new line
	 * @throws IOException
	 */
	public static List<String> post(String url, int timeout, Map header) throws IOException {
		return request(url, timeout, "POST", header);
	}
	
	/**
	 * Method to make a GET request to the specified url
	 * @param url URL to make the request on
	 * @param timeout The request timeout
	 * @param header The request header (can be null)
	 * @return Contents, each list entry represents a new line
	 * @throws IOException
	 */
	public static List<String> get(String url, int timeout, Map header) throws IOException {
		return request(url, timeout, "GET", header);
	}
	
	/**
	 * Simple helper method to read a github source file
	 * @param repo Repo name
	 * @param path Path in the repo
	 * @return The contents
	 * @throws IOException
	 * @deprecated See {@link com.austinv11.collectiveframework.services.GithubService}
	 */
	@Deprecated
	public static List<String> readGithub(String repo, String path) throws IOException{
		return readGithub(repo, "master", path);
	}
	
	/**
	 * Simple helper method to read a github source file
	 * @param repo Repo name
	 * @param branch The branch of the repo    
	 * @param path Path in the repo
	 * @return The contents
	 * @throws IOException
	 * @deprecated See {@link com.austinv11.collectiveframework.services.GithubService}
	 */
	@Deprecated
	public static List<String> readGithub(String repo, String branch, String path) throws IOException{
		return readURL("https://raw.github.com/austinv11/"+repo+"/"+branch+"/"+path);
	}
}
