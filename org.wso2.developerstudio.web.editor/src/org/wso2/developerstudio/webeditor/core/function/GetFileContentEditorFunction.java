package org.wso2.developerstudio.webeditor.core.function;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.eclipse.core.runtime.CoreException;
import org.wso2.developerstudio.webeditor.core.WebBasedEditor;

public class GetFileContentEditorFunction extends AbstractWebEditorFunction {

	public GetFileContentEditorFunction(WebBasedEditor editor) {
		super(editor, "IDEGetFileContent");
	}

	@Override
	public Object function(Object[] arguments) {
		InputStream inputStream = null;
		try {
			inputStream = editorInput.getFile().getContents();
			String content = IOUtils.toString(inputStream);
			return content;
		} catch (CoreException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return false;
	}
}
