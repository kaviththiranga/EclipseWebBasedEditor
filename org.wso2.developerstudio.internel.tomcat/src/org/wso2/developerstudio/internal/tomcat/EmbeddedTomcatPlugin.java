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

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.wso2.developerstudio.internal.tomcat.api.ITomcatServer;
import org.wso2.developerstudio.internal.tomcat.server.TomcatServerImpl;

public class EmbeddedTomcatPlugin implements BundleActivator {

	private static BundleContext context;

	// The shared instance
	private static EmbeddedTomcatPlugin plugin;

	private ITomcatServer serverInstance;

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
				serverInstance = new TomcatServerImpl();
				serverInstance.start();
			}
		}).start();
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
		serverInstance.stop();
	}

	public static EmbeddedTomcatPlugin getDefault() {
		return plugin;
	}

	public ITomcatServer getServer() {
		return serverInstance;
	}
}
