package org.wso2.developerstudio.webeditor.function;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.eclipse.core.runtime.CoreException;
import org.wso2.developerstudio.webeditor.core.WebBasedEditor;

public class SaveContentFunction extends AbstractWebEditorFunction{

	public SaveContentFunction(WebBasedEditor editor) {
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
