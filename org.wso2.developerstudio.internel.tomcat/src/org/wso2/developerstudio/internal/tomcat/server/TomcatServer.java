package org.wso2.developerstudio.internal.tomcat.server;

import java.io.File;
import java.net.MalformedURLException;

import javax.servlet.ServletException;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

public class TomcatServer {

	protected Tomcat serverInstance;

	public TomcatServer() {
		configureServer();
	}

	private void configureServer() {
		String webappDirLocation = "/home/kavith/webApp/sample";

		serverInstance = new Tomcat();
		// Bind the port to Tomcat server
		serverInstance.setPort(Integer.valueOf("8085"));

		// Define a web application context.
		Context context2;
		try {
			context2 = serverInstance.addWebapp("/editor", new File(
					webappDirLocation).getAbsolutePath());
			// Define and bind web.xml file location.
			File configFile = new File(webappDirLocation + "/WEB-INF/web.xml");
			context2.setConfigFile(configFile.toURI().toURL());
			context2.setParentClassLoader(Thread.currentThread().getContextClassLoader());

		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public void start(){
		try {
			serverInstance.start();
		} catch (LifecycleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		serverInstance.getServer().await();
	}

	public void stop(){
		try {
			serverInstance.stop();
		} catch (LifecycleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
