package nexus;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/** Provides the JavaFX graphical interface for Nexus. */
public class Main extends Application {
    private final Nexus nexus = new Nexus("data/nexus.txt");

    /** Builds and displays the Nexus window. */
    @Override
    public void start(Stage stage) {
        TextArea conversation = new TextArea("Hello! I'm Nexus.\nWhat can I do for you?\n");
        conversation.setEditable(false);
        conversation.setWrapText(true);

        TextField input = new TextField();
        input.setPromptText("Enter a command...");
        Button send = new Button("Send");
        Runnable submit = () -> {
            String command = input.getText().trim();
            if (!command.isEmpty()) {
                conversation.appendText("\n> " + command + "\n" + nexus.executeCommand(command) + "\n");
                input.clear();
            }
        };
        send.setOnAction(event -> submit.run());
        input.setOnAction(event -> submit.run());

        HBox controls = new HBox(10, input, send);
        controls.setPadding(new Insets(10));
        BorderPane root = new BorderPane(conversation);
        root.setBottom(controls);
        stage.setTitle("Nexus");
        stage.setScene(new Scene(root, 600, 450));
        stage.show();
    }
}
