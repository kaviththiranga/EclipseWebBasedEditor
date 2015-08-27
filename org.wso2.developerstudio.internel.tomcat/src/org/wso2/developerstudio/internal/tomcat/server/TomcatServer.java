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
