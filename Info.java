
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector2f;
import com.jme3.ui.Picture;
import com.jme3.math.FastMath;
import java.util.ArrayDeque;
import java.util.ArrayList;

//a class which contains only static methods and fields
//provide service on recording and changing the game information as well as game logics
public class Info {

    //define grid infomation
    private static final int GRID_NUMBER = 91;
    private static final int GRID_NUMBER_HALF = 40;
    private static final int[] GRID_COLOR = new int[] {
                1, 2, 3, 1, 2, 3,
               2, 3, 1, 2, 3, 1, 2,
              3, 1, 2, 3, 1, 2, 3, 1,
             1, 2, 3, 1, 2, 3, 1, 2, 3,
            2, 3, 1, 2, 3, 1, 2, 3, 1, 2,
           3, 1, 2, 3, 1, 2, 3, 1, 2, 3, 1,
            2, 3, 1, 2, 3, 1, 2, 3, 1, 2,
             1, 2, 3, 1, 2, 3, 1, 2, 3,
              3, 1, 2, 3, 1, 2, 3, 1,
               2, 3, 1, 2, 3, 1, 2,
                1, 2, 3, 1, 2, 3,
    };
    
    //define the alpha channel for illegal grids
    private static final float ILLEGAL_ALPHA = 0.5f;
    
    //game information recorder
    private static GridType[] gridType = new GridType[GRID_NUMBER];
    private static Picture[] gridPic = new Picture[GRID_NUMBER];
    private static PieceType[] pieceType = new PieceType[GRID_NUMBER];
    private static Picture[] piecePic = new Picture[GRID_NUMBER];
    
    //record if a grid is legal to put a piece in
    private static boolean[] legal = new boolean[GRID_NUMBER];
    
    //record several special game units
    private static int king, dragon, kingE, dragonE;
    private static int homeFortress = -1, enemyFortress = -1;
    
    //initialize the game information
    static {
        for (int i = 0; i < GRID_NUMBER; i++) {
            switch (GRID_COLOR[i]) {
                case 1:
                    gridType[i] = GridType.FOREST;
                    break;
                case 2:
                    gridType[i] = GridType.HILL;
                    break;
                case 3:
                    gridType[i] = GridType.WATER;
                    break;
            }
            pieceType[i] = PieceType.EMPTY;
            legal[i] = true;
        }
    }
    
    //get the home fortress index
    public static int getHomeFortress() {
        return homeFortress;
    }
    
    //get the enemy fortress index
    public static int getEnemyFortress() {
        return enemyFortress;
    }
    
    //set the home fortress index
    public static void setHomeFortress(int x) {
        homeFortress = x;
    }
    
    //set the enemy fortress index
    public static void setEnemyFortress(int x) {
        enemyFortress = x;
    }
    
    //get the grid type with integer index
    public static GridType getGridType(int x) {
        return gridType[x];
    }
    
    //get the grid type with vector index
    public static GridType getGridType(Vector2f vec) {
        int x = MathTools.toInt(vec);
        return gridType[x];
    }
    
    //set the grid type
    public static void setGridType(int x, GridType type) {
        gridType[x] = type;
    }
    
    //set the grid to its original type
    public static void setOriginalGridType(int x, AssetManager assetManager) {
        switch (GRID_COLOR[x]) {
            case 1: gridType[x] = GridType.FOREST;
                    gridPic[x].setImage(assetManager, "Textures/grid_forest.png", true);
                    break;
            case 2: gridType[x] = GridType.HILL;
                    gridPic[x].setImage(assetManager, "Textures/grid_hill.png", true);
                    break;
            case 3: gridType[x] = GridType.WATER;
                    gridPic[x].setImage(assetManager, "Textures/grid_water.png", true);
                    break;
        }
    }   
    
    //return the picture for a grid with integer index
    public static Picture getGridPicture(int x) {
        return gridPic[x];
    }
    
    //return the picture for a grid with vector index
    public static Picture getGridPicture(Vector2f vec) {
        int x = MathTools.toInt(vec);
        return gridPic[x];
    }    
    
    //set the picture for a grid
    public static void setGridPicture(int x, Picture pic) {
        gridPic[x] = pic;
    }    

    //get the piece type with integer index
    public static PieceType getPieceType(int x) {
        return pieceType[x];
    }
    
    //get the piece type with vector index
    public static PieceType getPieceType(Vector2f vec) {
        int x = MathTools.toInt(vec);
        return pieceType[x];
    }    
    
    //set the piece type
    public static void setPieceType(int x, PieceType type) {
        if (type == PieceType.KING)
            king = x;
        else if (type == PieceType.DRAGON)
            dragon = x;
        else if (type == PieceType.KING_E)
            kingE = x;
        else if (type == PieceType.DRAGON_E)
            dragonE = x;
        
        pieceType[x] = type;
    }
    
    //get the picture for a piece with integer index
    public static Picture getPiecePicture(int x) {
        return piecePic[x];
    }
    
    //get the picture for a piece with vector index
    public static Picture getPiecePicture(Vector2f vec) {
        int x = MathTools.toInt(vec);
        return piecePic[x];
    }   
    
    //set the picture for a piece
    public static void setPiecePicture(int x, Picture pic) {
        piecePic[x] = pic;
    }
    
    //return if a grid is legal to put
    public static boolean getLegal(int x) {
        return legal[x];
    }
    
    //set a grid's legality
    public static void setLegal(int x, boolean isLegal) {
        legal[x] = isLegal;
    }
    
    //mark a grid to legal, change the picture's alpha at the same time
    private static void markLegal(int x) {
        legal[x] = true;
        gridPic[x].getMaterial().setColor("Color", MathTools.getAlphaColor(1f));
        if (pieceType[x] != PieceType.EMPTY)
            piecePic[x].getMaterial().setColor("Color", MathTools.getAlphaColor(1f));
    }
    
    //mark a grid to illegal, change the picture's alpha at the same time
    private static void markIllegal(int x) {
        legal[x] = false;
        gridPic[x].getMaterial().setColor("Color", MathTools.getAlphaColor(ILLEGAL_ALPHA));
        if (pieceType[x] != PieceType.EMPTY)
            piecePic[x].getMaterial().setColor("Color", MathTools.getAlphaColor(ILLEGAL_ALPHA));
    }
    
    //mark the legality for all grids
    //used during setting state and the king-dragon placement rule is not active
    public static void markLegalSetting() {
        for (int i = 0; i < GRID_NUMBER; i++) {
            if (i < GRID_NUMBER_HALF && pieceType[i] != PieceType.EMPTY)
                markIllegal(i);
            else if (i >= GRID_NUMBER_HALF)
                setLegal(i, false);
        }
    }
    
    //mark the legality for all grids
    //used during setting state
    //when the king is picked up and the dragon is in board
    public static void markLegalSettingKing() {
        for (int i = 0; i < GRID_NUMBER; i++) {
            if (i < GRID_NUMBER_HALF) {
                if (MathTools.getStep(i, dragon) > 4 || pieceType[i] != PieceType.EMPTY)
                    markIllegal(i);
            }
            else setLegal(i, false);
        }
    }

    //mark the legality for all grids
    //used during setting state
    //when the dragon is picked up and the king is in board
    public static void markLegalSettingDragon() {
        for (int i = 0; i < GRID_NUMBER; i++) {
            if (i < GRID_NUMBER_HALF) {
                if (MathTools.getStep(i, king) > 4 || pieceType[i] != PieceType.EMPTY)
                    markIllegal(i);
            }
            else setLegal(i, false);
        }
    }
    
    //mark all grids to legal
    public static void unmarkLegal() {
        for (int i = 0; i < GRID_NUMBER; i++)
            markLegal(i);
    }
    
    //used in playing state
    //mark legality
    public static void markLegalPlaying(int index) {
        PieceType type = Info.getPieceType(index);
        if (type == PieceType.KING || type == PieceType.RABBLE)
            markLegalPlayingKing(MathTools.toVec(index));
        else if (type == PieceType.SPEAR)
            markLegalPlayingSpear(MathTools.toVec(index));
        else if (type == PieceType.ELEPHANT)
            markLegalPlayingElephant(MathTools.toVec(index));
        else if (type == PieceType.CROSSBOW)
            markLegalPlayingCrossbow(MathTools.toVec(index));
        else if (type == PieceType.TREBUCHET)
            markLegalPlayingTrebuchet(MathTools.toVec(index));
        else if (type == PieceType.LIGHTHORSE)
            markLegalPlayingLighthorse(MathTools.toVec(index));
        else if (type == PieceType.HEAVYHORSE)
            markLegalPlayingHeavyhorse(MathTools.toVec(index));
        else if (type == PieceType.DRAGON)
            markLegalPlayingDragon(MathTools.toVec(index));
    }
    
    //used for mark KING and RABBLE
    private static void markLegalPlayingKing(Vector2f vec) {
        for (int i = 0; i < GRID_NUMBER; i++)
            markIllegal(i);

        Vector2f[] v = MathTools.getOrthogonalGrids(vec);
        for (Vector2f loc : v) {
            if (MathTools.isInBoard(loc)) {
                if (getPieceType(loc) == PieceType.EMPTY)
                    markLegal(MathTools.toInt(loc));
                else if (getPieceType(loc).isEnemy())
                    if (checkFlanking(vec, loc))
                        markLegal(MathTools.toInt(loc));
            }
        }
    }
    
    //used for mark SPEAR
    private static void markLegalPlayingSpear(Vector2f vec) {
        for (int i = 0; i < GRID_NUMBER; i++)
            markIllegal(i);

        Vector2f[] v = MathTools.getDiagonalGrids(vec);
        for (Vector2f dir : v) {
            Vector2f unit = dir.subtract(vec);
            for (int i = 0; i < 2; i++) {
                Vector2f loc = vec.add(unit.mult(i + 1));
                if (MathTools.isInBoard(loc)) {
                    PieceType type = getPieceType(loc);
                    if (type.isFriend() || type.isMountain()) break;
                    else if (type == PieceType.EMPTY)
                        markLegal(MathTools.toInt(loc));
                    else if (getPieceType(loc).isEnemy()) {
                        if (checkFlanking(vec, loc))
                            markLegal(MathTools.toInt(loc));
                        break;
                    }
                }
                else break;
            }
        }
    }
    
    //used for mark ELEPHANT
    private static void markLegalPlayingElephant(Vector2f vec) {
        for (int i = 0; i < GRID_NUMBER; i++)
            markIllegal(i);

        Vector2f[] v = MathTools.getDiagonalGrids(vec);
        for (Vector2f dir : v) {
            Vector2f unit = dir.subtract(vec);
            for (int i = 0; i < 15; i++) {
                Vector2f loc = vec.add(unit.mult(i + 1));
                if (MathTools.isInBoard(loc)) {
                    PieceType type = getPieceType(loc);
                    if (type.isFriend() || type.isMountain()) break;
                    else if (type == PieceType.EMPTY)
                        markLegal(MathTools.toInt(loc));
                    else if (getPieceType(loc).isEnemy()) {
                        if (checkFlanking(vec, loc))
                            markLegal(MathTools.toInt(loc));
                        break;
                    }
                }
                else break;
            }
        }
    }
    
    //used for mark CROSSBOW
    private static void markLegalPlayingCrossbow(Vector2f vec) {
        for (int i = 0; i < GRID_NUMBER; i++)
            markIllegal(i);

        Vector2f[] v = MathTools.getOrthogonalGrids(vec);
        for (Vector2f dir : v) {
            Vector2f unit = dir.subtract(vec);
            for (int i = 0; i < 3; i++) {
                Vector2f loc = vec.add(unit.mult(i + 1));
                if (MathTools.isInBoard(loc)) {
                    PieceType type = getPieceType(loc);
                    if (type.isFriend() || type.isMountain()) break;
                    else if (type == PieceType.EMPTY)
                        markLegal(MathTools.toInt(loc));
                    else if (getPieceType(loc).isEnemy()) {
                        if (checkFlanking(vec, loc))
                            markLegal(MathTools.toInt(loc));
                        break;
                    }
                }
                else break;
            }
        }
    }
    
    //used for mark TREBUCHET
    private static void markLegalPlayingTrebuchet(Vector2f vec) {
        for (int i = 0; i < GRID_NUMBER; i++)
            markIllegal(i);

        Vector2f[] v = MathTools.getOrthogonalGrids(vec);
        for (Vector2f dir : v) {
            Vector2f unit = dir.subtract(vec);
            for (int i = 0; i < 15; i++) {
                Vector2f loc = vec.add(unit.mult(i + 1));
                if (MathTools.isInBoard(loc)) {
                    PieceType type = getPieceType(loc);
                    if (type.isFriend() || type.isMountain()) break;
                    else if (type == PieceType.EMPTY)
                        markLegal(MathTools.toInt(loc));
                    else if (getPieceType(loc).isEnemy()) {
                        if (checkFlanking(vec, loc))
                            markLegal(MathTools.toInt(loc));
                        break;
                    }
                }
                else break;
            }
        }
    }

    //used for mark LIGHTHORSE
    private static void markLegalPlayingLighthorse(Vector2f vec) {
        for (int i = 0; i < GRID_NUMBER; i++)
            markIllegal(i);
        
        if (homeFortress != -1) {
            int step = MathTools.getStep(homeFortress, MathTools.toInt(vec));
            ArrayDeque<Vector2f> queue = new ArrayDeque<Vector2f>();
            int flag[] = new int[GRID_NUMBER];
            for (int i = 0; i < GRID_NUMBER; i++)
                flag[i] = 100;
            queue.add(vec.clone());
            flag[MathTools.toInt(vec)] = 0;
            
            while (queue.size() > 0) {
                Vector2f head = queue.remove();
                Vector2f[] v = MathTools.getOrthogonalGrids(head);
                for (Vector2f loc : v) {
                    if (MathTools.isInBoard(loc)) {
                        int index = MathTools.toInt(loc), headIndex = MathTools.toInt(head);
                        if (flag[index] == 100 && flag[headIndex] + 1 <= 3 &&
                                MathTools.getStep(homeFortress, index) == step) {
                            if (getPieceType(index) == PieceType.EMPTY) {
                                markLegal(index);
                                queue.add(loc.clone());
                                flag[index] = flag[headIndex] + 1;
                            }
                            else if (getPieceType(loc).isEnemy())
                                if (checkFlanking(vec, loc))
                                    markLegal(MathTools.toInt(loc));
                        }
                    }
                }
            }
        }
        if (enemyFortress != -1) {
            int step = MathTools.getStep(enemyFortress, MathTools.toInt(vec));
            ArrayDeque<Vector2f> queue = new ArrayDeque<Vector2f>();
            int flag[] = new int[GRID_NUMBER];
            for (int i = 0; i < GRID_NUMBER; i++)
                flag[i] = 100;
            queue.add(vec.clone());
            flag[MathTools.toInt(vec)] = 0;
            
            while (queue.size() > 0) {
                Vector2f head = queue.remove();
                Vector2f[] v = MathTools.getOrthogonalGrids(head);
                for (Vector2f loc : v) {
                    if (MathTools.isInBoard(loc)) {
                        int index = MathTools.toInt(loc), headIndex = MathTools.toInt(head);
                        if (flag[index] == 100 && flag[headIndex] + 1 <= 3 &&
                                MathTools.getStep(enemyFortress, index) == step) {
                            if (getPieceType(index) == PieceType.EMPTY) {
                                markLegal(index);
                                queue.add(loc.clone());
                                flag[index] = flag[headIndex] + 1;
                            }
                            else if (getPieceType(loc).isEnemy())
                                if (checkFlanking(vec, loc))
                                    markLegal(MathTools.toInt(loc));
                        }
                    }
                }
            }
        }
    }    
    
    //used for mark HEAVYHORSE
    private static void markLegalPlayingHeavyhorse(Vector2f vec) {
        for (int i = 0; i < GRID_NUMBER; i++)
            markIllegal(i);
        
        if (homeFortress != -1) {
            int step = MathTools.getStep(homeFortress, MathTools.toInt(vec));
            ArrayDeque<Vector2f> queue = new ArrayDeque<Vector2f>();
            int flag[] = new int[GRID_NUMBER];
            for (int i = 0; i < GRID_NUMBER; i++)
                flag[i] = 100;
            queue.add(vec.clone());
            flag[MathTools.toInt(vec)] = 0;
            
            while (queue.size() > 0) {
                Vector2f head = queue.remove();
                Vector2f[] v = MathTools.getOrthogonalGrids(head);
                for (Vector2f loc : v) {
                    if (MathTools.isInBoard(loc)) {
                        int index = MathTools.toInt(loc), headIndex = MathTools.toInt(head);
                        if (flag[index] == 100 && MathTools.getStep(homeFortress, index) == step) {
                            if (getPieceType(index) == PieceType.EMPTY) {
                                markLegal(index);
                                queue.add(loc.clone());
                                flag[index] = flag[headIndex] + 1;
                            }
                            else if (getPieceType(loc).isEnemy())
                                if (checkFlanking(vec, loc))
                                    markLegal(MathTools.toInt(loc));
                        }
                    }
                }
            }
        }
        if (enemyFortress != -1) {
            int step = MathTools.getStep(enemyFortress, MathTools.toInt(vec));
            ArrayDeque<Vector2f> queue = new ArrayDeque<Vector2f>();
            int flag[] = new int[GRID_NUMBER];
            for (int i = 0; i < GRID_NUMBER; i++)
                flag[i] = 100;
            queue.add(vec.clone());
            flag[MathTools.toInt(vec)] = 0;
            
            while (queue.size() > 0) {
                Vector2f head = queue.remove();
                Vector2f[] v = MathTools.getOrthogonalGrids(head);
                for (Vector2f loc : v) {
                    if (MathTools.isInBoard(loc)) {
                        int index = MathTools.toInt(loc), headIndex = MathTools.toInt(head);
                        if (flag[index] == 100 && MathTools.getStep(enemyFortress, index) == step) {
                            if (getPieceType(index) == PieceType.EMPTY) {
                                markLegal(index);
                                queue.add(loc.clone());
                                flag[index] = flag[headIndex] + 1;
                            }
                            else if (getPieceType(loc).isEnemy())
                                if (checkFlanking(vec, loc))
                                    markLegal(MathTools.toInt(loc));
                        }
                    }
                }
            }
        }
    }
    
    //used for mark DRAGON
    private static void markLegalPlayingDragon(Vector2f vec) {
        for (int i = 0; i < GRID_NUMBER; i++)
            markIllegal(i);
        
        Vector2f[] v = MathTools.getDiagonalGrids(vec);
        for (Vector2f dir : v) {
            Vector2f unit = dir.subtract(vec);
            for (int i = 0; i < 15; i++) {
                Vector2f loc = vec.add(unit.mult(i + 1));
                if (MathTools.isInBoard(loc)) {
                    PieceType type = getPieceType(loc);
                    if (type.isFriend()) break;
                    else if (type == PieceType.EMPTY)
                        markLegal(MathTools.toInt(loc));
                    else if (getPieceType(loc).isEnemy()) {
                        if (checkFlanking(vec, loc))
                            markLegal(MathTools.toInt(loc));
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
                    PieceType type = getPieceType(loc);
                    if (type.isFriend()) break;
                    else if (type == PieceType.EMPTY)
                        markLegal(MathTools.toInt(loc));
                    else if (getPieceType(loc).isEnemy()) {
                        if (checkFlanking(vec, loc))
                            markLegal(MathTools.toInt(loc));
                        break;
                    }
                }
                else break;
            }
        }
    }
    
    //used in check if can flanking
    private static boolean checkFlanking(Vector2f vec, Vector2f loc) {
        PieceType attackType = getPieceType(vec);
        PieceType defendType = getPieceType(loc);
        int attackTier = attackType.getTier();
        int defendTier = defendType.getTier() + getGridType(loc).getImprovement(defendType);
        if (attackTier >= defendTier)
            return true;
        else {
            ArrayList<Integer> flanking = getFlanking(loc, MathTools.toInt(vec));
            if (!flanking.isEmpty()) {
                int maxTier = attackTier;
                for (int index : flanking)
                    if (getPieceType(index).getTier() > maxTier)
                        maxTier = getPieceType(index).getTier();
                attackTier = attackType == PieceType.KING ? maxTier : attackTier;
                if (attackTier == maxTier) {
                    float sumTier = attackTier;
                    for (int index : flanking) {
                        PieceType type = getPieceType(index);
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
    public static ArrayList<Integer> getFlanking(Vector2f vec, int filter) {
        ArrayList<Integer> array = new ArrayList<Integer>();
        for (int index : checkFlankingKing(vec))
            array.add(index);
        for (int index : checkFlankingSpear(vec))
            array.add(index);
        for (int index : checkFlankingElephant(vec))
            array.add(index);
        for (int index : checkFlankingCrossbow(vec))
            array.add(index);
        for (int index : checkFlankingTrebuchet(vec))
            array.add(index);
        for (int index : checkFlankingLighthorse(vec))
            array.add(index);
        for (int index : checkFlankingHeavyhorse(vec))
            array.add(index);
        for (int index : checkFlankingDragon(vec))
            array.add(index);
        array.remove((Integer)filter);
        return array;
    }
    
    //used in find flanking pieces (king)
    private static ArrayList<Integer> checkFlankingKing(Vector2f vec) {
        ArrayList<Integer> array = new ArrayList<Integer>();
        Vector2f[] v = MathTools.getOrthogonalGrids(vec);
        for (Vector2f loc : v) {
            if (MathTools.isInBoard(loc)) {
                if (getPieceType(loc) == PieceType.KING || 
                        getPieceType(loc) == PieceType.RABBLE)
                    array.add(MathTools.toInt(loc));
            }
        }
        return array;
    }
    
    //used in find flanking pieces (spear)
    private static ArrayList<Integer> checkFlankingSpear(Vector2f vec) {
        ArrayList<Integer> array = new ArrayList<Integer>();
        Vector2f[] v = MathTools.getDiagonalGrids(vec);
        for (Vector2f dir : v) {
            Vector2f unit = dir.subtract(vec);
            for (int i = 0; i < 2; i++) {
                Vector2f loc = vec.add(unit.mult(i + 1));
                if (MathTools.isInBoard(loc)) {
                    PieceType type = getPieceType(loc);
                    if (type != PieceType.EMPTY && type != PieceType.SPEAR) break;
                    else if (type == PieceType.SPEAR) {
                        array.add(MathTools.toInt(loc));
                        break;
                    }
                }
                else break;
            }
        }
        return array;
    }
    
    //used in find flanking pieces (elephant)
    private static ArrayList<Integer> checkFlankingElephant(Vector2f vec) {
        ArrayList<Integer> array = new ArrayList<Integer>();
        Vector2f[] v = MathTools.getDiagonalGrids(vec);
        for (Vector2f dir : v) {
            Vector2f unit = dir.subtract(vec);
            for (int i = 0; i < 15; i++) {
                Vector2f loc = vec.add(unit.mult(i + 1));
                if (MathTools.isInBoard(loc)) {
                    PieceType type = getPieceType(loc);
                    if (type != PieceType.EMPTY && type != PieceType.ELEPHANT) break;
                    else if (type == PieceType.ELEPHANT) {
                        array.add(MathTools.toInt(loc));
                        break;
                    }
                }
                else break;
            }
        }
        return array;
    }
    
    //used in find flanking pieces (crossbow)
    private static ArrayList<Integer> checkFlankingCrossbow(Vector2f vec) {
        ArrayList<Integer> array = new ArrayList<Integer>();
        Vector2f[] v = MathTools.getOrthogonalGrids(vec);
        for (Vector2f dir : v) {
            Vector2f unit = dir.subtract(vec);
            for (int i = 0; i < 3; i++) {
                Vector2f loc = vec.add(unit.mult(i + 1));
                if (MathTools.isInBoard(loc)) {
                    PieceType type = getPieceType(loc);
                    if (type != PieceType.EMPTY && type != PieceType.CROSSBOW) break;
                    else if (type == PieceType.CROSSBOW) {
                        array.add(MathTools.toInt(loc));
                        break;
                    }
                }
                else break;
            }
        }
        return array;
    }
    
    //used in find flanking pieces (trebuchet)
    private static ArrayList<Integer> checkFlankingTrebuchet(Vector2f vec) {
        ArrayList<Integer> array = new ArrayList<Integer>();
        Vector2f[] v = MathTools.getOrthogonalGrids(vec);
        for (Vector2f dir : v) {
            Vector2f unit = dir.subtract(vec);
            for (int i = 0; i < 3; i++) {
                Vector2f loc = vec.add(unit.mult(i + 1));
                if (MathTools.isInBoard(loc)) {
                    PieceType type = getPieceType(loc);
                    if (type != PieceType.EMPTY && type != PieceType.TREBUCHET) break;
                    else if (type == PieceType.TREBUCHET) {
                        array.add(MathTools.toInt(loc));
                        break;
                    }
                }
                else break;
            }
        }
        return array;
    }
    
    //used in find flanking pieces (lighthorse)
    private static ArrayList<Integer> checkFlankingLighthorse(Vector2f vec) {
        ArrayList<Integer> array = new ArrayList<Integer>();
        if (homeFortress != -1) {
            int step = MathTools.getStep(homeFortress, MathTools.toInt(vec));
            ArrayDeque<Vector2f> queue = new ArrayDeque<Vector2f>();
            int flag[] = new int[GRID_NUMBER];
            for (int i = 0; i < GRID_NUMBER; i++)
                flag[i] = 100;
            queue.add(vec.clone());
            flag[MathTools.toInt(vec)] = 0;
            
            while (queue.size() > 0) {
                Vector2f head = queue.remove();
                Vector2f[] v = MathTools.getOrthogonalGrids(head);
                for (Vector2f loc : v) {
                    if (MathTools.isInBoard(loc)) {
                        int index = MathTools.toInt(loc), headIndex = MathTools.toInt(head);
                        if (flag[index] == 100 && flag[headIndex] + 1 <= 3 &&
                                MathTools.getStep(homeFortress, index) == step) {
                            if (getPieceType(index) == PieceType.EMPTY) {
                                queue.add(loc.clone());
                                flag[index] = flag[headIndex] + 1;
                            }
                            else if (getPieceType(index) == PieceType.LIGHTHORSE)
                                array.add(index);
                        }
                    }
                }
            }
        }
        if (enemyFortress != -1) {
            int step = MathTools.getStep(enemyFortress, MathTools.toInt(vec));
            ArrayDeque<Vector2f> queue = new ArrayDeque<Vector2f>();
            int flag[] = new int[GRID_NUMBER];
            for (int i = 0; i < GRID_NUMBER; i++)
                flag[i] = 100;
            queue.add(vec.clone());
            flag[MathTools.toInt(vec)] = 0;
            
            while (queue.size() > 0) {
                Vector2f head = queue.remove();
                Vector2f[] v = MathTools.getOrthogonalGrids(head);
                for (Vector2f loc : v) {
                    if (MathTools.isInBoard(loc)) {
                        int index = MathTools.toInt(loc), headIndex = MathTools.toInt(head);
                        if (flag[index] == 100 && flag[headIndex] + 1 <= 3 &&
                                MathTools.getStep(enemyFortress, index) == step) {
                            if (getPieceType(index) == PieceType.EMPTY) {
                                queue.add(loc.clone());
                                flag[index] = flag[headIndex] + 1;
                            }
                            else if (getPieceType(index) == PieceType.LIGHTHORSE)
                                array.add(index);
                        }
                    }
                }
            }
        }
        //get new ability
        if (homeFortress == -1 || enemyFortress == -1) {
            Vector2f[] v = MathTools.getOrthogonalGrids(vec);
            for (Vector2f loc : v) {
                if (MathTools.isInBoard(loc)) {
                    if (getPieceType(loc) == PieceType.LIGHTHORSE)
                        array.add(MathTools.toInt(loc));
                }
            }
        }
        return array;
    }    
    
    //used in find flanking pieces (heavyhorse)
    private static ArrayList<Integer> checkFlankingHeavyhorse(Vector2f vec) {
        ArrayList<Integer> array = new ArrayList<Integer>();
        if (homeFortress != -1) {
            int step = MathTools.getStep(homeFortress, MathTools.toInt(vec));
            ArrayDeque<Vector2f> queue = new ArrayDeque<Vector2f>();
            int flag[] = new int[GRID_NUMBER];
            for (int i = 0; i < GRID_NUMBER; i++)
                flag[i] = 100;
            queue.add(vec.clone());
            flag[MathTools.toInt(vec)] = 0;
            
            while (queue.size() > 0) {
                Vector2f head = queue.remove();
                Vector2f[] v = MathTools.getOrthogonalGrids(head);
                for (Vector2f loc : v) {
                    if (MathTools.isInBoard(loc)) {
                        int index = MathTools.toInt(loc), headIndex = MathTools.toInt(head);
                        if (flag[index] == 100 && MathTools.getStep(homeFortress, index) == step) {
                            if (getPieceType(index) == PieceType.EMPTY) {
                                queue.add(loc.clone());
                                flag[index] = flag[headIndex] + 1;
                            }
                            else if (getPieceType(index) == PieceType.HEAVYHORSE)
                                array.add(index);
                        }
                    }
                }
            }
        }
        if (enemyFortress != -1) {
            int step = MathTools.getStep(enemyFortress, MathTools.toInt(vec));
            ArrayDeque<Vector2f> queue = new ArrayDeque<Vector2f>();
            int flag[] = new int[GRID_NUMBER];
            for (int i = 0; i < GRID_NUMBER; i++)
                flag[i] = 100;
            queue.add(vec.clone());
            flag[MathTools.toInt(vec)] = 0;
            
            while (queue.size() > 0) {
                Vector2f head = queue.remove();
                Vector2f[] v = MathTools.getOrthogonalGrids(head);
                for (Vector2f loc : v) {
                    if (MathTools.isInBoard(loc)) {
                        int index = MathTools.toInt(loc), headIndex = MathTools.toInt(head);
                        if (flag[index] == 100 && MathTools.getStep(enemyFortress, index) == step) {
                            if (getPieceType(index) == PieceType.EMPTY) {
                                queue.add(loc.clone());
                                flag[index] = flag[headIndex] + 1;
                            }
                            else if (getPieceType(index) == PieceType.HEAVYHORSE)
                                array.add(index);
                        }
                    }
                }
            }
        }
        //get new ability
        if (homeFortress == -1 || enemyFortress == -1) {
            Vector2f[] v = MathTools.getOrthogonalGrids(vec);
            for (Vector2f loc : v) {
                if (MathTools.isInBoard(loc)) {
                    if (getPieceType(loc) == PieceType.HEAVYHORSE)
                        array.add(MathTools.toInt(loc));
                }
            }
        }
        return array;
    }
    
    //used in find flanking pieces (dragon)
    private static ArrayList<Integer> checkFlankingDragon(Vector2f vec) {
        ArrayList<Integer> array = new ArrayList<Integer>();
        Vector2f[] v = MathTools.getDiagonalGrids(vec);
        for (Vector2f dir : v) {
            Vector2f unit = dir.subtract(vec);
            for (int i = 0; i < 15; i++) {
                Vector2f loc = vec.add(unit.mult(i + 1));
                if (MathTools.isInBoard(loc)) {
                    PieceType type = getPieceType(loc);
                    if (type != PieceType.EMPTY && type != PieceType.DRAGON) break;
                    else if (type == PieceType.DRAGON) {
                        array.add(MathTools.toInt(loc));
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
                    PieceType type = getPieceType(loc);
                    if (type != PieceType.EMPTY && type != PieceType.DRAGON) break;
                    else if (type == PieceType.DRAGON) {
                        array.add(MathTools.toInt(loc));
                        break;
                    }
                }
                else break;
            }
        }
        return array;
    }
    
}
