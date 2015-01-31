
package mygame;

import com.jme3.font.BitmapText;
import com.jme3.material.Material;
import com.jme3.scene.control.AbstractControl;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.ui.Picture;

//control for flickering objects
//the object would be always flickering unless the control is removed
public class FlickeringControl extends AbstractControl {
    
    //lowest : the lowest alpha
    //highest : the highest alpha
    //time : time of fading 
    private float lowest, highest, time;
    
    //pastTime : the time past
    //isAdding : if the alpha is adding
    private float pastTime;
    private boolean isAdding;
    
    //constructor
    public FlickeringControl(float lowest, float highest, float time) {
        //initialize
        this.lowest = lowest;
        this.highest = highest;
        this.time = time;
        pastTime = 0f;
        isAdding = true;
        
        //check the type of spatial
        if (spatial instanceof Picture) {
            Material mat = ((Picture)spatial).getMaterial();
            mat.setColor("Color", MathTools.getAlphaColor(lowest));
        }
        else if (spatial instanceof BitmapText)
            ((BitmapText)spatial).setColor(MathTools.getAlphaColor
                    (((BitmapText)spatial).getColor(), lowest));
    }
    
    //update
    protected void controlUpdate(float tpf) {
        //increase the time past
        pastTime += tpf;
        
        //check for the type 
        if (spatial instanceof Picture) {
            Material mat = ((Picture)spatial).getMaterial();
            //if it's time to switch inc alpha to dec alpha or vice versa
            if (pastTime >= time) {
                if (isAdding)
                    mat.setColor("Color", MathTools.getAlphaColor(highest));
                else mat.setColor("Color", MathTools.getAlphaColor(lowest));
                isAdding = !isAdding;
                pastTime = 0f;
            }
            //else calculate the alpha now
            else {
                if (isAdding)
                    mat.setColor("Color", MathTools.getAlphaColor
                            (lowest + pastTime / time * (highest - lowest)));
                else mat.setColor("Color", MathTools.getAlphaColor
                        (highest - pastTime / time * (highest - lowest)));
            }
        }
        else if (spatial instanceof BitmapText) {
            //if it's time to switch inc alpha to dec alpha or vice versa
            if (pastTime >= time) {
                if (isAdding)
                    ((BitmapText)spatial).setColor(MathTools.getAlphaColor
                            (((BitmapText)spatial).getColor(), highest));
                else ((BitmapText)spatial).setColor(MathTools.getAlphaColor
                        (((BitmapText)spatial).getColor(), lowest));
                isAdding = !isAdding;
                pastTime = 0f;
            }
            //else calculate the alpha now
            else {
                if (isAdding)
                    ((BitmapText)spatial).setColor(MathTools.getAlphaColor
                            (((BitmapText)spatial).getColor(), 
                            lowest + pastTime / time * (highest - lowest)));
                else ((BitmapText)spatial).setColor(MathTools.getAlphaColor
                        (((BitmapText)spatial).getColor(), 
                        highest - pastTime / time * (highest - lowest)));
            }
        }
    }
    
    protected void controlRender(RenderManager rm, ViewPort vp) { }
    
}
