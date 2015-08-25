package org.wso2.developerstudio.webeditor.handler;

import org.eclipse.jface.action.Action;
import org.wso2.developerstudio.webeditor.core.WebBasedEditor;

public class BrowserSaveHandler extends Action {

	protected WebBasedEditor browserEditor;
	
	public BrowserSaveHandler(WebBasedEditor browserEditor) {
		super();
		this.browserEditor = browserEditor;
	}


	@Override
	public void run() {
		browserEditor.updateContent("Executed Save Command");
		browserEditor.setDirty(false);
		setEnabled(false);
	}
}
