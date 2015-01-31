
package mygame;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.jme3.font.BitmapText;
import com.jme3.font.BitmapFont;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.ActionListener;
import com.jme3.ui.Picture;
import com.jme3.math.ColorRGBA;

//the state for the game title
public class TitleState extends AbstractAppState {
    
    //enum for the titlestate
    //FADE_IN : decrese the alpha channel of the transition picture(black picture)
    //MAIN : wait for the user input(Enter)
    //FADE_OUT : opposite stage of FADE_IN
    private enum Stage {
        FADE_IN, MAIN, FADE_OUT
    }
    
    //define constants for xy-coords
    private static final float SCREEN_WIDTH = 1280f;
    private static final float SCREEN_HEIGHT = 800f;
    private static final float BOTTOM_INTERVAL = 40f;
    
    //define constants for z-coords
    private static final float TITLE_Z = 0f;
    private static final float PROMPT_Z = 1f;
    private static final float TRANSITION_Z = 2f;
    
    //define constants for anim
    //include time and alpha limits of fading
    private static final float FADE_IN_TIME = 1.5f;
    private static final float FADE_OUT_TIME = 1.5f;
    private static final float FLICKERING_TIME = 0.5f;
    private static final float FLICKERING_ALPHA_L = 0.2f;
    private static final float FLICKERING_ALPHA_H = 1f;
    
    //define constans for user input
    //press Enter(Return) to switch to the FADE_OUT stage
    private static final String MAPPING_START = "start";
    private static final KeyTrigger TRIGGER_START = new KeyTrigger(KeyInput.KEY_RETURN);
    
    //basic managers for the app
    private Main app;
    private AppStateManager stateManager;
    private AssetManager assetManager;
    private InputManager inputManager;
    private Node guiNode;
    
    //record which stage is currently
    private Stage stage;
    
    //pictures and texts
    private Picture title;
    private Picture transition;
    private BitmapText prompt;
    
    //input listener
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean isPressed, float tpf) {
            //preconditions : user pressed Enter && stage is Main
            if (name.equals(MAPPING_START) && stage == Stage.MAIN && !isPressed) {
                //remove prompt
                guiNode.detachChild(prompt);
                
                //add transiton picture again with a fade control attached to it
                //switch the stage to FADE_OUT
                guiNode.attachChild(transition);
                transition.addControl(new FadeInControl(0f, 1f, FADE_OUT_TIME));
                stage = Stage.FADE_OUT;
            }
        }
    };
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        //get basic managers from app
        super.initialize(stateManager, app);       
        this.app = (Main)app;
        this.stateManager = stateManager;
        assetManager = this.app.getAssetManager();
        inputManager = this.app.getInputManager();
        guiNode = this.app.getGuiNode();
        
        //add input listener
        inputManager.addMapping(MAPPING_START, TRIGGER_START);
        inputManager.addListener(actionListener, MAPPING_START);

        //load title picture
        title = new Picture("title");
        title.setImage(assetManager, "Textures/title.png", false);
        title.setWidth(SCREEN_WIDTH);
        title.setHeight(SCREEN_HEIGHT);
        title.setLocalTranslation(0f, 0f, TITLE_Z);
        guiNode.attachChild(title);
        
        //load prompt text
        BitmapFont font = assetManager.loadFont("Interface/Default.fnt");
        prompt = new BitmapText(font);
        prompt.setText("Press Enter to Start");
        prompt.setSize(font.getCharSet().getRenderedSize());
        prompt.setColor(ColorRGBA.Black);
        prompt.setLocalTranslation(SCREEN_WIDTH / 2f - prompt.getLineWidth() / 2f, 
                                    BOTTOM_INTERVAL + prompt.getLineHeight(), 
                                    PROMPT_Z);

        //load transition picture
        transition = new Picture("transition");
        transition.setImage(assetManager, "Textures/transition.png", true);
        transition.setWidth(SCREEN_WIDTH);
        transition.setHeight(SCREEN_HEIGHT);
        transition.setLocalTranslation(0f, 0f, TRANSITION_Z);
        
        //add fade_out control to transition pic
        //because transition is fading out when FADE_IN stage and vice versa
        transition.addControl(new FadeOutControl(1f, 0f, FADE_IN_TIME));     
        guiNode.attachChild(transition);
        stage = Stage.FADE_IN;
    }
    
    @Override
    public void update(float tpf) {
        //when stage is FADE_IN
        if (stage == Stage.FADE_IN) {
            //get the control
            FadeOutControl control = (FadeOutControl)transition.getControl(0);
            //when control(this stage) is over
            if (control.isOver()) {
                //remove transiton
                transition.removeControl(control);
                guiNode.detachChild(transition);
                
                //add prompt to the scene and give it a flicking control
                prompt.addControl(new FlickeringControl(FLICKERING_ALPHA_L, 
                                                        FLICKERING_ALPHA_H, 
                                                        FLICKERING_TIME));
                guiNode.attachChild(prompt);
                
                //switch to MAIN stage
                stage = Stage.MAIN;
            }
        }
        // when FADE_OUT stage
        else if (stage == Stage.FADE_OUT) {
            //get control
            FadeInControl control = (FadeInControl)transition.getControl(0);
            if (control.isOver()) {
                //switch to the new setting state
                guiNode.detachAllChildren();
                SettingState state = new SettingState();
                stateManager.attach(state);
                stateManager.detach(this);
            }           
        }
    }
    
    //remove all picture in the titlestate
    @Override
    public void cleanup() {
        guiNode.detachAllChildren();
        super.cleanup();
    }
    
}
