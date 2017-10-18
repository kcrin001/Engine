import game.Game;
import org.lwjgl.*;

public class Main {

    public static void main(String... args) {
        System.out.println("Hello, you are using LWJGL " + Version.getVersion());
        new Game().run();
    }
}