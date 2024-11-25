package io.github.MyGame3;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Lever {

    private Texture offTexture;
    private Texture onTexture;
    private Rectangle hitbox;
    public boolean isOn;

    public Lever(String offTexturePath, String onTexturePath, float x, float y, float z) {
        this.offTexture = new Texture(offTexturePath);
        this.onTexture = new Texture(onTexturePath);
        this.hitbox = new Rectangle(x, y, offTexture.getWidth(), offTexture.getHeight());
        this.isOn = false;
    }

    public void setPosition(float x, float y) {
        hitbox.setPosition(x, y);
    }

    public Rectangle Hitbox() {
        return hitbox;
    }

    public void toggle() {
        isOn = !isOn;
    }

    public void draw(SpriteBatch batch) {
        if (isOn) {
            batch.draw(onTexture, hitbox.x, hitbox.y);  // Draw the "on" texture
        } else {
            batch.draw(offTexture, hitbox.x, hitbox.y);  // Draw the "off" texture
        }
    }

    public void dispose() {
        offTexture.dispose();
        onTexture.dispose();
    }
}
