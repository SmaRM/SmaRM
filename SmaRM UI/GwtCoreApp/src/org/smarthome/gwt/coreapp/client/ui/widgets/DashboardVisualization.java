package org.smarthome.gwt.coreapp.client.ui.widgets;

import java.util.Collection;
import java.util.HashSet;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.Query;
import com.google.gwt.visualization.client.Query.Callback;
import com.google.gwt.visualization.client.QueryResponse;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.corechart.CoreChart;
import com.google.gwt.visualization.client.visualizations.corechart.LineChart;
import com.google.gwt.visualization.client.visualizations.corechart.Options;
import com.google.gwt.visualization.client.visualizations.corechart.PieChart;
import com.smartgwt.client.util.SC;

public class DashboardVisualization extends VerticalPanel {

    private Collection<Widget> activeCharts = new HashSet<Widget>();

    // Create a callback to be called when the visualization API
    // has been loaded.
    private Runnable pieChartLoadCallback = new Runnable() {
        public void run() {
            // Create a pie chart visualization.
            String dataUrl = "http://spreadsheets.google.com/tq?key=pWiorx-0l9mwIuwX5CbEALA&range=A1:B12&gid=0&headers=-1";
            // Create a query to go to the above URL.
            Query query = Query.create(dataUrl);
            // Send the query.
            query.send(new Callback() {
                public void onResponse(QueryResponse response) {
                    if (response.isError()) {
                        SC.warn(response.getMessage());
                    } else { // Get the data from the QueryResponse.
                        // Remove active charts from panel
                        for (Widget chart : activeCharts) {
                            remove(chart);
                        }

                        final HorizontalPanel hPanelPieCharts = new HorizontalPanel();
                        hPanelPieCharts.setWidth("100%");
                        hPanelPieCharts.setHeight("50%");
                        
                        final PieChart pieChartExample = new PieChart(response.getDataTable(), createSimpleChartOptions("Example Graph"));
                        hPanelPieCharts.add(pieChartExample);
                        
                        final PieChart pieChartConumptionByActivity = new PieChart(createExampleDataTable(), createSimpleChartOptions("Consumption by Activity"));
                        hPanelPieCharts.add(pieChartConumptionByActivity);
                        
                        final HorizontalPanel hPanelLineCharts = new HorizontalPanel();
                        hPanelPieCharts.setWidth("100%");
                        hPanelPieCharts.setHeight("50%");
                                                
                        final LineChart lineChartConcumptionByMonth = new LineChart(createExampleDataTable2(), createSimpleChartOptions("Costs by Month"));
                        hPanelLineCharts.add(lineChartConcumptionByMonth);
                        
                        activeCharts.add(hPanelPieCharts);
                        activeCharts.add(hPanelLineCharts);
                        add(hPanelPieCharts);
                        add(hPanelLineCharts);
                    }
                }
            });
        }
    };

    public DashboardVisualization() {
        super();

        setHeight("100%");
        setWidth("100%");

        // Load the visualization API, passing the onLoadCallback to be called when loading is done.
        VisualizationUtils.loadVisualizationApi(pieChartLoadCallback, CoreChart.PACKAGE);
    }

    public void refresh() {
        // Load the visualization API, passing the onLoadCallback to be called when loading is done.
        VisualizationUtils.loadVisualizationApi(pieChartLoadCallback, CoreChart.PACKAGE);
    }

    private Options createSimpleChartOptions(String title) {
        Options options = Options.create();
        options.setWidth(400);
        options.setHeight(240);
        options.setTitle(title);
        return options;
    }

    private AbstractDataTable createExampleDataTable() {
        DataTable data = DataTable.create();
        data.addColumn(ColumnType.STRING, "Activity");
        data.addColumn(ColumnType.NUMBER, "Energy Consumption");
        data.addColumn(ColumnType.NUMBER, "Water Consumption");

        data.addRows(3);
        data.setValue(0, 0, "Working");
        data.setValue(0, 1, 0.4);
        data.setValue(0, 2, 0.01);
        data.setValue(1, 0, "Shower");
        data.setValue(1, 1, 4.3);
        data.setValue(1, 2, 1.2);
        data.setValue(1, 0, "Cooking");
        data.setValue(1, 1, 2.9);
        data.setValue(1, 2, 1.8);
        data.setValue(2, 0, "Housework");
        data.setValue(2, 1, 1.05);
        data.setValue(2, 2, 0.80);
        return data;
    }
    
    private AbstractDataTable createExampleDataTable2() {
        DataTable data = DataTable.create();
        data.addColumn(ColumnType.STRING, "Month");
        data.addColumn(ColumnType.NUMBER, "Costs");
        
        data.addRows(12);
        data.setValue(0, 0, "Jan");
        data.setValue(0, 1, 40);
        data.setValue(1, 0, "Feb");
        data.setValue(1, 1, 47.5);
        data.setValue(2, 0, "Mar");
        data.setValue(2, 1, 38.4);
        data.setValue(3, 0, "Apr");
        data.setValue(3, 1, 37.9);
        data.setValue(4, 0, "May");
        data.setValue(4, 1, 28.4);
        data.setValue(5, 0, "Jun");
        data.setValue(5, 1, 32);
        data.setValue(6, 0, "Jul");
        data.setValue(6, 1, 28);
        data.setValue(7, 0, "Aug");
        data.setValue(7, 1, 17);
        data.setValue(8, 0, "Sept");
        data.setValue(8, 1, 19);
        data.setValue(9, 0, "Oct");
        data.setValue(9, 1, 24.9);
        data.setValue(10, 0, "Nov");
        data.setValue(10, 1, 32.3);
        data.setValue(11, 0, "Dec");
        data.setValue(11, 1, 38.4);
        return data;
    }
}
