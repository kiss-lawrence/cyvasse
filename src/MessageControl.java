
package mygame;

import com.jme3.font.BitmapText;
import com.jme3.material.Material;
import com.jme3.scene.control.AbstractControl;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.ui.Picture;

//control for a message
//the message will fade in then display on the scene for a while then fade out
public class MessageControl extends AbstractControl {
    
    //enum for the control
    private enum Stage {
        FADE_IN, WAIT, FADE_OUT
    }
    
    //fadingTime and lastingTime
    //time for fade_in = time for fade_out
    private float fadingTime, lastingTime;
    
    //pastTime : the time that has pasted
    //isOver : if the control is over
    //stage : record which stage is currently
    private float pastTime;
    private boolean isOver;
    private Stage stage;
    
    //return isOver
    public boolean isOver() {
        return isOver;
    }
    
    //constructor
    public MessageControl(float fadingTime, float lastingTime) {
        //initialize
        this.fadingTime = fadingTime;
        this.lastingTime = lastingTime;
        pastTime = 0f;
        isOver = false;
        stage = Stage.FADE_IN;
        
        //check for the type of the spatial
        if (spatial instanceof Picture) {
            Material mat = ((Picture)spatial).getMaterial();
            mat.setColor("Color", MathTools.getAlphaColor(0f));
        }
        else if (spatial instanceof BitmapText)
            ((BitmapText)spatial).setColor(MathTools.getAlphaColor
                    (((BitmapText)spatial).getColor(), 0f));
    }
    
    //update 
    //divide into 3 parts(for 3 stages) and check types in each part
    protected void controlUpdate(float tpf) {
        //when the control is over, do nothing
        if (isOver) return;
        
        //increase the time past
        pastTime += tpf;
        
        //when fade_in stage
        if (stage == Stage.FADE_IN) {
            if (spatial instanceof Picture) {
                //go to wait stage
                if (pastTime > fadingTime) {
                    ((Picture)spatial).getMaterial().
                            setColor("Color", MathTools.getAlphaColor(1f));
                    pastTime = 0f;
                    stage = Stage.WAIT;
                }
                //caluculate the alpha
                else ((Picture)spatial).getMaterial().setColor("Color", 
                        MathTools.getAlphaColor(pastTime / fadingTime));
            }
            else if (spatial instanceof BitmapText) {
                //go to wait stage
                if (pastTime > fadingTime) {
                    ((BitmapText)spatial).setColor(MathTools.getAlphaColor
                            (((BitmapText)spatial).getColor(), 1f));
                    pastTime = 0f;
                    stage = Stage.WAIT;
                }
                //caluculate the alpha
                else ((BitmapText)spatial).setColor(MathTools.getAlphaColor
                    (((BitmapText)spatial).getColor(), pastTime / fadingTime));
            }
        }
        //when wating stage
        else if (stage == Stage.WAIT) {
            //it's a timer actually
            if (pastTime > lastingTime) {
                pastTime = 0f;
                //go to the fade_out stage
                stage = Stage.FADE_OUT;
            }
        }
        //when fade_out stage
        else if (stage == Stage.FADE_OUT) {
            if (spatial instanceof Picture) {
                //stop the control
                if (pastTime > fadingTime) {
                    ((Picture)spatial).getMaterial().
                            setColor("Color", MathTools.getAlphaColor(0f));
                    isOver = true;
                }
                //calculate the alpha
                else ((Picture)spatial).getMaterial().setColor("Color", 
                        MathTools.getAlphaColor(1f - pastTime / fadingTime));
            }
            else if (spatial instanceof BitmapText) {
                //stop the control
                if (pastTime > fadingTime) {
                    ((BitmapText)spatial).setColor(MathTools.getAlphaColor
                            (((BitmapText)spatial).getColor(), 0f));
                    isOver = true;
                }
                //calculate the alpha
                else ((BitmapText)spatial).setColor(MathTools.getAlphaColor
                    (((BitmapText)spatial).getColor(), 1f - pastTime / fadingTime));
            }
        }
    }    
    
    protected void controlRender(RenderManager rm, ViewPort vp) { }
    
}
