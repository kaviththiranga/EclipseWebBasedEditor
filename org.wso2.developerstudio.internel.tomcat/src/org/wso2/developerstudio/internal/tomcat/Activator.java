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
package org.wso2.developerstudio.internal.tomcat;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.FileLocator;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	private static BundleContext context;
	private Object serverInstance;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		new Thread(new Runnable() {
			public void run() {
				try {
					List<URL> classpath = new ArrayList<>();
					URL bundleURI = FileLocator.resolve(context.getBundle().getResource("."));
					classpath.add(bundleURI);
					URI libsURI = FileLocator.resolve(context.getBundle().getResource("libs")).toURI();
					File libs = new File(libsURI);
					addJarFileUrls(libs, classpath);
					ClassLoader classLoader = new URLClassLoader(classpath.toArray(new URL[classpath.size()]));
					// Set the proper class loader for this thread.
					//Thread.currentThread().setContextClassLoader(classLoader);
					Class<?> appClass = classLoader
							.loadClass("org.wso2.developerstudio.internal.tomcat.server.TomcatServer");
					serverInstance = appClass.newInstance();
					Method m = serverInstance.getClass().getMethod("start",
							new Class[0]);
					m.invoke(serverInstance, new Object[0]);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}).start();
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
		Method m = serverInstance.getClass().getMethod("stop", new Class[0]);
	    m.invoke(serverInstance, new Object[0]);
	}
	
	private static void addJarFileUrls(File root, List<URL> jarUrls)
			throws MalformedURLException {
		File[] files = root.listFiles();
		if (files == null) {
			return;
		}
		for (File file : files) {
			if (file.isDirectory() && file.canRead()) {
				addJarFileUrls(file, jarUrls);
			} else if (file.isFile() && file.canRead()
					&& file.getName().toLowerCase().endsWith(".jar")) {
				jarUrls.add(file.toURI().toURL());
			}
		}
	}
}
