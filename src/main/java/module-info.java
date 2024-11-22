module com.altloc.desktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires transitive javafx.base;

    requires com.fasterxml.jackson.databind;
    requires okhttp3;

    opens com.altloc.desktop to javafx.fxml;

    exports com.altloc.desktop;
}