/**
 * 
 */
package com.nam;

import java.io.IOException;

import javax.portlet.*;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * @author hp
 *
 */
public class Portlet extends MVCPortlet{
	
	static {
		System.out.println("Hello World! привет мир!");
	}
	
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException{
		System.out.println("->");
		super.doView(renderRequest, renderResponse);
		System.out.println("<-");
	}
}
