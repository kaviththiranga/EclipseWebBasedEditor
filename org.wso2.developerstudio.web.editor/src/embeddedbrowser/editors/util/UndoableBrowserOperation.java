package embeddedbrowser.editors.util;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import embeddedbrowser.editors.BrowserEditor;

public class UndoableBrowserOperation extends AbstractOperation {

	protected String uniqueOperationID;
	protected BrowserEditor browserEditor;
	
	public UndoableBrowserOperation(String label, String uniqueOperationID, BrowserEditor browserEditor) {
		super(label);
		this.uniqueOperationID = uniqueOperationID;
		this.browserEditor = browserEditor;
	}

	@Override 	
	public IStatus execute(IProgressMonitor arg0, IAdaptable arg1)
			throws ExecutionException {
		return Status.OK_STATUS;
	}

	@Override
	public IStatus redo(IProgressMonitor arg0, IAdaptable arg1)
			throws ExecutionException {
		if(browserEditor.sendUndoMessage(uniqueOperationID)){
			return  Status.OK_STATUS;
		}
		return Status.CANCEL_STATUS;
	}

	@Override
	public IStatus undo(IProgressMonitor arg0, IAdaptable arg1)
			throws ExecutionException {
		if(browserEditor.sendRedoMessage(uniqueOperationID)){
			return  Status.OK_STATUS;
		}
		return Status.CANCEL_STATUS;
	}

	
}
