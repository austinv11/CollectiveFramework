package com.austinv11.collectiveframework.services;

import com.austinv11.collectiveframework.utils.LogicUtils;
import com.austinv11.collectiveframework.utils.StringUtils;
import com.austinv11.collectiveframework.utils.WebUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * This contains helpful APIs with Github.
 */
public class GithubService {
	
	private final String user;
	private final String repo;
	private final String branch;
	
	public GithubService(String user, String repo, String branch) {
		this.user = user;
		this.repo = repo;
		this.branch = branch;
	}
	
	/**
	 * Instantiates with the branch as "master"
	 */
	public GithubService(String user, String repo) {
		this.user = user;
		this.repo = repo;
		branch = "master";
	}
	
	
	public Release[] getReleases() {
		try {
			URL input = new URL("https://api.github.com/repos/"+user+"/"+repo+"/releases");
			HttpURLConnection connection = (HttpURLConnection) input.openConnection();
			connection.setRequestMethod("GET");
			InputStream stream = connection.getInputStream();
			connection.connect();
			String json = StringUtils.stringFromList(StringUtils.getStringsFromStream(stream));
			Gson gson = new Gson();
			return gson.fromJson(json, Release[].class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null; //This shouldn't be reached
	}
	
	/**
	 * Generates a changelog from the given sha
	 * @param sha The sha to start the changelog from
	 * @return The changelog
	 */
	public String generateChangelog(String sha) {
		Commit[] commits = getCommitsToRepo(sha);
		String log = "";
		for (Commit commit : commits) {
			String list;
			String message = commit.commit.message.toLowerCase();
			if (LogicUtils.xor(message.contains("add"), message.contains("remov"))) {
				if (message.contains("add"))
					list = "+";
				else
					list = "-";
			} else
				list = "*";
			log = log+list+"\t"+commit.commit.message.replaceFirst("\n", "")+"("+commit.commit.author.name+" SHA:"+commit.sha+")\n";
		}
		return log;
	}
	
	/**
	 * Generates a changelog
	 * @return The changelog
	 */
	public String generateChangelog() {
		return generateChangelog("master");
	}
	
	/**
	 * Retrieves all the commits to the repo
	 * @param start The sha representing where to start listing commits from
	 * @return The commits
	 */
	public Commit[] getCommitsToRepo(String start) {
		try {
			URL input = new URL("https://api.github.com/repos/"+user+"/"+repo+"/commits");
			HttpURLConnection connection = (HttpURLConnection) input.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("sha", branch);
			InputStream stream = connection.getInputStream();
			connection.connect();
			String json = StringUtils.stringFromList(StringUtils.getStringsFromStream(stream));
			Gson gson = new Gson();
			return gson.fromJson(json, Commit[].class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null; //This shouldn't be reached
	}
	
	/**
	 * Retrieves all the commits to the repo
	 * @return The commits
	 */
	public Commit[] getCommitsToRepo() {
		return getCommitsToRepo("master");
	}
	
	/**
	 * Gets the commit with the corresponding sha
	 * @param sha The sha
	 * @return The commit
	 */
	public Commit getCommitToRepo(String sha) {
		try {
			URL input = new URL("https://api.github.com/repos/"+user+"/"+repo+"/commits/"+sha);
			HttpURLConnection connection = (HttpURLConnection) input.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("sha", branch);
			InputStream stream = connection.getInputStream();
			connection.connect();
			String json = StringUtils.stringFromList(StringUtils.getStringsFromStream(stream));
			Gson gson = new Gson();
			return gson.fromJson(json, Commit.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null; //This shouldn't be reached
	}
	
	/**
	 * Reads the contents of the file at the given path relative to the repo
	 * @param path The path
	 * @return The contents
	 * @throws IOException
	 */
	public List<String> readPath(String path) throws IOException {
		return WebUtils.readURL(constructUrl(true)+path);
	}
	
	/**
	 * Constructs the url this service is directed at
	 * @param raw Whether to use the raw version of the url
	 * @return The url
	 */
	public String constructUrl(boolean raw) {
		return "https://"+(raw ? "raw." : "")+"github.com/"+user+"/"+repo+"/"+branch+"/";
	}
	
	public static class Release {
		public String url;
		public String html_url;
		public String assets_url;
		public String upload_url;
		public String tarball_url;
		public String zipball_url;
		public int id;
		public String tag_name;
		public String target_commitish;
		public String name;
		public String body;
		public boolean draft;
		public boolean prerelease;
		public String created_at;
		public String published_at;
		public AuthorExtended author;
		public Asset[] assets;
		
		@Override
		public String toString() {
			return name+" -"+tag_name;
		}
	}
	
	public static class Asset {
		public String url;
		public String browser_download_url;
		public int id;
		public String name;
		public String label;
		public String state;
		public String content_type;
		public int size;
		public int download_count;
		public String created_at;
		public String updated_at;
		public AuthorExtended uploader;
	}
	
	public static class Commit {
		public String url;
		public String sha;
		public String html_url;
		public String comments_url;
		public CommitInfo commit;
		public int comment_count;
		public AuthorExtended author;
		public AuthorExtended committer;
		public SHAInfo[] parents;
		
		@Override
		public String toString() {
			return commit.message.split("\n")[0]+"@"+sha;
		}
	}
	
	public static class CommitInfo {
		public String url;
		public Author author;
		public Author committer;
		public String message;
	}
	
	public static class Author {
		public String name;
		public String email;
		public String date;
	}
	
	public static class SHAInfo {
		public String url;
		public String sha;
	}
	
	public static class AuthorExtended {
		public String login;
		public int id;
		public String avatar_url;
		public String gravatar_id;
		public String url;
		public String html_url;
		public String followers_url;
		public String following_url;
		public String gists_url;
		public String starred_url;
		public String subscriptions_url;
		public String organizations_url;
		public String repos_url;
		public String events_url;
		public String received_events_url;
		public String type;
		public boolean site_admin;
	}
}
