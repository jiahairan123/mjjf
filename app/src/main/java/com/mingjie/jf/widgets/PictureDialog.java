package com.mingjie.jf.widgets;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.mingjie.jf.R;
import com.mingjie.jf.utils.Utilities;

public class PictureDialog extends Dialog implements View.OnClickListener
{

    private Bitmap btmp = null;
    private ImageView imageView;

    private Context context;

    public PictureDialog(Context context)
    {
        super(context);
        this.context=context;

    }

    public PictureDialog(Context context, int theme)
    {
        super(context, theme);
        this.context=context;
    }

    public PictureDialog(Context context, Bitmap btmp)
    {
        super(context, R.style.dialog);
        this.btmp = btmp;
        this.context=context;


    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.widget_pc_dialog);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
        layoutParams.height= Utilities.getScreenWidth(context);
        layoutParams.width= WindowManager.LayoutParams.MATCH_PARENT;
        dialogWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setAttributes(layoutParams);
        imageView = (ImageView) findViewById(R.id.pc_code);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        imageView.setImageBitmap(btmp);

    }

    @Override
    public void onClick(View view)
    {

    }
}
