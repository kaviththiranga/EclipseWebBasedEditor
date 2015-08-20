package embeddedbrowser.editors;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.commands.operations.ObjectUndoContext;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
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

import embeddedbrowser.editors.util.BrowserSaveHandler;

public class BrowserEditor extends EditorPart {

	private static final String EDITOR_NAME = "Web";
	Browser browser;
	String content;
	ICommandService commandService;
	ObjectUndoContext objectUndoContext;
	private boolean isDirty;

	public BrowserEditor() {
		objectUndoContext = new ObjectUndoContext(this);

	}

	@Override
	public void doSave(IProgressMonitor arg0) {

	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		setSite(site);
		setInput(input);
		IActionBars actionBars = site.getActionBars();
		actionBars.setGlobalActionHandler(ActionFactory.UNDO.getId(),
				new UndoActionHandler(site, objectUndoContext));
		actionBars.setGlobalActionHandler(ActionFactory.REDO.getId(),
				new RedoActionHandler(site, objectUndoContext));
		actionBars.setGlobalActionHandler(ActionFactory.SAVE.getId(),
				new BrowserSaveHandler(this));
	}

	@Override
	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void createPartControl(Composite parent) {
		browser = new Browser(parent, SWT.NONE);
		// browser.setUrl("/home/kavith/GitHome/standalone-editor/index.html");
		browser.setUrl(embeddedbrowser.Activator.url);
		browser.addControlListener(new ControlListener() {
			public void controlResized(ControlEvent e) {
			}

			public void controlMoved(ControlEvent e) {
			}
		});
		browser.addProgressListener(new ProgressListener() {

			@Override
			public void completed(ProgressEvent arg0) {
				getEditorInput();

				try {
					IUndoableOperation oo = new BrowserOperation("First Task");
					oo.addContext(objectUndoContext);
					OperationHistoryFactory.getOperationHistory().execute(oo,
							null, null);
					oo = new BrowserOperation("Second Task");
					oo.addContext(objectUndoContext);
					OperationHistoryFactory.getOperationHistory().execute(oo,
							null, null);
					oo = new BrowserOperation("3rd Task");
					oo.addContext(objectUndoContext);
					OperationHistoryFactory.getOperationHistory().execute(oo,
							null, null);
					oo = new BrowserOperation("4th Task");
					oo.addContext(objectUndoContext);
					OperationHistoryFactory.getOperationHistory().execute(oo,
							null, null);
					oo = new BrowserOperation("5th Task");
					oo.addContext(objectUndoContext);
					OperationHistoryFactory.getOperationHistory().execute(oo,
							null, null);
					oo = new BrowserOperation("6th Task");
					oo.addContext(objectUndoContext);
					OperationHistoryFactory.getOperationHistory().execute(oo,
							null, null);
					oo = new BrowserOperation("7th Task");
					oo.addContext(objectUndoContext);
					OperationHistoryFactory.getOperationHistory().execute(oo,
							null, null);
					oo = new BrowserOperation("8th Task");
					oo.addContext(objectUndoContext);
					OperationHistoryFactory.getOperationHistory().execute(oo,
							null, null);
					oo = new BrowserOperation("9th Task");
					oo.addContext(objectUndoContext);
					OperationHistoryFactory.getOperationHistory().execute(oo,
							null, null);
					setDirty(true);
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
			// TODO Auto-generated constructor stub
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
		// TODO Auto-generated method stub
		return "Browser Based editor.";
	}

	class CallBackFunction extends BrowserFunction {

		public CallBackFunction(Browser browser, String name) {
			super(browser, name);
		}

		@Override
		public Object function(Object[] arguments) {
			// TODO Auto-generated method stub
			return super.function(arguments);
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

	@Override
	public boolean isDirty() {
		// TODO Auto-generated method stub
		return isDirty;
	}

	public void setDirty(boolean isDirty) {
		this.isDirty = isDirty;
		firePropertyChange(PROP_DIRTY);
	}
}
