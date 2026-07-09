
import javafx.application.Platform;

import javax.swing.*;

public class Main {

    public static void main (String[] args) {

        Platform.startup(() -> {});

        new Janela();
    }
}
