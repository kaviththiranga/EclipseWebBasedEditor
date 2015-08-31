package org.wso2.developerstudio.samplewebeditor;

import java.io.File;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.wso2.developerstudio.internal.tomcat.EmbeddedTomcatPlugin;

/**
 * The activator class controls the plug-in life cycle
 */
public class SampleWebEditorPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.wso2.developerstudio.samplewebeditor"; //$NON-NLS-1$

	// The shared instance
	private static SampleWebEditorPlugin sharedInstance;
	
	public static String EDITOR_WEB_APP_ID = "SampleWebEditor";

	private String webAppURL;

	/**
	 * The constructor
	 */
	public SampleWebEditorPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		sharedInstance = this;
		EmbeddedTomcatPlugin internalTomcatPlugin = EmbeddedTomcatPlugin.getDefault();
		webAppURL = internalTomcatPlugin.getAppURL(EDITOR_WEB_APP_ID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
	 */
	public void stop(BundleContext context) throws Exception {
		sharedInstance = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static SampleWebEditorPlugin getDefault() {
		return sharedInstance;
	}

	public String getEditorAppURL() {
		return webAppURL;
	}

	/**
	 * Returns an image descriptor for the image file at the given plug-in
	 * relative path
	 *
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
}
