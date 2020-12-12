package com.unieibar.uni_58_labirintua.logika;

public class Gelaxka {
    // Barne propietateak
    private int x;
    private int y;
    private boolean iparralde_pareta;
    private boolean ekialde_pareta;
    private boolean hegoalde_pareta;
    private boolean mendebalde_pareta;
    private boolean bisitatua;
    private GelaxkaMota mota;

    // Sortzailea
    public Gelaxka()
    {
        this(-1, -1);
    }

    public Gelaxka(int x, int y)
    {
        this.setX(x);
        this.setY(y);
        this.setIparralde_pareta(true);
        this.setEkialde_pareta(true);
        this.setHegoalde_pareta(true);
        this.setMendebalde_pareta(true);
        this.setBisitatua(false);
        this.setMota(GelaxkaMota.BIDEA);
    }

    // Getter eta Setter-ak
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean getIparralde_pareta() {
        return iparralde_pareta;
    }

    public void setIparralde_pareta(boolean iparralde_pareta) {
        this.iparralde_pareta = iparralde_pareta;
    }

    public boolean getEkialde_pareta() {
        return ekialde_pareta;
    }

    public void setEkialde_pareta(boolean ekialde_pareta) {
        this.ekialde_pareta = ekialde_pareta;
    }

    public boolean getHegoalde_pareta() {
        return hegoalde_pareta;
    }

    public void setHegoalde_pareta(boolean hegoalde_pareta) {
        this.hegoalde_pareta = hegoalde_pareta;
    }

    public boolean getMendebalde_pareta() {
        return mendebalde_pareta;
    }

    public void setMendebalde_pareta(boolean mendebalde_pareta) {
        this.mendebalde_pareta = mendebalde_pareta;
    }

    public boolean getBisitatua() {
        return bisitatua;
    }

    public void setBisitatua(boolean bisitatua) {
        this.bisitatua = bisitatua;
    }

    public GelaxkaMota getMota() {
        return mota;
    }

    public void setMota(GelaxkaMota mota) {
        this.mota = mota;
    }

    public boolean posizio_bera_du(Gelaxka besteGelaxka)
    {
        boolean posizio_berean = false;
        if (this.x == besteGelaxka.x && this.y == besteGelaxka.y)
        {
            posizio_berean = true;
        }
        return posizio_berean;
    }
}
