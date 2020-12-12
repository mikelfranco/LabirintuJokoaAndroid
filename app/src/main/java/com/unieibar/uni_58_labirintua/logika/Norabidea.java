package com.unieibar.uni_58_labirintua.logika;

public enum Norabidea {
    GELDIRIK(0),
    IPARRALDERA(1),
    EKIALDERA(2),
    HEGOALDERA(3),
    MENDEBALDERA(4);

    private int id;

    Norabidea(int id){
        this.id = id;
    }

    public int getID(){
        return id;
    }

    public static Norabidea fromInteger(int x) {
        switch(x) {
            case 0:
                return GELDIRIK;
            case 1:
                return IPARRALDERA;
            case 2:
                return EKIALDERA;
            case 3:
                return HEGOALDERA;
            case 4:
                return MENDEBALDERA;
        }
        return null;
    }

    @Override
    public String toString()
    {
        switch(this.id) {
            case 0:
                return "GELDIRIK";
            case 1:
                return "IPARRALDERA";
            case 2:
                return "EKIALDERA";
            case 3:
                return "HEGOALDERA";
            case 4:
                return "MENDEBALDERA";
        }
        return "EZEZAGUNA";
    }
}
