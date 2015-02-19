
package mygame;

import com.jme3.scene.control.AbstractControl;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.material.Material;
import com.jme3.ui.Picture;
import com.jme3.font.BitmapText;

//control for fading out
public class FadeOutControl extends AbstractControl {

    //lowest : the lowest alpha
    //highest : the highest alpha
    //time : time of fading    
    private float time, lowest, highest;
    
    //pastTime: record the time past
    //isOver : if the fading is over
    private float pastTime;
    private boolean isOver;
    
    //constructor
    public FadeOutControl(float highest, float lowest, float time) {
        //initialize
        this.time = time;
        this.lowest = lowest;
        this.highest = highest;
        pastTime = 0f;
        isOver = false;
        
        //check the type of the spatial
        if (spatial instanceof Picture) {
            Material mat = ((Picture)spatial).getMaterial();
            mat.setColor("Color", MathTools.getAlphaColor(highest));
        }
        else if (spatial instanceof BitmapText)
            ((BitmapText)spatial).setColor(MathTools.getAlphaColor
                    (((BitmapText)spatial).getColor(), highest));
        
    }
    
    //return isOver
    public boolean isOver() {
        return isOver;
    }
    
    //update
    protected void controlUpdate(float tpf) {
        //when control is over, do nothing
        if (isOver) return;
        
        //increase pastTime
        pastTime += tpf;
        
        //if spatial is a picture
        if (spatial instanceof Picture) {
            Material mat = ((Picture)spatial).getMaterial();
            //stop fading
            if (pastTime >= time) {
                mat.setColor("Color", MathTools.getAlphaColor(lowest));
                isOver = true;
            }
            //change alpha
            else mat.setColor("Color", MathTools.getAlphaColor
                        (highest - pastTime / time * (highest - lowest)));
        }
        //if spatial is a text
        else if (spatial instanceof BitmapText) {
            //stop fading
            if (pastTime >= time) {
                ((BitmapText)spatial).setColor(MathTools.getAlphaColor
                        (((BitmapText)spatial).getColor(), lowest));
                isOver = true;
            }
            //change alpha
            else ((BitmapText)spatial).setColor(MathTools.getAlphaColor
                    (((BitmapText)spatial).getColor(), 
                    highest - pastTime / time * (highest - lowest)));
        }
    }
    
    protected void controlRender(RenderManager rm, ViewPort vp) { }
    
}
