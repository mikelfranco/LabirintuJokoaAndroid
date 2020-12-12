package com.unieibar.uni_58_labirintua.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.unieibar.uni_58_labirintua.R;
import com.unieibar.uni_58_labirintua.logika.Gelaxka;
import com.unieibar.uni_58_labirintua.logika.GelaxkaMota;
import com.unieibar.uni_58_labirintua.logika.ILabirintuaListener;
import com.unieibar.uni_58_labirintua.logika.LabirintuSortzailea;
import com.unieibar.uni_58_labirintua.logika.LabirintukoBideBilatzailea;
import com.unieibar.uni_58_labirintua.logika.Norabidea;
import com.unieibar.uni_58_labirintua.logika.ZailtasunMota;


public class LabirintuaView extends View {

    // Barne propietateak
    private Thread joko_haria;
    private boolean jokoa_martxan = false;
    private int zabalera;               // Gelaxka kopurua X-en
    private int altuera;                // Gelaxka kopurua Y-n
    private int lapitzlodiera;          // Paretaren lodiera pixel-etan
    private int tartea;                 // Gelaxkaren tamaina pixel-etan
    private Gelaxka[][] labirintua;     // Labirintu gelaxkak
    private ZailtasunMota zailtasuna;   // Zailtasuna
    private LabirintuSortzailea sortzaile;
    // Jokalaria
    private int jokalaria_x;
    private int jokalaria_y;

    // Marrazteko objektuak
    private RectF atzeko_planoa;
    private Paint atzeko_pintzela;
    private RectF gelaxka_laukia;
    private Paint gelaxka_pintzela;
    private float marra_zabalera;
    private RectF marra;
    private Paint marra_pintzela;
    private int marra_kolorea;
    private Paint jokalari_pintzela;
    private int jokalari_kolorea;
    private int atzekalde_kolorea;


    //Listener
    ILabirintuaListener iLabirintuaListener;
    public void setILabirintuaListener(ILabirintuaListener listener) {
        this.iLabirintuaListener = listener;
    }


    // Getter eta Setter-ak

    public int getZabalera() {
        return zabalera;
    }

    public int getAltuera() {
        return altuera;
    }

    public int getLapitzlodiera() {
        return lapitzlodiera;
    }

    public int getTartea() {
        return tartea;
    }

    public Gelaxka[][] getLabirintua() {
        return labirintua;
    }

    public ZailtasunMota getZailtasuna() {
        return zailtasuna;
    }

    public void setZailtasuna(ZailtasunMota zailtasuna) {
        this.zailtasuna = zailtasuna;

        if (0 == getZailtasuna().compareTo(ZailtasunMota.ERRAZA))
        {
            this.zabalera = 8;
            this.altuera = 8;
            this.lapitzlodiera = 10;
        }
        else if (0 == getZailtasuna().compareTo(ZailtasunMota.ARRUNTA))
        {
            this.zabalera = 12;
            this.altuera = 12;
            this.lapitzlodiera = 8;
        }
        else if (0 == getZailtasuna().compareTo(ZailtasunMota.ZAILA))
        {
            this.zabalera = 16;
            this.altuera = 16;
            this.lapitzlodiera = 6;
        }
        else if (0 == getZailtasuna().compareTo(ZailtasunMota.OSO_ZAILA))
        {
            this.zabalera = 20;
            this.altuera = 20;
            this.lapitzlodiera = 4;
        }
        else if (0 == getZailtasuna().compareTo(ZailtasunMota.ZORATZEKOA))
        {
            this.zabalera = 24;
            this.altuera = 24;
            this.lapitzlodiera = 2;
        }
        else if (0 == getZailtasuna().compareTo(ZailtasunMota.EMANTIROBAT))
        {
            this.zabalera = 32;
            this.altuera = 32;
            this.lapitzlodiera = 1;
        }
    }

    // Sortzaileak

    public LabirintuaView(Context context) {
        super(context);
        init(null);
    }

    public LabirintuaView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public LabirintuaView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public LabirintuaView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(@Nullable AttributeSet set) {

        this.setZailtasuna(ZailtasunMota.ERRAZA);

        atzeko_planoa = new RectF(0,0, 10, 10);
        atzeko_pintzela = new Paint();
        gelaxka_laukia = new RectF(0,0, 10, 10);
        gelaxka_pintzela = new Paint();
        marra_zabalera = 10f;
        marra = new RectF(0,0, 10, 10);
        marra_pintzela = new Paint();
        marra_kolorea = Color.parseColor("#F69364");
        jokalari_pintzela = new Paint();
        jokalari_kolorea = Color.parseColor("#9C27B0");
        atzekalde_kolorea = Color.parseColor("#260945");


        if(null != set) {
            TypedArray a = getContext().getTheme().obtainStyledAttributes(
                    set,
                    R.styleable.LabirintuaView,
                    0, 0);

            try {
                this.marra_kolorea = a.getColor(R.styleable.LabirintuaView_marra_kolorea, Color.DKGRAY);
                this.marra_pintzela.setColor(marra_kolorea);
                this.jokalari_kolorea = a.getColor(R.styleable.LabirintuaView_jokalari_kolorea, Color.DKGRAY);
                this.jokalari_pintzela.setColor(jokalari_kolorea);
                this.atzekalde_kolorea = a.getColor(R.styleable.LabirintuaView_atzekaldea_kolorea, Color.LTGRAY);
                int zailtasuna = a.getInt(R.styleable.LabirintuaView_zailtasuna, ZailtasunMota.ERRAZA.getID());
                ZailtasunMota zm = ZailtasunMota.fromInteger(zailtasuna);
                this.setZailtasuna(zm);

            } finally {
                a.recycle();
            }
        }


    }

    private void marrazketa_parametroak_kalkulatu(Canvas canvas)
    {
        int height = canvas.getHeight();
        int width = canvas.getWidth();
        int neurri_estuena = Math.min(height, width);

        if (0 == getZailtasuna().compareTo(ZailtasunMota.ERRAZA))
        {
            this.tartea = (neurri_estuena / 8);
        }
        else if (0 == getZailtasuna().compareTo(ZailtasunMota.ARRUNTA))
        {
            this.tartea = (neurri_estuena / 12);
        }
        else if (0 == getZailtasuna().compareTo(ZailtasunMota.ZAILA))
        {
            this.tartea = (neurri_estuena / 16);
        }
        else if (0 == getZailtasuna().compareTo(ZailtasunMota.OSO_ZAILA))
        {
            this.tartea = (neurri_estuena / 20);
        }
        else if (0 == getZailtasuna().compareTo(ZailtasunMota.ZORATZEKOA))
        {
            this.tartea = (neurri_estuena / 24);
        }
        else if (0 == getZailtasuna().compareTo(ZailtasunMota.EMANTIROBAT))
        {
            this.tartea = (neurri_estuena / 32);
        }

        if (labirintua == null)
        {
            this.sortzaile = new LabirintuSortzailea(zabalera, altuera);
            this.labirintua = this.sortzaile.getLabirintua();
            this.jokalaria_x = this.sortzaile.get_X_IRTEERA();
            this.jokalaria_y = this.sortzaile.get_Y_IRTEERA();
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {

        // Lehenik eta behin, marrazketa parametroak kalkulatu
        marrazketa_parametroak_kalkulatu(canvas);

        // Marrazteko objektuak
        atzeko_pintzela = new Paint(Paint.ANTI_ALIAS_FLAG);
        atzeko_pintzela.setColor(this.atzekalde_kolorea);
        marra_pintzela = new Paint(Paint.ANTI_ALIAS_FLAG);
        marra_pintzela.setColor(this.marra_kolorea);
        marra_pintzela.setStrokeWidth(this.lapitzlodiera);
        gelaxka_pintzela = new Paint(Paint.ANTI_ALIAS_FLAG);
        gelaxka_pintzela.setColor(Color.parseColor("#CCCCCC"));
        jokalari_pintzela = new Paint(Paint.ANTI_ALIAS_FLAG);
        jokalari_pintzela.setColor(this.jokalari_kolorea);


        // Atzeko plano osoa garbitu
        atzeko_planoa = new RectF(0, 0, canvas.getWidth(), canvas.getHeight());
        canvas.drawRect(atzeko_planoa, atzeko_pintzela);

        // Gelaxkak banan banan marraztuko ditugu
        for (int y=0; y<this.altuera; y++)
        {
            for (int x=0; x<this.zabalera; x++)
            {
                // IRTEERA gelaxka bada
                if (this.labirintua[x][y].getMota() == GelaxkaMota.IRTEERA)
                {
                    gelaxka_pintzela.setColor(Color.RED);
                    gelaxka_laukia = new RectF(x * this.tartea,
                            y * this.tartea,
                            (x+1) * this.tartea,
                            (y+1) * this.tartea);
                    canvas.drawRect(gelaxka_laukia, gelaxka_pintzela);
                }

                // HELMUGA gelaxka bada
                if (this.labirintua[x][y].getMota() == GelaxkaMota.HELMUGA)
                {
                    gelaxka_pintzela.setColor(Color.GREEN);
                    gelaxka_laukia = new RectF(x * this.tartea,
                            y * this.tartea,
                            (x+1) * this.tartea,
                            (y+1) * this.tartea);
                    canvas.drawRect(gelaxka_laukia, gelaxka_pintzela);
                }

                // SOLUZIOA gelaxka bada
                if (this.labirintua[x][y].getMota() == GelaxkaMota.SOLUZIOA)
                {
                    // Atzekalde zuria
                    gelaxka_pintzela.setColor(this.atzekalde_kolorea);
                    gelaxka_laukia = new RectF(x * this.tartea,
                            y * this.tartea,
                            (x+1) * this.tartea,
                            (y+1) * this.tartea);
                    canvas.drawRect(gelaxka_laukia, gelaxka_pintzela);

                    // Erdian urdina
                    gelaxka_pintzela.setColor(Color.BLUE);
                    gelaxka_laukia = new RectF(x * this.tartea + this.tartea/3,
                            y * this.tartea + this.tartea/3,
                            x * this.tartea + this.tartea*2/3,
                            y * this.tartea + this.tartea*2/3);
                    canvas.drawRect(gelaxka_laukia, gelaxka_pintzela);

                    // Iparraldeko pareta libre badago eta iparraldeko gelaxka ere soluzioduna baldin bada, urdinez margotu

                    if (!this.labirintua[x][y].getIparralde_pareta())
                    {
                        if (this.labirintua[x][y-1].getMota() == GelaxkaMota.SOLUZIOA ||
                                this.labirintua[x][y-1].getMota() == GelaxkaMota.IRTEERA ||
                                this.labirintua[x][y-1].getMota() == GelaxkaMota.HELMUGA)
                        {
                            gelaxka_pintzela.setColor(Color.BLUE);
                            gelaxka_laukia = new RectF(x * this.tartea + this.tartea/3,
                                    y * this.tartea,
                                    x * this.tartea + this.tartea*2/3,
                                    y * this.tartea + this.tartea/3);
                            canvas.drawRect(gelaxka_laukia, gelaxka_pintzela);
                        }
                    }

                    // Ekialdeko pareta libre badago eta ekialdeko gelaxka ere soluzioduna baldin bada, urdinez margotu

                    if (!this.labirintua[x][y].getEkialde_pareta())
                    {
                        if (this.labirintua[x+1][y].getMota() == GelaxkaMota.SOLUZIOA ||
                                this.labirintua[x+1][y].getMota() == GelaxkaMota.IRTEERA ||
                                this.labirintua[x+1][y].getMota() == GelaxkaMota.HELMUGA)
                        {
                            gelaxka_pintzela.setColor(Color.BLUE);
                            gelaxka_laukia = new RectF(x * this.tartea + this.tartea*2/3,
                                    y * this.tartea + this.tartea/3,
                                    x * this.tartea + this.tartea,
                                    y * this.tartea + this.tartea*2/3);
                            canvas.drawRect(gelaxka_laukia, gelaxka_pintzela);
                        }
                    }

                    // Hegoaldeko pareta libre badago eta hegoaldeko gelaxka ere soluzioduna baldin bada, urdinez margotu
                    if (!this.labirintua[x][y].getHegoalde_pareta())
                    {
                        if (this.labirintua[x][y+1].getMota() == GelaxkaMota.SOLUZIOA ||
                                this.labirintua[x][y+1].getMota() == GelaxkaMota.IRTEERA ||
                                this.labirintua[x][y+1].getMota() == GelaxkaMota.HELMUGA)
                        {
                            gelaxka_pintzela.setColor(Color.BLUE);
                            gelaxka_laukia = new RectF(x * this.tartea + this.tartea/3,
                                    y * this.tartea + this.tartea*2/3,
                                    x * this.tartea + this.tartea*2/3,
                                    y * this.tartea + this.tartea);
                            canvas.drawRect(gelaxka_laukia, gelaxka_pintzela);
                        }
                    }

                    // Mendebaldeko pareta libre badago eta mendebaldeko gelaxka ere soluzioduna baldin bada, urdinez margotu
                    if (!this.labirintua[x][y].getMendebalde_pareta())
                    {
                        if (this.labirintua[x-1][y].getMota() == GelaxkaMota.SOLUZIOA ||
                                this.labirintua[x-1][y].getMota() == GelaxkaMota.IRTEERA ||
                                this.labirintua[x-1][y].getMota() == GelaxkaMota.HELMUGA)
                        {
                            gelaxka_pintzela.setColor(Color.BLUE);
                           gelaxka_laukia = new RectF(x * this.tartea,
                                    y * this.tartea + this.tartea/3,
                                    x * this.tartea + this.tartea/3,
                                    y * this.tartea + this.tartea*2/3);
                            canvas.drawRect(gelaxka_laukia, gelaxka_pintzela);
                        }
                    }

                }

                // BIDEA gelaxka bada
                if (this.labirintua[x][y].getMota() == GelaxkaMota.BIDEA)
                {
                    gelaxka_pintzela.setColor(this.atzekalde_kolorea);
                    gelaxka_laukia = new RectF(x * this.tartea,
                            y * this.tartea,
                            (x+1) * this.tartea,
                            (y+1) * this.tartea);
                    canvas.drawRect(gelaxka_laukia, gelaxka_pintzela);
                }

                //Iparraldeko pareta
                if (this.labirintua[x][y].getIparralde_pareta())
                {
                    canvas.drawLine(x * this.tartea,
                            y * this.tartea,
                            (x+1) * this.tartea,
                            y * this.tartea,
                            marra_pintzela);
                }
                //Ekialdeko pareta
                if (this.labirintua[x][y].getEkialde_pareta())
                {
                    canvas.drawLine((x+1) * this.tartea,
                            y * this.tartea,
                            (x+1) * this.tartea,
                            (y+1) * this.tartea,
                            marra_pintzela);
                }
                //Hegoaldeko pareta
                if (this.labirintua[x][y].getHegoalde_pareta())
                {
                    canvas.drawLine(x * this.tartea,
                            (y+1) * this.tartea,
                            (x+1) * this.tartea,
                            (y+1) * this.tartea,
                            marra_pintzela);
                }
                //Mendebaldeko pareta
                if (this.labirintua[x][y].getMendebalde_pareta())
                {
                    canvas.drawLine(x * this.tartea,
                            y * this.tartea,
                            x * this.tartea,
                            (y+1) * this.tartea,
                            marra_pintzela);
                }

            }
        }

        // Jokalaria marraztu
        float jokalari_draw_x = jokalaria_x * this.tartea + this.tartea/2;
        float jokalari_draw_y = jokalaria_y * this.tartea + this.tartea/2;
        float jokalari_draw_radius = this.tartea/3;
        canvas.drawCircle(jokalari_draw_x, jokalari_draw_y, jokalari_draw_radius, jokalari_pintzela);
    }

    public void labirintu_berria()
    {
        this.sortzaile = new LabirintuSortzailea(zabalera, altuera);
        this.labirintua = this.sortzaile.getLabirintua();
        this.jokalaria_x = this.sortzaile.get_X_IRTEERA();
        this.jokalaria_y = this.sortzaile.get_Y_IRTEERA();

        postInvalidate();
    }

    public void bidea_bilatu()
    {
        LabirintukoBideBilatzailea.LabirintukoBideBilatzaileaHasieratu(zabalera, altuera, labirintua);
        LabirintukoBideBilatzailea.labirintuaPrestatu();
        LabirintukoBideBilatzailea.bideZuzenaBilatuRekurtsiboki(LabirintukoBideBilatzailea.irteeraX, LabirintukoBideBilatzailea.irteeraY, "K");

        postInvalidate();
    }

    public void labirintua_garbitu()
    {
        LabirintukoBideBilatzailea.labirintuaGarbitu();
        postInvalidate();
    }

    public boolean mugitu_jokalaria(Norabidea norabidea)
    {
        boolean mugitu_da = false;

        switch (norabidea.getID())
        {
            case 1: // IPARRALDERA
                if (!labirintua[jokalaria_x][jokalaria_y].getIparralde_pareta()) // Pareta falta da
                {
                    jokalaria_y = jokalaria_y - 1;
                    mugitu_da = true;
                }
                break;
            case 2: // EKIALDERA
                if (!labirintua[jokalaria_x][jokalaria_y].getEkialde_pareta()) // Pareta falta da
                {
                    jokalaria_x = jokalaria_x + 1;
                    mugitu_da = true;
                }
                break;
            case 3: // HEGOALDERA
                if (!labirintua[jokalaria_x][jokalaria_y].getHegoalde_pareta()) // Pareta falta da
                {
                    jokalaria_y = jokalaria_y + 1;
                    mugitu_da = true;
                }
                break;
            case 4: // MENDEBALDERA
                if (!labirintua[jokalaria_x][jokalaria_y].getMendebalde_pareta()) // Pareta falta da
                {
                    jokalaria_x = jokalaria_x - 1;
                    mugitu_da = true;
                }
                break;
        }

        postInvalidate();
        jokalaria_helmugara_heldu_al_da();
        return mugitu_da;
    }

    private void jokalaria_helmugara_heldu_al_da()
    {
        if (labirintua[jokalaria_x][jokalaria_y].getMota() == GelaxkaMota.HELMUGA)
        {
            if (iLabirintuaListener != null)
            {
                iLabirintuaListener.jokoa_irabazi_duzu();
            }
        }
    }

}
