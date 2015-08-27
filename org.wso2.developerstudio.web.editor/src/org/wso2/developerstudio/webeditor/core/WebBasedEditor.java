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
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
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
import org.wso2.developerstudio.webeditor.function.ExecuteUndoableTaskFunction;
import org.wso2.developerstudio.webeditor.function.GetFileContentFunction;
import org.wso2.developerstudio.webeditor.function.SaveContentFunction;
import org.wso2.developerstudio.webeditor.function.SetDirtyFunction;
import org.wso2.developerstudio.webeditor.function.SetFocusToEditorPartFunction;
import org.wso2.developerstudio.webeditor.handler.BrowserCopyActionHandler;
import org.wso2.developerstudio.webeditor.model.BrowserScript;
import org.wso2.developerstudio.webeditor.util.ScriptFactory;

import com.google.gson.JsonObject;

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
		BrowserScript script = ScriptFactory.createFunctionCallScript("saveFile");
		executeScriptOnBrowser(script);
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
				executeScriptOnBrowser(ScriptFactory.FN_LOAD_FILE_CONTENT);
			}

			@Override
			public void changed(ProgressEvent arg0) {

			}
		});
		browser.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent arg0) {
				
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
				editorInstance.setFocus();
			}
		});
		injectEditorJSFunctions();
		setPartName(editorInput.getName());
	}

	protected void injectEditorJSFunctions() {
		if (browser != null) {
			new SaveContentFunction(editorInstance);
			new SetDirtyFunction(editorInstance);
			new ExecuteUndoableTaskFunction(editorInstance);
			new GetFileContentFunction(editorInstance);
			new SetFocusToEditorPartFunction(editorInstance);
		} else {
			throw new IllegalStateException("Browser is not yet instantiated.");
		}
	}

	@Override
	public void setFocus() {
		getEditorSite().getPage().activate(this);
	}

	@Override
	public String getTitleToolTip() {
		return "Browser Based editor.";
	}

	public boolean executeScriptOnBrowser(BrowserScript script){
		if(browser != null){
			return browser.execute(script.getScript());
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
