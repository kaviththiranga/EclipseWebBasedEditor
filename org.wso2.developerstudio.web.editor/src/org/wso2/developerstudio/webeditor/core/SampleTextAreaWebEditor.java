package org.wso2.developerstudio.webeditor.core;

public class SampleTextAreaWebEditor extends AbstractWebBasedEditor{

	@Override
	public String getWebAppURL() {
		return "/home/kavith/GitHome/standalone-editor/index.html";
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
