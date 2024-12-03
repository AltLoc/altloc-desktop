module com.altloc.desktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires transitive javafx.base;

    requires com.fasterxml.jackson.databind;
    requires okhttp3;
    requires com.fasterxml.jackson.core;

    opens com.altloc.desktop to javafx.fxml;
    opens com.altloc.desktop.controller to javafx.fxml; // Открываем пакет для FXMLLoader
    opens com.altloc.desktop.model to com.fasterxml.jackson.databind; // Открываем пакет для Jackson
    opens com.altloc.desktop.game to javafx.fxml; // Открываем пакет для FXMLLoader

    exports com.altloc.desktop;
    exports com.altloc.desktop.controller; // Экспортируем контроллеры, если они нужны извне
    exports com.altloc.desktop.model; // Экспортируем модель для Jackson
}
