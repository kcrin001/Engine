package engine.input;

import engine.Engine;

import java.awt.event.*;

/**
 * Kaden Cringle
 * 10/20/2017
 */

public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

    private final int MAX_KEYS = 256;
    private final int MAX_BUTTONS = 5;
    private Engine engine;
    private boolean[] keys = new boolean[MAX_KEYS];
    private boolean[] previousKeys = new boolean[MAX_KEYS];
    private boolean[] mouseButtons = new boolean[MAX_BUTTONS];
    private boolean[] previousMouseButtons = new boolean[MAX_BUTTONS];
    private int mousePosX, mousePosY, mouseScroll;

    public Input(Engine engine) {
        this.engine = engine;
        mousePosX = 0;
        mousePosY = 0;
        mouseScroll = 0;
        engine.getWindow().getCanvas().addKeyListener(this);
        engine.getWindow().getCanvas().addMouseListener(this);
        engine.getWindow().getCanvas().addMouseWheelListener(this);
        engine.getWindow().getCanvas().addMouseMotionListener(this);
    }

    public boolean isKeyPressed(int keyCode) {
        return keys[keyCode];
    }

    public boolean isKeyUp(int keyCode) {
        return previousKeys[keyCode] && !keys[keyCode];
    }

    public boolean isKeyDown(int keyCode) {
        return keys[keyCode] && !previousKeys[keyCode];
    }

    public boolean isButtonPressed(int keyCode) {
        return mouseButtons[keyCode];
    }

    public boolean isButtonUp(int keyCode) {
        return previousMouseButtons[keyCode] && !mouseButtons[keyCode];
    }

    public boolean isButtonDown(int keyCode) {
        return mouseButtons[keyCode] && !previousMouseButtons[keyCode];
    }

    public void update() {
        mouseScroll = 0;
        for(int i = 0; i < MAX_KEYS; i++)
            previousKeys[i] = keys[i];
        for(int i = 0; i < MAX_BUTTONS; i++)
            previousMouseButtons[i] = mouseButtons[i];
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseButtons[e.getButton()] = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseButtons[e.getButton()] = false;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mousePosX = (int)(e.getX() / engine.getScale());
        mousePosY = (int)(e.getY() / engine.getScale());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mousePosX = (int)(e.getX() / engine.getScale());
        mousePosY = (int)(e.getY() / engine.getScale());
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        mouseScroll = e.getWheelRotation();
    }

    public int getMousePosX() {
        return mousePosX;
    }

    public int getMousePosY() {
        return mousePosY;
    }

    public int getMouseScroll() {
        return mouseScroll;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {}
}
