package io.github.MyGame3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Intersector;

public class Screen1 implements Screen {

    private SpriteBatch sprite;
    private Texture background;
    private Player player;

    private Rectangle top;
    private Rectangle bottom;
    private Rectangle left;
    private Rectangle right;

    private FirstObject object;

    @Override
    public void show() {

        sprite = new SpriteBatch();
        background = new Texture("Screen1.png");

        player = new Player("idle/playeridle1.png", 4, "walk/playerwalk1.png", 7, 100, 100, 1,  100);

        int windowWidth = Gdx.graphics.getWidth();
        int windowHeight = Gdx.graphics.getHeight();

        top = new Rectangle(0, windowHeight - 170, windowWidth, 50);
        bottom = new Rectangle(0, 0, windowWidth, 50);
        left = new Rectangle(0, 0, 50, windowHeight);
        right = new Rectangle(windowWidth - 85, 0, 50, windowHeight);

        object = new FirstObject("Fenter.png", 250, 300);
    }

    public void render(float delta) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        int windowWidth = Gdx.graphics.getWidth();
        int windowHeight = Gdx.graphics.getHeight();

        float textureWidth = background.getWidth();
        float textureHeight = background.getHeight();

        float scaleX = (float) windowWidth / textureWidth;
        float scaleY = (float) windowHeight / textureHeight;

        float scale = Math.min(scaleX, scaleY);

        float scaledWidth = textureWidth * scale;
        float scaledHeight = textureHeight * scale;

        float x = (windowWidth - scaledWidth) / 2;
        float y = (windowHeight - scaledHeight) / 2;

        checkCollisions();

        sprite.begin();
        sprite.draw(background, x, y, scaledWidth, scaledHeight);

        player.update(delta);
        player.draw(sprite);

        if (player.getPosition().x > 530 && player.getPosition().x < 670 &&
            player.getPosition().y > 465 && player.getPosition().y < 487) {
            object.show();
            if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
                ((Main) Gdx.app.getApplicationListener()).setScreen(new Screen2());
            }
        } else {
            object.hide();
        }

        object.draw(sprite);

        sprite.end();
    }

    private void checkCollisions() {

        if (Intersector.overlaps(player.Hitbox(), top)) {

            player.getPosition().y = top.getY() - player.Hitbox().radius * 2;
        }

        if (Intersector.overlaps(player.Hitbox(), bottom)) {
            player.getPosition().y = bottom.getY() + bottom.height;
        }

        if (Intersector.overlaps(player.Hitbox(), left)) {
            player.getPosition().x = left.getX() + left.width;
        }

        if (Intersector.overlaps(player.Hitbox(), right)) {
            player.getPosition().x = right.getX() - player.Hitbox().radius * 2;
        }
    }

    @Override
    public void resize(int width, int height) {

        resizeWalls(width, height);
    }

    public void resizeWalls(float newWidth, float newHeight) {

        top.setSize(newWidth, 1000);
        bottom.setSize(newWidth, 1);
        left.setSize(85, newHeight);
        right.setSize(85, newHeight);
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

        background.dispose();
        sprite.dispose();
        player.dispose();
        object.dispose();
    }
}
