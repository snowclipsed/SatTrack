/**
 *
 *
 * NOTE : IF THIS FAILS TO RUN PLEASE CONTACT ME.
 * <p>
 * IF APPTest SHOWS AN ERROR, DELETE THE JUNIT TEST, IT HAS ISSUES WITH GRADLE!
 * <p>
 * ---
 * <p>
 * BEFORE STARTING:
 * <p>
 * Go to {@link com.example.app.Constants} and put in your API KEYS!!
 * <p>
 *
 * --
 * Welcome to the main or App class.
 * This class uses an ARCGiS runtime communicating with a local JavaFX Scene to display a map of the Earth.
 * ARCGiS can also display feature layers and graphics on top of the map using JavaFX and built in support for graphics
 * in its API.
 * <p>
 * Read more about ARCGiS here : @see <a href = "https://developers.arcgis.com/java/"> ARCGiS Documentation </a>
 * Read more on JavaFX here: @see <a href = "https://openjfx.io/">OpenJFX</a>
 * <p>
 * You may notice that the imports are divided into various blocks. It's because this makes it easier to identify
 * libraries when you are debugging.
 * <p>
 * Each block of the libraries serves its own purpose. The first block is Graphics related ARCGiS Libraries,
 * like Point and Graphic.
 * <p>
 * The second block is used to render the map.
 * <p>
 * The third block is JavaFX.
 *
 */



package com.example.app;
// Graphics Block
import com.esri.arcgisruntime.concurrent.ListenableFuture;
import com.esri.arcgisruntime.geometry.*;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.symbology.PictureMarkerSymbol;
//import com.esri.arcgisruntime.symbology.SimpleMarkerSceneSymbol;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import com.esri.arcgisruntime.symbology.TextSymbol;

//Maps Block
import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.BasemapStyle;
import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.tasks.geocode.GeocodeParameters;
import com.esri.arcgisruntime.tasks.geocode.GeocodeResult;
import com.esri.arcgisruntime.tasks.geocode.LocatorTask;

//import java.security.Timestamp;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
import java.util.*;

//JavaFX block
//import com.esri.arcgisruntime.tasks.geocode.LocatorTask;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.control.Slider;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

/**
 * {@link App} :
 * It is a class which extends a JavaFX {@link Application} and is the base for the entire project. This class is where a JavaFX {@link Scene} is created.
 *
 * <br><br>
 * {@link MapView}:
 * A user interface control that displays two-dimensional (2D) geographic content defined by an ArcGISMap.
 * A MapView is a user interface that displays ArcGISMap layers and graphics in 2D. It controls the area (extent) of the ArcGISMap that is visible and supports user interactions such as pan and zoom. A map view also provides access to the underlying layer data in a map.
 * <br><br>
 * {@link App#satID} : It is a global variable storing the NORAD ID of a website and can be changed using {@link App#returnNORADID(String)}.
 * It is input into {@link App#satAPICall(Integer)} which calls the N2YO Api @see <a href = "https://www.n2yo.com/api/"> N2YO API </a> to get the location
 * and relative position of the satellite from the observer.
 *<br><br>
 * {@link  App#scale}: It is a global variable storing the zoom scale of the map. Its value is controlled by a slider in the UI rendered using {@link Slider}.
 * Its value is initialized to 700000000.0. To change the value of the scaling, you can change the Slider properties (discussed in its own section below).
 *<br><br>
 * <p>
 * {@code App#timer}:
 * Instance of {@link Timer} used for scheduling tasks, specifically updating the satellite's position at regular intervals.
 * <p>
 * {@code App#countdownSeconds}:
 * Number of seconds for the countdown timer. Used in {@link App#countdown(Text)} to update the time remaining till the next update.
 *
 * <p>
 * {@code App#locatorTask}:
 * Instance of {@link LocatorTask} for geocoding addresses. Used in {@link App#locate(String, Graphic, Text, Text, Text, Text)}.
 *
 * <p>
 * {@code App#geocodeParameters}:
 * Instance of {@link GeocodeParameters} specifying parameters for geocoding. Used in {@link App#locate(String, Graphic, Text, Text, Text, Text)}.
 *
 * <p>
 * {@code App#address}:
 * Stores the address input by the user for geocoding. Used in {@link App#locate(String, Graphic, Text, Text, Text, Text)}.
 *
 * <p>
 * {@code App#pass}:
 * Array storing information about the satellite pass, such as starting time, max elevation, and end time.
 * Used in {@link App#locate(String, Graphic, Text, Text, Text, Text)} to update information after geocoding.
 *
 * <p>
 * {@code App#addressCoords}:
 * Array storing the latitude and longitude coordinates of a given address. Used in {@link App#locate(String, Graphic, Text, Text, Text, Text)}.
 *
 * <p>
 * {@code App#addressLocation}:
 * Instance of {@link Point} representing the location of an address. Used in {@link App#locate(String, Graphic, Text, Text, Text, Text)} to update the satellite's position.
 *
 * @author Hardik Bishnoi
 * @version 1.4
 * @since 1.0
 */
public class App extends Application {

    private MapView mapView;
    private Integer satID = 25544;
    private Double scale = 70000000.0;
    private Integer frequency = 10000;
    private final Integer countdownSeconds = 11;
    private Timer timer;
    private LocatorTask locatorTask;
    private GeocodeParameters geocodeParameters;

    private String address;

    String[] pass;

    private Double[] addressCoords = new Double[]{43.66166026255957, -70.2466777012979};

    private Point addressLocation = new Point(addressCoords[1], addressCoords[0], SpatialReferences.getWgs84());

    public static void main(String[] args) {
        Application.launch(args);
    }

    /**
     *
     * It is a function which starts the execution of a JavaFX Application.
     * It has various parameters and functions which control various aspects of the application.
     *
     *
     * @param stage The primary stage for this application, onto which
     * the application scene can be set. A stage is a top-level container object which represents a window.
     * It acts as a container for the JavaFX UI elements. The start program takes the stage as a parameter
     * and renders UI elements into that stage.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     * <br><br>
     *
     * @throws URISyntaxException Checked exception thrown to indicate that a string could not be parsed as a URI reference.
     * @throws IOException Signals that an I/O exception of some sort has occurred. This class is the general class of exceptions produced by failed or interrupted I/O operations.
     * @throws InterruptedException Thrown when a thread is waiting, sleeping, or otherwise occupied, and the thread is interrupted, either before or during the activity.
     *
     *
     *
     */

    @Override
    public void start(Stage stage) throws URISyntaxException, IOException, InterruptedException {
        this.timer = new Timer();
        // A string array of satellites we want to show.

        /**
         * {@link String} <b>{@link satList}</b> - Provides a list of satellites that will appear on the satellite tracker as options in the {@link ComboBox} combo_box.
         *      *              The listener of combo_box is directly linked to returnNORADID() which queries the satellite names and returns their NORAD ID (explanation in the method's section).
         *      *              This NORAD ID is used by satAPICall() to make a call to the <a href="https://www.n2yo.com/api/"> NY20 Satellite Position API </a>
         *      * <br><br>
         *      *
         */

        String[] satList =
                {
                        "ISS",
                        "Hubble Space Telescope",
                        "IRIDIUM 167",
                        "STARLINK-30783"
                };


        Rectangle whiteBox = new Rectangle(300, 450, Color.rgb(255, 255, 255, 0.5));
        whiteBox.setStroke(Color.BLACK);
        whiteBox.setStrokeWidth(2);


        Text UIheading = new Text("Control Panel");
        UIheading.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        UIheading.setFill(Color.BLACK);

        Text comboBoxHeading = new Text("Popular Satellites");
        comboBoxHeading.setFont(new Font(15));


        Text searchByNORAD = new Text("Find by NORAD ID");
        searchByNORAD.setFont(new Font(15));

        Text addressHeading = new Text("Get pass results for Address");
        addressHeading.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        addressHeading.setFill(Color.BLACK);

        Text satname = new Text("Satellite Name: " + "ISS");
        Text passStart = new Text("""
                Starting Time:

                Azimuth:\s
                Elevation:
                Direction:\s""");
        passStart.setFont(new Font(10));
        Text passMax = new Text("""
                Max Elevation At:

                Azimuth:\s
                Elevation:
                Direction:\s""");
        passMax.setFont(new Font(10));
        Text passEnd = new Text("""
                Pass End At:

                Azimuth:\s
                Elevation
                Direction:\s""");
        passEnd.setFont(new Font(10));


        Text timeheading = new Text("Time remaining till next update:");
        timeheading.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        timeheading.setFill(Color.WHITE);
        Text timeremaining = new Text(countdownSeconds.toString());
        timeremaining.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        timeremaining.setFill(Color.WHITE);



        //Create a combo box to switch satellites
        /**
         *      * {@link ComboBox} <b>combo_box</b> - Creates a JavaFX ComboBox UI Element on the screen which functions as a dropdown
         *      *              and allows the user to select the desired satellite to view. The list is queried from the String Array satList.
         *      *<br><br>
         *      *              combo_box also has a listener function which listens constantly for any user input and
         *      *              uses that input to call returnNORADID() to get the NORAD ID of the selected satellite,
         *      *              and set satID as that new NORAD ID.
         *      *<br><br>
         *      *              Then, setpoint() is called to instantly update the location of the point which shows the selected
         *      *              satellite's current position (which otherwise is set to update every 5 seconds)
         */


        ComboBox<String> combo_box =
                new ComboBox<>(FXCollections
                        .observableArrayList(satList));
        combo_box.setValue("ISS"); // Default Value set to ISS to not crash the system when it boots.


        // Add a slider to control zoom
        Slider slider = new Slider(0, 70000000, 70000000);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setMajorTickUnit(10000000f); // Set how much is a big tick.
        slider.setBlockIncrement(10000f);
        slider.setMaxSize(30,500);
        slider.setOrientation(Orientation.VERTICAL);
        slider.setSnapToPixel(false);
        slider.setLayoutX(250);
        slider.setLayoutX(100);

        TextField NORADinput = new TextField();
        NORADinput.setPromptText("Enter NORAD ID");
        NORADinput.setMaxWidth(175);
        Button saveButton = new Button("Search ID");

        TextField addressInput = new TextField();
        addressInput.setPromptText("Enter Address");
        addressInput.setMaxWidth(175);
        Button searchAddress = new Button("Search Passes");

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        // Add the title to the window
        stage.setTitle("Satellite Tracker");
        stage.setFullScreen(true);
        stage.setX(bounds.getMinX());
        stage.setY(bounds.getMinY());
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());
        stage.getIcons().add(new Image("https://github.com/snowclipsed/SatTrack/blob/master/src/Images/icon.png?raw=true"));

        stage.show();

        /**
         * * {@link StackPane} - This application uses the StackPane format. A StackPane is a JavaFX {@link javafx.scene.layout.Pane}
         *      *              implemented in a stack format which allows nodes initialized on the pane to be at the bottom,
         *      *              meaning it follows a LIFO format. A Pane is simply a JavaFX container which manages various UI nodes
         *      *              and their placement inside the stage. Multiple Panes can exist in a single JavaFX stage.
         *      *              <br><br>
         *      *              We can initialize a StackPane through (example): <br>
         *      *                  {@code
         *      *
         *      *                  StackPane stackPane = new StackPane();
         *      *
         *      *                  }
         *      *              <br><br>
         *      *              We can also add child nodes to the pane using (example):
         *      *              {@code
         *      *              mapView = new MapView();
         *      *              stackPane.getChildren().add(mapView); // In this case we add a MapView node to the stack.
         *      *              }
         *      *              <br><br>
         *      *              Since a pane controls the position of the child nodes (UI elements) on the Stage,
         *      *              we can control the relative position of the child nodes through a method, example :
         *      *              <br>
         *      *              {@code
         *      *                  StackPane.setAlignment(childNode, Pos.CENTER_LEFT);
         *      *                  // where childNode is a UI element like MapView, Slider or ComboBox
         *      *                  // this example puts the node passed as the argument into the CENTER_LEFT position.
         *      *              }
         *      * <br><br>
         *      *              Here, {@link Pos} is an Enum which contains a list of positions relative to the window.
         *      * <br><br>
         *      *
         */

        StackPane stackPane = new StackPane();
        Scene scene = new Scene(stackPane);
        stage.setScene(scene);

        /**
         * References API key from constants.
         * I do not recommend putting your key directly here for safety reasons.
         */
        String yourApiKey = Constants.ARCAPI;
        ArcGISRuntimeEnvironment.setApiKey(yourApiKey);

        /**
         * {@link MapView} mapView - It is a UI node attributable to a pane that displays a ESRI map element.
         * Essentially, it attaches the ARCGIS map to a renderable UI element that can be displayed on a Scene.
         *
         * One can add MapView to the pane using the {@code .getChildren()} method as done below.
         */
        mapView = new MapView();
        stackPane.getChildren().addAll(mapView,whiteBox,UIheading,comboBoxHeading,combo_box,slider,searchByNORAD,NORADinput,saveButton, addressHeading, addressInput, searchAddress, satname, passStart, passMax, passEnd, timeheading, timeremaining);
        StackPane.setAlignment(whiteBox,Pos.TOP_RIGHT);
        StackPane.setAlignment(UIheading, Pos.TOP_RIGHT);
        StackPane.setAlignment(slider,Pos.CENTER_LEFT);
        StackPane.setAlignment(comboBoxHeading, Pos.TOP_RIGHT);
        StackPane.setAlignment(combo_box, Pos.TOP_RIGHT);
        StackPane.setAlignment(searchByNORAD, Pos.TOP_RIGHT);
        StackPane.setAlignment(NORADinput, Pos.TOP_RIGHT);
        StackPane.setAlignment(saveButton, Pos.TOP_RIGHT);
        StackPane.setAlignment(addressHeading, Pos.TOP_RIGHT);
        StackPane.setAlignment(searchAddress, Pos.TOP_RIGHT);
        StackPane.setAlignment(addressInput, Pos.TOP_RIGHT);
        StackPane.setAlignment(satname, Pos.TOP_LEFT);
        StackPane.setAlignment(passStart, Pos.TOP_LEFT);
        StackPane.setAlignment(passMax, Pos.TOP_LEFT);
        StackPane.setAlignment(passEnd, Pos.TOP_LEFT);
        StackPane.setAlignment(timeheading, Pos.BOTTOM_RIGHT);
        StackPane.setAlignment(timeremaining, Pos.BOTTOM_RIGHT);

        StackPane.setMargin(whiteBox, new Insets(70, 40, 0, 0));
        StackPane.setMargin(UIheading, new Insets(75, 125, 0, 0));
        StackPane.setMargin(slider, new Insets(100));
        StackPane.setMargin(comboBoxHeading, new Insets(100, 135, 0, 0));
        StackPane.setMargin(combo_box, new Insets(125, 100, 0,0));
        StackPane.setMargin(searchByNORAD, new Insets(160, 135, 0, 0));
        StackPane.setMargin(NORADinput, new Insets(185, 100, 0, 0));
        StackPane.setMargin(saveButton, new Insets(220, 155, 0, 0));
        StackPane.setMargin(addressHeading, new Insets(260, 55, 0, 0));
        StackPane.setMargin(addressInput, new Insets(290, 100, 0, 0));
        StackPane.setMargin(searchAddress, new Insets(320, 145, 0, 0));
        StackPane.setMargin(satname, new Insets(350, 0, 75, 1000));
        StackPane.setMargin(passStart, new Insets(370, 0, 75, 950));
        StackPane.setMargin(passMax, new Insets(370, 0, 75, 1100));
        StackPane.setMargin(passEnd, new Insets(440, 0, 75, 1000));
        StackPane.setMargin(timeheading, new Insets(70, 40, 100, 0));
        StackPane.setMargin(timeremaining, new Insets(70, 175, 75, 0));

        // Make a map object and set its style
        ArcGISMap map = new ArcGISMap(BasemapStyle.ARCGIS_STREETS_NIGHT);

        // set map to mapView object
        mapView.setMap(map);
        //fetch initial coords for the point by doing an APICall for a chosen satID
        //default satID is for the ISS
        Float[] coords = satAPICall(satID);
        mapView.setViewpoint(new Viewpoint(coords[1], coords[0], scale));

        /**
         * {@link GraphicsOverlay} graphicsOverlay - It manages a collection of
         * ESRI JavaFX renderable {@link Graphic} objects that can be displayed on top of a
         * {@link MapView} object.
         *
         * A GraphicsOverlay object works like a StackPane, where objects rendered in a LIFO manner.
         *
         */
        GraphicsOverlay graphicsOverlay = new GraphicsOverlay();
        graphicsOverlay.setLabelsEnabled(true);

        /**
         * {@link Point} is a ESRI Geometry node and is a JavaFX renderable Point whose location
         * can be inputted in x and y spatial coordinates following a specific spatial reference pattern
         * like WGS84 or Mercator.
         *
         * @see <a href = "https://en.wikipedia.org/wiki/Mercator_projection"> Mercator Projection </a>
         * @see <a href = "https://en.wikipedia.org/wiki/World_Geodetic_System"> WGS84 </a>
         */
        Point point = new Point(coords[0], coords[1], SpatialReferences.getWgs84());
        // create a point geometry for the text below the image.
        Point textpoint = new Point(coords[0], coords[1]-3, SpatialReferences.getWgs84());


        PictureMarkerSymbol satmarker = new PictureMarkerSymbol("src/Images/satimage.png");
        satmarker.setHeight(40);
        satmarker.setWidth(40);

        SimpleMarkerSymbol markerSymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.ORANGE, 10);
        Graphic pointGraphic = new Graphic(addressLocation, markerSymbol);

        /**
         * sattext is a {@link TextSymbol} that initializes the text which tells the user which satellite is being displayed currently
         * on the screen.
         */
        TextSymbol sattext = new TextSymbol(20, "ISS", Color.SNOW, TextSymbol.HorizontalAlignment.CENTER,
                TextSymbol.VerticalAlignment.TOP);
        // add outline to the text so it is more visible.
        sattext.setHaloWidth(1);
        sattext.setHaloColor(Color.BLACK);

        // make ARCGiS graphics out of the symbols for text and image point

        Graphic sattextgraphic = new Graphic(textpoint, sattext);

        Graphic satpoint = new Graphic(point, satmarker);

        /**
         * A thread which calls the API every 10 seconds using setpoint() and then sets the point to the location of the moving satellite
         * again.
         */
        Thread updateThread = new Thread(() -> {
            while (true) {

                try {
                    setpoint(satpoint,sattextgraphic,mapView);
                    countdown(timeremaining);
                } catch (URISyntaxException | IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
//
                try {
                    Thread.sleep(frequency); // Update every 10000 milliseconds (10 seconds). Change this to change update time.
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        updateThread.start();

        /**
         * ComboBox listener for {@link combo_box} which changes the value of sattext to the selected satellite and
         * updates the position of the satellite point by calling setpoint for the new satellite.
         */
        combo_box.setOnAction((event) -> {
            String selection = combo_box.getValue();
            satID = returnNORADID(selection);
            System.out.println(selection);
            System.out.println(satID);

            try {
                setpoint(satpoint, sattextgraphic,mapView);
                countdown(timeremaining);
            } catch (URISyntaxException | IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
            sattext.setText(selection);

        });



        slider.valueProperty().addListener(new ChangeListener<Number>() {
            /**
             * listener for {@link Slider} which changes the value of
             * scale to zoom the map in and out.
             */
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                scale = (Double) newValue;
                mapView.setViewpointScaleAsync(scale);
            }
        });

        /**
         * Event handler for the saveButton action.
         * <p>
         * This event handler is triggered when the saveButton is pressed. It performs the following actions:
         * 1. Extracts the NORAD ID from the NORADinput TextField and sets it as the current satellite ID (satID).
         * 2. Calls the 'satNameCall' method to retrieve the name of the satellite associated with the given NORAD ID.
         * 3. Sets the point on the map using the 'setpoint' method and updates the graphical representation of the satellite
         *    using the 'sattextgraphic' on the provided MapView.
         * 4. Initiates a countdown using the 'countdown' method to display the remaining time for the satellite pass.
         * 5. Sets the text of the sattext Text object to display the name of the satellite.
         * <p>
         * In case of any exceptions during the execution of the above steps, a RuntimeException is thrown to indicate an error.
         *
         * @param e The ActionEvent triggered by the saveButton.
         * @throws RuntimeException If there is an issue with NORAD ID conversion, satellite name retrieval,
         *                          setting the map point, or initiating the countdown.
         * @see #satNameCall(int)
         * @see #setpoint(Point, Graphic, MapView)
         * @see #countdown(TextField)
         */
        saveButton.setOnAction(e -> {
            satID = Integer.valueOf(NORADinput.getText());
            String satName;
            try {
                satName = satNameCall(satID);
            } catch (URISyntaxException | IOException | InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            try {
                setpoint(satpoint, sattextgraphic, mapView);
                countdown(timeremaining);
            } catch (URISyntaxException | IOException | InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            sattext.setText(satName);
        });


        locatorcreator();

        searchAddress.setOnAction(event -> {
            address = addressInput.getText();
            System.out.println("This is the " + address);
            if (!address.isBlank()) {
                locate(address, pointGraphic, satname, passStart, passMax, passEnd);
            }
        });


        // add the point graphic to the graphics overlay
        graphicsOverlay.getGraphics().add(satpoint);
        graphicsOverlay.getGraphics().add(sattextgraphic);
        graphicsOverlay.getGraphics().add(pointGraphic);
        mapView.getGraphicsOverlays().add(graphicsOverlay);


    }


    /**
     * Sets a new coordinate for the position of an initialized satellite graphic and its text label.
     * This is done by initializing a new {@link Point} object with the input coordinates and then equating the current
     * satellite point to the new object by using .setGeometry.
     *
     * @param point -> point location Graphic object
     * @param text -> text Graphic object
     * @param mapView ->ARCGiS MapView Object
     */
    public void setpoint(Graphic point, Graphic text, MapView mapView) throws URISyntaxException, IOException, InterruptedException {
        Float[] newcoords;
        newcoords = satAPICall(satID);
        Point newpoint = new Point(newcoords[0], newcoords[1], SpatialReferences.getWgs84());
        Point newimagepoint = new Point(newcoords[0], newcoords[1]-3, SpatialReferences.getWgs84());
        point.setGeometry(newpoint);
        text.setGeometry(newimagepoint);
        mapView.setViewpoint(new Viewpoint(newcoords[1], newcoords[0], scale));
    }
    /**
     * Switch statement to select NORAD ID based on satellite name in dropdown
     *
     * @param satOption Satellite name in dropdown. Corresponds to switch statement.
     * @return ID, returns the right ID according to switch statement.
     */
    public Integer returnNORADID(String satOption){
       int ID;
            switch (satOption){
                case "ISS":
                    ID = 25544;
                    break;
                case "IRIDIUM 167":
                    ID = 43931;
                    break;
                case "STARLINK-30783":
                    ID = 58130;
                    break;
                case"Hubble Space Telescope":
                    ID = 20580;
                    break;
                default:
                    ID = 25544;
            }
        return ID;
    }

    /**
     * Makes an API call according to the NORAD ID provided by calling the {@link Satellite} class object.
     *
     * @param ID NORAD ID of Satellite object
     * @return {@link Satellite#satlat} {@link Satellite#satlong} satellite coords.
     * @throws URISyntaxException
     * @throws IOException
     * @throws InterruptedException
     */
    public Float[] satAPICall(Integer ID) throws URISyntaxException, IOException, InterruptedException {
        Satellite satellite = new Satellite(ID.toString(), "1", "43.66166026255957", "-70.2466777012979", Constants.N2YOAPI);
        return new Float[]{satellite.satlong,satellite.satlat};
    }

    public String satNameCall(Integer ID) throws URISyntaxException, IOException, InterruptedException {
        Satellite satellite = new Satellite(ID.toString(), "1", "43.66166026255957", "-70.2466777012979", Constants.N2YOAPI);
        return satellite.satname;
    }

    /**
     * Retrieves satellite pass information for a given satellite ID and observer's location.
     * <p>
     * This method makes an API call to the N2YO (www.n2yo.com) API to obtain detailed information about the
     * passes of a satellite with the specified ID as observed from a given location defined by latitude (obslat)
     * and longitude (obslong). The retrieved information includes the satellite name, pass start time, maximum
     * elevation time, pass end time, azimuth, elevation, and direction at various points in the pass, as well as
     * the total pass duration.
     *<p>
     * The method uses the Passes class from the N2YO API to encapsulate the pass information and then converts
     * the relevant timestamps into human-readable Date objects.
     *
     * @param ID      The NORAD satellite ID for which pass information is requested.
     * @param obslat  The observer's latitude for the pass calculation.
     * @param obslong The observer's longitude for the pass calculation.
     * @return An array of Strings representing different aspects of the satellite pass information:
     *         - pass[0]: Satellite Name
     *         - pass[1]: Starting time, Azimuth, Elevation, and Direction at pass start
     *         - pass[2]: Max Elevation time, Azimuth, Elevation, and Direction at maximum elevation
     *         - pass[3]: Pass end time, Azimuth, Elevation, and Direction at pass end
     *         - pass[4]: Duration of the pass
     * @throws URISyntaxException If there is an issue with the URI syntax while making the API call.
     * @throws IOException        If there is an issue with the IO operations while making the API call.
     * @throws InterruptedException If the execution is interrupted during the API call.
     * @see Passes
     * @see Constants#N2YOAPI
     */
    public String [] satPassCall(Integer ID, Double obslat, Double obslong) throws URISyntaxException, IOException, InterruptedException {
        Passes passes = new Passes(ID.toString(), "30", obslat.toString(), obslong.toString(), Constants.N2YOAPI);
        Date startdate = new Date(passes.startUTC*1000);
        Date maxdate = new Date(passes.maxUTC*1000);
        Date enddate = new Date(passes.endUTC*1000);

        String [] pass = new String[]{"Satellite Name: " + passes.satname ,"Starting time: \n" + startdate + "\nAzimuth: " + passes.startAz + "\nElevation" + passes.startEl + "\nDirection: " + passes.startAzCompass, "Max Elevation At: \n" + maxdate + "\nAzimuth: " + passes.maxAz + "\nElevation: " + passes.maxEl + "\nDirection: " + passes.maxAzCompass, "Pass End At: \n" + enddate + "\nAzimuth: " + passes.endAz + "\nElevation: " + passes.endEl + "\nDirection: " + passes.endAzCompass, "Duration: " + passes.duration};
        System.out.println(pass[1]);
        System.out.println(pass[2]);
        System.out.println(pass[3]);
        return pass;
    }

    /**
     * Initializes the LocatorTask and associated parameters for geocoding operations.
     * <p>
     * This method creates a new instance of the LocatorTask using the specified geocoding service URL.
     * It also configures GeocodeParameters to request detailed attribute information for the top result,
     * limits the maximum number of results to 1, and sets the output spatial reference to match the
     * spatial reference of the provided MapView.
     * <p>
     * The LocatorTask and GeocodeParameters are essential components for performing geocoding operations
     * in the application, allowing the conversion of addresses to geographic coordinates.
     * <p>
     * Note: This method should be called before using the 'locate' method to ensure proper initialization
     * of the LocatorTask and associated parameters.
     *
     * @see LocatorTask
     * @see GeocodeParameters
     * @see MapView#getSpatialReference()
     */
    public void locatorcreator(){
        locatorTask = new LocatorTask("https://geocode-api.arcgis.com/arcgis/rest/services/World/GeocodeServer");

        geocodeParameters = new GeocodeParameters();
        geocodeParameters.getResultAttributeNames().add("*");
        geocodeParameters.setMaxResults(1);
        geocodeParameters.setOutputSpatialReference(mapView.getSpatialReference());
    }

    /**
     * Retrieves the geographic coordinates and satellite pass information for a given address.
     *
     * This method asynchronously performs a geocoding operation to convert the provided address
     * into geographic coordinates. It then utilizes the obtained coordinates to retrieve
     * satellite pass information from N2YO, including pass name, start time, maximum elevation time, and end time.
     * The results are updated in the provided Graphic and Text objects.
     *
     * @param address      The address to be geocoded to obtain geographic coordinates.
     * @param pointGraphic The Graphic object representing the location on a map to be updated with the new coordinates.
     * @param name         The Text object to be updated with the satellite pass name.
     * @param start        The Text object to be updated with the start time of the satellite pass.
     * @param max          The Text object to be updated with the maximum elevation time of the satellite pass.
     * @param end          The Text object to be updated with the end time of the satellite pass.
     * @return An array of Doubles representing the latitude and longitude of the geocoded address.
     *         The first element is the latitude, and the second element is the longitude.
     * @throws RuntimeException If there is an issue with the satellite pass calculation, such as URISyntaxException or IOException.
     * @see LocatorTask#geocodeAsync(String, GeocodeParameters)
     * @see GeometryEngine#project(Geometry, SpatialReference) 
     * @see App#satPassCall(Integer, Double, Double)
     *
     */

    public Double[] locate(String address, Graphic pointGraphic, Text name, Text start, Text max, Text end){
        ListenableFuture<List<GeocodeResult>> geocodeResults = locatorTask.geocodeAsync(address, geocodeParameters);

        geocodeResults.addDoneListener(() -> {
            try {
                List<GeocodeResult> geocodes = geocodeResults.get();
                if (geocodes.size() > 0) {
                    GeocodeResult result = geocodes.get(0);
                    addressLocation = result.getDisplayLocation();
                    System.out.println(addressLocation.getSpatialReference());
                    addressLocation = (Point) GeometryEngine.project(addressLocation, SpatialReferences.getWgs84());
                    pointGraphic.setGeometry(addressLocation);
                    System.out.println(addressLocation.getY() + "," + addressLocation.getX());
                    addressCoords[0] = addressLocation.getY();
                    addressCoords[1] = addressLocation.getX();
                    try {
                        pass = satPassCall(satID,addressCoords[0], addressCoords[1]);
                    } catch (URISyntaxException | IOException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(addressCoords[0] + ","  + addressCoords[1]);
                    name.setText(pass[0]);
                    start.setText(pass[1]);
                    max.setText(pass[2]);
                    end.setText(pass[3]);
                } else {
                    new Alert(Alert.AlertType.INFORMATION, "No results found.").show();
                }
            } catch (InterruptedException | ExecutionException e) {
                new Alert(Alert.AlertType.ERROR, "Error getting result.").show();
                e.printStackTrace();
            }
        });
        return addressCoords;
    }

    /**
     * Initiates a countdown timer and updates a Text object with the remaining time.
     * <p>
     * This method uses the java.util.Timer class to schedule a TimerTask that runs at fixed intervals.
     * The TimerTask encapsulates the countdown logic, decrementing the remaining time each second.
     * The provided Text object (timeremaining) is updated with the current remaining time during each iteration.
     *
     * @param timeremaining The Text object that displays the remaining time of the countdown.
     * @implNote The countdown starts from the value specified in the 'countdownSeconds' variable.
     *           The method schedules the TimerTask to run every 1000 milliseconds (1 second).
     * @see Timer
     * @see TimerTask
     */
    public void countdown(Text timeremaining){
        timer.schedule(new TimerTask() {

            private int secondsRemaining = countdownSeconds;
            @Override
            public void run() {
                    if (secondsRemaining > 0) {
                        secondsRemaining--;
                        timeremaining.setText(String.valueOf(secondsRemaining));
                    }
            }
        }, 0, 1000); // Schedule the task to run every 1000 milliseconds (1 second)
    }




}