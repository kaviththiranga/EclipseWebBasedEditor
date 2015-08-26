package org.wso2.developerstudio.webeditor.model;

public class BrowserScript {

	protected String script;
	protected String functionName;
	protected String[] args;

	public BrowserScript(String script) {
		this.script = script;
	}

	public BrowserScript(String functionName, String[] args) {
		this.functionName = functionName;
		this.args = args;
		generateFunctionCallScript();
	}

	public String getScript() {
		return script;
	}

	private void generateFunctionCallScript() {
		if (functionName != null) {
			StringBuilder scriptBuilder = new StringBuilder();
			if (args != null) {
				String[] argNames = new String[args.length];
				for (int argIndex = 0; argIndex < args.length; argIndex++) {
					String argName = "arg" + argIndex;
					argNames[argIndex] = argName;
					scriptBuilder.append("var " + argName + " = "
							+ args[argIndex] + ";");
				}
				scriptBuilder.append(functionName + "(");
				for (int argNameIndex = 0; argNameIndex < argNames.length; argNameIndex++) {
					scriptBuilder.append(argNames[argNameIndex]);
					if (argNameIndex != (argNames.length - 1)) {
						scriptBuilder.append(", ");
					}
				}
				scriptBuilder.append(");");
			} else {
				scriptBuilder.append(functionName + "();");
			}
			this.script = scriptBuilder.toString();
		}
	}
	
	@Override
	public String toString() {
		return this.script;
	}
}
