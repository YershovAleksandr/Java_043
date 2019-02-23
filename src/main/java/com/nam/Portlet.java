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

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;
import com.nam.model.Entry;

/**
 * @author hp
 *
 */
public class Portlet extends MVCPortlet{
	
	static {
		System.out.println("Hello World!! привет мир!!");
	}
	
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException{
		System.out.println("->");
		super.doView(renderRequest, renderResponse);
		System.out.println("<-");
	}
	
	public void addEntry(ActionRequest request, ActionResponse response) {
		System.out.println("Add entry!!!!");
		
		try {
			PortletPreferences prefs = request.getPreferences();
			
			String[] guestbookEntries = prefs.getValues("guestbook-entries", new String[1]);
			
			ArrayList<String> entries = new ArrayList<String>();
			
			if (guestbookEntries != null) {
				entries = new ArrayList<String>(Arrays.asList(prefs.getValues("guestbook-entries", new String[1])));
			}
			
			String userName = ParamUtil.getString(request, "name");
			String message = ParamUtil.getString(request, "message");
			String entry = userName + "^" + message;
			
			entries.add(entry);
			
			String[] array = entries.toArray(new String[entries.size()]);
			
			prefs.setValues("guestbook-entries", array);
			
			try {
				prefs.store();
			}catch (IOException ex) {
				Logger.getLogger(Portlet.class.getName()).log(Level.SEVERE, "WTF?", ex);
			}catch (ValidatorException ex) {
				Logger.getLogger(Portlet.class.getName()).log(Level.SEVERE, "WTF??", ex);
			}
		}catch(ReadOnlyException ex) {
			Logger.getLogger(Portlet.class.getName()).log(Level.SEVERE, "WTF???", ex);
		}
	}
	
	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException, IOException{
		PortletPreferences prefs = renderRequest.getPreferences();
		
		String[] guestbookEntries = prefs.getValues("guestbook-entries", new String[1]);
		
		if (guestbookEntries != null) {
			List<Entry> entries = parseEntries(guestbookEntries);
			
			renderRequest.setAttribute("entries", entries);
		}
		
		super.render(renderRequest, renderResponse);
	}
	
	private List<Entry> parseEntries(String[] guestbookEntries){
		ArrayList<Entry> entries = new ArrayList<Entry>();
		
		for (String entry: guestbookEntries) {
			String[] parts = entry.split("\\^", 2);
			
			Entry gbEntry = new Entry(parts[0], parts[1]);
			entries.add(gbEntry);
		}
		
		return entries;
	}
}
