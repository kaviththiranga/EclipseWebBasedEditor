package org.wso2.developerstudio.webeditor.core.function;

import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.BrowserFunction;
import org.eclipse.ui.part.FileEditorInput;
import org.wso2.developerstudio.webeditor.core.WebBasedEditor;

public abstract class AbstractWebEditorFunction extends BrowserFunction {

	protected WebBasedEditor editor;
	protected FileEditorInput editorInput;

	public AbstractWebEditorFunction(WebBasedEditor editor, String name) {
		this(editor.getBrowser(), name);
		this.editor = editor;
		this.editorInput = (FileEditorInput) editor.getEditorInput();
	}

	public AbstractWebEditorFunction(Browser browser, String name) {
		super(browser, name);
	}
}
