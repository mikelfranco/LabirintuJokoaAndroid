package com.unieibar.uni_58_labirintua.logika;

public enum GelaxkaMota {
    IRTEERA(0),
    HELMUGA(1),
    BIDEA(2),
    EZEZAGUNA(3),
    SOLUZIOA(4);

    private int id;

    GelaxkaMota(int id){
        this.id = id;
    }

    public int getID(){
        return id;
    }

    public static GelaxkaMota fromInteger(int x) {
        switch(x) {
            case 0:
                return IRTEERA;
            case 1:
                return HELMUGA;
            case 2:
                return BIDEA;
            case 3:
                return EZEZAGUNA;
            case 4:
                return SOLUZIOA;
        }
        return null;
    }
}
