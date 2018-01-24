import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Objects;

/**
 * This is the main class of the application. It serves like an entry point to the application
 * and performs all necessary operations to set up Spark environment and graphical user
 * interface of the application.
 * @author Maksym
 * @since 1.0
 * */
public class Main extends Application {

    private static JavaSparkContext sparkContext;

    /**
     * This method overrides {@code start(Stage primaryStage)} method of the JavaFX's
     * {@link Application} class. It loads FXML layout of the application's GUI and sets
     * it as a primary scene of the GUI's form.
     * @throws NullPointerException if FXML layout file cannot be found in the resources
     * in fxml directory.
     * */
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader()
                    .getResource("fxml/MainWindow.fxml")));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Anomaly Detection in Time Series");
            primaryStage.show();
        } catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println("FXML layout file for primary stage was not found!");
        }
    }

    /**
     * This is the main method of the Anomaly Detection in Time Series application.
     * It is an entry point of the application. This method invokes {@code initializeSpark()}
     * method of the same class. The invocation takes place in a new thread that is called
     * using a new instance of {@link Runnable} interface. Value returned by the
     * {@code initializeSpark()} method is set as a value of the static field representing
     * {@link JavaSparkContext} object.
     * */
    public static void main(String[] args) {
        Runnable runnable = () -> sparkContext = initializeSpark();
        runnable.run();

        launch(args);
    }

    /**
     * This method returns the value of {@link JavaSparkContext} object that is a static
     * member of this class
     * @return {@link JavaSparkContext} object containing context of the current running
     * Spark instance
     * */
    public static JavaSparkContext getSparkContext() {
        return sparkContext;
    }

    /**
     * This method creates a new instance of {@link SparkConf} object that contains
     * application name and master URL. In this particular case a new Spark instance will
     * be running locally using 2 CPU cores. A value provided in square brackets indicates
     * the number of processing cores that will be available for the application to use.
     * @return {@link JavaSparkContext} object with Spark's context and configuration.
     * */
    private static JavaSparkContext initializeSpark() {
        SparkConf sparkConf = new SparkConf()
                .setAppName("Anomaly detection in TS")
                .setMaster("local[2]");
        return new JavaSparkContext(sparkConf);
    }
}
