package lando.systems.ld34.world;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lando.systems.ld34.LudumDare34;
import lando.systems.ld34.motivation.MotivationGame;
import lando.systems.ld34.resources.ResourceManager;
import lando.systems.ld34.screens.GameScreen;
import lando.systems.ld34.utils.Assets;

/**
 * Brian Ploeckelman created on 12/12/2015.
 */
public class AreaQuarry extends Area {

    GlyphLayout glyphLayout;

    private MotivationGame mg;

    public AreaQuarry(GameScreen gameScreen) {
        super(gameScreen, Type.QUARRY);
        worldX = 2;
        glyphLayout = new GlyphLayout(Assets.font, "Quarry Area");
        mg = new MotivationGame(
                LudumDare34.GameScreen.ResourceManager,
                ResourceManager.Resources.STONE);
    }

    @Override
    public void update(float delta) {
        mg.update(delta);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setColor(211f / 255f, 211f / 255f, 211f / 255f, 1f);
        //batch.draw(Assets.whiteTexture, 0, 0, gameScreen.camera.viewportWidth, gameScreen.camera.viewportHeight);

        Assets.font.setColor(0f, 0f, 0f, 1f);
        Assets.font.draw(batch,
                "Quarry Area - Amount:" + (int) gameScreen.ResourceManager.getAmount(ResourceManager.Resources.STONE) +" EFF%:" +(int)(100*gameScreen.ResourceManager.getEfficiency(ResourceManager.Resources.STONE)),
                gameScreen.camera.viewportWidth / 2f - glyphLayout.width / 2f,
                gameScreen.camera.viewportHeight / 2f - glyphLayout.height / 2f);
        Assets.font.setColor(1f, 1f, 1f, 1f);

        batch.setColor(1f, 1f, 1f, 1f);
        mg.render(batch);
    }

}
