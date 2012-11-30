package org.smarthome.shared.utils;

import org.w3c.dom.Element;

public class XmlUtils {
	
	public static Element getFirstChildElement(Element parent, String childName) {
        // Get first child with given name
        Element firstChild = null;
        if (parent.getElementsByTagName(childName) != null) {
            firstChild = (Element) parent.getElementsByTagName(childName).item(0);
        }
        return firstChild;
    }
}
