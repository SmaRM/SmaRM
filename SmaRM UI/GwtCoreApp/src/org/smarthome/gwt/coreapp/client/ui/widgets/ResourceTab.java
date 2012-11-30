package org.smarthome.gwt.coreapp.client.ui.widgets;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.smarthome.gwt.coreapp.shared.DataEntity;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.SummaryFunction;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class ResourceTab extends VLayout {

    private static final DateTimeFormat dateformat = DateTimeFormat.getFormat("yyyy-MM-dd");
    private static final DateTimeFormat timeformat = DateTimeFormat.getFormat("HH:mm:ss");

    private final ListGrid resourceGrid;
    private final DataEntity gridDataEntity;

    private final Label lblLastUpdated = new Label();

    private Map<Class<?>, ListGridFieldType> typeMap = new HashMap<Class<?>, ListGridFieldType>();

    public ResourceTab(DataEntity dataEntity) {
        super();

        gridDataEntity = dataEntity;

        initTypeMap();

        resourceGrid = initResourceGrid();

        HLayout hLayout = new HLayout();
        hLayout.setWidth100();

        final IButton btnNewEntry = new IButton("New Entry");
        btnNewEntry.setWidth(110);
        btnNewEntry.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                resourceGrid.startEditingNew();
            }
        });

        hLayout.addMember(btnNewEntry);

        addMember(resourceGrid);
        addMember(hLayout);
    }

    private ListGrid initResourceGrid() {
        // Initialize empty DataEntry object to access its field types via getProperties()
        gridDataEntity.init();

        final ListGrid newGrid = new ListGrid() {
            @Override
            public void removeRecordClick(int rowNum) {
                super.removeRecordClick(rowNum);
            };
        };

        newGrid.setWidth100();
        newGrid.setHeight100();
        newGrid.setShowAllRecords(true);
        newGrid.setCanResizeFields(true);
        newGrid.setCanRemoveRecords(true);
        newGrid.setCanEdit(true);
        newGrid.setShowGridSummary(true);
        newGrid.setDatetimeFormatter(DateDisplayFormat.TOSERIALIZEABLEDATE);

        Map<String, Object> recordProperties = gridDataEntity.getRecordProperties();
        ListGridField[] listGridFields = new ListGridField[recordProperties.size()];

        int i = 0;
        for (Map.Entry<String, Object> recordProperty : recordProperties.entrySet()) {
            String fieldName = recordProperty.getKey();
            ListGridField listGridField = new ListGridField(fieldName, fieldName);
            ListGridFieldType listGridFieldType = typeMap.get(recordProperty.getValue().getClass());
            listGridField.setType(listGridFieldType);

            // Set custom cell formatter
            setCellFormatter(fieldName, listGridField);

            listGridFields[i++] = listGridField;
        }
        newGrid.setFields(listGridFields);
        return newGrid;
    }

    private void setCellFormatter(final String fieldName, ListGridField listGridField) {
        final String fieldNameLower = fieldName.toLowerCase();

        if (fieldNameLower.contains("date")) {
            listGridField.setCellFormatter(new CellFormatter() {
                @Override
                public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
                    if (value == null)
                        return null;

                    return dateformat.format((Date) value);
                }
            });
        } else if (fieldNameLower.contains("time")) {
            listGridField.setCellFormatter(new CellFormatter() {
                @Override
                public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
                    if (value == null)
                        return null;

                    return timeformat.format((Date) value);
                }
            });
        } else if (!fieldNameLower.equals("id")) {
            listGridField.setShowGridSummary(true);
            listGridField.setSummaryFunction(new SummaryFunction() {
                public Object getSummaryValue(Record[] records, ListGridField field) {
                    Set<String> uniqueActivities = new HashSet<String>();
                    for (int i = 0; i < records.length; i++) {
                        Record record = records[i];
                        uniqueActivities.add(record.getAttribute(fieldName));
                    }
                    return uniqueActivities.size();
                }
            });
        }
    }

    public void addEntity(DataEntity entity) {
        if (entity.getClass().equals(gridDataEntity.getClass())) {
            Map<String, Object> recordProperties = entity.getRecordProperties();
            Record newRecord = new Record(recordProperties);
            resourceGrid.addData(newRecord);
        }
    }

    public void addEntities(Collection<DataEntity> entities) {
        for (DataEntity entity : entities) {
            addEntity(entity);
        }
    }
    
    public void clearEntities() {
        resourceGrid.setData(new ListGridRecord[] {});
    }

    public void setLastUpdated(Date datetime) {
        lblLastUpdated.setContents("Last updated: " + dateformat.format(datetime) + ", " + timeformat.format(datetime));
    }

    private void initTypeMap() {
        typeMap.put(String.class, ListGridFieldType.TEXT);
        typeMap.put(Integer.class, ListGridFieldType.INTEGER);
        typeMap.put(Float.class, ListGridFieldType.FLOAT);
        typeMap.put(Double.class, ListGridFieldType.FLOAT);
        typeMap.put(Date.class, ListGridFieldType.DATE);
        typeMap.put(Boolean.class, ListGridFieldType.BOOLEAN);
        typeMap.put(Date.class, ListGridFieldType.TIME);
    }
}
