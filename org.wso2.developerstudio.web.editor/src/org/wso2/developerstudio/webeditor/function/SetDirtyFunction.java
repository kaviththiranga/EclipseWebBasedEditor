package org.wso2.developerstudio.webeditor.function;

import org.wso2.developerstudio.webeditor.core.WebBasedEditor;

public class SetDirtyFunction extends AbstractWebEditorFunction {

	public SetDirtyFunction(WebBasedEditor editor) {
		super(editor, "IDESetDirty");
	}

	@Override
	public Object function(Object[] arguments) {
		
		boolean isDirty = (boolean) arguments[0];
		editor.setDirty(isDirty);
		
		return null;
	}
}
