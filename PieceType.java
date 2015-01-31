
package mygame;

//enum for piece types
public enum PieceType {
    
    EMPTY, 
    //friends
    KING, SPEAR, ELEPHANT, CROSSBOW, TREBUCHET, LIGHTHORSE, HEAVYHORSE,
    RABBLE, MOUNTAIN, DRAGON,
    //enemies
    KING_E, SPEAR_E, ELEPHANT_E, CROSSBOW_E, TREBUCHET_E, LIGHTHORSE_E, HEAVYHORSE_E,
    RABBLE_E, MOUNTAIN_E, DRAGON_E;
    
    //check if it's a friend piece
    //mountain is NOT seen as a friend
    public boolean isFriend() {
        if (this == KING || this == SPEAR || this == ELEPHANT || this == CROSSBOW || 
                this == TREBUCHET || this == LIGHTHORSE || this == HEAVYHORSE || 
                this == RABBLE || this == DRAGON) return true;
        else return false;
    }
    
    //check if it's a friend piece
    //mountain is NOT seen as an enemy
    public boolean isEnemy() {
        if (this == KING_E || this == SPEAR_E || this == ELEPHANT_E || this == CROSSBOW_E || 
                this == TREBUCHET_E || this == LIGHTHORSE_E || this == HEAVYHORSE_E || 
                this == RABBLE_E || this == DRAGON_E) return true;
        else return false;
    }
    
    //check if it's a mountain piece
    public boolean isMountain() {
        if (this == MOUNTAIN || this == MOUNTAIN_E)
            return true;
        else return false;
    }
    
    //get piece tier
    public int getTier() {
        if (this == KING || this == KING_E || this == RABBLE || this == RABBLE_E)
            return 1;
        else if (this == SPEAR || this == SPEAR_E || this == CROSSBOW || 
                this == CROSSBOW_E || this == LIGHTHORSE || this == LIGHTHORSE_E)
            return 2;
        else if (this == ELEPHANT || this == ELEPHANT_E || this == TREBUCHET || 
                this == TREBUCHET_E || this == HEAVYHORSE || this == HEAVYHORSE_E)
            return 3;
        else if (this == DRAGON || this == DRAGON_E)
            return 4;
        else return 0;
    }
    
    //get piece name (for display messages)
    public String getName() {
        if (this == KING) return "White King";
        else if (this == SPEAR) return "White Spear";
        else if (this == ELEPHANT) return "White Elephant";
        else if (this == CROSSBOW) return "White Crossbow";
        else if (this == TREBUCHET) return "White Trebuchet";
        else if (this == LIGHTHORSE) return "White Lighthorse";
        else if (this == HEAVYHORSE) return "White Heavyhorse";
        else if (this == RABBLE) return "White Rabble";
        else if (this == MOUNTAIN) return "White Mountain";
        else if (this == DRAGON) return "White Dragon";
        
        else if (this == KING_E) return "Black King";
        else if (this == SPEAR_E) return "Black Spear";
        else if (this == ELEPHANT_E) return "Black Elephant";
        else if (this == CROSSBOW_E) return "Black Crossbow";
        else if (this == TREBUCHET_E) return "Black Trebuchet";
        else if (this == LIGHTHORSE_E) return "Black Lighthorse";
        else if (this == HEAVYHORSE_E) return "Black Heavyhorse";
        else if (this == RABBLE_E) return "Black Rabble";
        else if (this == MOUNTAIN_E) return "Black Mountain";
        else if (this == DRAGON_E) return "Black Dragon";
        
        else return "";
    }
    
}
