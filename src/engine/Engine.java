package engine;

import engine.graphics.Renderer;
import engine.graphics.Window;
import engine.input.Input;

import java.util.ArrayList;

/**
 * Kaden Cringle
 * 10/20/2017
 */

public class Engine implements Runnable {

    private String title = "2D Engine";
    private Thread thread;
    private Window window;
    private Renderer renderer;
    private Input input;
    private IGame game;
    private int width = 320, height = 240;
    private float scale = 3.0f;
    private boolean running = false;
    private final double FPS = 1.0 / 60.0;

    public Engine(IGame game) {
        this.game = game;
        window = new Window(this);
        renderer = new Renderer(this);
        input = new Input(this);
    }

    public void start() {
        thread = new Thread(this);
        thread.run();
    }

    public void stop() {

    }

    public void run() {
        running = true;
        boolean render;
        double start;
        double finish = System.nanoTime() / 1000000000.0;
        double elapsed;
        double unprocessed = 0.0;
        double frameTime = 0.0;
        int frames = 0, fps = 0;
        while (running) {
            render = false;
            start = System.nanoTime() / 1000000000.0;
            elapsed = start - finish;
            finish = start;
            unprocessed += elapsed;
            frameTime += elapsed;
            while (unprocessed >= FPS) {
                unprocessed -= FPS;
                render = true;
                game.update(this, (float)FPS);
                input.update();
                if (frameTime >= 1.0) {
                    frameTime = 0.0;
                    fps = frames;
                    frames = 0;
                    System.out.println(fps + " fps");
                }
            }
            if (render) {
                renderer.clear();
                game.render(this, renderer);
                renderer.processSprites();
                renderer.renderText("fps: " + fps, 0, 0, 0xFF00FF00);
                window.update();
                frames++;
            } else {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        dispose();
    }

    private void dispose() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public Window getWindow() {
        return window;
    }

    public Input getInput() {
        return input;
    }
}
