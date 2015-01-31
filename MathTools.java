
package mygame;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.FastMath;
import java.util.HashMap;
import java.util.ArrayList;

//this is a class for math utilities, containing only static methods and fields
//it provides service relating to math (vector coords and integer index)

public class MathTools {
    
    //define some constants for the board
    private static final float CENTER_X = 530f;
    private static final float CENTER_Y = 400f;
    private static final float GRID_UNIT = 60f;
    private static final float PIECE_UNIT = 48f;
    private static final int GRID_NUMBER = 91;
    
    //an ArrayList to change the integer to vector
    private static final ArrayList<Vector2f> toVec = new ArrayList<Vector2f>();
    //a HashMap to change the vector to integer
    private static final HashMap<Vector2f, Integer> toInt = new HashMap<Vector2f, Integer>();
    //record the step number between each grid pair
    private static final int[][] step = new int[GRID_NUMBER][GRID_NUMBER];
    
    //initialize the static final fields
    static {
        //initialize the vector and integer mapping
        int count = 0;
        float x = CENTER_X - 3f * GRID_UNIT;
        float y = CENTER_Y - 5.5f * GRID_UNIT;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6 + i; j++) {
                Vector2f vec = new Vector2f(x + j * GRID_UNIT, y);
                toVec.add(vec);
                toInt.put(vec, count++);
            }
            x -= 0.5f * GRID_UNIT;
            y += GRID_UNIT;
        }
        x = CENTER_X - 5f * GRID_UNIT;
        y = CENTER_Y + 0.5f * GRID_UNIT;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10 - i; j++) {
                Vector2f vec = new Vector2f(x + j * GRID_UNIT, y);
                toVec.add(vec);
                toInt.put(vec, count++);
            }
            x += 0.5f * GRID_UNIT;
            y += GRID_UNIT;
        }
        
        //initialize the step array
        //use Floyd algorithm
        for (int i = 0; i < GRID_NUMBER; i++)
            for (int j = 0; j < GRID_NUMBER; j++) {
                if (i == j)
                    step[i][j] = 0;
                else if (isAdjacent(i, j))
                    step[i][j] = 1;
                else step[i][j] = 100;
            }
        for (int k = 0; k < GRID_NUMBER; k++)
            for (int i = 0; i < GRID_NUMBER; i++)
                for (int j = 0; j < GRID_NUMBER; j++) {
                    if (step[i][k] + step[k][j] < step[i][j])
                        step[i][j] = step[i][k] + step[k][j];
                }
    }
    
    //change from integer index to vector coord for a grid
    public static Vector2f toVec(int x) {
        return toVec.get(x);
    }
    
    //change from integer index to vector coord for a piece
    public static Vector2f toVecPiece(int x) {
        Vector2f vec = toVec(x);
        return new Vector2f(vec.x + 6f, vec.y + 6f);
    }
    
    //change from vector coord to integer index
    public static int toInt(Vector2f vec) {
        return toInt.get(vec);
    }
    
    //return the color with alpha channel for a picture
    public static ColorRGBA getAlphaColor(float a) {
        return new ColorRGBA(1f, 1f, 1f, a);
    }
    
    //return the color with alpha channel for a text
    //need to pass the color of the text
    public static ColorRGBA getAlphaColor(ColorRGBA c, float a) {
        return new ColorRGBA(c.getRed(), c.getGreen(), c.getBlue(), a);
    }
    
    //private method, to check if a point is in a rectangle
    //vec : the point
    //4 floats : the location and the size of the rectangle
    private static boolean isInRect(Vector2f vec, float loc_x, float loc_y, 
                                    float width, float height) {
        if ((vec.x >= loc_x) && (vec.x <= loc_x + width) && 
                        (vec.y >= loc_y) && (vec.y <= loc_y + height))
            return true;
        else 
            return false;
    }
    
    //return the step number between two grids
    public static int getStep(int x, int y) {
        return step[x][y];
    }
    
    //check if two grids are adjacent
    private static boolean isAdjacent(int x, int y) {
        Vector2f vecx = toVec.get(x), vecy = toVec.get(y);
        float dis = FastMath.sqrt(FastMath.sqr(vecx.x - vecy.x) + FastMath.sqr(vecx.y - vecy.y));
        if (dis <= FastMath.ceil(30 * FastMath.sqrt(5))) return true;
        else return false;
    }
    
    //get orthogonal grids
    public static Vector2f[] getOrthogonalGrids(Vector2f vec) {
        float x = vec.x, y = vec.y;
        Vector2f[] v = new Vector2f[6];
        v[0] = new Vector2f(x - 60f, y);
        v[1] = new Vector2f(x + 60f, y);
        v[2] = new Vector2f(x - 30f, y - 60f);
        v[3] = new Vector2f(x + 30f, y - 60f);
        v[4] = new Vector2f(x - 30f, y + 60f);
        v[5] = new Vector2f(x + 30f, y + 60f);
        return v;
    }
    
    //get diagonal grids
    public static Vector2f[] getDiagonalGrids(Vector2f vec) {
        float x = vec.x, y = vec.y;
        Vector2f[] v = new Vector2f[6];
        v[0] = new Vector2f(x, y - 120f);
        v[1] = new Vector2f(x, y + 120f);
        v[2] = new Vector2f(x - 90f, y - 60f);
        v[3] = new Vector2f(x + 90f, y - 60f);
        v[4] = new Vector2f(x - 90f, y + 60f);
        v[5] = new Vector2f(x + 90f, y + 60f);
        return v;
    }
    
    //check if a click is on the box pieces
    //return the piece type when true, return empty type when false
    public static PieceType isInBoxPiece(Vector2f vec) {
        if (isInRect(vec, KING_LOC_X, KING_LOC_Y, PIECE_UNIT, PIECE_UNIT))
            return PieceType.KING;
        if (isInRect(vec, SPEAR_LOC_X, SPEAR_LOC_Y, PIECE_UNIT, PIECE_UNIT))
            return PieceType.SPEAR;
        if (isInRect(vec, ELEPHANT_LOC_X, ELEPHANT_LOC_Y, PIECE_UNIT, PIECE_UNIT))
            return PieceType.ELEPHANT;
        if (isInRect(vec, CROSSBOW_LOC_X, CROSSBOW_LOC_Y, PIECE_UNIT, PIECE_UNIT))
            return PieceType.CROSSBOW;
        if (isInRect(vec, TREBUCHET_LOC_X, TREBUCHET_LOC_Y, PIECE_UNIT, PIECE_UNIT))
            return PieceType.TREBUCHET;
        if (isInRect(vec, LIGHTHORSE_LOC_X, LIGHTHORSE_LOC_Y, PIECE_UNIT, PIECE_UNIT))
            return PieceType.LIGHTHORSE;
        if (isInRect(vec, HEAVYHORSE_LOC_X, HEAVYHORSE_LOC_Y, PIECE_UNIT, PIECE_UNIT))
            return PieceType.HEAVYHORSE;
        if (isInRect(vec, RABBLE_LOC_X, RABBLE_LOC_Y, PIECE_UNIT, PIECE_UNIT))
            return PieceType.RABBLE;
        if (isInRect(vec, MOUNTAIN_LOC_X, MOUNTAIN_LOC_Y, PIECE_UNIT, PIECE_UNIT))
            return PieceType.MOUNTAIN;
        if (isInRect(vec, DRAGON_LOC_X, DRAGON_LOC_Y, PIECE_UNIT, PIECE_UNIT))
            return PieceType.DRAGON;
        return PieceType.EMPTY;
    }
    
    //check if a click is in a grid
    //return the index of that grid when true, return -1 when not
    //used when placing a piece
    public static int isInGrid(Vector2f vec) {
        float x = vec.x, y = vec.y;
        float ylim = CENTER_Y - 5.5f * GRID_UNIT;
        if (y >= ylim + 11f * GRID_UNIT || y <= ylim)
            return -1;
        float c = FastMath.abs(FastMath.floor((y - ylim) / GRID_UNIT) - 5f);
        y = FastMath.floor((y - ylim) / GRID_UNIT) * GRID_UNIT + ylim;
        float xlim = CENTER_X - (5.5f - c * 0.5f) * GRID_UNIT;
        if (x <= xlim || x >= CENTER_X * 2 - xlim)
            return -1;
        x = FastMath.floor((x - xlim) / GRID_UNIT) * GRID_UNIT + xlim;
        return toInt(new Vector2f(x, y));
    }
    
    //check if a click is in a piece (smaller area then that of the same grid)
    //return the index of that grid when true, return -1 when not
    //used when picking a piece
    public static int isInGridPiece(Vector2f vec) {
        int index = isInGrid(vec);
        if (index == -1) return -1;
        Vector2f gVec = toVec(index);
        if (vec.x >= gVec.x + 6f && vec.x <= gVec.x + 54f && 
                vec.y >= gVec.y + 6f && vec.y <= gVec.y + 54f)
            return index;
        else return -1;
    }
    
    //check if in the promotion icon
    //vec : the vector of click
    //return the index of the grid where the promotion icon is in, return -1 otherwise
    public static int isInPromotion(Vector2f vec) {
        int index = isInGridPiece(vec);
        if (index == -1) return -1;
        Vector2f gVec = toVec(index);
        //in the promotion icon on the right top corner
        if (vec.x >= gVec.x + 39f && vec.x <= gVec.x + 54f &&
                vec.y >= gVec.y + 39f && vec.y <= gVec.y + 54f)
            return index;
        else return -1;
    }
    
    //check if the vector is in the board area
    public static boolean isInBoard(Vector2f vec) {
        if (toInt.containsKey(vec)) return true;
        else return false;
    }

    //some constants of piece locations in the box
    private static final float PIECE_X = 1020f;
    private static final float PIECE_Y = 214f;
    
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
    
}


