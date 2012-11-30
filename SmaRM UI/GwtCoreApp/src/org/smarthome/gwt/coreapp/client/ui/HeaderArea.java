package org.smarthome.gwt.coreapp.client.ui;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.HLayout;

public class HeaderArea extends HLayout {

	private static final int HEADER_AREA_HEIGHT = 60;
	
	public HeaderArea() {
		super();

		setHeight(HEADER_AREA_HEIGHT);
		setWidth100();
		
		Img hfuLogo = new Img("hfu_logo.png", 142, 60);
		Img unbLogo = new Img("unb_logo.png", 56, 60);
		hfuLogo.setMargin(8);
        unbLogo.setMargin(8);
		
		Label name = new Label();
		name.setOverflow(Overflow.HIDDEN);
		name.setContents("SmartHome Resource Manager");
	    
	    HLayout westLayout = new HLayout();
	    westLayout.setHeight(HEADER_AREA_HEIGHT);
	    westLayout.setWidth("85%");
	    westLayout.addMember(unbLogo);
	    westLayout.addMember(hfuLogo);
	    westLayout.addMember(name);
	    
		Label copyrightInfo = new Label();
		copyrightInfo.setAlign(Alignment.RIGHT);
		copyrightInfo.setWidth("100%");
		copyrightInfo.setContents("<b>Christian Fabbricatore, &copy; 2012</b>");
		copyrightInfo.setMargin(12);

	    HLayout eastLayout = new HLayout();
	    eastLayout.setAlign(Alignment.RIGHT);
	    eastLayout.setHeight(HEADER_AREA_HEIGHT);
	    eastLayout.setWidth("15%");
	    eastLayout.addMember(copyrightInfo);
	    
		this.addMember(westLayout);
		this.addMember(eastLayout);
	}

}
