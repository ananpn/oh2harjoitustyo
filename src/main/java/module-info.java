module com.oh2harjoitustyo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.oh2harjoitustyo to javafx.fxml;
    exports com.oh2harjoitustyo;
    exports com.oh2harjoitustyo.Screens;
    opens com.oh2harjoitustyo.Screens to javafx.fxml;
}