package engine.graphics;

/**
 * Kaden Cringle
 * 10/22/2017
 */

public class Font {

    private static final Font instance = new Font("/font.png");

    private Sprite image;
    private int[] offsets;
    private int[] widths;

    private Font(String path) {
        image = new Sprite(path);
        offsets = new int[256];
        widths = new int[256];
        int character = 0;
        for(int i = 0; i < image.getWidth(); i++) {
            if(image.getPixels()[i] == 0xFF0000FF)
                offsets[character] = i;
            if(image.getPixels()[i] == 0xFFFFFF00) {
                widths[character] = i - offsets[character];
                character++;
            }
        }
    }

    public Sprite getImage() {
        return image;
    }

    public int[] getOffsets() {
        return offsets;
    }

    public int[] getWidths() {
        return widths;
    }

    public static Font getInstance() {
        return instance;
    }
}
