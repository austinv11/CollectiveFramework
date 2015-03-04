package com.austinv11.collectiveframework.dependencies.download;

import com.austinv11.collectiveframework.utils.Logger;
import com.austinv11.collectiveframework.utils.StringUtils;
import com.austinv11.collectiveframework.utils.WebUtils;
import com.google.gson.Gson;
import net.minecraftforge.common.MinecraftForge;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Pseudo-provider for modpack installation
 */
public class ModpackProvider  {
	
	//Firefox UA Mozilla/5.0 (Windows NT 6.3; rv:36.0) Gecko/20100101 Firefox/36.0
	
	/**
	 * Determines whether to overwrite based on a modpack.xml
	 * @param xmlFile The file to parse
	 * @return Whether to overwrite
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	public boolean doOverwrite(File xmlFile) throws ParserConfigurationException, IOException, SAXException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(xmlFile);
		doc.getDocumentElement().normalize();
		return doc.getDocumentElement().hasAttribute("overwrite") && Boolean.parseBoolean(doc.getDocumentElement().getAttribute("overwrite"));
	}
	
	/**
	 * Parses an xml file following the "Modpack Schema"
	 * @param xmlFile The file to parse
	 * @return The mods
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	public List<IModpackFile> parseModpackXML(File xmlFile) throws ParserConfigurationException, IOException, SAXException {
		ArrayList<IModpackFile> mods = new ArrayList<IModpackFile>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(xmlFile);
		doc.getDocumentElement().normalize();
		
		//Curse mods
		NodeList curseNodeList = doc.getElementsByTagName("cursemod");
		Logger.info(curseNodeList.getLength()+" Curse mod(s) found!");
		for (int i = 0; i < curseNodeList.getLength(); i++) {
			Node node = curseNodeList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				String name = element.getAttribute("id");
				String slug = element.getElementsByTagName("slug").item(0).getTextContent();
				String mcversion = element.getElementsByTagName("mcversion").getLength() > 0 ? element.getElementsByTagName("mcversion").item(0).getTextContent() : MinecraftForge.MC_VERSION;
				ReleaseType releaseType = element.getElementsByTagName("releasetype").getLength() > 0 ? ReleaseType.fromString(element.getElementsByTagName("releasetype").item(0).getTextContent()) : ReleaseType.RELEASE;
				int id = element.getElementsByTagName("id").getLength() > 0 ? Integer.valueOf(element.getElementsByTagName("id").item(0).getTextContent()) : -1;
				String fileName = element.getElementsByTagName("name").getLength() > 0 ? element.getElementsByTagName("name").item(0).getTextContent() : "@REPLACE@";
				mods.add(new CurseMod(name, slug, mcversion, releaseType, id, fileName));
			}
		}
		
		//Adfly mods
		NodeList adflyNodeList = doc.getElementsByTagName("adflymod");
		Logger.info(adflyNodeList.getLength()+" Adfly-type mod(s) found!");
		for (int i = 0; i < adflyNodeList.getLength(); i++) {
			Node node = adflyNodeList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				String name = element.getAttribute("id");
				String link = element.getElementsByTagName("link").item(0).getTextContent();
				String fileName = element.getElementsByTagName("name").item(0).getTextContent();
				mods.add(new AdflyMod(name, link, fileName));
			}
		}
		
		//Other mods
		NodeList modNodeList = doc.getElementsByTagName("mod");
		Logger.info(modNodeList.getLength()+" Other mod(s) found!");
		for (int i = 0; i < modNodeList.getLength(); i++) {
			Node node = modNodeList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				String name = element.getAttribute("id");
				String link = element.getElementsByTagName("link").item(0).getTextContent();
				String fileName = element.getElementsByTagName("name").item(0).getTextContent();
				mods.add(new OtherMod(name, link, fileName));
			}
		}
		
		//Plaintext files
		NodeList plainNodeList = doc.getElementsByTagName("plainfile");
		Logger.info(plainNodeList.getLength()+" Other plain file(s) found!");
		for (int i = 0; i < plainNodeList.getLength(); i++) {
			Node node = plainNodeList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				String name = element.getAttribute("id");
				String link = element.getElementsByTagName("link").item(0).getTextContent();
				String fileName = element.getElementsByTagName("path").item(0).getTextContent();
				mods.add(new OtherFile(name, link, fileName, FileType.PLAIN_TEXT));
			}
		}
		
		//Binary files
		NodeList binaryNodeList = doc.getElementsByTagName("binaryfile");
		Logger.info(binaryNodeList.getLength()+" Other binary file(s) found!");
		for (int i = 0; i < binaryNodeList.getLength(); i++) {
			Node node = binaryNodeList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				String name = element.getAttribute("id");
				String link = element.getElementsByTagName("link").item(0).getTextContent();
				String fileName = element.getElementsByTagName("path").item(0).getTextContent();
				mods.add(new OtherFile(name, link, fileName, FileType.BINARY));
			}
		}
		return mods;
	}
	
	/**
	 * Installs mods from a {@link com.austinv11.collectiveframework.dependencies.download.ModpackProvider.IModpackFile} list
	 * @param mods Mods to install
	 * @param overwrite Whether to override existing files under the same filename
	 */
	public void installMods(List<IModpackFile> mods, boolean overwrite) {
		for (IModpackFile mod : mods) {
			Logger.info("Installing "+mod.getType()+" '"+mod.getName()+"'...");
			if (new File(formatPath(mod.getPath())).exists() && !overwrite)
				Logger.info("Skipping "+mod.getType()+" '"+mod.getName()+"', it already exists!");
			boolean didDownload = mod.install();
			if (didDownload)
				Logger.info("Installed "+mod.getType()+" '"+mod.getName()+"'!");
			else
				Logger.warn("Failed to install "+mod.getType()+" '"+mod.getName()+"'!");
		}
	}
	
	protected static String formatPath(String path) {
		if (path.contains("./") && path.startsWith("./"))
			return path;
		return "./"+path;
	}
	
	/**
	 * An interface to represent a file to be downloaded based on the modpack.xml
	 */
	public static interface IModpackFile {
		
		public boolean install();
		
		public String getType();
		
		public String getPath();
		
		public String getName();
	}
	
	/**
	 * An object representing a non-mod file
	 */
	public static class OtherFile implements IModpackFile {
		
		public String name;
		public String url;
		public String fileName;
		public String path;
		public FileType type;
		
		public OtherFile(String name, String url, String fileName, FileType type) {
			this.name = name;
			this.url = url;
			this.fileName = ModpackProvider.formatPath(fileName);
			this.type = type;
		}
		
		@Override
		public boolean install() {
			if (type == FileType.BINARY) {
				return new BinaryProvider().downloadFile(url, getPath());
			} else if (type == FileType.PLAIN_TEXT) {
				return new PlainTextProvider().downloadFile(url, getPath());
			}
			return false;
		}
		
		@Override
		public String getType() {
			return "file";
		}
		
		@Override
		public String getPath() {
			return this.fileName;
		}
		
		@Override
		public String getName() {
			return name;
		}
	}
	/**
	 * An object representing a mod not on curse
	 */
	public static class OtherMod implements IModpackFile {
		
		public String name;
		public String url;
		public String fileName;
		
		public OtherMod(String name, String url, String fileName) {
			this.name = name;
			this.url = url;
			this.fileName = fileName;
		}
		
		@Override
		public boolean install() {
			return new BinaryProvider().downloadFile(url, getPath());
		}
		
		@Override
		public String getType() {
			return "mod";
		}
		
		@Override
		public String getPath() {
			return "./mods/"+this.fileName;
		}
		
		@Override
		public String getName() {
			return name;
		}
	}
	
	/**
	 * An object representing a mod using adfly or similar
	 */
	public static class AdflyMod implements IModpackFile {
		
		public String name;
		public String url;
		public String fileName;
		
		public AdflyMod(String name, String url, String fileName) {
			this.name = name;
			this.url = url;
			this.fileName = fileName;
		}
		
		@Override
		public boolean install() {
			Logger.info("Redirecting you to "+url);
			try {
				Desktop.getDesktop().browse(new URI(url));
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileFilter(new FileNameExtensionFilter("Mod files", "jar", "zip"));
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				int result = fileChooser.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					return selectedFile.renameTo(new File(getPath()));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}
		
		@Override
		public String getType() {
			return "adfly mod";
		}
		
		@Override
		public String getPath() {
			return "./mods/"+this.fileName;
		}
		
		@Override
		public String getName() {
			return name;
		}
	}
	
	/**
	 * An object representing a mod on curse
	 */
	public static class CurseMod implements IModpackFile {
		
		public String name;
		public String slug;
		public String mcversion;
		public ReleaseType releasetype;
		public int id;
		public String fileName;
		public JSONCurseResponse curseResponse;
		public String url;
		
		public CurseMod(String name, String slug, String mcversion, ReleaseType releaseType, int id, String fileName) {
			this.name = name;
			this.slug = slug;
			this.mcversion = mcversion;
			this.releasetype = releaseType;
			this.id = id;
			this.fileName = fileName;
			parseCurseJson();
			if (id != -1) {
				url = "http://minecraft.curseforge.com/mc-mods/"+slug+"/files/"+id+"/download";
			} else {
				for (JSONCurseResponse.JSONDownload download : curseResponse.versions.get(mcversion)) {
					if (download.version.equalsIgnoreCase(mcversion)) {
						url = "http://minecraft.curseforge.com/mc-mods/"+slug+"/files/"+download.id+"/download";
						break;
					}
				}
			}
			if (fileName.equals("@REPLACE@")) {
				fileName = curseResponse.files.get(id+"").name;
			}
		}
		
		public void parseCurseJson() {
			Gson gson = new Gson();
			try {
				curseResponse = gson.fromJson(StringUtils.stringFromList(WebUtils.readURL("http://widget.mcf.li/mc-mods/minecraft/"+slug+".json")), JSONCurseResponse.class);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public boolean install() {
			return new BinaryProvider().downloadFile(this.url, getPath());
		}
		
		@Override
		public String getType() {
			return "curse mod";
		}
		
		@Override
		public String getPath() {
			return "./mods/"+this.fileName;
		}
		
		@Override
		public String getName() {
			return name;
		}
	}
	
	/**
	 * Type of release the mod is classified as in curse
	 */
	public static enum ReleaseType {
		RELEASE,BETA,ALPHA;
		
		public static ReleaseType fromString(String s) {
			if (s != null) {
				if (s.equalsIgnoreCase("beta"))
					return BETA;
				if (s.equalsIgnoreCase("alpha"))
					return ALPHA;
			}
			return RELEASE;
		}
	}
	
	public static class JSONCurseResponse {
		String name;
		String game;
		String category;
		String url;
		String thumbnail;
		String[] authors;
		JSONDownloads downloads;
		int favorites;
		int likes;
		String updated_at;
		String created_at;
		String project_url;
		String release_type;
		String license;
		JSONDownload download;
		Map<String, JSONDownload[]> versions;
		Map<String, JSONDownload> files;
		
		public static class JSONDownloads {
			int monthly;
			int total;
		}
		
		public static class JSONDownload {
			int id;
			String url;
			String name;
			String type;
			String version;
			String downloads;
			String created_at;
		}
	}
}
