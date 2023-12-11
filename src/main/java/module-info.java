module com.example.app {
    // require ArcGIS Runtime module
    requires com.esri.arcgisruntime;

    // requires JavaFX modules that the application uses
    requires javafx.graphics;

    // requires SLF4j module
    requires org.slf4j.nop;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires org.junit.jupiter.api;
    requires com.opencsv;
    exports com.example.app;
}