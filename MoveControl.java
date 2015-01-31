
package mygame;

import com.jme3.scene.control.AbstractControl;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.math.Vector2f;

//control for fading in
public class MoveControl extends AbstractControl {
    
    private int start, end;
    private float velocity, time, pastTime;
    private Vector2f startVec, endVec, dir;

    private boolean isOver;
    
    //constructor
    public MoveControl(int start, int end, float velocity) {
        //initialize
        this.start = start;
        startVec = MathTools.toVecPiece(start);
        this.end = end;
        endVec = MathTools.toVecPiece(end);
        dir = endVec.subtract(startVec);
        this.velocity = velocity;
        time = dir.length() / velocity;
        pastTime = 0f;
        isOver = false;
    }
    
    //return isOver
    public boolean isOver() {
        return isOver;
    }
    
    //update
    protected void controlUpdate(float tpf) {
        //when control is over, do nothing
        if (isOver) return;
        
        pastTime += tpf;
        if (pastTime >= time) {
            spatial.setLocalTranslation(endVec.x, endVec.y, PIECE_Z);
            isOver = true;
        }
        else {
            Vector2f move = new Vector2f();
            move.x = startVec.x + dir.x * pastTime / time;
            move.y = startVec.y + dir.y * pastTime / time;
            spatial.setLocalTranslation(move.x, move.y, PIECE_Z);
        }
    }
    
    protected void controlRender(RenderManager rm, ViewPort vp) { }
    
    private static final float PIECE_Z = 3f;
    
}
