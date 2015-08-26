package org.wso2.developerstudio.webeditor.util;

import org.wso2.developerstudio.webeditor.model.BrowserScript;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class ScriptFactory {

	private static Gson gson;
	public static final BrowserScript FN_LOAD_FILE_CONTENT;

	static {
		gson = new Gson();
		FN_LOAD_FILE_CONTENT = createFunctionCallScript("loadFileContent");
	}

	public static BrowserScript createScript(String script) {
		return new BrowserScript(script);
	}

	public static BrowserScript createFunctionCallScript(String functionName,
			String[] args) {
		return new BrowserScript(functionName, args);
	}

	public static BrowserScript createFunctionCallScript(String functionName,
			JsonElement[] args) {
		return createFunctionCallScript(functionName, toJsonStringArray(args));
	}

	public static BrowserScript createFunctionCallScript(String functionName,
			JsonElement argument) {
		return createFunctionCallScript(functionName,
				new String[] { toJsonString(argument) });
	}

	public static BrowserScript createFunctionCallScript(String functionName) {
		return createFunctionCallScript(functionName, new String[] {});
	}

	private static String toJsonString(JsonElement jsonElement) {
		return gson.toJson(jsonElement);
	}

	private static String[] toJsonStringArray(JsonElement[] jsonElements) {
		String[] elements = new String[jsonElements.length];
		for (int index = 0; index < jsonElements.length; index++) {
			elements[index] = toJsonString(jsonElements[index]);
		}
		return elements;
	}
}
