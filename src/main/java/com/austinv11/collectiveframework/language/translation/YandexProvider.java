package com.austinv11.collectiveframework.language.translation;

import com.austinv11.collectiveframework.utils.StringUtils;
import com.austinv11.collectiveframework.utils.WebUtils;
import com.google.gson.Gson;

import java.io.IOException;

/**
 * Yandex.net translation provider
 */
public class YandexProvider implements ITranslationProvider {
	
	protected static final String API_KEY = "trnsl.1.1.20150228T025035Z.3525a3f37289aaaa.4964142c63fd40463bc91ea39e42363dd16a6a91";
	
	@Override
	public String getProviderName() {
		return "Yandex";
	}
	
	@Override
	public String translate(String text, String fromLang, String toLang) throws TranslationException, QueryLimitException, IOException {
		return translate(text, fromLang+"-"+toLang);
	}
	
	@Override
	public String translate(String text, String toLang) throws TranslationException, QueryLimitException, IOException {
		text = text.replace(" ", "+");
		Gson gson = new Gson();
		JSONTranslateResponse response = gson.fromJson(StringUtils.stringFromList(WebUtils.readURL("https://translate.yandex.net/api/v1.5/tr.json/translate?key="+API_KEY+"&lang="+toLang+"&text="+text)), JSONTranslateResponse.class);
		switch (response.code) {
			case 403:
			case 404:
				throw new QueryLimitException(this);
			case 413:
				throw new TranslationException(TranslationException.ErrorTypes.TEXT_TOO_LONG);
			case 422:
				throw new TranslationException(TranslationException.ErrorTypes.UNABLE_TO_TRANSLATE);
			case 501:
				throw new TranslationException(TranslationException.ErrorTypes.LANGUAGE_NOT_SUPPORTED);
		}
		String translated = "";
		for (String s : response.text)
			translated = translated+s;
		return translated;
	}
	
	@Override
	public boolean canDetectLanguage() {
		return true;
	}
	
	@Override
	public String detectLangauge(String text) throws IOException, QueryLimitException {
		Gson gson = new Gson();
		JSONDetectResponse response = gson.fromJson(StringUtils.stringFromList(WebUtils.readURL("https://translate.yandex.net/api/v1.5/tr.json/detect?key="+API_KEY+"&text=Hello+world")), JSONDetectResponse.class);
		if (response.code == 403 || response.code == 404)
			throw new QueryLimitException(this);
		return response.lang;
	}
	
	/**
	 * Method to get the available translations
	 * @return Possible language combinations
	 */
	public static String[] getLangs() throws IOException {
		Gson gson = new Gson();
		JSONLangResponse response = gson.fromJson(StringUtils.stringFromList(WebUtils.readURL("https://translate.yandex.net/api/v1.5/tr.json/getLangs?key="+API_KEY+"&ui=us")), JSONLangResponse.class);
		return response.dirs;
	}
	
	public static class JSONLangResponse {
		public String[] dirs;
	}
	
	public static class JSONDetectResponse {
		public int code;
		public String lang;
	}
	
	public static class JSONTranslateResponse {
		public int code;
		public String lang;
		public String[] text;
	}
}
