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
import java.lang.reflect.InvocationTargetException;
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

public class EmbeddedTomcatPlugin implements BundleActivator {

	private static BundleContext context;

	// The shared instance
	private static EmbeddedTomcatPlugin plugin;

	private Object serverInstance;

	private ClassLoader tomcatClassLoader;

	public static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext bundleContext) throws Exception {
		context = bundleContext;
		plugin = this;
		new Thread(new Runnable() {
			public void run() {

			}
		}).start();
		try {
			List<URL> classpath = new ArrayList<>();
			URL bundleURI = FileLocator.resolve(context.getBundle()
					.getResource("."));
			classpath.add(bundleURI);
			URI libsURI = FileLocator.resolve(
					context.getBundle().getResource("libs")).toURI();
			File libs = new File(libsURI);
			addJarFileUrls(libs, classpath);
			tomcatClassLoader = new URLClassLoader(
					classpath.toArray(new URL[classpath.size()]));
			// Set the proper class loader for this thread.
			Thread.currentThread().setContextClassLoader(tomcatClassLoader);
			Class<?> appClass = tomcatClassLoader
					.loadClass("org.wso2.developerstudio.internal.tomcat.server.TomcatServerImpl");
			serverInstance = appClass.newInstance();
			File webAppRoot = new File(FileLocator.resolve(
					context.getBundle().getResource("webapps")).toURI());
			Method setWebRootMethod = serverInstance.getClass().getMethod(
					"setWebAppRoot", File.class);
			setWebRootMethod.invoke(serverInstance, webAppRoot);

			Method startMethod = serverInstance.getClass().getMethod("start",
					new Class[0]);
			startMethod.invoke(serverInstance, new Object[0]);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		plugin = null;
		context = null;
		Method m = serverInstance.getClass().getMethod("stop", new Class[0]);
		m.invoke(serverInstance, new Object[0]);
	}

	public static EmbeddedTomcatPlugin getDefault() {
		return plugin;
	}

	public String getAppURL(String appID) throws Exception {
		Method m = serverInstance.getClass().getMethod("getAppURL",
				String.class);
		Object result = m.invoke(serverInstance, appID);
		return result.toString();
	}

	public void addWebApp(final String appID, final String context,
			final String docBase) throws Exception {
		new Thread(new Runnable() {
			@Override
			public void run() {
				Thread.currentThread().setContextClassLoader(tomcatClassLoader);
				try {
					Method m = serverInstance.getClass().getMethod("addWebApp",
							String.class, String.class, String.class);
					m.invoke(serverInstance, appID, context, docBase);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();

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
