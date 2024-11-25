package io.github.MyGame3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;

public class Menu implements Screen {

    private Texture menuTexture;
    private SpriteBatch spriteBatch;

    private static final float PLAY_BUTTON_X = 50;
    private static final float PLAY_BUTTON_Y = 110;
    private static final float PLAY_BUTTON_WIDTH = 177;
    private static final float PLAY_BUTTON_HEIGHT = 80;

    private static final float EXIT_BUTTON_X = 50;
    private static final float EXIT_BUTTON_Y = 20;
    private static final float EXIT_BUTTON_WIDTH = 155;
    private static final float EXIT_BUTTON_HEIGHT = 70;

    private boolean playButtonClicked = false;

    @Override
    public void show() {

        spriteBatch = new SpriteBatch();
        menuTexture = new Texture("mainscrn.png");
    }

    public void render(float delta) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        int windowWidth = Gdx.graphics.getWidth();
        int windowHeight = Gdx.graphics.getHeight();

        float textureWidth = menuTexture.getWidth();
        float textureHeight = menuTexture.getHeight();

        float scaleX = (float) windowWidth / textureWidth;
        float scaleY = (float) windowHeight / textureHeight;

        float scale = Math.min(scaleX, scaleY);

        float scaledWidth = textureWidth * scale;
        float scaledHeight = textureHeight * scale;

        float x = (windowWidth - scaledWidth) / 2;
        float y = (windowHeight - scaledHeight) / 2;

        spriteBatch.begin();
        spriteBatch.draw(menuTexture, x, y, scaledWidth, scaledHeight);
        spriteBatch.end();

        if (Gdx.input.isTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.input.getY();

            touchY = Gdx.graphics.getHeight() - touchY;

            touchX = (touchX - x) / scale;
            touchY = (touchY - y) / scale;

            if (isInsideButton(touchX, touchY, PLAY_BUTTON_X, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT) && !playButtonClicked) {

                System.out.println("next scene");
                ((Main) Gdx.app.getApplicationListener()).setScreen(new Screen1());
                playButtonClicked = true;
            }
            else if (isInsideButton(touchX, touchY, EXIT_BUTTON_X, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT)) {

                System.out.println("Exit button clicked!");
                Gdx.app.exit();
            }
        } else {

            playButtonClicked = false;
        }
    }

    private boolean isInsideButton(float touchX, float touchY, float buttonX, float buttonY, float buttonWidth, float buttonHeight) {
        return touchX >= buttonX && touchX <= buttonX + buttonWidth && touchY >= buttonY && touchY <= buttonY + buttonHeight;
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    public void dispose(){

        menuTexture.dispose();
        spriteBatch.dispose();
    }
}
