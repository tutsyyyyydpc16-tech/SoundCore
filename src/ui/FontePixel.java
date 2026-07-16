package ui;

import java.awt.*;
import java.io.File;
import java.io.InputStream;

public class FontePixel {

    public static Font obter(int tamanho) {

        try {
            if (fonteBase == null) {

                try (InputStream is = FontePixel.class.getResourceAsStream("/Resource/PressStart2P-Regular.ttf")) {
                    fonteBase = Font.createFont(Font.TRUETYPE_FONT, is);
                }
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
