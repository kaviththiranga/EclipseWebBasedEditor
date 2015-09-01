package org.wso2.developerstudio.internal.tomcat.server;

import java.io.File;

import org.apache.catalina.startup.Bootstrap;
import org.wso2.developerstudio.internal.tomcat.api.ITomcatServer;

public class TomcatServerImpleNew implements ITomcatServer {
	
	private Bootstrap tomcatBootstrap;

	public TomcatServerImpleNew() throws Exception {
		tomcatBootstrap = new Bootstrap();
	}

	@Override
	public void addWebApp(String appID, String context, String docBase)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getAppURL(String appID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void start() throws Exception {
		tomcatBootstrap.setCatalinaBase("/home/kavith/catbase");
		tomcatBootstrap.setCatalinaHome("/home/kavith/cathome");	
		tomcatBootstrap.init();
		tomcatBootstrap.setAwait(true);
		tomcatBootstrap.start();
	}

	@Override
	public void stop() throws Exception {
		tomcatBootstrap.stop();
	}

	@Override
	public void setWebAppRoot(File webAppRoot) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Integer getServerPort() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
