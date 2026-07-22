import app.Janela;
import com.formdev.flatlaf.FlatDarkLaf;
import javafx.application.Platform;

import javax.swing.*;

public class Main {

    public static void main (String[] args) {

        FlatDarkLaf.setup();

        Platform.startup(() -> {});
        Platform.setImplicitExit(false);

        //RepaintManager.currentManager(null).setDoubleBufferingEnabled(false);

        SwingUtilities.invokeLater(Janela::new);
    }
}
