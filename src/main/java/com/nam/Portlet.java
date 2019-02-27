/**
 * 
 */
package com.nam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.portlet.*;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;
import com.nam.srv.model.Entry;
import com.nam.srv.model.Guestbook;
import com.nam.srv.service.EntryLocalServiceUtil;
import com.nam.srv.service.GuestbookLocalServiceUtil;


/**
 * @author hp
 *
 */
public class Portlet extends MVCPortlet{
	
	static {
		System.out.println("Hello World!! привет мир!!");
	}
	
	/*public void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException{
		System.out.println("->");
		super.doView(renderRequest, renderResponse);
		System.out.println("<-");
	}*/
	
	public void addGuestbook(ActionRequest request, ActionResponse response) throws PortalException, SystemException{
		System.out.println("Add guestbook!!!!");
		
		ServiceContext serviceContext = ServiceContextFactory.getInstance(Guestbook.class.getName(), request);
		
		String name = ParamUtil.getString(request, "name");
		
		try {
			GuestbookLocalServiceUtil.addGuestbook(serviceContext.getUserId(), name, serviceContext);
			
			SessionMessages.add(request, "guestbookAdded");
		} catch (Exception e) {
			SessionErrors.add(request, e.getClass().getName());
			
			response.setRenderParameter("mvcPath", "/guestbook/edit_guestbook.jsp");
		}
	}
	
	public void addEntry(ActionRequest request, ActionResponse response) throws PortalException, SystemException{
		System.out.println("Add entry!!!!");
		
		ServiceContext serviceContext = ServiceContextFactory.getInstance(Entry.class.getName(), request);
		
		String userName = ParamUtil.getString(request, "name");
		String email = ParamUtil.getString(request, "email");
		String message = ParamUtil.getString(request, "message");
		
		long guestbookId = ParamUtil.getLong(request, "guestbookId");
		
		try {
			EntryLocalServiceUtil.addEntry(serviceContext.getUserId(), guestbookId, userName, email, message, serviceContext);
			
			SessionMessages.add(request, "entryAdded");
			
			response.setRenderParameter("guestbookId", Long.toString(guestbookId));
		}catch(Exception ex) {
			SessionErrors.add(request, ex.getClass().getName());
			
			PortalUtil.copyRequestParameters(request, response);
			response.setRenderParameter("mvcPath", "/edit_entry.jsp");
		}
	}
	
	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException, IOException{
		try {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(Entry.class.getName(), renderRequest);
			
			long groupId = serviceContext.getScopeGroupId();
			
			long guestbookId = ParamUtil.getLong(renderRequest, "guestbookId");
			
			List<Guestbook> guestbooks = GuestbookLocalServiceUtil.getGuestbooks(groupId);
			
			if (guestbooks.size() == 0) {
				Guestbook guestbook = GuestbookLocalServiceUtil.addGuestbook(serviceContext.getUserId(), "Main", serviceContext);
				
				guestbookId = guestbook.getGuestbookId();
			}
			
			if (!(guestbookId > 0)) {
				guestbookId = guestbooks.get(0).getGuestbookId();
			}
			
			renderRequest.setAttribute("guestbookId", guestbookId);
		}catch (Exception e) {
			throw new PortletException(e);
		}
		
		super.render(renderRequest, renderResponse);
	}
}
