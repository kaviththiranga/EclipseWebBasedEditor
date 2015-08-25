package org.wso2.developerstudio.webeditor.core;

import org.eclipse.core.commands.operations.ObjectUndoContext;
import org.eclipse.core.commands.operations.UndoContext;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.browser.ProgressListener;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.operations.RedoActionHandler;
import org.eclipse.ui.operations.UndoActionHandler;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.FileEditorInput;
import org.wso2.developerstudio.webeditor.core.function.ExecuteUndoableTaskEditorFunction;
import org.wso2.developerstudio.webeditor.core.function.GetFileContentEditorFunction;
import org.wso2.developerstudio.webeditor.core.function.SaveContentEditorFunction;
import org.wso2.developerstudio.webeditor.core.function.SetDirtyEditorFunction;
import org.wso2.developerstudio.webeditor.handler.BrowserCopyActionHandler;

public class WebBasedEditor extends EditorPart {

	protected Browser browser;
	protected ObjectUndoContext editorUndoContext;
	protected FileEditorInput editorInput;
	protected WebBasedEditor editorInstance;
	
	protected boolean isDirty;

	public WebBasedEditor() {
		editorInstance = this;
		editorUndoContext = new ObjectUndoContext(this);
	}

	@Override
	public void doSave(IProgressMonitor arg0) {
		String script = "var data ={'data' : {'_type':2}};receiveMessage(data);";
		boolean execute = browser.execute(script);
	}

	@Override
	public void doSaveAs() {
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		setSite(site);
		setInput(input);
		editorInput = (FileEditorInput) input;
		IActionBars actionBars = site.getActionBars();
		actionBars.setGlobalActionHandler(ActionFactory.UNDO.getId(),
				new UndoActionHandler(site, editorUndoContext));
		actionBars.setGlobalActionHandler(ActionFactory.REDO.getId(),
				new RedoActionHandler(site, editorUndoContext));
		actionBars.setGlobalActionHandler(ActionFactory.COPY.getId(), new BrowserCopyActionHandler());
	}

	@Override
	public boolean isSaveAsAllowed() {
		return true;
	}

	@Override
	public void createPartControl(Composite parent) {
		browser = new Browser(parent, SWT.NONE);
		browser.setUrl(org.wso2.developerstudio.webeditor.Activator.url);
		browser.addControlListener(new ControlListener() {
			public void controlResized(ControlEvent e) {
			}
			public void controlMoved(ControlEvent e) {
			}
		});
		browser.addProgressListener(new ProgressListener() {

			@Override
			public void completed(ProgressEvent arg0) {
				browser.execute("loadFileContent();");
			}

			@Override
			public void changed(ProgressEvent arg0) {

			}
		});

		new SaveContentEditorFunction(editorInstance);
		new SetDirtyEditorFunction(editorInstance);
		new ExecuteUndoableTaskEditorFunction(editorInstance);
		new GetFileContentEditorFunction(editorInstance);
		setPartName(editorInput.getName());
	}

	@Override
	public void setFocus() {
		browser.setFocus();	
	}

	@Override
	public String getTitleToolTip() {
		return "Browser Based editor.";
	}
	
	public void updateContent(String newContent) {

		String script = "var data ={'data' : {'_type':1, 'filePath':'c:/ssdsd.xml', 'content':'/*"
				+ newContent + "*/'}};receiveMessage(data);";
		browser.execute(script);
	}

	public boolean executeScriptOnBrowser(String script){
		if(browser != null){
			return browser.execute(script);
		}
		return false;
	}

	@Override
	public boolean isDirty() {
		return isDirty;
	}

	public void setDirty(boolean isDirty) {
		this.isDirty = isDirty;
		firePropertyChange(PROP_DIRTY);
	}

	public boolean sendUndoMessage(String uniqueOperationID) {
		return false;
	}

	public boolean sendRedoMessage(String uniqueOperationID) {
		return false;
	}
	public Browser getBrowser() {
		return browser;
	}

	public UndoContext getUndoContext() {
		return editorUndoContext;
	}
}
