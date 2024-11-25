package io.github.MyGame3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Circle;

public class Player {

    private Texture[] idleTexture;
    private Texture[] walkingTexture;
    private Sprite sprite;
    private Vector2 locate;
    private float speed;
    private Circle hitbox;

    private float stateTime;
    private boolean isIdle;
    private boolean isMoving;

    public Player(String idle, int idleFrameCount, String walk, int walkFrameCount, float x, float y, float z, float speed) {

        this.idleTexture = new Texture[4];

        for (int i = 0; i < 4; i++) {
            idleTexture[i] = new Texture("idle/playeridle" + (i + 1) + ".png");
        }

        this.walkingTexture = new Texture[7];

        for (int i = 0; i < 7; i++) {
            walkingTexture[i] = new Texture("walk/playerwalk" + (i + 1) + ".png");
        }

        this.sprite = new Sprite(idleTexture[0]);
        this.locate = new Vector2(x, y);
        this.speed = speed;
        this.stateTime = 0;
        this.isMoving = false;
        this.isIdle = true;

        float radius = sprite.getWidth() / 2;
        this.hitbox = new Circle(locate.x + radius, locate.y + radius, radius);
    }

    public void update(float deltaTime){

        isMoving = false;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            locate.y += speed * deltaTime;
            isMoving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            locate.y -= speed * deltaTime;
            isMoving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            locate.x -= speed * deltaTime;
            isMoving = true;
            if (!sprite.isFlipX()) {
                sprite.flip(true, false);
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            locate.x += speed * deltaTime;
            isMoving = true;
            if (sprite.isFlipX()) {
                sprite.flip(true, false);
            }
        }

        hitbox.setPosition(locate.x + hitbox.radius, locate.y + hitbox.radius);

        if (isMoving) {
            isIdle = false;

            stateTime += deltaTime;
            int frameIndex = (int) (stateTime * 5) % walkingTexture.length;
            sprite.setTexture(walkingTexture[frameIndex]);
        } else {
            isIdle = true;

            stateTime += deltaTime;
            int frameIndex = (int) (stateTime * 10) % idleTexture.length;
            sprite.setTexture(idleTexture[frameIndex]);
        }

        sprite.setPosition(locate.x, locate.y);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(sprite, locate.x, locate.y);
    }

    public void dispose() {

        for (Texture texture : idleTexture) {
            texture.dispose();
        }
        for (Texture texture : walkingTexture) {
            texture.dispose();
        }
    }

    public Vector2 getPosition() {
        return locate;
    }

    public Circle Hitbox() {
        return hitbox;
    }
}
