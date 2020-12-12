package com.unieibar.uni_58_labirintua.logika;

public class LabirintukoBideBilatzailea {

    // Barne propietateak
    private static int zabalera;
    private static int altuera;
    private static Gelaxka[][] labirintua;
    public static int irteeraX;
    public static int irteeraY;

    // Sortzailea
    public static void LabirintukoBideBilatzaileaHasieratu(int zabalera, int altuera, Gelaxka[][] labirintua)
    {
        LabirintukoBideBilatzailea.zabalera = zabalera;
        LabirintukoBideBilatzailea.altuera = altuera;
        LabirintukoBideBilatzailea.labirintua = labirintua;
        LabirintukoBideBilatzailea.labirintuaPrestatu();
    }

    // Metodo pribatuak
    public static void labirintuaPrestatu()
    {
        for (int y=0; y<altuera; y++)
        {
            for (int x=0; x<zabalera; x++)
            {
                labirintua[x][y].setBisitatua(false);
                if (labirintua[x][y].getMota() == GelaxkaMota.IRTEERA)
                {
                    irteeraX = x;
                    irteeraY = y;
                }
            }
        }
    }

    // Metodo publikoak

    public static boolean bideZuzenaBilatuRekurtsiboki(int posX, int posY, String nondiknator)
    {
        // nondiknator
        // I = Iparraldetik
        // E = Ekialdetik
        // H = Hegoaldetik
        // M = Mendebaldetik
        // K = kanpotik

        int posXberria = posX;
        int posYberria = posY;
        labirintua[posXberria][posYberria].setBisitatua(true);


        if (labirintua[posX][posY].getMota() == GelaxkaMota.HELMUGA)
        {
            return true;
        }
        else
        {
            // Lau paretak begiratu, banan banan
            if (!labirintua[posX][posY].getIparralde_pareta() && 0 != nondiknator.compareTo("I")) // Iparraldetik baldin badator, ez atzera itzuli
            {
                posXberria = posX;
                posYberria = posY-1;

                if (!labirintua[posXberria][posYberria].getBisitatua())
                {
                    if (bideZuzenaBilatuRekurtsiboki(posXberria, posYberria, "H")) // Bilaketa errekurtsiboa
                    {
                        if (labirintua[posX][posY].getMota() != GelaxkaMota.IRTEERA)
                        {
                            labirintua[posX][posY].setMota(GelaxkaMota.SOLUZIOA);
                        }
                        return true;
                    }
                }

            }
            if (!labirintua[posX][posY].getEkialde_pareta() && 0 != nondiknator.compareTo("E")) // Ekialdetik baldin badator, ez atzera itzuli
            {
                posXberria = posX+1;
                posYberria = posY;
                if (!labirintua[posXberria][posYberria].getBisitatua())
                {
                    if (bideZuzenaBilatuRekurtsiboki(posXberria, posYberria, "M")) // Bilaketa errekurtsiboa
                    {
                        if (labirintua[posX][posY].getMota() != GelaxkaMota.IRTEERA)
                        {
                            labirintua[posX][posY].setMota(GelaxkaMota.SOLUZIOA);
                        }
                        return true;
                    }
                }
            }
            if (!labirintua[posX][posY].getHegoalde_pareta() && 0 != nondiknator.compareTo("H")) // Hegoaldetik baldin badator, ez atzera itzuli)
            {
                posXberria = posX;
                posYberria = posY+1;
                if (!labirintua[posXberria][posYberria].getBisitatua())
                {
                    if (bideZuzenaBilatuRekurtsiboki(posXberria, posYberria, "I")) // Bilaketa errekurtsiboa
                    {
                        if (labirintua[posX][posY].getMota() != GelaxkaMota.IRTEERA)
                        {
                            labirintua[posX][posY].setMota(GelaxkaMota.SOLUZIOA);
                        }
                        return true;
                    }
                }
            }
            if (!labirintua[posX][posY].getMendebalde_pareta() && 0 != nondiknator.compareTo("M")) // Mendebaldetik baldin badator, ez atzera itzuli)
            {
                posXberria = posX-1;
                posYberria = posY;
                if (!labirintua[posXberria][posYberria].getBisitatua())
                {
                    if (bideZuzenaBilatuRekurtsiboki(posXberria, posYberria, "E")) // Bilaketa errekurtsiboa
                    {
                        if (labirintua[posX][posY].getMota() != GelaxkaMota.IRTEERA)
                        {
                            labirintua[posX][posY].setMota(GelaxkaMota.SOLUZIOA);
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void labirintuaGarbitu()
    {
        for (int y=0; y<altuera; y++)
        {
            for (int x=0; x<zabalera; x++)
            {
                if (labirintua[x][y].getMota() == GelaxkaMota.SOLUZIOA)
                {
                    labirintua[x][y].setMota(GelaxkaMota.BIDEA);
                }
            }
        }
    }
}

