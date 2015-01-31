
package mygame;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.jme3.ui.Picture;
import com.jme3.font.BitmapText;
import com.jme3.font.BitmapFont;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.KeyTrigger;

//setting state 
//fade in the transition picture and fade out the cover picture
public class SettingState extends AbstractAppState {
    
    //enum for stages
    private enum Stage {
        FADE_IN, MAIN, FADE_OUT
    }
 
    //some basic managers for the app
    private Main app;
    private AppStateManager stateManager;
    private AssetManager assetManager;
    private InputManager inputManager;
    private Node guiNode;
    
    //record which stage is now
    private Stage stage;
    
    //pictures for the scenes
    private Picture background ,box, board, cover, transition, tutorial;
    //pictures for the box pieces
    private Picture[] king, spear, elephant, crossbow, trebuchet, 
                    lighthorse, heavyhorse, rabble, mountain, dragon;
    
    //numbers of the box pieces
    private int numKing, numSpear, numElephant, numCrossbow, numTrebuchet, 
                numLighthorse, numHeavyhorse, numRabble, numMountain, numDragon;
    //texts to display the numbers of the box pieces
    private BitmapText txtKing, txtSpear, txtElephant, txtCrossbow, txtTrebuchet, 
                        txtLighthorse, txtHeavyhorse, txtRabble, txtMountain,
                        txtDragon;
    
    //picture and text for the prompt message
    private Picture frame;
    private BitmapText message;
    //a boolean to record if the message is being displayed
    private boolean isMessage = false;
    
    //an integer index to record the page of tutorial 
    private int curTutorial = 0;
    
    //some IMPORTANT variables to record the picking and placing movements
    //if the user is picking a piece
    private boolean isPicking = false;
    //the picture reference for the picking piece
    private Picture pickingPic;
    //the previous coord before the piece is moved
    private Vector2f preCoord = new Vector2f();
    //the original coord before the piece is picked
    private Vector2f oriCoord = new Vector2f();
    //if the piece is picked from a box
    private boolean isFromBox;
    //the original type of the piece
    private PieceType oriType;
    //the original index of the piece, useful only when it's not picked from the box
    private int oriIndex;
    
    //the input listener for all instant user inputs
    //all events occur when the buttons or keys are released
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean isPressed, float tpf) {
            //the input is only responded when it's in the main stage
            if (stage != Stage.MAIN) return;
            
            //when left click
            if (name.equals(MAPPING_CHOOSE) && !isPressed) {
                //record the previous coord
                preCoord = inputManager.getCursorPosition().clone();
                
                //when no piece is being picked
                //that is to say, the user is choosing a piece to pick up
                if (!isPicking) {
                    //it's divided into two cases : picked from the box / picked from the board
                    //both cases are checked at the beginning
                    
                    //oriType records the type when the piece is picked from the box
                    oriType = MathTools.isInBoxPiece(preCoord);
                    //oriIndex records the index when the piece is picked from the board
                    oriIndex = MathTools.isInGridPiece(preCoord);
                    
                    //when the click in the box is in the right place (returned a non-empty type)
                    //HASN'T checked if there remains pieces of the same type in the box
                    if (oriType != PieceType.EMPTY) {
                        //according to the type of the piece, do different things
                        //when the type is king and there remains king
                        if (oriType == PieceType.KING && numKing > 0) {
                            //set picking picture
                            pickingPic = king[--numKing];
                            pickingPic.move(0f, 0f, PICKING_Z);
                            
                            //set number text
                            if (numKing == 0)
                                guiNode.detachChild(txtKing);
                            else txtKing.setText("" + numKing);
                            
                            //set other variables to record the information
                            oriCoord.x = pickingPic.getLocalTranslation().x;
                            oriCoord.y = pickingPic.getLocalTranslation().y;
                            isFromBox = true;
                            isPicking = true;
                            
                            //set the picture to semi-transparent
                            pickingPic.getMaterial().setColor("Color", 
                                        MathTools.getAlphaColor(PICKING_ALPHA));
                            
                            //mark legality
                            //king-dragon placement rule
                            if (numDragon == 0)
                                Info.markLegalSettingKing();
                            else Info.markLegalSetting();
                        }
                        
                        //for details, refer to the king type
                        else if (oriType == PieceType.SPEAR && numSpear > 0) {
                            pickingPic = spear[--numSpear];
                            pickingPic.move(0f, 0f, PICKING_Z);
                            if (numSpear == 0)
                                guiNode.detachChild(txtSpear);
                            else txtSpear.setText("" + numSpear);
                            oriCoord.x = pickingPic.getLocalTranslation().x;
                            oriCoord.y = pickingPic.getLocalTranslation().y;
                            isFromBox = true;
                            isPicking = true;
                            pickingPic.getMaterial().setColor("Color", 
                                        MathTools.getAlphaColor(PICKING_ALPHA));
                            Info.markLegalSetting();
                        }
                        
                        //for details, refer to the king type
                        else if (oriType == PieceType.ELEPHANT && numElephant > 0) {
                            pickingPic = elephant[--numElephant];
                            pickingPic.move(0f, 0f, PICKING_Z);
                            if (numElephant == 0)
                                guiNode.detachChild(txtElephant);
                            else txtElephant.setText("" + numElephant);
                            oriCoord.x = pickingPic.getLocalTranslation().x;
                            oriCoord.y = pickingPic.getLocalTranslation().y;
                            isFromBox = true;
                            isPicking = true;
                            pickingPic.getMaterial().setColor("Color", 
                                        MathTools.getAlphaColor(PICKING_ALPHA));
                            Info.markLegalSetting();
                        }
                                                
                        //for details, refer to the king type
                        else if (oriType == PieceType.CROSSBOW && numCrossbow > 0) {
                            pickingPic = crossbow[--numCrossbow];
                            pickingPic.move(0f, 0f, PICKING_Z);
                            if (numCrossbow == 0)
                                guiNode.detachChild(txtCrossbow);
                            else txtCrossbow.setText("" + numCrossbow);
                            oriCoord.x = pickingPic.getLocalTranslation().x;
                            oriCoord.y = pickingPic.getLocalTranslation().y;
                            isFromBox = true;
                            isPicking = true;
                            pickingPic.getMaterial().setColor("Color", 
                                        MathTools.getAlphaColor(PICKING_ALPHA));
                            Info.markLegalSetting();
                        }
                        
                        //for details, refer to the king type
                        else if (oriType == PieceType.TREBUCHET && numTrebuchet > 0) {
                            pickingPic = trebuchet[--numTrebuchet];
                            pickingPic.move(0f, 0f, PICKING_Z);
                            if (numTrebuchet == 0)
                                guiNode.detachChild(txtTrebuchet);
                            else txtTrebuchet.setText("" + numTrebuchet);
                            oriCoord.x = pickingPic.getLocalTranslation().x;
                            oriCoord.y = pickingPic.getLocalTranslation().y;
                            isFromBox = true;
                            isPicking = true;
                            pickingPic.getMaterial().setColor("Color", 
                                        MathTools.getAlphaColor(PICKING_ALPHA));
                            Info.markLegalSetting();
                        }
                        
                        //for details, refer to the king type
                        else if (oriType == PieceType.LIGHTHORSE && numLighthorse > 0) {
                            pickingPic = lighthorse[--numLighthorse];
                            pickingPic.move(0f, 0f, PICKING_Z);
                            if (numLighthorse == 0)
                                guiNode.detachChild(txtLighthorse);
                            else txtLighthorse.setText("" + numLighthorse);
                            oriCoord.x = pickingPic.getLocalTranslation().x;
                            oriCoord.y = pickingPic.getLocalTranslation().y;
                            isFromBox = true;
                            isPicking = true;
                            pickingPic.getMaterial().setColor("Color", 
                                        MathTools.getAlphaColor(PICKING_ALPHA));
                            Info.markLegalSetting();
                        }
                        
                        //for details, refer to the king type
                        else if (oriType == PieceType.HEAVYHORSE && numHeavyhorse > 0) {
                            pickingPic = heavyhorse[--numHeavyhorse];
                            pickingPic.move(0f, 0f, PICKING_Z);
                            if (numHeavyhorse == 0)
                                guiNode.detachChild(txtHeavyhorse);
                            else txtHeavyhorse.setText("" + numHeavyhorse);
                            oriCoord.x = pickingPic.getLocalTranslation().x;
                            oriCoord.y = pickingPic.getLocalTranslation().y;
                            isFromBox = true;
                            isPicking = true;
                            pickingPic.getMaterial().setColor("Color", 
                                        MathTools.getAlphaColor(PICKING_ALPHA));
                            Info.markLegalSetting();
                        }
                        
                        //for details, refer to the king type
                        else if (oriType == PieceType.RABBLE && numRabble > 0) {
                            pickingPic = rabble[--numRabble];
                            pickingPic.move(0f, 0f, PICKING_Z);
                            if (numRabble == 0)
                                guiNode.detachChild(txtRabble);
                            else txtRabble.setText("" + numRabble);
                            oriCoord.x = pickingPic.getLocalTranslation().x;
                            oriCoord.y = pickingPic.getLocalTranslation().y;
                            isFromBox = true;
                            isPicking = true;
                            pickingPic.getMaterial().setColor("Color", 
                                        MathTools.getAlphaColor(PICKING_ALPHA));
                            Info.markLegalSetting();
                        }
                        
                        //for details, refer to the king type
                        else if (oriType == PieceType.MOUNTAIN && numMountain > 0) {
                            pickingPic = mountain[--numMountain];
                            pickingPic.move(0f, 0f, PICKING_Z);
                            if (numMountain == 0)
                                guiNode.detachChild(txtMountain);
                            else txtMountain.setText("" + numMountain);
                            oriCoord.x = pickingPic.getLocalTranslation().x;
                            oriCoord.y = pickingPic.getLocalTranslation().y;
                            isFromBox = true;
                            isPicking = true;
                            pickingPic.getMaterial().setColor("Color", 
                                        MathTools.getAlphaColor(PICKING_ALPHA));
                            Info.markLegalSetting();
                        }
                        
                        //for details, refer to the king type
                        else if (oriType == PieceType.DRAGON && numDragon > 0) {
                            pickingPic = dragon[--numDragon];
                            pickingPic.move(0f, 0f, PICKING_Z);
                            if (numDragon == 0)
                                guiNode.detachChild(txtDragon);
                            else txtDragon.setText("" + numDragon);
                            oriCoord.x = pickingPic.getLocalTranslation().x;
                            oriCoord.y = pickingPic.getLocalTranslation().y;
                            isFromBox = true;
                            isPicking = true;
                            pickingPic.getMaterial().setColor("Color", 
                                        MathTools.getAlphaColor(PICKING_ALPHA));
                            //king-dragon placement rule
                            if (numKing == 0)
                                Info.markLegalSettingDragon();
                            else Info.markLegalSetting();
                        }
                    } 
                    
                    //when the click is in the grid and there is a piece in the grid
                    else if (oriIndex != -1 && Info.getPieceType(oriIndex) != PieceType.EMPTY) {
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
                        
                        //when the picked is picked, set the piece type of the grid to empty
                        Info.setPieceType(oriIndex, PieceType.EMPTY);
                        
                        //set booleans
                        isFromBox = false;
                        isPicking = true;
                        
                        //mark legality
                        //pay attention to the king-dragon rule
                        if (oriType == PieceType.KING && numDragon == 0)
                            Info.markLegalSettingKing();
                        else if (oriType == PieceType.DRAGON && numKing == 0)
                            Info.markLegalSettingDragon();
                        else Info.markLegalSetting();
                        
                        //if the picking piece is king, set the grid type to its original type
                        //remove the fortress
                        if (oriType == PieceType.KING) {
                            Info.setOriginalGridType(oriIndex, assetManager);
                            Info.setHomeFortress(-1);
                        }
                    }
                }
                
                //if there is a piece being picked
                //that is to say, the user is choosing a grid to place it
                else {
                    //get which grid is clicked
                    int index = MathTools.isInGrid(preCoord);
                    
                    //when the click is in the right place 
                    //and the clicked grid is legal to put a piece in
                    if (index != -1 && Info.getLegal(index)) {
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
                        
                        //if the user place a king, then set the grid type to fortress
                        if (oriType == PieceType.KING) {
                            Picture pic = Info.getGridPicture(index);
                            pic.setImage(assetManager, "Textures/grid_fortress.png", true);
                            Info.setGridType(index, GridType.FORTRESS);
                            Info.setHomeFortress(index);
                        }
                    }
                    //give a illegal placement message
                    /*else promptMessage(MESSAGE_ILLEGAL_PLACEMENT);*/
                }
            }
            
            //if right click and there is a piece being picked
            if (name.equals(MAPPING_CANCEL) && !isPressed && isPicking) {
                //change the picking picture to original alpha and translation
                pickingPic.setLocalTranslation(oriCoord.x, oriCoord.y, PIECE_Z);
                pickingPic.getMaterial().setColor("Color", 
                        MathTools.getAlphaColor(1f));
                //no piece picked
                isPicking = false;
                //unmark legality of all grids
                Info.unmarkLegal();
                
                //if the piece is picked from the piece box
                if (isFromBox) {
                    //set the piece number and text according to the type
                    if (oriType == PieceType.KING) {
                        if (++numKing == 1)
                            guiNode.attachChild(txtKing);
                        else txtKing.setText("" + numKing);
                    }
                    else if (oriType == PieceType.SPEAR) {
                        if (++numSpear == 1)
                            guiNode.attachChild(txtSpear);
                        else txtSpear.setText("" + numSpear);
                    }
                    else if (oriType == PieceType.ELEPHANT) {
                        if (++numElephant == 1)
                            guiNode.attachChild(txtElephant);
                        else txtElephant.setText("" + numElephant);
                    }
                    else if (oriType == PieceType.CROSSBOW) {
                        if (++numCrossbow == 1)
                            guiNode.attachChild(txtCrossbow);
                        else txtCrossbow.setText("" + numCrossbow);
                    }
                    else if (oriType == PieceType.TREBUCHET) {
                        if (++numTrebuchet == 1)
                            guiNode.attachChild(txtTrebuchet);
                        else txtTrebuchet.setText("" + numTrebuchet);
                    }
                    else if (oriType == PieceType.LIGHTHORSE) {
                        if (++numLighthorse == 1)
                            guiNode.attachChild(txtLighthorse);
                        else txtLighthorse.setText("" + numLighthorse);
                    }
                    else if (oriType == PieceType.HEAVYHORSE) {
                        if (++numHeavyhorse == 1)
                            guiNode.attachChild(txtHeavyhorse);
                        else txtHeavyhorse.setText("" + numHeavyhorse);
                    }
                    else if (oriType == PieceType.RABBLE) {
                        if (++numRabble == 1)
                            guiNode.attachChild(txtRabble);
                        else txtRabble.setText("" + numRabble);
                    }
                    else if (oriType == PieceType.MOUNTAIN) {
                        if (++numMountain == 1)
                            guiNode.attachChild(txtMountain);
                        else txtMountain.setText("" + numMountain);
                    }
                    else if (oriType == PieceType.DRAGON) {
                        if (++numDragon == 1)
                            guiNode.attachChild(txtDragon);
                        else txtDragon.setText("" + numDragon);
                    }
                }
                
                //if the piece is picked from the board
                else {
                    //return to the original piece type
                    Info.setPieceType(oriIndex, oriType);
                    
                    //if the type is king, add the fortress grid again
                    if (oriType == PieceType.KING) {
                        Picture pic = Info.getGridPicture(oriIndex);
                        pic.setImage(assetManager, "Textures/grid_fortress.png", true);
                        Info.setGridType(oriIndex, GridType.FORTRESS);
                        Info.setHomeFortress(oriIndex);
                    }
                }
            }
            
            //if the user presses Enter
            if (name.equals(MAPPING_DECIDE) && !isPressed) {
                //if no piece is being picked
                if (!isPicking) {
                    //if setting is legal
                    if (checkSetting()) {
                        //load enemy assets
                        AI.enemySetting(assetManager, guiNode);
                        
                        //switch to the FADE_OUT stage
                        //fade the cover and the tutorial
                        stage = Stage.FADE_OUT;
                        cover.addControl(new FadeOutControl(1f, 0f, FADE_OUT_TIME));
                        tutorial.addControl(new FadeOutControl(1f, 0f, FADE_OUT_TIME));
                    }
                    //if setting is illegal, give a message
                    else promptMessage(MESSAGE_ILLEGAL_SETTING);
                }
                //nothing occurs when a piece is picked
                else { }
            }
            
            //if the left or right key is pressed
            //check if it's on the first or last page
            if (name.equals(MAPPING_PREVIOUS) && !isPressed && curTutorial > 0) {
                String key = "Textures/tutorial_" + (--curTutorial) + ".png";
                tutorial.setImage(assetManager, key, true);
            }   
            if (name.equals(MAPPING_NEXT) && !isPressed && 
                    curTutorial < TUTORIAL_NUMBER - 1) {
                String key = "Textures/tutorial_" + (++curTutorial) + ".png";
                tutorial.setImage(assetManager, key, true);
            }
        }
    };
    
    //the input listener for continuous user inputs
    //keep the picking piece with the cursor
    private AnalogListener analogListener = new AnalogListener() {
        public void onAnalog(String name, float intensity, float tpf) { 
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
        
        //add input listeners
        inputManager.addMapping(MAPPING_CHOOSE, TRIGGER_CHOOSE);
        inputManager.addMapping(MAPPING_CANCEL, TRIGGER_CANCEL);
        inputManager.addMapping(MAPPING_MOVE, TRIGGER_MOVE_LEFT, TRIGGER_MOVE_RIGHT, 
                                TRIGGER_MOVE_DOWN, TRIGGER_MOVE_UP);
        inputManager.addMapping(MAPPING_DECIDE, TRIGGER_DECIDE);
        inputManager.addMapping(MAPPING_PREVIOUS, TRIGGER_PREVIOUS);
        inputManager.addMapping(MAPPING_NEXT, TRIGGER_NEXT);
        inputManager.addListener(actionListener, MAPPING_CHOOSE, MAPPING_CANCEL, 
                                        MAPPING_DECIDE, MAPPING_PREVIOUS, MAPPING_NEXT);
        inputManager.addListener(analogListener, MAPPING_MOVE);

        //load the scene
        loadBackground();
        loadBoard();
        loadBox();
        loadCover();
        loadTutorial();
        loadGrid();
        loadFrame();
        loadMessage();
        loadNumber();
        loadPiece();
        
        //load the transition picture
        //switch to the FADE_IN stage
        loadTransition();
        transition.addControl(new FadeOutControl(1f, 0f, FADE_IN_TIME));
        stage = Stage.FADE_IN;
    }
    
    //update the state
    @Override
    public void update(float tpf) {
        //when FADE_IN stage
        if (stage == Stage.FADE_IN) {
            //get the control
            FadeOutControl control = (FadeOutControl)transition.getControl(0);
            //if the control is over
            if (control.isOver()) {
                transition.removeControl(control);
                guiNode.detachChild(transition);
                //switch to the MAIN stage
                stage = Stage.MAIN;
            }
        }
        //when MAIN stage and a message is prompted
        else if (stage == Stage.MAIN && isMessage) {
            //get the control
            MessageControl control = (MessageControl)frame.getControl(0);
            //if the message is over
            if (control.isOver()) {
                //not displaying message now
                isMessage = false;
                
                //remove control, picture and text
                frame.removeControl(control);
                message.removeControl(control);
                guiNode.detachChild(frame);
                guiNode.detachChild(message);
            }
        }
        //when FADE_OUT stage
        else if (stage == Stage.FADE_OUT) {
            //get the control
            FadeOutControl control = (FadeOutControl)cover.getControl(0);
            //if the control is over
            if (control.isOver()) {
                //remove cover and tutorial
                guiNode.detachChild(cover);
                guiNode.detachChild(tutorial);
                
                //go to the new playing state
                PlayingState state = new PlayingState();
                stateManager.attach(state);
                stateManager.detach(this);
            }
        }
    }
    
    //load background picture and display it
    private void loadBackground() {
        background = new Picture("background");
        background.setImage(assetManager, "Textures/background.png", false);
        background.setWidth(SCREEN_WIDTH); background.setHeight(SCREEN_HEIGHT);
        background.setLocalTranslation(0f, 0f, BACKGROUND_Z);
        guiNode.attachChild(background);
    }
    
    //load board(frame) picture and display it
    private void loadBoard() {
        board = new Picture("board");
        board.setImage(assetManager, "Textures/board.png", false);
        board.setWidth(BOARD_SIZE_X); board.setHeight(BOARD_SIZE_Y);
        board.setLocalTranslation(BOARD_LOC_X, BOARD_LOC_Y, BOARD_Z);
        guiNode.attachChild(board);
    }

    //load box picture and display it
    private void loadBox() {
        box = new Picture("box");
        box.setImage(assetManager, "Textures/box.png", false);
        box.setWidth(BOX_SIZE_X); box.setHeight(BOX_SIZE_Y);
        box.setLocalTranslation(BOX_LOC_X, BOX_LOC_Y, BOX_Z);
        guiNode.attachChild(box);
    }    
    
    //load cover picture and display it
    private void loadCover() {
        cover = new Picture("cover");
        cover.setImage(assetManager, "Textures/cover.png", true);
        cover.setWidth(COVER_SIZE_X); cover.setHeight(COVER_SIZE_Y);
        cover.setLocalTranslation(COVER_LOC_X, COVER_LOC_Y, COVER_Z);
        guiNode.attachChild(cover);
    }
    
    //load tutorial picture and display it
    //initially the first page
    private void loadTutorial() {
        tutorial = new Picture("tutorial");
        tutorial.setImage(assetManager, "Textures/tutorial_0.png", true);
        tutorial.setWidth(TUTORIAL_SIZE_X); tutorial.setHeight(TUTORIAL_SIZE_Y);
        tutorial.setLocalTranslation(TUTORIAL_LOC_X, TUTORIAL_LOC_Y, TUTORIAL_Z);
        guiNode.attachChild(tutorial);
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
    
    //load grids and display them
    private void loadGrid() {
        for (int i = 0; i < GRID_NUMBER; i++) {
            Picture pic = new Picture("grid");
            GridType type = Info.getGridType(i);
            if (type == GridType.FOREST)
                pic.setImage(assetManager, "Textures/grid_forest.png", true);
            else if (type == GridType.HILL)
                pic.setImage(assetManager, "Textures/grid_hill.png", true);
            else if (type == GridType.WATER)
                pic.setImage(assetManager, "Textures/grid_water.png", true);       
            pic.setWidth(GRID_UNIT); pic.setHeight(GRID_UNIT);
            Vector2f vec = MathTools.toVec(i);
            pic.setLocalTranslation(vec.x, vec.y, GRID_Z);
            Info.setGridPicture(i, pic);
            
            guiNode.attachChild(pic);
        }
    }
    
    //load pieces(in the box) and display them
    private void loadPiece() {
        
        king = new Picture[numKing];
        for (int i = 0; i < numKing; i++) {
            king[i] = new Picture("piece");
            king[i].setImage(assetManager, "Textures/piece_king.png", true);
            king[i].setWidth(PIECE_UNIT); king[i].setHeight(PIECE_UNIT);
            king[i].setLocalTranslation(KING_LOC_X, KING_LOC_Y, PIECE_Z);
            guiNode.attachChild(king[i]);
        }
        
        spear = new Picture[numSpear];
        for (int i = 0; i < numSpear; i++) {
            spear[i] = new Picture("piece");
            spear[i].setImage(assetManager, "Textures/piece_spear.png", true);
            spear[i].setWidth(PIECE_UNIT); spear[i].setHeight(PIECE_UNIT);
            spear[i].setLocalTranslation(SPEAR_LOC_X, SPEAR_LOC_Y, PIECE_Z);
            guiNode.attachChild(spear[i]);
        }
        
        elephant = new Picture[numElephant];
        for (int i = 0; i < numElephant; i++) {
            elephant[i] = new Picture("piece");
            elephant[i].setImage(assetManager, "Textures/piece_elephant.png", true);
            elephant[i].setWidth(PIECE_UNIT); elephant[i].setHeight(PIECE_UNIT);
            elephant[i].setLocalTranslation(ELEPHANT_LOC_X, ELEPHANT_LOC_Y, PIECE_Z);
            guiNode.attachChild(elephant[i]);
        }
        
        crossbow = new Picture[numCrossbow];
        for (int i = 0; i < numCrossbow; i++) {
            crossbow[i] = new Picture("piece");
            crossbow[i].setImage(assetManager, "Textures/piece_crossbow.png", true);
            crossbow[i].setWidth(PIECE_UNIT); crossbow[i].setHeight(PIECE_UNIT);
            crossbow[i].setLocalTranslation(CROSSBOW_LOC_X, CROSSBOW_LOC_Y, PIECE_Z);
            guiNode.attachChild(crossbow[i]);
        }
        
        trebuchet = new Picture[numTrebuchet];
        for (int i = 0; i < numTrebuchet; i++) {
            trebuchet[i] = new Picture("piece");
            trebuchet[i].setImage(assetManager, "Textures/piece_trebuchet.png", true);
            trebuchet[i].setWidth(PIECE_UNIT); trebuchet[i].setHeight(PIECE_UNIT);
            trebuchet[i].setLocalTranslation(TREBUCHET_LOC_X, TREBUCHET_LOC_Y, PIECE_Z);
            guiNode.attachChild(trebuchet[i]);
        }
        
        lighthorse = new Picture[numLighthorse];
        for (int i = 0; i < numLighthorse; i++) {
            lighthorse[i] = new Picture("piece");
            lighthorse[i].setImage(assetManager, "Textures/piece_lighthorse.png", true);
            lighthorse[i].setWidth(PIECE_UNIT); lighthorse[i].setHeight(PIECE_UNIT);
            lighthorse[i].setLocalTranslation(LIGHTHORSE_LOC_X, LIGHTHORSE_LOC_Y, PIECE_Z);
            guiNode.attachChild(lighthorse[i]);
        }
        
        heavyhorse = new Picture[numHeavyhorse];
        for (int i = 0; i < numHeavyhorse; i++) {
            heavyhorse[i] = new Picture("piece");
            heavyhorse[i].setImage(assetManager, "Textures/piece_heavyhorse.png", true);
            heavyhorse[i].setWidth(PIECE_UNIT); heavyhorse[i].setHeight(PIECE_UNIT);
            heavyhorse[i].setLocalTranslation(HEAVYHORSE_LOC_X, HEAVYHORSE_LOC_Y, PIECE_Z);
            guiNode.attachChild(heavyhorse[i]);
        }
        
        rabble = new Picture[numRabble];
        for (int i = 0; i < numRabble; i++) {
            rabble[i] = new Picture("piece");
            rabble[i].setImage(assetManager, "Textures/piece_rabble.png", true);
            rabble[i].setWidth(PIECE_UNIT); rabble[i].setHeight(PIECE_UNIT);
            rabble[i].setLocalTranslation(RABBLE_LOC_X, RABBLE_LOC_Y, PIECE_Z);
            guiNode.attachChild(rabble[i]); 
        }
        
        mountain = new Picture[numMountain];
        for (int i = 0; i < numMountain; i++) {
            mountain[i] = new Picture("piece");
            mountain[i].setImage(assetManager, "Textures/piece_mountain.png", true);
            mountain[i].setWidth(PIECE_UNIT); mountain[i].setHeight(PIECE_UNIT);
            mountain[i].setLocalTranslation(MOUNTAIN_LOC_X, MOUNTAIN_LOC_Y, PIECE_Z);
            guiNode.attachChild(mountain[i]);
        }
        
        dragon = new Picture[numDragon];
        for (int i = 0; i < numDragon; i++) {
            dragon[i] = new Picture("piece");
            dragon[i].setImage(assetManager, "Textures/piece_dragon.png", true);
            dragon[i].setWidth(PIECE_UNIT); dragon[i].setHeight(PIECE_UNIT);
            dragon[i].setLocalTranslation(DRAGON_LOC_X, DRAGON_LOC_Y, PIECE_Z);
            guiNode.attachChild(dragon[i]);
        }     
    }
    
    //load numbers and display them
    private void loadNumber() {
        numKing = 1; numSpear = 2; numElephant = 2; numCrossbow = 2; 
        numTrebuchet = 2; numLighthorse = 2; numHeavyhorse = 2; 
        numRabble = 6; numMountain = 6; numDragon = 1;       
        BitmapFont font = assetManager.loadFont("Interface/Default.fnt");
        
        txtKing = new BitmapText(font);
        txtKing.setText("" + numKing);
        txtKing.setSize(font.getCharSet().getRenderedSize());
        txtKing.setColor(ColorRGBA.Black);
        txtKing.setLocalTranslation(KING_LOC_X + PIECE_UNIT - txtKing.getLineWidth(), 
                                    KING_LOC_Y + txtKing.getLineHeight(), 
                                    NUMBER_Z);
        guiNode.attachChild(txtKing);
        
        txtSpear = new BitmapText(font);
        txtSpear.setText("" + numSpear);
        txtSpear.setSize(font.getCharSet().getRenderedSize());
        txtSpear.setColor(ColorRGBA.Black);
        txtSpear.setLocalTranslation(SPEAR_LOC_X + PIECE_UNIT - txtSpear.getLineWidth(), 
                                    SPEAR_LOC_Y + txtSpear.getLineHeight(), 
                                    NUMBER_Z);
        guiNode.attachChild(txtSpear);
        
        txtElephant = new BitmapText(font);
        txtElephant.setText("" + numElephant);
        txtElephant.setSize(font.getCharSet().getRenderedSize());
        txtElephant.setColor(ColorRGBA.Black);
        txtElephant.setLocalTranslation(ELEPHANT_LOC_X + PIECE_UNIT - txtElephant.getLineWidth(), 
                                    ELEPHANT_LOC_Y + txtElephant.getLineHeight(), 
                                    NUMBER_Z);
        guiNode.attachChild(txtElephant);
        
        txtCrossbow = new BitmapText(font);
        txtCrossbow.setText("" + numCrossbow);
        txtCrossbow.setSize(font.getCharSet().getRenderedSize());
        txtCrossbow.setColor(ColorRGBA.Black);
        txtCrossbow.setLocalTranslation(CROSSBOW_LOC_X + PIECE_UNIT - txtCrossbow.getLineWidth(), 
                                    CROSSBOW_LOC_Y + txtCrossbow.getLineHeight(), 
                                    NUMBER_Z);
        guiNode.attachChild(txtCrossbow);
        
        txtTrebuchet = new BitmapText(font);
        txtTrebuchet.setText("" + numTrebuchet);
        txtTrebuchet.setSize(font.getCharSet().getRenderedSize());
        txtTrebuchet.setColor(ColorRGBA.Black);
        txtTrebuchet.setLocalTranslation(TREBUCHET_LOC_X + PIECE_UNIT - txtTrebuchet.getLineWidth(), 
                                    TREBUCHET_LOC_Y + txtTrebuchet.getLineHeight(), 
                                    NUMBER_Z);
        guiNode.attachChild(txtTrebuchet);
        
        txtLighthorse = new BitmapText(font);
        txtLighthorse.setText("" + numLighthorse);
        txtLighthorse.setSize(font.getCharSet().getRenderedSize());
        txtLighthorse.setColor(ColorRGBA.Black);
        txtLighthorse.setLocalTranslation(LIGHTHORSE_LOC_X + PIECE_UNIT - txtLighthorse.getLineWidth(), 
                                    LIGHTHORSE_LOC_Y + txtLighthorse.getLineHeight(), 
                                    NUMBER_Z);
        guiNode.attachChild(txtLighthorse);
        
        txtHeavyhorse = new BitmapText(font);
        txtHeavyhorse.setText("" + numHeavyhorse);
        txtHeavyhorse.setSize(font.getCharSet().getRenderedSize());
        txtHeavyhorse.setColor(ColorRGBA.Black);
        txtHeavyhorse.setLocalTranslation(HEAVYHORSE_LOC_X + PIECE_UNIT - txtHeavyhorse.getLineWidth(), 
                                    HEAVYHORSE_LOC_Y + txtHeavyhorse.getLineHeight(), 
                                    NUMBER_Z);
        guiNode.attachChild(txtHeavyhorse);
        
        txtRabble = new BitmapText(font);
        txtRabble.setText("" + numRabble);
        txtRabble.setSize(font.getCharSet().getRenderedSize());
        txtRabble.setColor(ColorRGBA.Black);
        txtRabble.setLocalTranslation(RABBLE_LOC_X + PIECE_UNIT - txtRabble.getLineWidth(), 
                                    RABBLE_LOC_Y + txtRabble.getLineHeight(), 
                                    NUMBER_Z);
        guiNode.attachChild(txtRabble);
        
        txtMountain = new BitmapText(font);
        txtMountain.setText("" + numMountain);
        txtMountain.setSize(font.getCharSet().getRenderedSize());
        txtMountain.setColor(ColorRGBA.Black);
        txtMountain.setLocalTranslation(MOUNTAIN_LOC_X + PIECE_UNIT - txtMountain.getLineWidth(), 
                                    MOUNTAIN_LOC_Y + txtMountain.getLineHeight(), 
                                    NUMBER_Z);
        guiNode.attachChild(txtMountain);
        
        txtDragon = new BitmapText(font);
        txtDragon.setText("" + numDragon);
        txtDragon.setSize(font.getCharSet().getRenderedSize());
        txtDragon.setColor(ColorRGBA.Black);
        txtDragon.setLocalTranslation(DRAGON_LOC_X + PIECE_UNIT - txtDragon.getLineWidth(), 
                                    DRAGON_LOC_Y + txtDragon.getLineHeight(), 
                                    NUMBER_Z);
        guiNode.attachChild(txtDragon);
    }
    
    //load transition picture and display it
    private void loadTransition() {
        transition = new Picture("transition");
        transition.setImage(assetManager, "Textures/transition.png", true);
        transition.setWidth(SCREEN_WIDTH); transition.setHeight(SCREEN_HEIGHT);
        transition.setLocalTranslation(0f, 0f, TRANSITION_Z);
        guiNode.attachChild(transition);        
    }
    
    //check if the user's setting is legal
    private boolean checkSetting() {
        //if no piece except the dragon hasn't been placed
        if (numKing > 0 || numSpear > 0 || numElephant > 0 || numCrossbow > 0 || 
            numTrebuchet > 0 || numLighthorse > 0 || numHeavyhorse > 0 || 
            numRabble > 0 || numMountain > 0 || numDragon > 0)
            return false;
        return true;
    }
    
    //prompt a message
    //msg : the content, must be ONE LINE ONLY
    private void promptMessage(String msg) {
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
    
    //some basic constants
    private static final float SCREEN_WIDTH = 1280f;
    private static final float SCREEN_HEIGHT = 800f;
    private static final float GRID_UNIT = 60f;
    private static final float PIECE_UNIT = 48f;
    private static final float CENTER_X = 530f;
    private static final float CENTER_Y = 0.5f * SCREEN_HEIGHT;
    private static final float PIECE_X = 1020f;
    private static final float PIECE_Y = 214f;
    private static final int GRID_NUMBER = 91;
    
    //constants for z-coord
    private static final float BACKGROUND_Z = 0f;
    private static final float BOARD_Z = 1f;
    private static final float BOX_Z = 1f;
    private static final float GRID_Z = 2f;
    private static final float PIECE_Z = 3f;
    private static final float NUMBER_Z = 4f;
    private static final float COVER_Z = 4f;
    private static final float FRAME_Z = 4f;
    private static final float TUTORIAL_Z = 5f;
    private static final float MESSAGE_Z = 5f;
    private static final float PICKING_Z = 6f;
    private static final float TRANSITION_Z = 7f;
    
    //constants for board size and location
    private static final float BOARD_SIZE_X = GRID_UNIT * 11 + 2 * 20f;
    private static final float BOARD_SIZE_Y = BOARD_SIZE_X;
    private static final float BOARD_LOC_X = CENTER_X - 0.5f * BOARD_SIZE_X;
    private static final float BOARD_LOC_Y = CENTER_Y - 0.5f * BOARD_SIZE_Y;
    
    //constants for box size and location
    private static final float BOX_SIZE_X = PIECE_UNIT * 2.25f + 2 * 20f;
    private static final float BOX_SIZE_Y = PIECE_UNIT * 7.75f + 2 * 20f;
    private static final float BOX_LOC_X = PIECE_X - 1.25f * PIECE_UNIT - 20f;
    private static final float BOX_LOC_Y = PIECE_Y - 20f;
    
    //constants for cover size and location
    private static final float COVER_SIZE_X = GRID_UNIT * 11;
    private static final float COVER_SIZE_Y = 0.5f * COVER_SIZE_X;
    private static final float COVER_LOC_X = CENTER_X - 5.5f * GRID_UNIT;
    private static final float COVER_LOC_Y = 0.5f * SCREEN_HEIGHT;
    
    //constants for tutorial size and location and number
    private static final float TUTORIAL_SIZE_X = COVER_SIZE_X - 2 * 20f;
    private static final float TUTORIAL_SIZE_Y = COVER_SIZE_Y - 2 * 20f;
    private static final float TUTORIAL_LOC_X = COVER_LOC_X + 20f;
    private static final float TUTORIAL_LOC_Y = COVER_LOC_Y + 20f;
    private static final int TUTORIAL_NUMBER = 3;
    
    //constants for message frame size and location
    private static final float FRAME_SIZE_X = SCREEN_WIDTH;
    private static final float FRAME_SIZE_Y = 50f;
    private static final float FRAME_LOC_X = 0f;
    private static final float FRAME_LOC_Y = 100f;
    
    //constants for location of pieces in the box
    private static final float DRAGON_LOC_X = PIECE_X;
    private static final float DRAGON_LOC_Y = PIECE_Y;
    private static final float MOUNTAIN_LOC_X = PIECE_X - 1.25f * PIECE_UNIT;
    private static final float MOUNTAIN_LOC_Y = DRAGON_LOC_Y + 0.75f * PIECE_UNIT;
    private static final float RABBLE_LOC_X = PIECE_X;
    private static final float RABBLE_LOC_Y = MOUNTAIN_LOC_Y + 0.75f * PIECE_UNIT;
    private static final float HEAVYHORSE_LOC_X = PIECE_X - 1.25f * PIECE_UNIT;
    private static final float HEAVYHORSE_LOC_Y = RABBLE_LOC_Y + 0.75f * PIECE_UNIT;
    private static final float LIGHTHORSE_LOC_X = PIECE_X;
    private static final float LIGHTHORSE_LOC_Y = HEAVYHORSE_LOC_Y + 0.75f * PIECE_UNIT;
    private static final float TREBUCHET_LOC_X = PIECE_X - 1.25f * PIECE_UNIT;
    private static final float TREBUCHET_LOC_Y = LIGHTHORSE_LOC_Y + 0.75f * PIECE_UNIT;
    private static final float CROSSBOW_LOC_X = PIECE_X;
    private static final float CROSSBOW_LOC_Y = TREBUCHET_LOC_Y + 0.75f * PIECE_UNIT;
    private static final float ELEPHANT_LOC_X = PIECE_X - 1.25f * PIECE_UNIT;
    private static final float ELEPHANT_LOC_Y = CROSSBOW_LOC_Y + 0.75f * PIECE_UNIT;
    private static final float SPEAR_LOC_X = PIECE_X;
    private static final float SPEAR_LOC_Y = ELEPHANT_LOC_Y + 0.75f * PIECE_UNIT;
    private static final float KING_LOC_X = PIECE_X - 1.25f * PIECE_UNIT;
    private static final float KING_LOC_Y = SPEAR_LOC_Y + 0.75f * PIECE_UNIT;
    
    //constants for times(fade_in, fade_out, message_time)
    private static final float FADE_IN_TIME = 1.5f;
    private static final float FADE_OUT_TIME = 1.5f;
    private static final float MESSAGE_FADING_TIME = 0.5f;
    private static final float MESSAGE_LASTING_TIME = 1f;   

    //the alpha channel of the picking picture
    private static final float PICKING_ALPHA = 0.7f; 
    
    //user input mappings and triggers
    private static final String MAPPING_CHOOSE = "choose";
    private static final String MAPPING_CANCEL = "cancel";
    private static final String MAPPING_MOVE = "move";
    private static final String MAPPING_DECIDE = "decide";
    private static final String MAPPING_PREVIOUS = "previous";
    private static final String MAPPING_NEXT = "next";
    //left click for choose
    private static final MouseButtonTrigger TRIGGER_CHOOSE = 
                                            new MouseButtonTrigger(MouseInput.BUTTON_LEFT);
    //right click for cancel
    private static final MouseButtonTrigger TRIGGER_CANCEL = 
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
    //enter for decide
    private static final KeyTrigger TRIGGER_DECIDE = 
                                            new KeyTrigger(KeyInput.KEY_RETURN);
    //left and right arrow for changing tutorial page
    private static final KeyTrigger TRIGGER_PREVIOUS = 
                                            new KeyTrigger(KeyInput.KEY_LEFT);
    private static final KeyTrigger TRIGGER_NEXT = 
                                            new KeyTrigger(KeyInput.KEY_RIGHT);
    
    //message
    private static final String MESSAGE_ILLEGAL_PLACEMENT = 
                        "Illegal placement, please check the setting rules again.";
    private static final String MESSAGE_ILLEGAL_SETTING = 
                        "You haven't placed all your pieces on your half board.";
    
    private void debug() {
        BitmapFont font = assetManager.loadFont("Interface/Default.fnt");
        for (int i = 0; i < GRID_NUMBER; i++) {
            BitmapText x = new BitmapText(font);
            x.setText("" + i);
            x.setSize(font.getCharSet().getRenderedSize());
            x.setColor(ColorRGBA.Black);
            Vector2f vec = MathTools.toVec(i);
            x.setLocalTranslation(vec.x, vec.y + x.getHeight(), NUMBER_Z);
            guiNode.attachChild(x);
        }
    }
    
}
