/**
 *
 *
 * NOTE : IF THIS FAILS TO RUN PLEASE CONTACT ME.
 *
 * IF APPTest SHOWS AN ERROR, DELETE THE JUNIT TEST, IT HAS ISSUES WITH GRADLE!
 *
 * ---
 *
 * BEFORE STARTING:
 *
 * Go to {@link com.example.app.Constants} and put in your API KEYS!!
 *
 *
 * --
 * Welcome to the main or App class.
 * This class uses an ARCGiS runtime communicating with a local JavaFX Scene to display a map of the Earth.
 * ARCGiS can also display feature layers and graphics on top of the map using JavaFX and built in support for graphics
 * in its API.
 *
 * Read more about ARCGiS here : @see <a href = "https://developers.arcgis.com/java/"> ARCGiS Documentation </a>
 * Read more on JavaFX here: @see <a href = "https://openjfx.io/">OpenJFX</a>
 *
 * You may notice that the imports are divided into various blocks. It's because this makes it easier to identify
 * libraries when you are debugging.
 *
 * Each block of the libraries serves its own purpose. The first block is Graphics related ARCGiS Libraries,
 * like Point and Graphic.
 *
 * The second block is used to render the map.
 *
 * The third block is JavaFX.
 *
 */



package com.example.app;
// Graphics Block
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.symbology.PictureMarkerSymbol;
import com.esri.arcgisruntime.symbology.TextSymbol;

//Maps Block
import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.BasemapStyle;
import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.view.MapView;

//JavaFX block
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Slider;
import java.io.IOException;
import java.net.URISyntaxException;

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
 * @author Hardik Bishnoi
 * @version 1.4
 * @since 1.0
 */
public class App extends Application {

    private MapView mapView;
    private Integer satID = 25544;
    private  Double scale = 700000000.0;

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
        slider.setMaxSize(200,10);
        slider.setSnapToPixel(false);
        slider.setLayoutX(250);
        slider.setLayoutX(100);


        // Add the title to the window
        stage.setTitle("Satellite Tracker");
        stage.setWidth(700);
        stage.setHeight(700);
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
        stackPane.getChildren().add(mapView);
        stackPane.getChildren().add(combo_box);
        stackPane.getChildren().add(slider);
        StackPane.setAlignment(slider, Pos.CENTER_LEFT);
        StackPane.setAlignment(combo_box, Pos.TOP_LEFT);

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
                } catch (URISyntaxException | IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
//
                try {
                    Thread.sleep(10000); // Update every 10000 milliseconds (10 seconds). Change this to change update time.
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
            } catch (URISyntaxException | IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
            sattext.setText(selection);

        });

        /**
         * listener for {@link slider} which changes the value of
         * scale to zoom the map in and out.
         */
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                scale = (Double) newValue;
                mapView.setViewpointScaleAsync(scale);
            }
        });

        // add the point graphic to the graphics overlay
        graphicsOverlay.getGraphics().add(satpoint);
        graphicsOverlay.getGraphics().add(sattextgraphic);
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
        Satellite satellite = new Satellite(ID.toString(), "1", "-70.24637829392033", "43.66167125666995", Constants.N2YOAPI);
        return new Float[]{satellite.satlong,satellite.satlat};
    }


}

