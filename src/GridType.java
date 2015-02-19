
package mygame;

//enum for grid types
public enum GridType {
    
    FOREST, HILL, WATER, FORTRESS;
    
    //get terrain improvement on tier level
    public int getImprovement(PieceType type) {
        if ((type == PieceType.SPEAR || type == PieceType.ELEPHANT || 
                type == PieceType.SPEAR_E || type == PieceType.ELEPHANT_E) && this == FOREST)
            return 1;
        if ((type == PieceType.CROSSBOW || type == PieceType.TREBUCHET || 
                type == PieceType.CROSSBOW_E || type == PieceType.TREBUCHET_E) && this == HILL)
            return 1;
        if ((type == PieceType.LIGHTHORSE || type == PieceType.HEAVYHORSE || 
                type == PieceType.LIGHTHORSE_E || type == PieceType.HEAVYHORSE_E) && this == WATER)
            return 1;
        if (type != PieceType.DRAGON && type != PieceType.DRAGON_E && this == FORTRESS)
            return 1;
        return 0;
    }
    
}
