module com.altloc.desktop {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.altloc.desktop to javafx.fxml;
    exports com.altloc.desktop;
}
