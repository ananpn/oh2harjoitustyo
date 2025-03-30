module com.oh2harjoitustyo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.media;


    opens com.oh2harjoitustyo to javafx.fxml, javafx.media;
    exports com.oh2harjoitustyo;
    exports com.oh2harjoitustyo.Screens;
    opens com.oh2harjoitustyo.Screens to javafx.fxml, javafx.media;
}