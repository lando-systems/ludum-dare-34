package lando.systems.ld34.screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ObjectMap;
import lando.systems.ld34.LudumDare34;
import lando.systems.ld34.resources.ResourceManager;
import lando.systems.ld34.utils.Assets;
import lando.systems.ld34.world.*;

/**
 * Brian Ploeckelman created on 12/9/2015.
 */
public class GameScreen extends AbstractScreen {

    final SpriteBatch      batch;
    final NavigationLayout layout;

    ObjectMap<Area.Type, Area> areaMap;
    Area currentArea;
    ResourceManager resourceManager;

    public GameScreen(LudumDare34 game) {
        super(game);
        batch = Assets.batch;
        layout = new NavigationLayout(this);
        resourceManager = new ResourceManager();
        areaMap = new ObjectMap<Area.Type, Area>();
        areaMap.put(Area.Type.MGMT, new AreaMgmt(this));
        areaMap.put(Area.Type.PYRAMID, new AreaPyramid(this));
        areaMap.put(Area.Type.QUARRY, new AreaQuarry(this));
        areaMap.put(Area.Type.FIELD, new AreaField(this));
        areaMap.put(Area.Type.WOODS, new AreaWoods(this));
        currentArea = areaMap.get(Area.Type.MGMT);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        layout.update();
        resourceManager.update(delta);
    }

    @Override
    public void render(float delta) {
        update(delta);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        currentArea.render(batch);
        batch.end();

        layout.render(batch, uiCamera);
    }

}
