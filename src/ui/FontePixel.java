package ui;

import java.awt.*;
import java.io.File;

public class FontePixel {

    public static Font obter(int tamanho) {

        try {
            if (fonteBase == null) {
                fonteBase = Font.createFont(Font.TRUETYPE_FONT, new File("Resource/PressStart2P-Regular.ttf"));
            }

            return fonteBase.deriveFont((float) tamanho);
        } catch (Exception e) {
            System.out.println("Não consegui carregar a fonte pixelada: " + e.getMessage());
            return new Font("Consolas", Font.PLAIN, tamanho);
        }
    }

    public static Font fonteBase;
}
