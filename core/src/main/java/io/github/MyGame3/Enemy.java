package io.github.MyGame3;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Enemy {

    private Sprite sprite;
    private Circle hitbox;
    private float speed;
    private Texture[] chasing;
    float hitBoxScale = 0.5f;
    private boolean isChasing = false;
    private float stateTime;

    public Enemy(String texturePath, float x, float y, float speed) {

        this.sprite = new Sprite(new Texture("enemy/enemy1.png"));

        this.chasing = new Texture[4];

        for (int i = 0; i < 4; i++) {
            chasing[i] = new Texture("enemy/enemy" + (i + 1) + ".png");
        }

        this.sprite.setPosition(x, y);
        float hitboxRadius = Math.max(sprite.getWidth(), sprite.getHeight()) / 2 * hitBoxScale;
        this.hitbox = new Circle(x + sprite.getWidth() / 2, y + sprite.getHeight() / 2, Math.max(sprite.getWidth(), sprite.getHeight()) / 2);
        this.speed = speed;
    }

    public void update(float deltaTime, Player player) {
        Vector2 playerPos = player.getPosition();

        float deltaX = playerPos.x - sprite.getX();
        float deltaY = playerPos.y - sprite.getY();

        float length = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        if (length != 0) {
            float dirX = deltaX / length;
            float dirY = deltaY / length;

            isChasing = true;

            sprite.translateX(dirX * speed * deltaTime);
            sprite.translateY(dirY * speed * deltaTime);
        }

        if (isChasing) {

            stateTime += deltaTime;
            int frameIndex = (int) (stateTime * 5) % chasing.length;
            sprite.setTexture(chasing[frameIndex]);
        }

        hitbox.setPosition(sprite.getX() + sprite.getWidth() / 2, sprite.getY() + sprite.getHeight() / 2);
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public boolean checkCollision(Circle playerHitbox) {
        return playerHitbox.overlaps(hitbox);
    }

    public void HitboxScale(float scale) {
        hitBoxScale = scale;
        float newRadius = Math.max(sprite.getWidth(), sprite.getHeight()) / 2 * hitBoxScale;
        hitbox.setRadius(newRadius);
    }

    public Circle Hitbox() {
        return hitbox;
    }

    public Sprite Sprite() {
        return sprite;
    }

    public void dispose() {
        for (Texture texture : chasing) {
            texture.dispose();
        }
    }
}
