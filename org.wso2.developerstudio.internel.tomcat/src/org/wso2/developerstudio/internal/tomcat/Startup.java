package org.wso2.developerstudio.internal.tomcat;

import org.eclipse.ui.IStartup;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;

public class Startup implements IStartup {

	@Override
	public void earlyStartup() {
		BundleContext context = EmbeddedTomcatPlugin.getContext();
		Bundle bundle = context.getBundle();
		if (bundle.getState() != Bundle.ACTIVE) {
			try {
				bundle.start();
			} catch (BundleException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
