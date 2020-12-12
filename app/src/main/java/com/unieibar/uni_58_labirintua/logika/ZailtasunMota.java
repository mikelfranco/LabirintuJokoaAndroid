package com.unieibar.uni_58_labirintua.logika;

public enum ZailtasunMota {
    ERRAZA(0),         // 0
    ARRUNTA(1),        // 1
    ZAILA(2),          // 2
    OSO_ZAILA(3),      // 3
    ZORATZEKOA(4),     // 4
    EMANTIROBAT(5);    // 5

    private int id;

    ZailtasunMota(int id){
        this.id = id;
    }

    public int getID(){
        return id;
    }

    public static ZailtasunMota fromInteger(int x) {
        switch(x) {
            case 0:
                return ERRAZA;
            case 1:
                return ARRUNTA;
            case 2:
                return ZAILA;
            case 3:
                return OSO_ZAILA;
            case 4:
                return ZORATZEKOA;
            case 5:
                return EMANTIROBAT;
        }
        return null;
    }
}
