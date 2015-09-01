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
package org.wso2.developerstudio.samplewebeditor;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.wso2.developerstudio.internal.tomcat.AppIDConstants;
import org.wso2.developerstudio.internal.tomcat.EmbeddedTomcatPlugin;

/**
 * The activator class controls the plug-in life cycle
 */
public class SampleWebEditorPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.wso2.developerstudio.samplewebeditor";

	// The shared instance
	private static SampleWebEditorPlugin sharedInstance;

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
//		EmbeddedTomcatPlugin internalTomcatPlugin = EmbeddedTomcatPlugin
//				.getDefault();
//		webAppURL = internalTomcatPlugin
//				.getAppURL(AppIDConstants.SAMPLE_WEB_EDITOR_APP_ID);
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

	/**
	 * Method to get the web application URL for sample web editor.
	 * 
	 * @return URL to access sample web editor.
	 */
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
