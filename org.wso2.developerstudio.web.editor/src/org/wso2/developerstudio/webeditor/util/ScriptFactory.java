/*
 * Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
