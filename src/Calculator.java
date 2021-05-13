import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Calculator extends Application
{
    @FXML
    Label mainScreen;

    @FXML
    Label subScreen;

    public static void main(String[] args) throws Exception 
    {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception 
    {
        Parent root = FXMLLoader.load(getClass().getResource("scene.fxml"));
        Scene scene = new Scene(root);

        stage.setTitle("Calculator");
        stage.setScene(scene);     
        stage.show();
    }
}