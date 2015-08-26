package org.wso2.developerstudio.webeditor.function;

import org.wso2.developerstudio.webeditor.core.WebBasedEditor;

public class SetFocusToEditorPartFunction extends AbstractWebEditorFunction {

	public SetFocusToEditorPartFunction(WebBasedEditor editor) {
		super(editor, "IDESetFocusToEditorPart");
	}

	@Override
	public Object function(Object[] arguments) {
		editor.setFocus();
		return null;
	}
}
