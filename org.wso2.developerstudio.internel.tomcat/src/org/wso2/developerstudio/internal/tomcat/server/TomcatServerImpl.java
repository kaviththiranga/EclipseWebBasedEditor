/*
 * Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wso2.developerstudio.internal.tomcat.server;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.eclipse.core.runtime.FileLocator;
import org.wso2.developerstudio.internal.tomcat.EmbeddedTomcatPlugin;
import org.wso2.developerstudio.internal.tomcat.api.ITomcatServer;

public class TomcatServerImpl implements ITomcatServer {

	protected Tomcat tomcat;
	protected Map<String, String> deployedApps;
	protected Integer port;

	public TomcatServerImpl() {
		deployedApps = new HashMap<>();
		configureServer();
	}

	private void configureServer() {

		tomcat = new Tomcat();
		try {
			port = getAvailablePort();
			tomcat.setPort(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void start() {
		try {
			tomcat.init();
			tomcat.getService().setContainer(tomcat.getEngine());
			tomcat.start();
		} catch (LifecycleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// tomcat.getServer().await();
	}

	@Override
	public void stop() {
		try {
			tomcat.stop();
		} catch (LifecycleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void addWebApp(String appID, String context, String docBase) {
		try {
			Context appContext = tomcat.addWebapp(context, docBase);
			File configFile = new File(docBase + "/WEB-INF/web.xml");
			if (configFile.exists()) {
				appContext.setConfigFile(configFile.toURI().toURL());
			}
			appContext.setParentClassLoader(Thread.currentThread()
					.getContextClassLoader());
			deployedApps.put(appID, getURLForContext(context));
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getAppURL(String appID) {
		return deployedApps.get(appID);
	}

	private String getURLForContext(String context) {
		return "http://localhost:" + port + "/" + context;
	}

	private Integer getAvailablePort() throws IOException {
		ServerSocket socket = new ServerSocket(0);
		Integer port = socket.getLocalPort();
		socket.close();
		return port;
	}

}
