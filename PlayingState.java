
package mygame;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.InputManager;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import com.jme3.ui.Picture;
import java.util.ArrayList;
import java.util.concurrent.Callable;

//playing state
public class PlayingState extends AbstractAppState {
    
    //enum for stages
    //PLAYER : player-controlled
    //COMPUTER : AI search
    //ANIME : AI move
    private enum Stage {
        PLAYER, COMPUTER, ANIME
    }
    
    //some basic managers for the app
    private Main app;
    private AppStateManager stateManager;
    private AssetManager assetManager;
    private InputManager inputManager;
    private Node guiNode;
    
    //record which stage is now
    private Stage stage;
    
    //picture and text for the prompt message
    private Picture frame;
    private BitmapText message;
    //a boolean to record if the message is being displayed
    private boolean isMessage = false;
    
    //displaying flanking prompts
    private boolean isFlanking = false;
    private Picture[] flanking = new Picture[5];
    private int flankingSize;
    
    //IMPORTANT picking control variables
    private boolean isPicking = false;
    private Picture pickingPic;
    private Vector2f oriCoord = new Vector2f(), preCoord = new Vector2f();
    
    //IMPORTANT record original piece infomation
    private PieceType oriType;
    private int oriIndex;
    
    //AI and anime
    private AI enemy = new AI();
    private Vector2f decision;
    private int startIndex, endIndex;
    private Picture movingPic;
    
    private boolean isOverComputing = false;
    private float pastTime = 0f;
    
    //the input listener for all instant user inputs
    //all events occur when the buttons or keys are released
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean isPressed, float tpf) {
            //the input is only responded when it's in the player-controlled stage
            if (stage != Stage.PLAYER) return;
            
            //button left is clicked
            if (name.equals(MAPPING_LEFT) && !isPressed) {
                //not in the middle of picking process
                if (!isPicking ) {
                    //get click index
                    preCoord = inputManager.getCursorPosition().clone();
                    oriIndex = MathTools.isInGridPiece(preCoord);

                    //when in the right place and the piece is controllable
                    if (oriIndex != -1 && Info.getPieceType(oriIndex).isFriend()) {
                        //set the picking picture
                        pickingPic = Info.getPiecePicture(oriIndex);
                        pickingPic.move(0f, 0f, PICKING_Z);
                        pickingPic.getMaterial().setColor("Color", 
                                    MathTools.getAlphaColor(PICKING_ALPHA));                
                        //record this pick
                        //set oriCoord and oriType for this click
                        oriCoord.x = pickingPic.getLocalTranslation().x;
                        oriCoord.y = pickingPic.getLocalTranslation().y;
                        oriType = Info.getPieceType(oriIndex);                   
                        //mark legality
                        Info.markLegalPlaying(oriIndex);

                        //when the picked is picked, set the piece type of the grid to empty
                        Info.setPieceType(oriIndex, PieceType.EMPTY);
                        //picked now
                        isPicking = true;
                    }
                }

                //when it's time to put down a picked piece
                else {
                    //get which grid is clicked
                    int index = MathTools.isInGrid(preCoord);
                    
                    //when the click is in the right place 
                    //and the clicked grid is legal to put a piece in
                    if (index != -1 && Info.getLegal(index)) {
                        /*//give user a message of movement (game log)
                        String message = oriType.getName() + " moves";
                        if (Info.getPieceType(index) == PieceType.EMPTY)
                            message += ".";
                        else {
                            message += " and captures " + Info.getPieceType(index).getName() + ".";
                        }
                        promptMessage(message);*/
                        
                        //delete the enemy piece picture which is captured
                        //and display flanking icons
                        if (Info.getPieceType(index).isEnemy()) {
                            PieceType type = Info.getPieceType(index);
                            guiNode.detachChild(Info.getPiecePicture(index));
                            
                            int attackTier = oriType.getTier();
                            int defendTier = type.getTier() + Info.getGridType(index).getImprovement(type);
                            if (attackTier < defendTier) {
                                ArrayList<Integer> array = Info.getFlanking(MathTools.toVec(index), oriIndex);
                                promptFlanking(array);
                            }
                        }
                        
                        //according to the pick information recorded, change the game info
                        //set the piece type
                        Info.setPieceType(index, oriType);
                        //set the picture's alpha and translation
                        pickingPic.getMaterial().setColor("Color", 
                                    MathTools.getAlphaColor(1f));
                        Vector2f vec = MathTools.toVecPiece(index);
                        pickingPic.setLocalTranslation(vec.x, vec.y, PIECE_Z);
                        Info.setPiecePicture(index, pickingPic);
                        
                        //now no piece is being picked
                        isPicking = false;
                        //remove mark for legality 
                        Info.unmarkLegal();
                        
                        stage = Stage.COMPUTER;
                        isOverComputing = false;
                        app.enqueue(new Callable() {
                            public Void call() {
                                decision = enemy.getMove();
                                isOverComputing = true;                            
                                return null;
                            }
                        });
                    }
                }
            }
            
            //button right is clicked
            if (name.equals(MAPPING_RIGHT) && !isPressed) {
                //is picking (wants to return picking piece)
                if (isPicking) {
                    //change the picking picture to original alpha and translation
                    pickingPic.setLocalTranslation(oriCoord.x, oriCoord.y, PIECE_Z);
                    pickingPic.getMaterial().setColor("Color", 
                            MathTools.getAlphaColor(1f));
                    //return to the original piece type
                    Info.setPieceType(oriIndex, oriType);
                    //no piece picked
                    isPicking = false;
                    //unmark legality of all grids
                    Info.unmarkLegal();
                }
            }
        }
    };
    
    //the input listener for continuous user inputs
    //keep the picking piece with the cursor
    private AnalogListener analogListener = new AnalogListener() {
        public void onAnalog(String name, float intensity, float tpf) { 
            if (stage != Stage.PLAYER) return;
            
            //when the mouse is moved and a piece is picked
            if (name.equals(MAPPING_MOVE) && isPicking) {
                //get new coord of the cursor
                Vector2f newCoord = inputManager.getCursorPosition();
                //the picture moves the difference between the previous coord and the new coord 
                Vector2f dir = newCoord.subtract(preCoord);
                pickingPic.move(dir.x, dir.y, PICKING_Z);
                //refresh the previous coord
                preCoord = inputManager.getCursorPosition().clone();
            }
        }
    };
    
    //initialize the state
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        //load basic pre-defined app manager
        super.initialize(stateManager, app);       
        this.app = (Main)app;
        this.stateManager = stateManager;
        assetManager = this.app.getAssetManager();
        inputManager = this.app.getInputManager();
        guiNode = this.app.getGuiNode(); 
        
        //add input listener
        inputManager.addMapping(MAPPING_LEFT, TRIGGER_LEFT);
        inputManager.addMapping(MAPPING_RIGHT, TRIGGER_RIGHT);
        inputManager.addMapping(MAPPING_MOVE, TRIGGER_MOVE_LEFT, TRIGGER_MOVE_RIGHT, 
                                TRIGGER_MOVE_DOWN, TRIGGER_MOVE_UP);
        inputManager.addListener(actionListener, MAPPING_LEFT, MAPPING_RIGHT);
        inputManager.addListener(analogListener, MAPPING_MOVE);
        
        //load assets
        loadFrame();
        loadMessage();
        loadFlanking();
        
        //initialize the stage
        stage = Stage.PLAYER;
        
        //debug();
    }
    
    //update the state
    @Override
    public void update(float tpf) {
        //remove message control
        if (isMessage) {
            MessageControl control = (MessageControl)frame.getControl(0);
            if (control.isOver()) {
                isMessage = false;
                frame.removeControl(control);
                message.removeControl(control);
                guiNode.detachChild(frame);
                guiNode.detachChild(message);
            }
        }
        
        //remove flanking control
        if (isFlanking) {
            MessageControl control = (MessageControl)flanking[0].getControl(0);
            if (control.isOver()) {
                isFlanking = false;
                for (int i = 0; i < flankingSize; i++) {
                    flanking[i].removeControl(control);
                    guiNode.detachChild(flanking[i]);
                }           
            }

        }
        
        //computing
        if (stage == Stage.COMPUTER) {
            pastTime += tpf;
            if (isOverComputing && pastTime >= COMPUTING_TIME) {
                startIndex = (int)decision.x;
                endIndex = (int)decision.y;        
                movingPic = Info.getPiecePicture(startIndex);
                movingPic.move(0f, 0f, PICKING_Z);
                movingPic.getMaterial().setColor("Color", MathTools.getAlphaColor(PICKING_ALPHA));
                movingPic.addControl(new MoveControl(startIndex, endIndex, MOVING_VELOCITY));
                
                pastTime = 0f;
                stage = Stage.ANIME;
            }
        }
        
        //show AI move animation
        if (stage == Stage.ANIME) {
            MoveControl control = (MoveControl)movingPic.getControl(0);
            if (control.isOver()) {
                movingPic.removeControl(control);
                
                /*//give user a message of movement (game log)
                String message = Info.getPieceType(startIndex).getName() + " moves";
                if (Info.getPieceType(endIndex) == PieceType.EMPTY)
                    message += ".";
                else {
                    message += " and captures " + Info.getPieceType(endIndex).getName() + ".";
                }
                promptMessage(message);*/
                
                if (Info.getPieceType(endIndex).isFriend()) {
                    PieceType type = Info.getPieceType(endIndex);
                    guiNode.detachChild(Info.getPiecePicture(endIndex));
                    
                    int attackTier = Info.getPieceType(startIndex).getTier();
                    int defendTier = type.getTier() + Info.getGridType(endIndex).getImprovement(type);
                    if (attackTier < defendTier) {
                        ArrayList<Integer> array = enemy.getFlanking(endIndex, startIndex);
                        promptFlanking(array);
                    }
                    
                }
                movingPic.getMaterial().setColor("Color", MathTools.getAlphaColor(1f));
                Info.setPiecePicture(endIndex, movingPic);
                Info.setPieceType(endIndex, Info.getPieceType(startIndex));
                Info.setPieceType(startIndex, PieceType.EMPTY);
                
                stage = Stage.PLAYER;
            }
        }
    }
    
    //load frame picture and DON'T display it now
    private void loadFrame() {
        frame = new Picture("frame");
        frame.setImage(assetManager, "Textures/frame.png", true);
        frame.setWidth(FRAME_SIZE_X); frame.setHeight(FRAME_SIZE_Y);
        frame.setLocalTranslation(FRAME_LOC_X, FRAME_LOC_Y, FRAME_Z);
    }
    
    //load message and DON'T display it now
    private void loadMessage() {
        BitmapFont font = assetManager.loadFont("Interface/Default.fnt");
        message = new BitmapText(font);
        message.setSize(font.getCharSet().getRenderedSize());
        message.setColor(ColorRGBA.Black);
    }
    
    //load flanking and DON'T display
    private void loadFlanking() {
        for (int i = 0; i < 5; i++) {
            flanking[i] = new Picture("flanking");
            flanking[i].setImage(assetManager, "Textures/flanking.png", true);
            flanking[i].setWidth(FLANKING_SIZE_X);
            flanking[i].setHeight(FLANKING_SIZE_Y);
        }
    }
    
    //prompt a message
    //msg : the content, must be ONE LINE ONLY
    private void promptMessage(final String msg) {
        //when there's already a messge, do nothing
        if (isMessage) return;
        isMessage = true;

        //set text and translation
        message.setText(msg);
        message.setLocalTranslation(SCREEN_WIDTH / 2 - message.getLineWidth() / 2, 
                                    FRAME_SIZE_Y / 2 + message.getLineHeight() / 2 + FRAME_LOC_Y, 
                                    MESSAGE_Z);

        //add message controls and display them
        frame.addControl(new MessageControl(MESSAGE_FADING_TIME, MESSAGE_LASTING_TIME));
        message.addControl(new MessageControl(MESSAGE_FADING_TIME, MESSAGE_LASTING_TIME));
        guiNode.attachChild(frame); guiNode.attachChild(message);
    }
    
    //prompt flanking
    private void promptFlanking(ArrayList<Integer> array) {
        if (array.isEmpty()) return;    
        if (isFlanking) return;
        isFlanking = true;
        
        flankingSize = array.size();
        for (int i = 0; i < flankingSize; i++) {
            int index = array.get(i);
            Vector2f vec = MathTools.toVec(index);
            flanking[i].setLocalTranslation(vec.x, vec.y, FLANKING_Z);
            
            flanking[i].addControl(new MessageControl(FLANKING_FADING_TIME, FLANKING_LASTING_TIME));
            guiNode.attachChild(flanking[i]);
        }
    }

    //some basic constants
    private static final float SCREEN_WIDTH = 1280f;
    private static final float SCREEN_HEIGHT = 800f;
    private static final float GRID_UNIT = 60f;
    private static final float PIECE_UNIT = 48f;
    private static final float CENTER_X = 530f;
    private static final int GRID_NUMBER = 91;
    
    //constants for message frame size and location
    private static final float FRAME_SIZE_X = SCREEN_WIDTH;
    private static final float FRAME_SIZE_Y = 50f;
    private static final float FRAME_LOC_X = 0f;
    private static final float FRAME_LOC_Y = 100f;
    
    //constants for flanking prompts size
    private static final float FLANKING_SIZE_X = 20f;
    private static final float FLANKING_SIZE_Y = 20f;
    
    //constants for z-coord
    private static final float PIECE_Z = 3f;
    private static final float FLANKING_Z = 4f;
    private static final float FRAME_Z = 4f;
    private static final float MESSAGE_Z = 5f;
    private static final float PICKING_Z = 6f;
    
    //constants for fading times
    private static final float MESSAGE_FADING_TIME = 0.5f;
    private static final float MESSAGE_LASTING_TIME = 1f;
    private static final float FLANKING_FADING_TIME = 0.5f;
    private static final float FLANKING_LASTING_TIME = 1f;
    private static final float COMPUTING_TIME = 2f;
    
    //constants for alpha channels
    private static final float PICKING_ALPHA = 0.5f;
    
    //constants for moving velocity (per second)
    private static final float MOVING_VELOCITY = 120f;
    
    //user input mappings and triggers
    private static final String MAPPING_LEFT = "left";
    private static final String MAPPING_RIGHT = "right";
    private static final String MAPPING_MOVE = "move";
    private static final MouseButtonTrigger TRIGGER_LEFT = 
                                new MouseButtonTrigger(MouseInput.BUTTON_LEFT);
    private static final MouseButtonTrigger TRIGGER_RIGHT = 
                                new MouseButtonTrigger(MouseInput.BUTTON_RIGHT);
    //mouse move
    private static final MouseAxisTrigger TRIGGER_MOVE_LEFT = 
                                            new MouseAxisTrigger(MouseInput.AXIS_X, false);
    private static final MouseAxisTrigger TRIGGER_MOVE_RIGHT = 
                                            new MouseAxisTrigger(MouseInput.AXIS_X, true);
    private static final MouseAxisTrigger TRIGGER_MOVE_DOWN = 
                                            new MouseAxisTrigger(MouseInput.AXIS_Y, false);
    private static final MouseAxisTrigger TRIGGER_MOVE_UP = 
                                            new MouseAxisTrigger(MouseInput.AXIS_Y, true);
    
    private void debug() {
        /*for (int i = 0; i < GRID_NUMBER; i++) {
            Info.setPieceType(i, PieceType.RABBLE);
        }*/
        BitmapFont font = assetManager.loadFont("Interface/Default.fnt");
        for (int i = 0; i < GRID_NUMBER; i++) {
            BitmapText x = new BitmapText(font);
            x.setText("" + i);
            x.setSize(font.getCharSet().getRenderedSize());
            x.setColor(ColorRGBA.Black);
            Vector2f vec = MathTools.toVec(i);
            x.setLocalTranslation(vec.x, vec.y + x.getHeight(), 6f);
            guiNode.attachChild(x);
        }
    }
    
}
