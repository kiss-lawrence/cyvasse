
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import com.jme3.ui.Picture;
import com.jme3.scene.Node;
import java.util.ArrayList;
import java.util.ArrayDeque;

//AI class
//use to produce a virtual enemy
public class AI {
    
    //produce a setting randomly
    public static void enemySetting(AssetManager assetManager, Node guiNode) {
        int index = FastMath.nextRandomInt(79, 83);
        Info.setEnemyFortress(index);
        Info.setGridType(index, GridType.FORTRESS);
        Info.getGridPicture(index).setImage(assetManager, "Textures/grid_fortress.png", false);
        Info.setPieceType(index, PieceType.KING_E);
        Picture kingE = new Picture("kingE");
        kingE.setImage(assetManager, "Textures/piece_king_e.png", true);
        kingE.setWidth(PIECE_UNIT); kingE.setHeight(PIECE_UNIT);
        Vector2f vec = MathTools.toVecPiece(index);
        kingE.setLocalTranslation(vec.x, vec.y, PIECE_Z);
        Info.setPiecePicture(index, kingE);
        guiNode.attachChild(kingE);
        
        while (Info.getPieceType(index) != PieceType.EMPTY || 
                MathTools.getStep(Info.getEnemyFortress(), index) > 4)
            index = FastMath.nextRandomInt(61, 84);
        Info.setPieceType(index, PieceType.DRAGON_E);
        Picture dragonE = new Picture("dragonE");
        dragonE.setImage(assetManager, "Textures/piece_dragon_e.png", true);
        dragonE.setWidth(PIECE_UNIT); dragonE.setHeight(PIECE_UNIT);
        vec = MathTools.toVecPiece(index);
        dragonE.setLocalTranslation(vec.x, vec.y, PIECE_Z);
        Info.setPiecePicture(index, dragonE);
        guiNode.attachChild(dragonE);
        
        for (int i = 0; i < 6; i++) {
            while (Info.getPieceType(index) != PieceType.EMPTY)
                index = FastMath.nextRandomInt(51, 77);
            Info.setPieceType(index, PieceType.RABBLE_E);
            Picture rabbleE = new Picture("rabbleE");
            rabbleE.setImage(assetManager, "Textures/piece_rabble_e.png", true);
            rabbleE.setWidth(PIECE_UNIT); rabbleE.setHeight(PIECE_UNIT);
            vec = MathTools.toVecPiece(index);
            rabbleE.setLocalTranslation(vec.x, vec.y, PIECE_Z);
            Info.setPiecePicture(index, rabbleE);
            guiNode.attachChild(rabbleE);
        }
        
        for (int i = 0; i < 6; i++) {
            while (Info.getPieceType(index) != PieceType.EMPTY)
                index = FastMath.nextRandomInt(51, 69);
            Info.setPieceType(index, PieceType.MOUNTAIN_E);
            Picture mountainE = new Picture("mountainE");
            mountainE.setImage(assetManager, "Textures/piece_mountain_e.png", true);
            mountainE.setWidth(PIECE_UNIT); mountainE.setHeight(PIECE_UNIT);
            vec = MathTools.toVecPiece(index);
            mountainE.setLocalTranslation(vec.x, vec.y, PIECE_Z);
            Info.setPiecePicture(index, mountainE);
            guiNode.attachChild(mountainE);
        }
        
        for (int i = 0; i < 2; i++) {
            while (Info.getPieceType(index) != PieceType.EMPTY)
                index = FastMath.nextRandomInt(61, 84);
            Info.setPieceType(index, PieceType.SPEAR_E);
            Picture spearE = new Picture("spearE");
            spearE.setImage(assetManager, "Textures/piece_spear_e.png", true);
            spearE.setWidth(PIECE_UNIT); spearE.setHeight(PIECE_UNIT);
            vec = MathTools.toVecPiece(index);
            spearE.setLocalTranslation(vec.x, vec.y, PIECE_Z);
            Info.setPiecePicture(index, spearE);
            guiNode.attachChild(spearE);
        }
        
        for (int i = 0; i < 2; i++) {
            while (Info.getPieceType(index) != PieceType.EMPTY)
                index = FastMath.nextRandomInt(61, 84);
            Info.setPieceType(index, PieceType.CROSSBOW_E);
            Picture crossbowE = new Picture("crossbow_e");
            crossbowE.setImage(assetManager, "Textures/piece_crossbow_e.png", true);
            crossbowE.setWidth(PIECE_UNIT); crossbowE.setHeight(PIECE_UNIT);
            vec = MathTools.toVecPiece(index);
            crossbowE.setLocalTranslation(vec.x, vec.y, PIECE_Z);
            Info.setPiecePicture(index, crossbowE);
            guiNode.attachChild(crossbowE);
        }
        
        for (int i = 0; i < 2; i++) {
            while (Info.getPieceType(index) != PieceType.EMPTY)
                index = FastMath.nextRandomInt(61, 84);
            Info.setPieceType(index, PieceType.LIGHTHORSE_E);
            Picture lighthorseE = new Picture("lighthorse_e");
            lighthorseE.setImage(assetManager, "Textures/piece_lighthorse_e.png", true);
            lighthorseE.setWidth(PIECE_UNIT); lighthorseE.setHeight(PIECE_UNIT);
            vec = MathTools.toVecPiece(index);
            lighthorseE.setLocalTranslation(vec.x, vec.y, PIECE_Z);
            Info.setPiecePicture(index, lighthorseE);
            guiNode.attachChild(lighthorseE);
        }
        
        for (int i = 0; i < 2; i++) {
            while (Info.getPieceType(index) != PieceType.EMPTY)
                index = FastMath.nextRandomInt(61, 84);
            Info.setPieceType(index, PieceType.ELEPHANT_E);
            Picture elephantE = new Picture("elephant_e");
            elephantE.setImage(assetManager, "Textures/piece_elephant_e.png", true);
            elephantE.setWidth(PIECE_UNIT); elephantE.setHeight(PIECE_UNIT);
            vec = MathTools.toVecPiece(index);
            elephantE.setLocalTranslation(vec.x, vec.y, PIECE_Z);
            Info.setPiecePicture(index, elephantE);
            guiNode.attachChild(elephantE);
        }
        
        for (int i = 0; i < 2; i++) {
            while (Info.getPieceType(index) != PieceType.EMPTY)
                index = FastMath.nextRandomInt(61, 84);
            Info.setPieceType(index, PieceType.TREBUCHET_E);
            Picture trebuchetE = new Picture("trebuchet_e");
            trebuchetE.setImage(assetManager, "Textures/piece_trebuchet_e.png", true);
            trebuchetE.setWidth(PIECE_UNIT); trebuchetE.setHeight(PIECE_UNIT);
            vec = MathTools.toVecPiece(index);
            trebuchetE.setLocalTranslation(vec.x, vec.y, PIECE_Z);
            Info.setPiecePicture(index, trebuchetE);
            guiNode.attachChild(trebuchetE);
        }
        
        for (int i = 0; i < 2; i++) {
            while (Info.getPieceType(index) != PieceType.EMPTY)
                index = FastMath.nextRandomInt(61, 84);
            Info.setPieceType(index, PieceType.HEAVYHORSE_E);
            Picture heavyhorseE = new Picture("heavyhorse_e");
            heavyhorseE.setImage(assetManager, "Textures/piece_heavyhorse_e.png", true);
            heavyhorseE.setWidth(PIECE_UNIT); heavyhorseE.setHeight(PIECE_UNIT);
            vec = MathTools.toVecPiece(index);
            heavyhorseE.setLocalTranslation(vec.x, vec.y, PIECE_Z);
            Info.setPiecePicture(index, heavyhorseE);
            guiNode.attachChild(heavyhorseE);
        }
    }
    
    //this class is a simplized copy of info class
    //only record some basic game infomation
    private static class SimpleInfo {
        
        private GridType[] gridType = new GridType[GRID_NUMBER];
        private PieceType[] pieceType = new PieceType[GRID_NUMBER];
        
        private boolean king = true, kingE = true;

        public SimpleInfo() {
            for (int i = 0; i < GRID_NUMBER; i++) {
                gridType[i] = Info.getGridType(i);
                pieceType[i] = Info.getPieceType(i);
            }
        }
        
        public SimpleInfo(SimpleInfo info) {
            for (int i = 0; i < GRID_NUMBER; i++) {
                this.gridType[i] = info.gridType[i];
                this.pieceType[i] = info.pieceType[i];
            }
        }
        
        public boolean isEnd() {
            if (!king || !kingE) return true;
            else return false;
        }

    }
    
    private Vector2f decision;
    
    //get the optimized movement for a certain search depth
    public Vector2f getMove() {
        minValue(new SimpleInfo(), SEARCH_DEPTH, -1000000f, 1000000f);
        return decision;
    }
    
    //get the minValue
    //used by AI
    private float minValue(SimpleInfo info, int depth, float alpha, float beta) {
        if (depth == 0 || info.isEnd()) return evaluate(info);
        
        float min = beta;
        ArrayList<Vector2f> array = getAllLegalMove(info);
        for (Vector2f vec : array) {
            Thread.yield();
            int indexFrom = (int)vec.x, indexTo = (int)vec.y;
            SimpleInfo infoNext = new SimpleInfo(info);
            if (infoNext.pieceType[indexTo] == PieceType.KING)
                info.king = false;
            infoNext.pieceType[indexTo] = infoNext.pieceType[indexFrom];
            infoNext.pieceType[indexFrom] = PieceType.EMPTY;
            float value = maxValue(infoNext, depth - 1, alpha, min);
            if (value < min) {
                min = value;
                if (depth == SEARCH_DEPTH) decision = vec.clone();
            }
            if (min < alpha) return alpha;
        }
        return min;
    }
    
    //get the maxValue
    //used by player
    private float maxValue(SimpleInfo info, int depth, float alpha, float beta) {
        if (depth == 0 || info.isEnd()) return evaluate(info);
        
        float max = alpha;
        ArrayList<Vector2f> array = getAllLegalMoveM(info);
        for (Vector2f vec : array) {
            Thread.yield();
            int indexFrom = (int)vec.x, indexTo = (int)vec.y;
            SimpleInfo infoNext = new SimpleInfo(info);
            if (infoNext.pieceType[indexTo] == PieceType.KING_E)
                info.kingE = false;
            infoNext.pieceType[indexTo] = infoNext.pieceType[indexFrom];
            infoNext.pieceType[indexFrom] = PieceType.EMPTY;
            float value = minValue(infoNext, depth - 1, max, beta);
            if (value > max) {
                max = value;
            }
            if (max > beta) return beta;
        }
        return max;
    }
    
    //evaluate the board case
    private float evaluate(SimpleInfo info) {
        float sum = 0;
        for (int i = 0; i < GRID_NUMBER; i++)
            sum += evaluatePiece(info.pieceType[i], info.gridType[i]);
        return sum;
    }
    
    //evaluate a piece value
    //only for controllable pieces
    private float evaluatePiece(PieceType pieceType, GridType gridType) {
        float value = 0;
        if (pieceType == PieceType.RABBLE || pieceType == PieceType.RABBLE_E) 
            value = gridType.getImprovement(pieceType) == 1 ? RABBLE_VALUE * 1.5f : RABBLE_VALUE;
        else if (pieceType == PieceType.LIGHTHORSE || pieceType == PieceType.LIGHTHORSE_E) 
            value = gridType.getImprovement(pieceType) == 1 ? LIGHTHORSE_VALUE * 1.5f : LIGHTHORSE_VALUE;
        else if (pieceType == PieceType.HEAVYHORSE || pieceType == PieceType.HEAVYHORSE_E) 
            value = gridType.getImprovement(pieceType) == 1 ? HEAVYHORSE_VALUE * 1.5f : HEAVYHORSE_VALUE;
        else if (pieceType == PieceType.SPEAR || pieceType == PieceType.SPEAR_E) 
            value = gridType.getImprovement(pieceType) == 1 ? SPEAR_VALUE * 1.5f : SPEAR_VALUE;
        else if (pieceType == PieceType.ELEPHANT || pieceType == PieceType.ELEPHANT_E) 
            value = gridType.getImprovement(pieceType) == 1 ? ELEPHANT_VALUE * 1.5f : ELEPHANT_VALUE;
        else if (pieceType == PieceType.CROSSBOW || pieceType == PieceType.CROSSBOW_E) 
            value = gridType.getImprovement(pieceType) == 1 ? CROSSBOW_VALUE * 1.5f : CROSSBOW_VALUE;
        else if (pieceType == PieceType.TREBUCHET || pieceType == PieceType.TREBUCHET) 
            value = gridType.getImprovement(pieceType) == 1 ? TREBUCHET_VALUE * 1.5f : TREBUCHET_VALUE;
        else if (pieceType == PieceType.DRAGON || pieceType == PieceType.DRAGON_E) 
            value = gridType.getImprovement(pieceType) == 1 ? DRAGON_VALUE * 1.5f : DRAGON_VALUE;
        else if (pieceType == PieceType.KING || pieceType == PieceType.KING_E) 
            value = gridType.getImprovement(pieceType) == 1 ? KING_VALUE * 1.5f : KING_VALUE;
        
        if (pieceType.isEnemy()) value = -value;
        return value;
    }
    
    //find all legal movements of all pieces on the board
    private ArrayList<Vector2f> getAllLegalMove(SimpleInfo info) {
        ArrayList<Vector2f> array = new ArrayList<Vector2f>();
        for (int i = 0; i < GRID_NUMBER; i++) {
            ArrayList<Vector2f> arr = getLegalMove(info, i);
            for (int j = 0; j < arr.size(); j++)
                array.add(arr.get(j));
        }
        return array;
    }
    
    //find legal movement of a piece
    private ArrayList<Vector2f> getLegalMove(SimpleInfo info, int index) {
        ArrayList<Vector2f> array;       
        PieceType type = info.pieceType[index];
        if (type == PieceType.KING_E || type == PieceType.RABBLE_E)
            array = getLegalMoveKing(info, index);
        else if (type == PieceType.SPEAR_E)
            array = getLegalMoveSpear(info, index);
        else if (type == PieceType.ELEPHANT_E)
            array = getLegalMoveElephant(info, index);
        else if (type == PieceType.CROSSBOW_E)
            array = getLegalMoveCrossbow(info, index);
        else if (type == PieceType.TREBUCHET_E)
            array = getLegalMoveTrebuchet(info, index);
        else if (type == PieceType.LIGHTHORSE_E)
            array = getLegalMoveLighthorse(info, index);
        else if (type == PieceType.HEAVYHORSE_E)
            array = getLegalMoveHeavyhorse(info, index);
        else if (type == PieceType.DRAGON_E)
            array = getLegalMoveDragon(info, index);
        else array = new ArrayList<Vector2f>();
        return array;
    }
    
    //used for get KING and RABBLE movements
    private ArrayList<Vector2f> getLegalMoveKing(SimpleInfo info, int index) {
        ArrayList<Vector2f> array = new ArrayList<Vector2f>();
        Vector2f vec = MathTools.toVec(index);
        Vector2f[] v = MathTools.getOrthogonalGrids(vec);
        for (Vector2f loc : v) {
            if (MathTools.isInBoard(loc)) {
                int indexTarget = MathTools.toInt(loc);
                if (info.pieceType[indexTarget] == PieceType.EMPTY)
                    array.add(new Vector2f(index, indexTarget));
                else if (info.pieceType[indexTarget].isFriend())
                    if (checkFlanking(info, index, indexTarget))
                        array.add(new Vector2f(index, indexTarget));
            }
        }
        return array;
    }
    
    //used for get SPEAR movements
    private ArrayList<Vector2f> getLegalMoveSpear(SimpleInfo info, int index) {
        ArrayList<Vector2f> array = new ArrayList<Vector2f>();
        Vector2f vec = MathTools.toVec(index);
        Vector2f[] v = MathTools.getDiagonalGrids(vec);
        for (Vector2f dir : v) {
            Vector2f unit = dir.subtract(vec);
            for (int i = 0; i < 2; i++) {
                Vector2f loc = vec.add(unit.mult(i + 1));
                if (MathTools.isInBoard(loc)) {
                    int indexTarget = MathTools.toInt(loc);
                    PieceType type = info.pieceType[indexTarget];
                    if (type.isEnemy() || type.isMountain()) break;
                    else if (type == PieceType.EMPTY)
                        array.add(new Vector2f(index, indexTarget));
                    else if (type.isFriend()) {
                        if (checkFlanking(info, index, indexTarget))
                            array.add(new Vector2f(index, indexTarget));
                        break;
                    }
                }
                else break;
            }
        }
        return array;
    }
    
    //used for get ELEPHANT movements
    private ArrayList<Vector2f> getLegalMoveElephant(SimpleInfo info, int index) {
        ArrayList<Vector2f> array = new ArrayList<Vector2f>();
        Vector2f vec = MathTools.toVec(index);
        Vector2f[] v = MathTools.getDiagonalGrids(vec);
        for (Vector2f dir : v) {
            Vector2f unit = dir.subtract(vec);
            for (int i = 0; i < 2; i++) {
                Vector2f loc = vec.add(unit.mult(i + 1));
                if (MathTools.isInBoard(loc)) {
                    int indexTarget = MathTools.toInt(loc);
                    PieceType type = info.pieceType[indexTarget];
                    if (type.isEnemy() || type.isMountain()) break;
                    else if (type == PieceType.EMPTY)
                        array.add(new Vector2f(index, indexTarget));
                    else if (type.isFriend()) {
                        if (checkFlanking(info, index, indexTarget))
                            array.add(new Vector2f(index, indexTarget));
                        break;
                    }
                }
                else break;
            }
        }
        return array;
    }
    
    //used for get CROSSBOW movements
    private ArrayList<Vector2f> getLegalMoveCrossbow(SimpleInfo info, int index) {
        ArrayList<Vector2f> array = new ArrayList<Vector2f>();
        Vector2f vec = MathTools.toVec(index);
        Vector2f[] v = MathTools.getOrthogonalGrids(vec);
        for (Vector2f dir : v) {
            Vector2f unit = dir.subtract(vec);
            for (int i = 0; i < 3; i++) {
                Vector2f loc = vec.add(unit.mult(i + 1));
                if (MathTools.isInBoard(loc)) {
                    int indexTarget = MathTools.toInt(loc);
                    PieceType type = info.pieceType[indexTarget];
                    if (type.isEnemy() || type.isMountain()) break;
                    else if (type == PieceType.EMPTY)
                        array.add(new Vector2f(index, indexTarget));
                    else if (type.isFriend()) {
                        if (checkFlanking(info, index, indexTarget))
                            array.add(new Vector2f(index, indexTarget));
                        break;
                    }
                }
                else break;
            }
        }
        return array;
    }
    
    //used for get TREBUCHET movements
    private ArrayList<Vector2f> getLegalMoveTrebuchet(SimpleInfo info, int index) {
        ArrayList<Vector2f> array = new ArrayList<Vector2f>();
        Vector2f vec = MathTools.toVec(index);
        Vector2f[] v = MathTools.getOrthogonalGrids(vec);
        for (Vector2f dir : v) {
            Vector2f unit = dir.subtract(vec);
            for (int i = 0; i < 15; i++) {
                Vector2f loc = vec.add(unit.mult(i + 1));
                if (MathTools.isInBoard(loc)) {
                    int indexTarget = MathTools.toInt(loc);
                    PieceType type = info.pieceType[indexTarget];
                    if (type.isEnemy() || type.isMountain()) break;
                    else if (type == PieceType.EMPTY)
                        array.add(new Vector2f(index, indexTarget));
                    else if (type.isFriend()) {
                        if (checkFlanking(info, index, indexTarget))
                            array.add(new Vector2f(index, indexTarget));
                        break;
                    }
                }
                else break;
            }
        }
        return array;
    }

    //used for get LIGHTHORSE movements
    private ArrayList<Vector2f> getLegalMoveLighthorse(SimpleInfo info, int index) {
        ArrayList<Vector2f> array = new ArrayList<Vector2f>();
        Vector2f vec = MathTools.toVec(index);
        
        int homeFortress = Info.getHomeFortress();
        if (homeFortress != -1) {
            int step = MathTools.getStep(homeFortress, index);
            ArrayDeque<Vector2f> queue = new ArrayDeque<Vector2f>();
            int flag[] = new int[GRID_NUMBER];
            for (int i = 0; i < GRID_NUMBER; i++)
                flag[i] = 100;
            queue.add(vec.clone());
            flag[index] = 0;
            
            while (queue.size() > 0) {
                Vector2f head = queue.remove();
                Vector2f[] v = MathTools.getOrthogonalGrids(head);
                for (Vector2f loc : v) {
                    if (MathTools.isInBoard(loc)) {
                        int newIndex = MathTools.toInt(loc), headIndex = MathTools.toInt(head);
                        if (flag[newIndex] == 100 && flag[headIndex] + 1 <= 3 &&
                                MathTools.getStep(homeFortress, newIndex) == step) {
                            if (info.pieceType[newIndex] == PieceType.EMPTY) {
                                array.add(new Vector2f(index, newIndex));
                                queue.add(loc.clone());
                                flag[newIndex] = flag[headIndex] + 1;
                            }
                            else if (info.pieceType[newIndex].isFriend())
                                if (checkFlanking(info, index, newIndex))
                                    array.add(new Vector2f(index, newIndex));
                        }
                    }
                }
            }
        }
        int enemyFortress = Info.getEnemyFortress();
        if (enemyFortress != -1) {
            int step = MathTools.getStep(enemyFortress, index);
            ArrayDeque<Vector2f> queue = new ArrayDeque<Vector2f>();
            int flag[] = new int[GRID_NUMBER];
            for (int i = 0; i < GRID_NUMBER; i++)
                flag[i] = 100;
            queue.add(vec.clone());
            flag[index] = 0;
            
            while (queue.size() > 0) {
                Vector2f head = queue.remove();
                Vector2f[] v = MathTools.getOrthogonalGrids(head);
                for (Vector2f loc : v) {
                    if (MathTools.isInBoard(loc)) {
                        int newIndex = MathTools.toInt(loc), headIndex = MathTools.toInt(head);
                        if (flag[newIndex] == 100 && flag[headIndex] + 1 <= 3 &&
                                MathTools.getStep(enemyFortress, newIndex) == step) {
                            if (info.pieceType[newIndex] == PieceType.EMPTY) {
                                array.add(new Vector2f(index, newIndex));
                                queue.add(loc.clone());
                                flag[newIndex] = flag[headIndex] + 1;
                            }
                            else if (info.pieceType[newIndex].isFriend())
                                if (checkFlanking(info, index, newIndex))
                                    array.add(new Vector2f(index, newIndex));
                        }
                    }
                }
            }
        }
        return array;
    }    
    
    //used for get HEAVYHORSE movements
    private ArrayList<Vector2f> getLegalMoveHeavyhorse(SimpleInfo info, int index) {
        ArrayList<Vector2f> array = new ArrayList<Vector2f>();
        Vector2f vec = MathTools.toVec(index);
        
        int homeFortress = Info.getHomeFortress();
        if (homeFortress != -1) {
            int step = MathTools.getStep(homeFortress, index);
            ArrayDeque<Vector2f> queue = new ArrayDeque<Vector2f>();
            int flag[] = new int[GRID_NUMBER];
            for (int i = 0; i < GRID_NUMBER; i++)
                flag[i] = 100;
            queue.add(vec.clone());
            flag[index] = 0;
            
            while (queue.size() > 0) {
                Vector2f head = queue.remove();
                Vector2f[] v = MathTools.getOrthogonalGrids(head);
                for (Vector2f loc : v) {
                    if (MathTools.isInBoard(loc)) {
                        int newIndex = MathTools.toInt(loc), headIndex = MathTools.toInt(head);
                        if (flag[newIndex] == 100 && MathTools.getStep(homeFortress, newIndex) == step) {
                            if (info.pieceType[newIndex] == PieceType.EMPTY) {
                                array.add(new Vector2f(index, newIndex));
                                queue.add(loc.clone());
                                flag[newIndex] = flag[headIndex] + 1;
                            }
                            else if (info.pieceType[newIndex].isFriend())
                                if (checkFlanking(info, index, newIndex))
                                    array.add(new Vector2f(index, newIndex));
                        }
                    }
                }
            }
        }
        int enemyFortress = Info.getEnemyFortress();
        if (enemyFortress != -1) {
            int step = MathTools.getStep(enemyFortress, index);
            ArrayDeque<Vector2f> queue = new ArrayDeque<Vector2f>();
            int flag[] = new int[GRID_NUMBER];
            for (int i = 0; i < GRID_NUMBER; i++)
                flag[i] = 100;
            queue.add(vec.clone());
            flag[index] = 0;
            
            while (queue.size() > 0) {
                Vector2f head = queue.remove();
                Vector2f[] v = MathTools.getOrthogonalGrids(head);
                for (Vector2f loc : v) {
                    if (MathTools.isInBoard(loc)) {
                        int newIndex = MathTools.toInt(loc), headIndex = MathTools.toInt(head);
                        if (flag[newIndex] == 100 && MathTools.getStep(enemyFortress, newIndex) == step) {
                            if (info.pieceType[newIndex] == PieceType.EMPTY) {
                                array.add(new Vector2f(index, newIndex));
                                queue.add(loc.clone());
                                flag[newIndex] = flag[headIndex] + 1;
                            }
                            else if (info.pieceType[newIndex].isFriend())
                                if (checkFlanking(info, index, newIndex))
                                    array.add(new Vector2f(index, newIndex));
                        }
                    }
                }
            }
        }
        return array;
    } 
    
    //used for get DRAGON movements
    private ArrayList<Vector2f> getLegalMoveDragon(SimpleInfo info, int index) {
        ArrayList<Vector2f> array = new ArrayList<Vector2f>();
        Vector2f vec = MathTools.toVec(index);
        
        Vector2f[] v = MathTools.getDiagonalGrids(vec);
        for (Vector2f dir : v) {
            Vector2f unit = dir.subtract(vec);
            for (int i = 0; i < 15; i++) {
                Vector2f loc = vec.add(unit.mult(i + 1));
                if (MathTools.isInBoard(loc)) {
                    int indexTarget = MathTools.toInt(loc);
                    PieceType type = info.pieceType[indexTarget];
                    if (type.isEnemy()) break;
                    else if (type == PieceType.EMPTY)
                        array.add(new Vector2f(index, indexTarget));
                    else if (type.isFriend()) {
                        if (checkFlanking(info, index, indexTarget))
                            array.add(new Vector2f(index, indexTarget));
                        break;
                    }
                }
                else break;
            }
        }
        
        v = MathTools.getOrthogonalGrids(vec);
        for (Vector2f dir : v) {
            Vector2f unit = dir.subtract(vec);
            for (int i = 0; i < 15; i++) {
                Vector2f loc = vec.add(unit.mult(i + 1));
                if (MathTools.isInBoard(loc)) {
                    int indexTarget = MathTools.toInt(loc);
                    PieceType type = info.pieceType[indexTarget];
                    if (type.isEnemy()) break;
                    else if (type == PieceType.EMPTY)
                        array.add(new Vector2f(index, indexTarget));
                    else if (type.isFriend()) {
                        if (checkFlanking(info, index, indexTarget))
                            array.add(new Vector2f(index, indexTarget));
                        break;
                    }
                }
                else break;
            }
        }
        return array;
    }
    
    //used in check if can flanking
    private boolean checkFlanking(SimpleInfo info, int index, int indexTarget) {
        PieceType attackType = info.pieceType[index];
        PieceType defendType = info.pieceType[indexTarget];
        int attackTier = attackType.getTier();
        int defendTier = defendType.getTier() + info.gridType[indexTarget].getImprovement(defendType);
        if (attackTier >= defendTier)
            return true;
        else {
            ArrayList<Integer> flanking = getFlanking(info, indexTarget, index);
            if (!flanking.isEmpty()) {
                int maxTier = attackTier;
                for (int indexFrom : flanking)
                    if (info.pieceType[indexFrom].getTier() > maxTier)
                        maxTier = info.pieceType[indexFrom].getTier();
                attackTier = attackType == PieceType.KING_E ? maxTier : attackTier;
                if (attackTier == maxTier) {
                    float sumTier = attackTier;
                    for (int indexFrom : flanking) {
                        PieceType type = info.pieceType[indexFrom];
                        if (type == PieceType.KING_E) sumTier += 1f;
                        else {
                            int tier = type.getTier();
                            sumTier += FastMath.pow(0.5f, maxTier - tier);
                        }
                    }
                    if (sumTier >= defendTier) return true;
                }
            }
        }
        return false;
    }
    
    //used in find flanking pieces
    //return an array without attacking piece
    private ArrayList<Integer> getFlanking(SimpleInfo info, int indexTarget, int filter) {
        ArrayList<Integer> array = new ArrayList<Integer>();
        for (int index : checkFlankingKing(info, indexTarget))
            array.add(index);
        for (int index : checkFlankingSpear(info, indexTarget))
            array.add(index);
        for (int index : checkFlankingElephant(info, indexTarget))
            array.add(index);
        for (int index : checkFlankingCrossbow(info, indexTarget))
            array.add(index);
        for (int index : checkFlankingTrebuchet(info, indexTarget))
            array.add(index);
        for (int index : checkFlankingLighthorse(info, indexTarget))
            array.add(index);
        for (int index : checkFlankingHeavyhorse(info, indexTarget))
            array.add(index);
        for (int index : checkFlankingDragon(info, indexTarget))
            array.add(index);
        array.remove((Integer)filter);
        return array;
    }
    
    //used in playing state
    //for animation ONLY
    public ArrayList<Integer> getFlanking(int indexTarget, int filter) {
        ArrayList<Integer> array = new ArrayList<Integer>();
        SimpleInfo info = new SimpleInfo();
        for (int index : checkFlankingKing(info, indexTarget))
            array.add(index);
        for (int index : checkFlankingSpear(info, indexTarget))
            array.add(index);
        for (int index : checkFlankingElephant(info, indexTarget))
            array.add(index);
        for (int index : checkFlankingCrossbow(info, indexTarget))
            array.add(index);
        for (int index : checkFlankingTrebuchet(info, indexTarget))
            array.add(index);
        for (int index : checkFlankingLighthorse(info, indexTarget))
            array.add(index);
        for (int index : checkFlankingHeavyhorse(info, indexTarget))
            array.add(index);
        for (int index : checkFlankingDragon(info, indexTarget))
            array.add(index);
        array.remove((Integer)filter);
        return array;
    }
    
    //used in find flanking pieces (king)
    private ArrayList<Integer> checkFlankingKing(SimpleInfo info, int index) {
        ArrayList<Integer> array = new ArrayList<Integer>();
        Vector2f vec = MathTools.toVec(index);
        Vector2f[] v = MathTools.getOrthogonalGrids(vec);
        for (Vector2f loc : v) {
            if (MathTools.isInBoard(loc)) {
                int indexTarget = MathTools.toInt(loc);
                if (info.pieceType[indexTarget] == PieceType.KING_E || 
                        info.pieceType[indexTarget] == PieceType.RABBLE_E)
                    array.add(indexTarget);
            }
        }
        return array;
    }
    
    //used in find flanking pieces (spear)
    private ArrayList<Integer> checkFlankingSpear(SimpleInfo info, int index) {
        ArrayList<Integer> array = new ArrayList<Integer>();
        Vector2f vec = MathTools.toVec(index);
        Vector2f[] v = MathTools.getDiagonalGrids(vec);
        for (Vector2f dir : v) {
            Vector2f unit = dir.subtract(vec);
            for (int i = 0; i < 2; i++) {
                Vector2f loc = vec.add(unit.mult(i + 1));
                if (MathTools.isInBoard(loc)) {
                    int indexTarget = MathTools.toInt(loc);
                    PieceType type = info.pieceType[indexTarget];
                    if (type != PieceType.EMPTY && type != PieceType.SPEAR_E) break;
                    else if (type == PieceType.SPEAR_E) {
                        array.add(indexTarget);
                        break;
                    }
                }
                else break;
            }
        }
        return array;
    }
    
    //used in find flanking pieces (elephant)
    private ArrayList<Integer> checkFlankingElephant(SimpleInfo info, int index) {
        ArrayList<Integer> array = new ArrayList<Integer>();
        Vector2f vec = MathTools.toVec(index);
        Vector2f[] v = MathTools.getDiagonalGrids(vec);
        for (Vector2f dir : v) {
            Vector2f unit = dir.subtract(vec);
            for (int i = 0; i < 15; i++) {
                Vector2f loc = vec.add(unit.mult(i + 1));
                if (MathTools.isInBoard(loc)) {
                    int indexTarget = MathTools.toInt(loc);
                    PieceType type = info.pieceType[indexTarget];
                    if (type != PieceType.EMPTY && type != PieceType.ELEPHANT_E) break;
                    else if (type == PieceType.ELEPHANT_E) {
                        array.add(indexTarget);
                        break;
                    }
                }
                else break;
            }
        }
        return array;
    }
    
    //used in find flanking pieces (crossbow)
    private ArrayList<Integer> checkFlankingCrossbow(SimpleInfo info, int index) {
        ArrayList<Integer> array = new ArrayList<Integer>();
        Vector2f vec = MathTools.toVec(index);
        Vector2f[] v = MathTools.getOrthogonalGrids(vec);
        for (Vector2f dir : v) {
            Vector2f unit = dir.subtract(vec);
            for (int i = 0; i < 3; i++) {
                Vector2f loc = vec.add(unit.mult(i + 1));
                if (MathTools.isInBoard(loc)) {
                    int indexTarget = MathTools.toInt(loc);
                    PieceType type = info.pieceType[indexTarget];
                    if (type != PieceType.EMPTY && type != PieceType.CROSSBOW_E) break;
                    else if (type == PieceType.CROSSBOW_E) {
                        array.add(indexTarget);
                        break;
                    }
                }
                else break;
            }
        }
        return array;
    }
    
    //used in find flanking pieces (trebuchet)
    private ArrayList<Integer> checkFlankingTrebuchet(SimpleInfo info, int index) {
        ArrayList<Integer> array = new ArrayList<Integer>();
        Vector2f vec = MathTools.toVec(index);
        Vector2f[] v = MathTools.getOrthogonalGrids(vec);
        for (Vector2f dir : v) {
            Vector2f unit = dir.subtract(vec);
            for (int i = 0; i < 15; i++) {
                Vector2f loc = vec.add(unit.mult(i + 1));
                if (MathTools.isInBoard(loc)) {
                    int indexTarget = MathTools.toInt(loc);
                    PieceType type = info.pieceType[indexTarget];
                    if (type != PieceType.EMPTY && type != PieceType.TREBUCHET_E) break;
                    else if (type == PieceType.TREBUCHET_E) {
                        array.add(indexTarget);
                        break;
                    }
                }
                else break;
            }
        }
        return array;
    }  
    
    //used in find flanking pieces (lighthorse)
    private ArrayList<Integer> checkFlankingLighthorse(SimpleInfo info, int index) {
        ArrayList<Integer> array = new ArrayList<Integer>();
        Vector2f vec = MathTools.toVec(index);
        
        int homeFortress = Info.getHomeFortress();
        if (homeFortress != -1) {
            int step = MathTools.getStep(homeFortress, index);
            ArrayDeque<Vector2f> queue = new ArrayDeque<Vector2f>();
            int flag[] = new int[GRID_NUMBER];
            for (int i = 0; i < GRID_NUMBER; i++)
                flag[i] = 100;
            queue.add(vec.clone());
            flag[index] = 0;
            
            while (queue.size() > 0) {
                Vector2f head = queue.remove();
                Vector2f[] v = MathTools.getOrthogonalGrids(head);
                for (Vector2f loc : v) {
                    if (MathTools.isInBoard(loc)) {
                        int newIndex = MathTools.toInt(loc), headIndex = MathTools.toInt(head);
                        if (flag[newIndex] == 100 && flag[headIndex] + 1 <= 3 &&
                                MathTools.getStep(homeFortress, newIndex) == step) {
                            if (info.pieceType[newIndex] == PieceType.EMPTY) {
                                queue.add(loc.clone());
                                flag[newIndex] = flag[headIndex] + 1;
                            }
                            else if (info.pieceType[newIndex] == PieceType.LIGHTHORSE_E)
                                array.add(newIndex);
                        }
                    }
                }
            }
        }
        int enemyFortress = Info.getEnemyFortress();
        if (enemyFortress != -1) {
            int step = MathTools.getStep(enemyFortress, index);
            ArrayDeque<Vector2f> queue = new ArrayDeque<Vector2f>();
            int flag[] = new int[GRID_NUMBER];
            for (int i = 0; i < GRID_NUMBER; i++)
                flag[i] = 100;
            queue.add(vec.clone());
            flag[index] = 0;
            
            while (queue.size() > 0) {
                Vector2f head = queue.remove();
                Vector2f[] v = MathTools.getOrthogonalGrids(head);
                for (Vector2f loc : v) {
                    if (MathTools.isInBoard(loc)) {
                        int newIndex = MathTools.toInt(loc), headIndex = MathTools.toInt(head);
                        if (flag[newIndex] == 100 && flag[headIndex] + 1 <= 3 &&
                                MathTools.getStep(enemyFortress, newIndex) == step) {
                            if (info.pieceType[newIndex] == PieceType.EMPTY) {
                                queue.add(loc.clone());
                                flag[newIndex] = flag[headIndex] + 1;
                            }
                            else if (info.pieceType[newIndex] == PieceType.LIGHTHORSE_E)
                                array.add(newIndex);
                        }
                    }
                }
            }
        }
        return array;
    }    
    
    //used in find flanking pieces (heavyhorse)
    private ArrayList<Integer> checkFlankingHeavyhorse(SimpleInfo info, int index) {
        ArrayList<Integer> array = new ArrayList<Integer>();
        Vector2f vec = MathTools.toVec(index);
        
        int homeFortress = Info.getHomeFortress();
        if (homeFortress != -1) {
            int step = MathTools.getStep(homeFortress, index);
            ArrayDeque<Vector2f> queue = new ArrayDeque<Vector2f>();
            int flag[] = new int[GRID_NUMBER];
            for (int i = 0; i < GRID_NUMBER; i++)
                flag[i] = 100;
            queue.add(vec.clone());
            flag[index] = 0;
            
            while (queue.size() > 0) {
                Vector2f head = queue.remove();
                Vector2f[] v = MathTools.getOrthogonalGrids(head);
                for (Vector2f loc : v) {
                    if (MathTools.isInBoard(loc)) {
                        int newIndex = MathTools.toInt(loc), headIndex = MathTools.toInt(head);
                        if (flag[newIndex] == 100 && MathTools.getStep(homeFortress, newIndex) == step) {
                            if (info.pieceType[newIndex] == PieceType.EMPTY) {
                                queue.add(loc.clone());
                                flag[newIndex] = flag[headIndex] + 1;
                            }
                            else if (info.pieceType[newIndex] == PieceType.HEAVYHORSE_E)
                                array.add(newIndex);
                        }
                    }
                }
            }
        }
        int enemyFortress = Info.getEnemyFortress();
        if (enemyFortress != -1) {
            int step = MathTools.getStep(enemyFortress, index);
            ArrayDeque<Vector2f> queue = new ArrayDeque<Vector2f>();
            int flag[] = new int[GRID_NUMBER];
            for (int i = 0; i < GRID_NUMBER; i++)
                flag[i] = 100;
            queue.add(vec.clone());
            flag[index] = 0;
            
            while (queue.size() > 0) {
                Vector2f head = queue.remove();
                Vector2f[] v = MathTools.getOrthogonalGrids(head);
                for (Vector2f loc : v) {
                    if (MathTools.isInBoard(loc)) {
                        int newIndex = MathTools.toInt(loc), headIndex = MathTools.toInt(head);
                        if (flag[newIndex] == 100 && MathTools.getStep(enemyFortress, newIndex) == step) {
                            if (info.pieceType[newIndex] == PieceType.EMPTY) {
                                queue.add(loc.clone());
                                flag[newIndex] = flag[headIndex] + 1;
                            }
                            else if (info.pieceType[newIndex] == PieceType.HEAVYHORSE_E)
                                array.add(newIndex);
                        }
                    }
                }
            }
        }
        return array;
    }    
    
    //used in find flanking pieces (dragon)
    private ArrayList<Integer> checkFlankingDragon(SimpleInfo info, int index) {
        ArrayList<Integer> array = new ArrayList<Integer>();
        Vector2f vec = MathTools.toVec(index);
        Vector2f[] v = MathTools.getDiagonalGrids(vec);
        for (Vector2f dir : v) {
            Vector2f unit = dir.subtract(vec);
            for (int i = 0; i < 15; i++) {
                Vector2f loc = vec.add(unit.mult(i + 1));
                if (MathTools.isInBoard(loc)) {
                    int indexTarget = MathTools.toInt(loc);
                    PieceType type = info.pieceType[indexTarget];
                    if (type != PieceType.EMPTY && type != PieceType.DRAGON_E) break;
                    else if (type == PieceType.DRAGON_E) {
                        array.add(indexTarget);
                        break;
                    }
                }
                else break;
            }
        }
        
        v = MathTools.getOrthogonalGrids(vec);
        for (Vector2f dir : v) {
            Vector2f unit = dir.subtract(vec);
            for (int i = 0; i < 15; i++) {
                Vector2f loc = vec.add(unit.mult(i + 1));
                if (MathTools.isInBoard(loc)) {
                    int indexTarget = MathTools.toInt(loc);
                    PieceType type = info.pieceType[indexTarget];
                    if (type != PieceType.EMPTY && type != PieceType.DRAGON_E) break;
                    else if (type == PieceType.DRAGON_E) {
                        array.add(indexTarget);
                        break;
                    }
                }
                else break;
            }
        }
        return array;
    }
    
    //find all legal movements of all pieces on the board
    private ArrayList<Vector2f> getAllLegalMoveM(SimpleInfo info) {
        ArrayList<Vector2f> array = new ArrayList<Vector2f>();
        for (int i = 0; i < GRID_NUMBER; i++) {
            ArrayList<Vector2f> arr = getLegalMoveM(info, i);
            for (int j = 0; j < arr.size(); j++)
                array.add(arr.get(j));
        }
        return array;
    }
    
    //find legal movement of a piece
    private ArrayList<Vector2f> getLegalMoveM(SimpleInfo info, int index) {
        ArrayList<Vector2f> array;       
        PieceType type = info.pieceType[index];
        if (type == PieceType.KING || type == PieceType.RABBLE)
            array = getLegalMoveKingM(info, index);
        else if (type == PieceType.SPEAR)
            array = getLegalMoveSpearM(info, index);
        else if (type == PieceType.ELEPHANT)
            array = getLegalMoveElephantM(info, index);
        else if (type == PieceType.CROSSBOW)
            array = getLegalMoveCrossbowM(info, index);
        else if (type == PieceType.TREBUCHET)
            array = getLegalMoveTrebuchetM(info, index);
        else if (type == PieceType.LIGHTHORSE)
            array = getLegalMoveLighthorseM(info, index);
        else if (type == PieceType.HEAVYHORSE)
            array = getLegalMoveHeavyhorseM(info, index);
        else if (type == PieceType.DRAGON)
            array = getLegalMoveDragonM(info, index);
        else array = new ArrayList<Vector2f>();
        return array;
    }
    
    //used for get KING and RABBLE movements
    private ArrayList<Vector2f> getLegalMoveKingM(SimpleInfo info, int index) {
        ArrayList<Vector2f> array = new ArrayList<Vector2f>();
        Vector2f vec = MathTools.toVec(index);
        Vector2f[] v = MathTools.getOrthogonalGrids(vec);
        for (Vector2f loc : v) {
            if (MathTools.isInBoard(loc)) {
                int indexTarget = MathTools.toInt(loc);
                if (info.pieceType[indexTarget] == PieceType.EMPTY)
                    array.add(new Vector2f(index, indexTarget));
                else if (info.pieceType[indexTarget].isEnemy())
                    if (checkFlankingM(info, index, indexTarget))
                        array.add(new Vector2f(index, indexTarget));
            }
        }
        return array;
    }
    
    //used for get SPEAR movements
    private ArrayList<Vector2f> getLegalMoveSpearM(SimpleInfo info, int index) {
        ArrayList<Vector2f> array = new ArrayList<Vector2f>();
        Vector2f vec = MathTools.toVec(index);
        Vector2f[] v = MathTools.getDiagonalGrids(vec);
        for (Vector2f dir : v) {
            Vector2f unit = dir.subtract(vec);
            for (int i = 0; i < 2; i++) {
                Vector2f loc = vec.add(unit.mult(i + 1));
                if (MathTools.isInBoard(loc)) {
                    int indexTarget = MathTools.toInt(loc);
                    PieceType type = info.pieceType[indexTarget];
                    if (type.isFriend() || type.isMountain()) break;
                    else if (type == PieceType.EMPTY)
                        array.add(new Vector2f(index, indexTarget));
                    else if (type.isEnemy()) {
                        if (checkFlankingM(info, index, indexTarget))
                            array.add(new Vector2f(index, indexTarget));
                        break;
                    }
                }
                else break;
            }
        }
        return array;
    }
    
    //used for get ELEPHANT movements
    private ArrayList<Vector2f> getLegalMoveElephantM(SimpleInfo info, int index) {
        ArrayList<Vector2f> array = new ArrayList<Vector2f>();
        Vector2f vec = MathTools.toVec(index);
        Vector2f[] v = MathTools.getDiagonalGrids(vec);
        for (Vector2f dir : v) {
            Vector2f unit = dir.subtract(vec);
            for (int i = 0; i < 2; i++) {
                Vector2f loc = vec.add(unit.mult(i + 1));
                if (MathTools.isInBoard(loc)) {
                    int indexTarget = MathTools.toInt(loc);
                    PieceType type = info.pieceType[indexTarget];
                    if (type.isFriend() || type.isMountain()) break;
                    else if (type == PieceType.EMPTY)
                        array.add(new Vector2f(index, indexTarget));
                    else if (type.isEnemy()) {
                        if (checkFlankingM(info, index, indexTarget))
                            array.add(new Vector2f(index, indexTarget));
                        break;
                    }
                }
                else break;
            }
        }
        return array;
    }
    
    //used for get CROSSBOW movements
    private ArrayList<Vector2f> getLegalMoveCrossbowM(SimpleInfo info, int index) {
        ArrayList<Vector2f> array = new ArrayList<Vector2f>();
        Vector2f vec = MathTools.toVec(index);
        Vector2f[] v = MathTools.getOrthogonalGrids(vec);
        for (Vector2f dir : v) {
            Vector2f unit = dir.subtract(vec);
            for (int i = 0; i < 3; i++) {
                Vector2f loc = vec.add(unit.mult(i + 1));
                if (MathTools.isInBoard(loc)) {
                    int indexTarget = MathTools.toInt(loc);
                    PieceType type = info.pieceType[indexTarget];
                    if (type.isFriend() || type.isMountain()) break;
                    else if (type == PieceType.EMPTY)
                        array.add(new Vector2f(index, indexTarget));
                    else if (type.isEnemy()) {
                        if (checkFlankingM(info, index, indexTarget))
                            array.add(new Vector2f(index, indexTarget));
                        break;
                    }
                }
                else break;
            }
        }
        return array;
    }
    
    //used for get TREBUCHET movements
    private ArrayList<Vector2f> getLegalMoveTrebuchetM(SimpleInfo info, int index) {
        ArrayList<Vector2f> array = new ArrayList<Vector2f>();
        Vector2f vec = MathTools.toVec(index);
        Vector2f[] v = MathTools.getOrthogonalGrids(vec);
        for (Vector2f dir : v) {
            Vector2f unit = dir.subtract(vec);
            for (int i = 0; i < 15; i++) {
                Vector2f loc = vec.add(unit.mult(i + 1));
                if (MathTools.isInBoard(loc)) {
                    int indexTarget = MathTools.toInt(loc);
                    PieceType type = info.pieceType[indexTarget];
                    if (type.isFriend() || type.isMountain()) break;
                    else if (type == PieceType.EMPTY)
                        array.add(new Vector2f(index, indexTarget));
                    else if (type.isEnemy()) {
                        if (checkFlankingM(info, index, indexTarget))
                            array.add(new Vector2f(index, indexTarget));
                        break;
                    }
                }
                else break;
            }
        }
        return array;
    }

    //used for get LIGHTHORSE movements
    private ArrayList<Vector2f> getLegalMoveLighthorseM(SimpleInfo info, int index) {
        ArrayList<Vector2f> array = new ArrayList<Vector2f>();
        Vector2f vec = MathTools.toVec(index);
        
        int homeFortress = Info.getHomeFortress();
        if (homeFortress != -1) {
            int step = MathTools.getStep(homeFortress, index);
            ArrayDeque<Vector2f> queue = new ArrayDeque<Vector2f>();
            int flag[] = new int[GRID_NUMBER];
            for (int i = 0; i < GRID_NUMBER; i++)
                flag[i] = 100;
            queue.add(vec.clone());
            flag[index] = 0;
            
            while (queue.size() > 0) {
                Vector2f head = queue.remove();
                Vector2f[] v = MathTools.getOrthogonalGrids(head);
                for (Vector2f loc : v) {
                    if (MathTools.isInBoard(loc)) {
                        int newIndex = MathTools.toInt(loc), headIndex = MathTools.toInt(head);
                        if (flag[newIndex] == 100 && flag[headIndex] + 1 <= 3 &&
                                MathTools.getStep(homeFortress, newIndex) == step) {
                            if (info.pieceType[newIndex] == PieceType.EMPTY) {
                                array.add(new Vector2f(index, newIndex));
                                queue.add(loc.clone());
                                flag[newIndex] = flag[headIndex] + 1;
                            }
                            else if (info.pieceType[newIndex].isEnemy())
                                if (checkFlankingM(info, index, newIndex))
                                    array.add(new Vector2f(index, newIndex));
                        }
                    }
                }
            }
        }
        int enemyFortress = Info.getEnemyFortress();
        if (enemyFortress != -1) {
            int step = MathTools.getStep(enemyFortress, index);
            ArrayDeque<Vector2f> queue = new ArrayDeque<Vector2f>();
            int flag[] = new int[GRID_NUMBER];
            for (int i = 0; i < GRID_NUMBER; i++)
                flag[i] = 100;
            queue.add(vec.clone());
            flag[index] = 0;
            
            while (queue.size() > 0) {
                Vector2f head = queue.remove();
                Vector2f[] v = MathTools.getOrthogonalGrids(head);
                for (Vector2f loc : v) {
                    if (MathTools.isInBoard(loc)) {
                        int newIndex = MathTools.toInt(loc), headIndex = MathTools.toInt(head);
                        if (flag[newIndex] == 100 && flag[headIndex] + 1 <= 3 &&
                                MathTools.getStep(enemyFortress, newIndex) == step) {
                            if (info.pieceType[newIndex] == PieceType.EMPTY) {
                                array.add(new Vector2f(index, newIndex));
                                queue.add(loc.clone());
                                flag[newIndex] = flag[headIndex] + 1;
                            }
                            else if (info.pieceType[newIndex].isEnemy())
                                if (checkFlankingM(info, index, newIndex))
                                    array.add(new Vector2f(index, newIndex));
                        }
                    }
                }
            }
        }
        return array;
    }    
    
    //used for get HEAVYHORSE movements
    private ArrayList<Vector2f> getLegalMoveHeavyhorseM(SimpleInfo info, int index) {
        ArrayList<Vector2f> array = new ArrayList<Vector2f>();
        Vector2f vec = MathTools.toVec(index);
        
        int homeFortress = Info.getHomeFortress();
        if (homeFortress != -1) {
            int step = MathTools.getStep(homeFortress, index);
            ArrayDeque<Vector2f> queue = new ArrayDeque<Vector2f>();
            int flag[] = new int[GRID_NUMBER];
            for (int i = 0; i < GRID_NUMBER; i++)
                flag[i] = 100;
            queue.add(vec.clone());
            flag[index] = 0;
            
            while (queue.size() > 0) {
                Vector2f head = queue.remove();
                Vector2f[] v = MathTools.getOrthogonalGrids(head);
                for (Vector2f loc : v) {
                    if (MathTools.isInBoard(loc)) {
                        int newIndex = MathTools.toInt(loc), headIndex = MathTools.toInt(head);
                        if (flag[newIndex] == 100 && MathTools.getStep(homeFortress, newIndex) == step) {
                            if (info.pieceType[newIndex] == PieceType.EMPTY) {
                                array.add(new Vector2f(index, newIndex));
                                queue.add(loc.clone());
                                flag[newIndex] = flag[headIndex] + 1;
                            }
                            else if (info.pieceType[newIndex].isEnemy())
                                if (checkFlankingM(info, index, newIndex))
                                    array.add(new Vector2f(index, newIndex));
                        }
                    }
                }
            }
        }
        int enemyFortress = Info.getEnemyFortress();
        if (enemyFortress != -1) {
            int step = MathTools.getStep(enemyFortress, index);
            ArrayDeque<Vector2f> queue = new ArrayDeque<Vector2f>();
            int flag[] = new int[GRID_NUMBER];
            for (int i = 0; i < GRID_NUMBER; i++)
                flag[i] = 100;
            queue.add(vec.clone());
            flag[index] = 0;
            
            while (queue.size() > 0) {
                Vector2f head = queue.remove();
                Vector2f[] v = MathTools.getOrthogonalGrids(head);
                for (Vector2f loc : v) {
                    if (MathTools.isInBoard(loc)) {
                        int newIndex = MathTools.toInt(loc), headIndex = MathTools.toInt(head);
                        if (flag[newIndex] == 100 && MathTools.getStep(enemyFortress, newIndex) == step) {
                            if (info.pieceType[newIndex] == PieceType.EMPTY) {
                                array.add(new Vector2f(index, newIndex));
                                queue.add(loc.clone());
                                flag[newIndex] = flag[headIndex] + 1;
                            }
                            else if (info.pieceType[newIndex].isEnemy())
                                if (checkFlankingM(info, index, newIndex))
                                    array.add(new Vector2f(index, newIndex));
                        }
                    }
                }
            }
        }
        return array;
    } 
    
    //used for get DRAGON movements
    private ArrayList<Vector2f> getLegalMoveDragonM(SimpleInfo info, int index) {
        ArrayList<Vector2f> array = new ArrayList<Vector2f>();
        Vector2f vec = MathTools.toVec(index);
        
        Vector2f[] v = MathTools.getDiagonalGrids(vec);
        for (Vector2f dir : v) {
            Vector2f unit = dir.subtract(vec);
            for (int i = 0; i < 15; i++) {
                Vector2f loc = vec.add(unit.mult(i + 1));
                if (MathTools.isInBoard(loc)) {
                    int indexTarget = MathTools.toInt(loc);
                    PieceType type = info.pieceType[indexTarget];
                    if (type.isFriend()) break;
                    else if (type == PieceType.EMPTY)
                        array.add(new Vector2f(index, indexTarget));
                    else if (type.isEnemy()) {
                        if (checkFlankingM(info, index, indexTarget))
                            array.add(new Vector2f(index, indexTarget));
                        break;
                    }
                }
                else break;
            }
        }
        
        v = MathTools.getOrthogonalGrids(vec);
        for (Vector2f dir : v) {
            Vector2f unit = dir.subtract(vec);
            for (int i = 0; i < 15; i++) {
                Vector2f loc = vec.add(unit.mult(i + 1));
                if (MathTools.isInBoard(loc)) {
                    int indexTarget = MathTools.toInt(loc);
                    PieceType type = info.pieceType[indexTarget];
                    if (type.isFriend()) break;
                    else if (type == PieceType.EMPTY)
                        array.add(new Vector2f(index, indexTarget));
                    else if (type.isEnemy()) {
                        if (checkFlankingM(info, index, indexTarget))
                            array.add(new Vector2f(index, indexTarget));
                        break;
                    }
                }
                else break;
            }
        }
        return array;
    }
    
    //used in check if can flanking
    private boolean checkFlankingM(SimpleInfo info, int index, int indexTarget) {
        PieceType attackType = info.pieceType[index];
        PieceType defendType = info.pieceType[indexTarget];
        int attackTier = attackType.getTier();
        int defendTier = defendType.getTier() + info.gridType[indexTarget].getImprovement(defendType);
        if (attackTier >= defendTier)
            return true;
        else {
            ArrayList<Integer> flanking = getFlankingM(info, indexTarget, index);
            if (!flanking.isEmpty()) {
                int maxTier = attackTier;
                for (int indexFrom : flanking)
                    if (info.pieceType[indexFrom].getTier() > maxTier)
                        maxTier = info.pieceType[indexFrom].getTier();
                attackTier = attackType == PieceType.KING ? maxTier : attackTier;
                if (attackTier == maxTier) {
                    float sumTier = attackTier;
                    for (int indexFrom : flanking) {
                        PieceType type = info.pieceType[indexFrom];
                        if (type == PieceType.KING) sumTier += 1f;
                        else {
                            int tier = type.getTier();
                            sumTier += FastMath.pow(0.5f, maxTier - tier);
                        }
                    }
                    if (sumTier >= defendTier) return true;
                }
            }
        }
        return false;
    }
    
    //used in find flanking pieces
    //return an array without attacking piece
    private ArrayList<Integer> getFlankingM(SimpleInfo info, int indexTarget, int filter) {
        ArrayList<Integer> array = new ArrayList<Integer>();
        for (int index : checkFlankingKingM(info, indexTarget))
            array.add(index);
        for (int index : checkFlankingSpearM(info, indexTarget))
            array.add(index);
        for (int index : checkFlankingElephantM(info, indexTarget))
            array.add(index);
        for (int index : checkFlankingCrossbowM(info, indexTarget))
            array.add(index);
        for (int index : checkFlankingTrebuchetM(info, indexTarget))
            array.add(index);
        for (int index : checkFlankingLighthorseM(info, indexTarget))
            array.add(index);
        for (int index : checkFlankingHeavyhorseM(info, indexTarget))
            array.add(index);
        for (int index : checkFlankingDragonM(info, indexTarget))
            array.add(index);
        array.remove((Integer)filter);
        return array;
    }
    
    //used in find flanking pieces (king)
    private ArrayList<Integer> checkFlankingKingM(SimpleInfo info, int index) {
        ArrayList<Integer> array = new ArrayList<Integer>();
        Vector2f vec = MathTools.toVec(index);
        Vector2f[] v = MathTools.getOrthogonalGrids(vec);
        for (Vector2f loc : v) {
            if (MathTools.isInBoard(loc)) {
                int indexTarget = MathTools.toInt(loc);
                if (info.pieceType[indexTarget] == PieceType.KING || 
                        info.pieceType[indexTarget] == PieceType.RABBLE)
                    array.add(indexTarget);
            }
        }
        return array;
    }
    
    //used in find flanking pieces (spear)
    private ArrayList<Integer> checkFlankingSpearM(SimpleInfo info, int index) {
        ArrayList<Integer> array = new ArrayList<Integer>();
        Vector2f vec = MathTools.toVec(index);
        Vector2f[] v = MathTools.getDiagonalGrids(vec);
        for (Vector2f dir : v) {
            Vector2f unit = dir.subtract(vec);
            for (int i = 0; i < 2; i++) {
                Vector2f loc = vec.add(unit.mult(i + 1));
                if (MathTools.isInBoard(loc)) {
                    int indexTarget = MathTools.toInt(loc);
                    PieceType type = info.pieceType[indexTarget];
                    if (type != PieceType.EMPTY && type != PieceType.SPEAR) break;
                    else if (type == PieceType.SPEAR) {
                        array.add(indexTarget);
                        break;
                    }
                }
                else break;
            }
        }
        return array;
    }
    
    //used in find flanking pieces (elephant)
    private ArrayList<Integer> checkFlankingElephantM(SimpleInfo info, int index) {
        ArrayList<Integer> array = new ArrayList<Integer>();
        Vector2f vec = MathTools.toVec(index);
        Vector2f[] v = MathTools.getDiagonalGrids(vec);
        for (Vector2f dir : v) {
            Vector2f unit = dir.subtract(vec);
            for (int i = 0; i < 15; i++) {
                Vector2f loc = vec.add(unit.mult(i + 1));
                if (MathTools.isInBoard(loc)) {
                    int indexTarget = MathTools.toInt(loc);
                    PieceType type = info.pieceType[indexTarget];
                    if (type != PieceType.EMPTY && type != PieceType.ELEPHANT) break;
                    else if (type == PieceType.ELEPHANT) {
                        array.add(indexTarget);
                        break;
                    }
                }
                else break;
            }
        }
        return array;
    }
    
    //used in find flanking pieces (crossbow)
    private ArrayList<Integer> checkFlankingCrossbowM(SimpleInfo info, int index) {
        ArrayList<Integer> array = new ArrayList<Integer>();
        Vector2f vec = MathTools.toVec(index);
        Vector2f[] v = MathTools.getOrthogonalGrids(vec);
        for (Vector2f dir : v) {
            Vector2f unit = dir.subtract(vec);
            for (int i = 0; i < 3; i++) {
                Vector2f loc = vec.add(unit.mult(i + 1));
                if (MathTools.isInBoard(loc)) {
                    int indexTarget = MathTools.toInt(loc);
                    PieceType type = info.pieceType[indexTarget];
                    if (type != PieceType.EMPTY && type != PieceType.CROSSBOW) break;
                    else if (type == PieceType.CROSSBOW) {
                        array.add(indexTarget);
                        break;
                    }
                }
                else break;
            }
        }
        return array;
    }
    
    //used in find flanking pieces (trebuchet)
    private ArrayList<Integer> checkFlankingTrebuchetM(SimpleInfo info, int index) {
        ArrayList<Integer> array = new ArrayList<Integer>();
        Vector2f vec = MathTools.toVec(index);
        Vector2f[] v = MathTools.getOrthogonalGrids(vec);
        for (Vector2f dir : v) {
            Vector2f unit = dir.subtract(vec);
            for (int i = 0; i < 15; i++) {
                Vector2f loc = vec.add(unit.mult(i + 1));
                if (MathTools.isInBoard(loc)) {
                    int indexTarget = MathTools.toInt(loc);
                    PieceType type = info.pieceType[indexTarget];
                    if (type != PieceType.EMPTY && type != PieceType.TREBUCHET) break;
                    else if (type == PieceType.TREBUCHET) {
                        array.add(indexTarget);
                        break;
                    }
                }
                else break;
            }
        }
        return array;
    }  
    
    //used in find flanking pieces (lighthorse)
    private ArrayList<Integer> checkFlankingLighthorseM(SimpleInfo info, int index) {
        ArrayList<Integer> array = new ArrayList<Integer>();
        Vector2f vec = MathTools.toVec(index);
        
        int homeFortress = Info.getHomeFortress();
        if (homeFortress != -1) {
            int step = MathTools.getStep(homeFortress, index);
            ArrayDeque<Vector2f> queue = new ArrayDeque<Vector2f>();
            int flag[] = new int[GRID_NUMBER];
            for (int i = 0; i < GRID_NUMBER; i++)
                flag[i] = 100;
            queue.add(vec.clone());
            flag[index] = 0;
            
            while (queue.size() > 0) {
                Vector2f head = queue.remove();
                Vector2f[] v = MathTools.getOrthogonalGrids(head);
                for (Vector2f loc : v) {
                    if (MathTools.isInBoard(loc)) {
                        int newIndex = MathTools.toInt(loc), headIndex = MathTools.toInt(head);
                        if (flag[newIndex] == 100 && flag[headIndex] + 1 <= 3 &&
                                MathTools.getStep(homeFortress, newIndex) == step) {
                            if (info.pieceType[newIndex] == PieceType.EMPTY) {
                                queue.add(loc.clone());
                                flag[newIndex] = flag[headIndex] + 1;
                            }
                            else if (info.pieceType[newIndex] == PieceType.LIGHTHORSE)
                                array.add(newIndex);
                        }
                    }
                }
            }
        }
        int enemyFortress = Info.getEnemyFortress();
        if (enemyFortress != -1) {
            int step = MathTools.getStep(enemyFortress, index);
            ArrayDeque<Vector2f> queue = new ArrayDeque<Vector2f>();
            int flag[] = new int[GRID_NUMBER];
            for (int i = 0; i < GRID_NUMBER; i++)
                flag[i] = 100;
            queue.add(vec.clone());
            flag[index] = 0;
            
            while (queue.size() > 0) {
                Vector2f head = queue.remove();
                Vector2f[] v = MathTools.getOrthogonalGrids(head);
                for (Vector2f loc : v) {
                    if (MathTools.isInBoard(loc)) {
                        int newIndex = MathTools.toInt(loc), headIndex = MathTools.toInt(head);
                        if (flag[newIndex] == 100 && flag[headIndex] + 1 <= 3 &&
                                MathTools.getStep(enemyFortress, newIndex) == step) {
                            if (info.pieceType[newIndex] == PieceType.EMPTY) {
                                queue.add(loc.clone());
                                flag[newIndex] = flag[headIndex] + 1;
                            }
                            else if (info.pieceType[newIndex] == PieceType.LIGHTHORSE)
                                array.add(newIndex);
                        }
                    }
                }
            }
        }
        return array;
    }    
    
    //used in find flanking pieces (heavyhorse)
    private ArrayList<Integer> checkFlankingHeavyhorseM(SimpleInfo info, int index) {
        ArrayList<Integer> array = new ArrayList<Integer>();
        Vector2f vec = MathTools.toVec(index);
        
        int homeFortress = Info.getHomeFortress();
        if (homeFortress != -1) {
            int step = MathTools.getStep(homeFortress, index);
            ArrayDeque<Vector2f> queue = new ArrayDeque<Vector2f>();
            int flag[] = new int[GRID_NUMBER];
            for (int i = 0; i < GRID_NUMBER; i++)
                flag[i] = 100;
            queue.add(vec.clone());
            flag[index] = 0;
            
            while (queue.size() > 0) {
                Vector2f head = queue.remove();
                Vector2f[] v = MathTools.getOrthogonalGrids(head);
                for (Vector2f loc : v) {
                    if (MathTools.isInBoard(loc)) {
                        int newIndex = MathTools.toInt(loc), headIndex = MathTools.toInt(head);
                        if (flag[newIndex] == 100 && MathTools.getStep(homeFortress, newIndex) == step) {
                            if (info.pieceType[newIndex] == PieceType.EMPTY) {
                                queue.add(loc.clone());
                                flag[newIndex] = flag[headIndex] + 1;
                            }
                            else if (info.pieceType[newIndex] == PieceType.HEAVYHORSE)
                                array.add(newIndex);
                        }
                    }
                }
            }
        }
        int enemyFortress = Info.getEnemyFortress();
        if (enemyFortress != -1) {
            int step = MathTools.getStep(enemyFortress, index);
            ArrayDeque<Vector2f> queue = new ArrayDeque<Vector2f>();
            int flag[] = new int[GRID_NUMBER];
            for (int i = 0; i < GRID_NUMBER; i++)
                flag[i] = 100;
            queue.add(vec.clone());
            flag[index] = 0;
            
            while (queue.size() > 0) {
                Vector2f head = queue.remove();
                Vector2f[] v = MathTools.getOrthogonalGrids(head);
                for (Vector2f loc : v) {
                    if (MathTools.isInBoard(loc)) {
                        int newIndex = MathTools.toInt(loc), headIndex = MathTools.toInt(head);
                        if (flag[newIndex] == 100 && MathTools.getStep(enemyFortress, newIndex) == step) {
                            if (info.pieceType[newIndex] == PieceType.EMPTY) {
                                queue.add(loc.clone());
                                flag[newIndex] = flag[headIndex] + 1;
                            }
                            else if (info.pieceType[newIndex] == PieceType.HEAVYHORSE)
                                array.add(newIndex);
                        }
                    }
                }
            }
        }
        return array;
    }    
    
    //used in find flanking pieces (dragon)
    private ArrayList<Integer> checkFlankingDragonM(SimpleInfo info, int index) {
        ArrayList<Integer> array = new ArrayList<Integer>();
        Vector2f vec = MathTools.toVec(index);
        Vector2f[] v = MathTools.getDiagonalGrids(vec);
        for (Vector2f dir : v) {
            Vector2f unit = dir.subtract(vec);
            for (int i = 0; i < 15; i++) {
                Vector2f loc = vec.add(unit.mult(i + 1));
                if (MathTools.isInBoard(loc)) {
                    int indexTarget = MathTools.toInt(loc);
                    PieceType type = info.pieceType[indexTarget];
                    if (type != PieceType.EMPTY && type != PieceType.DRAGON) break;
                    else if (type == PieceType.DRAGON) {
                        array.add(indexTarget);
                        break;
                    }
                }
                else break;
            }
        }
        
        v = MathTools.getOrthogonalGrids(vec);
        for (Vector2f dir : v) {
            Vector2f unit = dir.subtract(vec);
            for (int i = 0; i < 15; i++) {
                Vector2f loc = vec.add(unit.mult(i + 1));
                if (MathTools.isInBoard(loc)) {
                    int indexTarget = MathTools.toInt(loc);
                    PieceType type = info.pieceType[indexTarget];
                    if (type != PieceType.EMPTY && type != PieceType.DRAGON) break;
                    else if (type == PieceType.DRAGON) {
                        array.add(indexTarget);
                        break;
                    }
                }
                else break;
            }
        }
        return array;
    }
    
    //evaluation constants
    private static final float RABBLE_VALUE = 2f;
    private static final float LIGHTHORSE_VALUE = 5f;
    private static final float HEAVYHORSE_VALUE = LIGHTHORSE_VALUE * 2;
    private static final float SPEAR_VALUE = 5f;
    private static final float ELEPHANT_VALUE = SPEAR_VALUE * 2;
    private static final float CROSSBOW_VALUE = 7f;
    private static final float TREBUCHET_VALUE = CROSSBOW_VALUE * 2;
    private static final float DRAGON_VALUE = 20f;
    private static final float KING_VALUE = 500f;
    
    //some constants
    private static final float PIECE_UNIT = 48f;
    private static final float PIECE_Z = 3f;
    private static final int GRID_NUMBER = 91;
    
    //search depth
    private static final int SEARCH_DEPTH = 3;
            
}
