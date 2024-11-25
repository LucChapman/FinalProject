package io.github.MyGame3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Intersector;

public class FirstObject {

    private Texture texture;
    private Rectangle hitbox;
    public boolean isVisible;

    public FirstObject(String texturePath, float x, float y) {
        this.texture = new Texture(texturePath);
        this.hitbox = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
        this.isVisible = false;  // Initially, the object is not visible
    }

    public void setPosition(float x, float y) {
        hitbox.setPosition(x, y);
    }

    public Rectangle Hitbox() {
        return hitbox;
    }

    public void show() {
        this.isVisible = true;  // Make the object visible
    }

    public void hide() {
        this.isVisible = false;  // Hide the object
    }

    public void draw(SpriteBatch batch) {
        if (isVisible) {
            batch.draw(texture, hitbox.x, hitbox.y);
        }
    }

    public void dispose() {
        texture.dispose();
    }
}
