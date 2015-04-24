package com.austinv11.collectiveframework.minecraft.utils;

import com.austinv11.collectiveframework.minecraft.CollectiveFramework;
import com.austinv11.collectiveframework.utils.StringUtils;
import com.austinv11.collectiveframework.utils.WebUtils;
import com.google.gson.Gson;
import net.minecraftforge.common.MinecraftForge;

/**
 * An easy to use version checker which utilizes curseforge for versioning
 */
public class CurseVersionChecker {
	
	public static final String BASE_API_URL = "http://widget.mcf.li/mc-mods/minecraft/@MOD@.json";
	
	private final String curseId;
	private final String name;
	private final String url;
	
	private CurseWidgetResponse check;
	
	private boolean didCheck = false;
	
	/**
	 * @param curseId The project slug/id used in curseforge urls
	 * @param name The name of the file of the currently running version of the mod
	 */
	public CurseVersionChecker(String curseId, String name) {
		this.curseId = curseId;
		this.name = name;
		url = BASE_API_URL.replace("@MOD@", curseId);
	}
	
	private void doCheckIfNescessary() {
		if (check == null) {
			try {
				Gson gson = new Gson();
				String response = StringUtils.stringFromList(WebUtils.readURL(url));
				check = gson.fromJson(response, CurseWidgetResponse.class);
			} catch (Exception e) {
				CollectiveFramework.LOGGER.error("A problem has occurred attempting to retrieve mod update info");
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * This checks if an update is available
	 * @return True if an update is available
	 */
	public boolean isUpdateAvailable() {
		doCheckIfNescessary();
		return !check.download.name.equals(name) && check.download.version.equals(MinecraftForge.MC_VERSION);
	}
	
	/**
	 * Gets the direct url to the latest file
	 * @return The url
	 */
	public String getDownloadUrl() {
		doCheckIfNescessary();
		return "http://minecraft.curseforge.com/mc-mods/"+curseId+"/files/"+check.download.id+"/download";
	}
	
	/**
	 * Gets the file name of the latest file
	 * @return The file name
	 */
	public String getUpdateFileName() {
		doCheckIfNescessary();
		return check.download.name;
	}
	
	/**
	 * Gets the curseforge project url
	 * @return The url
	 */
	public String getProjectUrl() {
		doCheckIfNescessary();
		return check.project_url;
	}
	
	private static class CurseWidgetResponse {
		public String title;
		public String game;
		public String category;
		public String url;
		public String thumbnail;
		public String[] authors;
		public Downloads downloads;
		public int favorites;
		public int likes;
		public String updated_at;
		public String created_at;
		public String project_url;
		public String release_type;
		public String license;
		public Download download;
		//No versions
	}
	
	private static class Downloads {
		public int monthly;
		public int total;
	}
	
	private static class Download {
		public int id;
		public String url;
		public String name;
		public String type;
		public String version;
		public int downloads;
		public String created_at;
	}
}
