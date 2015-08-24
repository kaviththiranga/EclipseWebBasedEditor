package embeddedbrowser.editors;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.commands.operations.ObjectUndoContext;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.BrowserFunction;
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
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.operations.RedoActionHandler;
import org.eclipse.ui.operations.UndoActionHandler;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.FileEditorInput;

import embeddedbrowser.editors.util.BrowserCopyActionHandler;
import embeddedbrowser.editors.util.UndoableBrowserOperation;

public class BrowserEditor extends EditorPart {

	private static final String EDITOR_NAME = "Web";
	Browser browser;
	String content;
	ICommandService commandService;
	ObjectUndoContext browserEditorUndoContext;
	FileEditorInput editorInput;
	BrowserEditor browserEditor;
	
	private boolean isDirty;

	public BrowserEditor() {
		browserEditor = this;
		browserEditorUndoContext = new ObjectUndoContext(this);
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
				new UndoActionHandler(site, browserEditorUndoContext));
		actionBars.setGlobalActionHandler(ActionFactory.REDO.getId(),
				new RedoActionHandler(site, browserEditorUndoContext));
		actionBars.setGlobalActionHandler(ActionFactory.COPY.getId(), new BrowserCopyActionHandler());
	}

	@Override
	public boolean isSaveAsAllowed() {
		return true;
	}

	@Override
	public void createPartControl(Composite parent) {
		browser = new Browser(parent, SWT.NONE);
		browser.setUrl(embeddedbrowser.Activator.url);
		browser.addControlListener(new ControlListener() {
			public void controlResized(ControlEvent e) {
			}
			public void controlMoved(ControlEvent e) {
			}
		});
		new IDESaveContentFunction(browser);
		new IDESetDirtyFunction(browser);
		new IDEExecUndoableOperationFunction(browser);
		browser.addProgressListener(new ProgressListener() {

			@Override
			public void completed(ProgressEvent arg0) {
				try {
					InputStream inputStream = editorInput.getFile()
							.getContents();
					String content = IOUtils.toString(inputStream);
					updateContent(content);
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				setDirty(true);
			}

			@Override
			public void changed(ProgressEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		setPartName(EDITOR_NAME);
	}

	class BrowserOperation extends AbstractOperation {

		public BrowserOperation(String label) {
			super(label);
		}

		@Override
		public IStatus execute(IProgressMonitor arg0, IAdaptable arg1)
				throws ExecutionException {
			String newContent = "Executing main operation for " + getLabel();
			String script = "var data ={'data' : {'_type':1, 'filePath':'c:/ssdsd.xml', 'content':'"
					+ newContent + "'}};receiveMessage(data);";
			browser.execute(script);
			return Status.OK_STATUS;
		}

		@Override
		public IStatus redo(IProgressMonitor arg0, IAdaptable arg1)
				throws ExecutionException {
			String newContent = "Executing redo operation for " + getLabel();
			String script = "var data ={'data' : {'_type':1, 'filePath':'c:/ssdsd.xml', 'content':'"
					+ newContent + "'}};receiveMessage(data);";
			browser.execute(script);
			return Status.OK_STATUS;
		}

		@Override
		public IStatus undo(IProgressMonitor arg0, IAdaptable arg1)
				throws ExecutionException {
			String newContent = "Executing undo operation for " + getLabel();
			String script = "var data ={'data' : {'_type':1, 'filePath':'c:/ssdsd.xml', 'content':'"
					+ newContent + "'}};receiveMessage(data);";
			browser.execute(script);
			return Status.OK_STATUS;
		}

	}

	@Override
	public void setFocus() {
	}

	@Override
	public String getTitleToolTip() {
		return "Browser Based editor.";
	}

	class IDESaveContentFunction extends BrowserFunction{
		public IDESaveContentFunction(Browser browser) {
			super(browser, "IDESaveContent");
		}
		
		@Override
		public Object function(Object[] arguments) {
			
			String content = (String) arguments[0];
			try {
				editorInput.getFile().setContents(IOUtils.toInputStream(content), true, true, null);
				setDirty(false);
				return Boolean.TRUE.toString();
			} catch (CoreException e) {
				e.printStackTrace();
				return Boolean.FALSE.toString();
				
			}
			
		}	
	}
	class IDESetDirtyFunction extends BrowserFunction{

		public IDESetDirtyFunction(Browser browser) {
			super(browser, "IDESetDirty");
		}
		
		@Override
		public Object function(Object[] arguments) {
			
			boolean isDirty = (boolean) arguments[0];
			setDirty(isDirty);
			
			return null;
		}	
	}
	
	class IDEExecUndoableOperationFunction extends BrowserFunction {

		public IDEExecUndoableOperationFunction(Browser browser) {
			super(browser, "IDEExecUndoableOperation");
		}

		@Override
		public Object function(Object[] arguments) {

			String label = (String) arguments[0];
			String uniqueOpID = (String) arguments[1];

			IUndoableOperation operation = new UndoableBrowserOperation(label,
					uniqueOpID, browserEditor);
			operation.addContext(browserEditorUndoContext);

			try {
				OperationHistoryFactory.getOperationHistory().execute(
						operation, null, null);
				return true;
			} catch (ExecutionException e) {
				e.printStackTrace();
				return false;
			}
		}
	}

	public void updateContent(String newContent) {

		String script = "var data ={'data' : {'_type':1, 'filePath':'c:/ssdsd.xml', 'content':'"
				+ newContent + "'}};receiveMessage(data);";
		browser.execute(script);
	}

	public void setContent(String string) {
		this.content = string;

	}
	
	public boolean executeOnBrowser(String script){
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
}
