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
package org.wso2.developerstudio.webeditor.function;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.eclipse.core.runtime.CoreException;
import org.wso2.developerstudio.webeditor.core.AbstractWebBasedEditor;

public class SaveContentFunction extends AbstractWebEditorFunction{

	public SaveContentFunction(AbstractWebBasedEditor editor) {
		super(editor, "IDESaveContent");
	}

	@Override
	public Object function(Object[] arguments) {

		String content = (String) arguments[0];
		InputStream inputStream = null;
		try {
			inputStream = IOUtils.toInputStream(content);
			editorInput.getFile().setContents(inputStream,
					true, true, null);
			editor.setDirty(false);
			return Boolean.TRUE.toString();
		} catch (CoreException e) {
			e.printStackTrace();
			return Boolean.FALSE.toString();
		} finally {
			if(inputStream != null){
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
