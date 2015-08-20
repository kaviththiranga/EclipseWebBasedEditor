package embeddedbrowser.editors.util;

import org.eclipse.jface.action.Action;

import embeddedbrowser.editors.BrowserEditor;

public class BrowserSaveHandler extends Action {

	protected BrowserEditor browserEditor;
	
	public BrowserSaveHandler(BrowserEditor browserEditor) {
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
