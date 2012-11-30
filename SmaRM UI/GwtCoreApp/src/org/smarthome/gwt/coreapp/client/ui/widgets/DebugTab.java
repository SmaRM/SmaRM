package org.smarthome.gwt.coreapp.client.ui.widgets;

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;

public class DebugTab extends SectionStack {

    final TextAreaItem dbg1EditingArea = new TextAreaItem();

    public DebugTab() {

        this.setWidth100();
        this.setVisibilityMode(VisibilityMode.MUTEX);
        this.setShowExpandControls(false);
        this.setAnimateSections(true);

        SectionStackSection section1 = new SectionStackSection("Machine Learning");
        section1.setExpanded(true);

        final DynamicForm debugForm1 = new DynamicForm();
        debugForm1.setWidth100();

        dbg1EditingArea.setTitle("Debug");
        dbg1EditingArea.setTitleOrientation(TitleOrientation.TOP);
        dbg1EditingArea.setWidth(800);
        dbg1EditingArea.setHeight(600);

        section1.addItem(debugForm1);

        SectionStackSection section2 = new SectionStackSection("TabSection2");
        section2.setExpanded(false);
        HTMLFlow htmlFlow2 = new HTMLFlow();
        htmlFlow2.setOverflow(Overflow.AUTO);
        htmlFlow2.setPadding(10);
        htmlFlow2.setContents("TabSection2");
        section2.addItem(htmlFlow2);

        SectionStackSection section3 = new SectionStackSection("TabSection3");
        section3.setExpanded(false);
        HTMLFlow htmlFlow3 = new HTMLFlow();
        htmlFlow3.setOverflow(Overflow.AUTO);
        htmlFlow3.setPadding(10);
        htmlFlow3.setContents("TabSection3");
        section3.addItem(htmlFlow3);

        this.addSection(section1);
        this.addSection(section2);
        this.addSection(section3);

    }

    public void clearDbg1Output() {
        dbg1EditingArea.clearValue();
    }

    public void setText(String text) {
        dbg1EditingArea.setValue(text);
    }

    public void append(String text) {
        String content = dbg1EditingArea.getValueAsString();
        content += text;
        setText(content);
    }

}
