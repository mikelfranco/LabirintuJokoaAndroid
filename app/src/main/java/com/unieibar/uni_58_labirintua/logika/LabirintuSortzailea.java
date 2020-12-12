package com.unieibar.uni_58_labirintua.logika;

import java.util.Random;

public class LabirintuSortzailea {

    // Barne propietateak
    private int zabalera;
    private int altuera;
    private Gelaxka[][] labirintua;
    private int X_IRTEERA = 0;
    private int Y_IRTEERA = 0;
    private int X_HELMUGA = 0;
    private int Y_HELMUGA = 0;

    // Balio estatikoak
    private static int GUTXIENEKO_ZABALERA = 5;
    private static int GUTXIENEKO_ALTUERA = 5;
    private static int BEREZKO_ZABALERA = 10;
    private static int BEREZKO_ALTUERA = 10;

    // Sortzailea
    public LabirintuSortzailea()
    {
        this(LabirintuSortzailea.BEREZKO_ZABALERA, LabirintuSortzailea.BEREZKO_ALTUERA);
    }

    public LabirintuSortzailea(int zabalera, int altuera)
    {
        this.zabalera = LabirintuSortzailea.GUTXIENEKO_ZABALERA;
        this.altuera = LabirintuSortzailea.GUTXIENEKO_ALTUERA;

        if (zabalera > this.zabalera)
        {
            this.zabalera = zabalera;
        }
        if (altuera > this.altuera)
        {
            this.altuera = altuera;
        }

        hasieratuLabirintua();
        beteLabirintua();
    }

    // Metodo pribatuak
    private void hasieratuLabirintua()
    {
        this.labirintua = new Gelaxka[this.zabalera][this.altuera];

        // Labirintu osoa paretekin bete
        for (int x=0; x<this.zabalera; x++)
        {
            for(int y=0; y<this.altuera; y++)
            {
                this.labirintua[x][y] = new Gelaxka(x,y);
            }
        }

        // Lehen gelaxka IRTEERA motakoa jarri eta azkena HELMUGA
        X_IRTEERA = 0;
        Y_IRTEERA = 0;
        X_HELMUGA = this.zabalera-1;
        Y_HELMUGA = this.altuera-1;
        this.labirintua[X_IRTEERA][Y_IRTEERA].setMota(GelaxkaMota.IRTEERA);
        this.labirintua[X_HELMUGA][Y_HELMUGA].setMota(GelaxkaMota.HELMUGA);
    }

    private void beteLabirintua()
    {
        // Labirintua betetzeko algoritmoa:
        // Labirintuko gelaxka guztiak, hasieran, "bisitatu gabe" daude eta 4 paretak dituzte (true balioarekin).
        // Ausaz aukeratu hasierako gelaxka bat. Erreferentziazko gelaxka bihurtuko da.
        // Markatu "bisitatu" moduan.
        // Labirintuko gelaxka guztiak (zabalera x altuera) bisitatu arte, ondorengoa egin:
        // 		Aukeratu ausaz goiko (iparraldeko), beheko (hegoaldeko), ezkerreko (mendebaldeko) edo eskuineko (ekialdeko) gelaxka bat.
        // 		Begiratu aurretiaz bisitatu dugun ala ez:
        // 			Bisitatua baldin badago, ez egin ezer.
        // 			Bisitatu ez badugu aurretik, aurretiko gelaxkaren eta gelaxka berriaren arteko paretak bota (false) moduan jarri.
        //		Gelaxka berria orain izango da erreferentziazko.


        int gelaxkakGuztira = this.altuera * this.zabalera;
        int posX = -1;
        int posY = -1;


        Random ausazko = new Random(); // Ausazko aldagaia
        posX = ausazko.nextInt(this.zabalera); // Ausazko X posizioa
        posY = ausazko.nextInt(this.altuera); // Ausazko Y posizioa
        this.labirintua[posX][posY].setBisitatua(true); // Hasierako ausazko gelaxka
        int bisitatutakoGelaxkak = 1; // Lehen gelaxka bisitatu dugu


        int posXberria = -1; // Hurrengo X posizioa
        int posYberria = -1; // Hurrengo Y posizioa
        while (bisitatutakoGelaxkak < gelaxkakGuztira)
        {
            int norabidea = ausazko.nextInt(4);
            switch (norabidea)
            {
                case 0: // Iparraldea
                    posXberria = posX;
                    posYberria = posY-1;
                    break;
                case 1: // Ekialdea
                    posXberria = posX+1;
                    posYberria = posY;
                    break;
                case 2: // Hegoaldea
                    posXberria = posX;
                    posYberria = posY+1;
                    break;
                case 3: // Mendebaldea
                    posXberria = posX-1;
                    posYberria = posY;
                    break;
            }
            // Begiratu ez garela labirintutik irten
            // Irte
            boolean irtenGara = false;
            if (posXberria < 0 || posXberria >= this.zabalera) {
                posXberria = posX;
                irtenGara = true;
            }
            if (posYberria < 0 || posYberria >= this.altuera) {
                posYberria = posY;
                irtenGara = true;
            }

            // Irten baldin bagara, ez dugu ezer egingo, gelaxka bat eta bestea berdin geratzen direlako
            // Soilik irten ez bagara izango dira posizioak desberdinak
            if (!irtenGara)
            {
                // Begiratu gelaxka aurretik bisitatu den ala ez
                if (this.labirintua[posXberria][posYberria].getBisitatua())
                {
                    // Bisitatua dagoeneko, ez dago ezer egiteko
                    // Gelaxka berriaren posizioa finkatu bakarrik
                    posX = posXberria;
                    posY = posYberria;
                }
                else
                {
                    // Gelaxken arteko paretak bota
                    switch (norabidea)
                    {
                        case 0: // Iparraldea
                            this.labirintua[posX][posY].setIparralde_pareta(false);
                            this.labirintua[posXberria][posYberria].setHegoalde_pareta(false);
                            break;
                        case 1: // Ekialdea
                            this.labirintua[posX][posY].setEkialde_pareta(false);
                            this.labirintua[posXberria][posYberria].setMendebalde_pareta(false);
                            break;
                        case 2: // Hegoaldea
                            this.labirintua[posX][posY].setHegoalde_pareta(false);
                            this.labirintua[posXberria][posYberria].setIparralde_pareta(false);
                            break;
                        case 3: // Mendebaldea
                            this.labirintua[posX][posY].setMendebalde_pareta(false);
                            this.labirintua[posXberria][posYberria].setEkialde_pareta(false);
                            break;
                    }

                    // Gelaxka berriaren posizioa finkatu eta bisita markatu
                    posX = posXberria;
                    posY = posYberria;
                    this.labirintua[posX][posY].setBisitatua(true);
                    bisitatutakoGelaxkak++;
                }
            }



        }
    }


    // Metodo publikoak
    public int getZabalera()
    {
        return this.zabalera;
    }

    public int getAltuera()
    {
        return this.altuera;
    }

    public Gelaxka[][] getLabirintua()
    {
        return this.labirintua;
    }

    public int get_X_IRTEERA()
    {
        return this.X_IRTEERA;
    }

    public int get_Y_IRTEERA()
    {
        return this.Y_IRTEERA;
    }

    public int get_X_HELMUGA()
    {
        return this.X_HELMUGA;
    }

    public int get_Y_HELMUGA()
    {
        return this.Y_HELMUGA;
    }

}
