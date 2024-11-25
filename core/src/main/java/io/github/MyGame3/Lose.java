package io.github.MyGame3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Lose implements Screen {

    private Texture menuTexture;
    private SpriteBatch spriteBatch;

    @Override
    public void show() {

        spriteBatch = new SpriteBatch();
        menuTexture = new Texture("lose.png");
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

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            ((Main) Gdx.app.getApplicationListener()).setScreen(new Menu());
        }
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
