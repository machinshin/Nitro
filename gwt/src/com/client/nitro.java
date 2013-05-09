package com.client;

import com.google.gwt.user.client.ui.RootPanel;
import com.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.visualization.client.visualizations.LineChart;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.visualizations.LineChart.Options;
import com.google.gwt.user.client.ui.DisclosurePanel;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Method;

import java.util.List;
import com.server.model.*;
import com.server.rest.*;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class nitro implements EntryPoint {
  /**
   * The message displayed to the user when the server cannot be reached or
   * returns an error.

  private static final String SERVER_ERROR = "An error occurred while "
      + "attempting to contact the server. Please check your network "
      + "connection and try again.";
*/
    private static final String JSON_URL = "http://localhost:9000/api/";

    private Button submitButton           = new Button("Submit");
    private TextBox businessIdTB          = new TextBox();
    private HorizontalPanel hp1           = new HorizontalPanel();
    private HorizontalPanel hp2           = new HorizontalPanel();
    private Label yelpIdLabel             = new Label("Yelp Business Id");
    private Label errorMsgLabel           = new Label();
    private LineChart chart;//               = new LineChart();
    private DisclosurePanel ratingsPanel  = new DisclosurePanel();
    private HorizontalPanel ratingsHolder = new HorizontalPanel();
  
/**
   * If can't get JSON, display error message.
   * @param error
   */
  private void displayError(String error) {
    errorMsgLabel.setText("Error: " + error);
    errorMsgLabel.setVisible(true);
  }
  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {

    yelpIdLabel.addStyleName("bizIdHeader");
    hp1.add(yelpIdLabel);
    hp1.add(businessIdTB);
    hp2.add(submitButton);

    RootPanel.get("get_biz_id").add(hp1);
    RootPanel.get("get_biz_id").add(hp2);
    //RootPanel.get("get_biz_id").add(feeds);

    errorMsgLabel.setStyleName("errorMessage");
    errorMsgLabel.setVisible(false);

    RootPanel.get("get_biz_id").add(errorMsgLabel);

    businessIdTB.setFocus(true);

    submitButton.addClickHandler( new ClickHandler(){
        public void onClick(ClickEvent event) {
            submitBusinessId();
        }
    });

    submitButton.addKeyPressHandler( new KeyPressHandler(){
        public void onKeyPress(KeyPressEvent event) {
            if( event.getCharCode() == KeyCodes.KEY_ENTER ) {
                submitBusinessId();
            }
        }
    });

  }

  private void submitBusinessId() {
    final String businessId = businessIdTB.getText().trim();
    businessIdTB.setFocus(true);
    Window.alert("We got signal!");
    ApiEntry.Util.get().getAll(businessId,
        new MethodCallback<NitroJsonWrapper>() {
            @Override
            public void onSuccess( Method method, NitroJsonWrapper object ){
                Window.alert("We got signal!");
                /*

                VerticalPanel result = new VerticalPanel();
                Options options = Options.create();
                options.setHeight(240);
                options.setTitle("Tweets Over Time");
                options.setWidth(400);
                AxisOptions vAxisOptions = AxisOptions.create();
                vAxisOptions.setMinValue(0);
                vAxisOptions.setMaxValue(5);
                options.setVAxisOptions(vAxisOptions);

                DataTable data = getDataTable(object);
                LineChart viz =  new LineChart(data, options);
                //viz.addReadHandler();
                //viz.addOnMouseOverHandler();
                //viz.addOnMouseOutHandler();

                Label status = new Labe();
                Label onMouseOverAndOutStatus = new Labe();

                result.add(status);
                result.add(viz);
                RootPanel.get("show_ui").add(viz);
            */
            }
        private AbstractDataTable getDataTable( NitroJsonWrapper  object ) {
            DataTable tab = DataTable.create();
            tab.addColumn(ColumnType.DATE, "date");
            tab.addColumn(ColumnType.NUMBER, "numTweets");
            List<Tweets> tweets = object.tweets;
            tab.addRows(tweets.size());
            for( int i = 0; i < tweets.size(); ++i) {
                tab.setValue(i, 0, tweets.get(i).numTweets);
                tab.setValue(i, 1, tweets.get(i).timestamp);
            }
            return tab;
        }
        @Override
        public void onFailure( Method method, Throwable e) {
            Window.alert("js exception: " + e);

        }

    });
  }
}
