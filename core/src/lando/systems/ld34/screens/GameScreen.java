package lando.systems.ld34.screens;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ObjectMap;
import lando.systems.ld34.LudumDare34;
import lando.systems.ld34.resources.ResourceManager;
import lando.systems.ld34.uielements.AreaButton;
import lando.systems.ld34.uielements.ManagementButton;
import lando.systems.ld34.uielements.PyramidButton;
import lando.systems.ld34.utils.Assets;
import lando.systems.ld34.world.*;

/**
 * Brian Ploeckelman created on 12/9/2015.
 */
public class GameScreen extends AbstractScreen {

    final SpriteBatch      batch;
    final NavigationLayout layout;

    // java# (tm)
    public ResourceManager ResourceManager;
    public Area.Type CurrentArea;

    ObjectMap<Area.Type, Area> areaMap;
    Area currentArea;
    Background background;

    public GameScreen(LudumDare34 game) {
        super(game);
        LudumDare34.GameScreen = this;

        batch = Assets.batch;

        ResourceManager = new ResourceManager();

        background = new Background();
        areaMap = new ObjectMap<Area.Type, Area>();
        areaMap.put(Area.Type.MGMT, new AreaMgmt(this));
        areaMap.put(Area.Type.PYRAMID, new AreaPyramid(this));
        areaMap.put(Area.Type.QUARRY, new AreaQuarry(this));
        areaMap.put(Area.Type.FIELD, new AreaField(this));
        areaMap.put(Area.Type.WOODS, new AreaWoods(this));

        currentArea = areaMap.get(Area.Type.MGMT);

        layout = new NavigationLayout(this);
        SetupNavigation(layout);

        TransitionToArea(AreaButton.SelectedButton.AreaLocation);
    }

    public void TransitionToArea(Area.Type area) {
        CurrentArea = area;

        final Area nextArea = areaMap.get(area);
        Tween.to(background.xOffset, 1, 1f)
                .target(nextArea.worldX * (512/5f))
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int i, BaseTween<?> baseTween) {
                        currentArea = nextArea;
                    }
                })
                .start(Assets.tween);

    }

    public void ShowManagementScreen(Manage.Type skillScreen) {
        // TODO: call me from NavigationLayout and then do stuff
    }

    private void SetupNavigation(NavigationLayout navLayout) {
        AreaButton managementAreaButton = new AreaButton("Management", Area.Type.MGMT);
        AreaButton.SelectedButton = managementAreaButton;

        navLayout.add(managementAreaButton);
        navLayout.add(new AreaButton("Quarry", Area.Type.QUARRY));
        navLayout.add(new AreaButton("Field", Area.Type.FIELD));
        navLayout.add(new AreaButton("Woods", Area.Type.WOODS));

        // layout added buttons first before adding pyramid button - hacky but fuck it
        float height = uiCamera.viewportHeight;
        navLayout.layoutAreaButtons(new Rectangle(0, background.SandHeight, 75, height - background.SandHeight));

        Rectangle pyramidBounds = new Rectangle(uiCamera.viewportWidth - 75,
                background.SandHeight, 75, height - background.SandHeight);
        navLayout.add(new PyramidButton(pyramidBounds));

        ManagementButton skillsManagementButton = new ManagementButton("Workers", Manage.Type.WORKERS);
        ManagementButton.SelectedButton = skillsManagementButton;

        navLayout.add(skillsManagementButton);
        navLayout.add(new ManagementButton("Slaves", Manage.Type.SLAVES));
        navLayout.add(new ManagementButton("Pharoah", Manage.Type.PHAROAH));
        navLayout.add(new ManagementButton("Upgrades", Manage.Type.UPGRADES));
        navLayout.add(new ManagementButton("Resources", Manage.Type.RESOURCES));

        navLayout.layoutManagement(new Rectangle(0, 0, uiCamera.viewportWidth, background.SandHeight));
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        layout.update();
        ResourceManager.update(delta);
        currentArea.update(delta);
    }

    @Override
    public void render(float delta) {
        update(delta);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        background.render(batch);
        currentArea.render(batch);
        batch.end();

        layout.render(batch, uiCamera);
    }

}
