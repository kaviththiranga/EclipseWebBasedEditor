package org.wso2.developerstudio.webeditor.function;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.commands.operations.UndoContext;
import org.wso2.developerstudio.webeditor.core.WebBasedEditor;
import org.wso2.developerstudio.webeditor.model.UndoableBrowserOperation;

public class ExecuteUndoableTaskFunction extends AbstractWebEditorFunction {

	protected UndoContext undoContext;
	
	public ExecuteUndoableTaskFunction(WebBasedEditor editor) {
		super(editor, "IDEExecUndoableOperation");
		this.undoContext = editor.getUndoContext();
	}

	@Override
	public Object function(Object[] arguments) {

		String label = (String) arguments[0];
		String uniqueOpID = (String) arguments[1];

		IUndoableOperation operation = new UndoableBrowserOperation(label,
				uniqueOpID, editor);
		operation.addContext(undoContext);

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
