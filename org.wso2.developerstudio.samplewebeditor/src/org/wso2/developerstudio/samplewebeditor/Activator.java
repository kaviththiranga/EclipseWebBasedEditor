package org.wso2.developerstudio.samplewebeditor;

import java.io.File;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.wso2.developerstudio.internal.tomcat.api.ITomcatServer;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.wso2.developerstudio.samplewebeditor"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	
	private ITomcatServer tomcatServer;
	
	private String webAppURL;
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		ServiceReference<?> serviceReference = context.
				  getServiceReference(ITomcatServer.class.getName());
		tomcatServer = (ITomcatServer) context.getService(serviceReference);
		File webApp = new File(FileLocator.resolve(context.getBundle().getResource("webapp")).toURI());
		tomcatServer.addWebApp("SampleWeBEditor", "sampleEditor", webApp.getAbsolutePath());
		webAppURL = tomcatServer.getAppURL("SampleWeBEditor");
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}
	
	public String getEditorAppURL(){
		return webAppURL;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
}
