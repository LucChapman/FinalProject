package io.github.MyGame3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Input;

public class Screen2 implements Screen {

    private SpriteBatch sprite;
    private Texture background;
    private Player player;

    private Rectangle top;
    private Rectangle bottom;
    private Rectangle left;
    private Rectangle right;
    private Rectangle leverWall;

    private Lever lever;
    private FirstObject object;
    private FirstObject fobject;

    private Enemy enemy;

    @Override
    public void show() {

        sprite = new SpriteBatch();
        background = new Texture("Screen2.1-sheet.png");

        enemy = new Enemy("enemy/enemy1.png", 700, 300, 70);
        player = new Player("idle/playeridle1.png", 4, "walk/playerwalk1.png", 7, 100, 100, 1, 100);

        int windowWidth = Gdx.graphics.getWidth();
        int windowHeight = Gdx.graphics.getHeight();

        top = new Rectangle(0, windowHeight - 170, windowWidth, 50);
        bottom = new Rectangle(0, 0, windowWidth, 50);
        left = new Rectangle(0, 0, 50, windowHeight);
        right = new Rectangle(windowWidth - 85, 0, 50, windowHeight);

        leverWall = new Rectangle(520, 510, 1, 5);

        lever = new Lever("lever/lever1.png", "lever/lever2.png", 500, 475, 0);
        object = new FirstObject("Fenter.png", 530, 300);
        fobject = new FirstObject("Flever2.png", 200, 400);
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

        if (isCircleOverlap(player.Hitbox(), leverWall)) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
                lever.toggle();
            }
        }
        if(lever.isOn){
            background = new Texture("Screen2.2-sheet.png");
            if (player.getPosition().x > 770 && player.getPosition().x < 900 &&
                player.getPosition().y > 465 && player.getPosition().y < 487) {
                object.show();
                if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
                    ((Main) Gdx.app.getApplicationListener()).setScreen(new Screen3());
                }
            } else {
                object.hide();
            }
        }else{
            background = new Texture("Screen2.1-sheet.png");
        }

        if (player.getPosition().x > 452 && player.getPosition().x < 572 &&
            player.getPosition().y > 448 && player.getPosition().y < 487) {
            fobject.show();
        } else {
            fobject.hide();
        }

        if (enemy.checkCollision(player.Hitbox())) {
            ((Main) Gdx.app.getApplicationListener()).setScreen(new Lose());
        }

        enemy.HitboxScale(0.5f);
        enemy.update(delta, player);

        sprite.begin();
        sprite.draw(background, x, y, scaledWidth, scaledHeight);

        lever.draw(sprite);

        player.update(delta);
        player.draw(sprite);

        enemy.draw(sprite);

        object.draw(sprite);
        fobject.draw(sprite);

        sprite.end();
    }

    private boolean isCircleOverlap(Circle circle, Rectangle rect) {

        float closestX = Math.max(rect.x, Math.min(circle.x, rect.x + rect.width));
        float closestY = Math.max(rect.y, Math.min(circle.y, rect.y + rect.height));

        float distanceX = circle.x - closestX;
        float distanceY = circle.y - closestY;

        return (distanceX * distanceX + distanceY * distanceY) < (circle.radius * circle.radius);
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

        leverWall.setSize(50, 50);
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
        lever.dispose();
        fobject.dispose();
        enemy.dispose();
    }
}
