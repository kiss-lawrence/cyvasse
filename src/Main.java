
package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.system.AppSettings;
import com.jme3.renderer.RenderManager;

//entrance of the whole game
//adjust the settings

public class Main extends SimpleApplication {

    public static void main(String[] args) {
        Main app = new Main();
        AppSettings settings = new AppSettings(true);
        
        //adjust settings
        settings.setTitle("Cyvasse_rr");
        settings.setResolution(1280, 800);
        settings.setSamples(16);
        app.setSettings(settings);
        
        //remove default stats GUI
        app.setDisplayFps(false);
        app.setDisplayStatView(false);
        app.setShowSettings(false);
        
        //start the game
        app.start();
    }

    @Override
    public void simpleInitApp() {
        //use third-person viewpoint
        setPauseOnLostFocus(false);
        flyCam.setEnabled(false);
        
        //switch to the titlestate
        TitleState state = new TitleState();
        stateManager.attach(state);
    }

    @Override
    public void simpleUpdate(float tpf) { }

    @Override
    public void simpleRender(RenderManager rm) { }
    
}
