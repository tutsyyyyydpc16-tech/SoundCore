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

    public static Font paraTexto(String texto, int tamanho) {

        Font pixel = obter(tamanho);

        if (texto == null || pixel.canDisplayUpTo(texto) == -1) {
            return pixel;
        }

        return FONTE_FALLBACK.deriveFont((float) (tamanho + 3));
    }

    public static Font fonteBase;
    private static final Font FONTE_FALLBACK = new Font(Font.DIALOG, Font.PLAIN, 12);
}
