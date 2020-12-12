package com.unieibar.uni_58_labirintua.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.unieibar.uni_58_labirintua.R;
import com.unieibar.uni_58_labirintua.logika.GeziakViewListener;
import com.unieibar.uni_58_labirintua.logika.Norabidea;

public class GeziakView extends GridLayout{

    // =========================================
    // Propietate pribatuak
    // =========================================

    private int atzekaldeaColor;
    private Paint atzekaldeaPaint;
    private int geziaColor;
    private int geziaColorPush;
    private Paint geziaPaint;

    private ImageView img_gora, img_behera, img_ezkerrera, img_eskubira;
    private GeziakViewListener geziakViewListener;

    // =========================================
    // Hasieratzea
    // =========================================

    public GeziakView(Context context) {
        super(context);
        initGeziakView(null);
        initBarneBistak();
    }

    public GeziakView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initGeziakView(attrs);
        initBarneBistak();
    }

    public GeziakView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGeziakView(attrs);
        initBarneBistak();
    }

    public GeziakView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initGeziakView(attrs);
        initBarneBistak();
    }

    private void initGeziakView(@Nullable AttributeSet attrs) {

        if (null == attrs)
        {
            this.atzekaldeaColor = Color.LTGRAY;
            this.geziaColor = Color.DKGRAY;
            this.geziaColorPush = Color.RED;
        }
        else
        {
            TypedArray a = getContext().getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.GeziakView,
                    0, 0);

            try {
                this.atzekaldeaColor = a.getColor(R.styleable.GeziakView_atzekaldea_color, Color.LTGRAY);
                this.geziaColor = a.getColor(R.styleable.GeziakView_gezia_color, Color.DKGRAY);
                this.geziaColorPush = a.getColor(R.styleable.GeziakView_atzekaldea_color, Color.RED);

            } finally {
                a.recycle();
            }
        }

        setFocusable(true);

        atzekaldeaPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        atzekaldeaPaint.setColor(atzekaldeaColor);

        geziaPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        geziaPaint.setColor(geziaColor);

    }

    private void initBarneBistak()
    {
        // 3x3 erako "taula" osatu
        this.setColumnCount(3);
        this.setRowCount(3);

        Context context = getContext();

        // GORA botoia
        img_gora = new ImageView(context);
        img_gora.setImageResource(R.drawable.ic_gora);
        LayoutParams img_gora_params = new LayoutParams();
        img_gora_params.columnSpec = GridLayout.spec(1, 1);
        img_gora_params.rowSpec = GridLayout.spec(0, 1);
        img_gora.setLayoutParams(img_gora_params);
        this.addView(img_gora);

        // BEHERA botoia
        img_behera = new ImageView(context);
        img_behera.setImageResource(R.drawable.ic_behera);
        LayoutParams img_behera_params = new LayoutParams();
        img_behera_params.columnSpec = GridLayout.spec(1, 1);
        img_behera_params.rowSpec = GridLayout.spec(2, 1);
        img_behera.setLayoutParams(img_behera_params);
        this.addView(img_behera);

        // EZKERRERA botoia
        img_ezkerrera = new ImageView(context);
        img_ezkerrera.setImageResource(R.drawable.ic_ezkerrera);
        LayoutParams img_ezkerrera_params = new LayoutParams();
        img_ezkerrera_params.columnSpec = GridLayout.spec(0, 1);
        img_ezkerrera_params.rowSpec = GridLayout.spec(1, 1);
        img_ezkerrera.setLayoutParams(img_ezkerrera_params);
        this.addView(img_ezkerrera);

        // ESKUBIRA botoia
        img_eskubira = new ImageView(context);
        img_eskubira.setImageResource(R.drawable.ic_eskubira);
        LayoutParams img_eskubira_params = new LayoutParams();
        img_eskubira_params.columnSpec = GridLayout.spec(2, 1);
        img_eskubira_params.rowSpec = GridLayout.spec(1, 1);
        img_eskubira.setLayoutParams(img_eskubira_params);
        this.addView(img_eskubira);

        // Botoien jokaera klikatzerakoan
        img_gora.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // Pressed
                    img_gora.setImageResource(R.drawable.ic_gora_berdea);
                    mugitzekoAbisuaPasa(Norabidea.IPARRALDERA);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // Released
                    img_gora.setImageResource(R.drawable.ic_gora);
                }
                return true;
            }
        });

        img_behera.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // Pressed
                    img_behera.setImageResource(R.drawable.ic_behera_berdea);
                    mugitzekoAbisuaPasa(Norabidea.HEGOALDERA);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // Released
                    img_behera.setImageResource(R.drawable.ic_behera);
                }
                return true;
            }
        });

        img_ezkerrera.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // Pressed
                    img_ezkerrera.setImageResource(R.drawable.ic_ezkerrera_berdea);
                    mugitzekoAbisuaPasa(Norabidea.MENDEBALDERA);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // Released
                    img_ezkerrera.setImageResource(R.drawable.ic_ezkerrera);
                }
                return true;
            }
        });

        img_eskubira.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // Pressed
                    img_eskubira.setImageResource(R.drawable.ic_eskubira_berdea);
                    mugitzekoAbisuaPasa(Norabidea.EKIALDERA);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // Released
                    img_eskubira.setImageResource(R.drawable.ic_eskubira);
                }
                return true;
            }
        });
    }


    // =========================================
    // LISTENER-a esleitu eta erabili
    // =========================================

    public void setGeziakViewListener(GeziakViewListener geziakViewListener) {
        this.geziakViewListener = geziakViewListener;
    }

    private void mugitzekoAbisuaPasa(Norabidea norabidea)
    {
        if (null != this.geziakViewListener)
        {
            this.geziakViewListener.onMugitu(norabidea);
        }
    }
}
