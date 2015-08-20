package embeddedbrowser;

import java.io.File;
import java.net.MalformedURLException;

import javax.servlet.ServletException;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.TomcatURLStreamHandlerFactory;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "EmbeddedBrowser"; //$NON-NLS-1$
	
	public static String url = "http://localhost:8085/editor2";

	// The shared instance
	private static Activator plugin;
	
	Tomcat tomcat;
	
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
		
		Bundle bundle = Platform.getBundle("org.wso2.developerstudio.tomcat.embedded.server");

		new Thread(new Runnable() {
			@Override
			public void run() {
				String webappDirLocation = "/home/kavith/webApp/app";

				TomcatURLStreamHandlerFactory.disable();
				tomcat = new Tomcat();
				// Bind the port to Tomcat server
				tomcat.setPort(Integer.valueOf("8085"));

				// Define a web application context.
				Context context2;
				try {
					context2 = tomcat.addWebapp("/editor2",
							new File(webappDirLocation).getAbsolutePath());
					//Define and bind web.xml file location.
					File configFile = new File(webappDirLocation + "/WEB-INF/web.xml");
					context2.setConfigFile(configFile.toURI().toURL());

					tomcat.start();
					tomcat.getServer().await();
				} catch (ServletException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (LifecycleException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		tomcat.stop();
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
