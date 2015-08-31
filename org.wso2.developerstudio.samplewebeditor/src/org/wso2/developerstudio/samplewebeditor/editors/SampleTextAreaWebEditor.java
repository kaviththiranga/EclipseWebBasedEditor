package org.wso2.developerstudio.samplewebeditor.editors;

import org.wso2.developerstudio.webeditor.core.AbstractWebBasedEditor;
import org.wso2.developerstudio.samplewebeditor.SampleWebEditorPlugin;

public class SampleTextAreaWebEditor extends AbstractWebBasedEditor{

	@Override
	public String getWebAppURL() {
		return SampleWebEditorPlugin.getDefault().getEditorAppURL();
	}

	@Override
	public String getEditorName() {
		return "Sample Editor";
	}

	@Override
	public String getEditorTitleToolTip() {
		return "Sample Editor";
	}

}