package org.wso2.developerstudio.webeditor.core.function;

import org.wso2.developerstudio.webeditor.core.WebBasedEditor;

public class SetDirtyEditorFunction extends AbstractWebEditorFunction {

	public SetDirtyEditorFunction(WebBasedEditor editor) {
		super(editor, "IDESetDirty");
	}

	@Override
	public Object function(Object[] arguments) {
		
		boolean isDirty = (boolean) arguments[0];
		editor.setDirty(isDirty);
		
		return null;
	}
}
