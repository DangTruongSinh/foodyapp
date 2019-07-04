package com.example.foodyappversion2.control;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.foodyappversion2.control.Interface.ITienIch;
import com.example.foodyappversion2.model.TienIchModel;

import java.util.List;

public class TienIchControler {
    TienIchModel tienIchModel;
    public TienIchControler()
    {
        tienIchModel = new TienIchModel();
    }
    public void getAllTienIchModel(List<String> listHinh, final LinearLayout linearLayout, final Context context)
    {
        ITienIch iTienIch = new ITienIch() {
            @Override
            public void setHinhAnh(Bitmap bitmap) {
                ImageView imageView = new ImageView(context);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(100,100);
                layoutParams.leftMargin = 10;
                imageView.setLayoutParams(layoutParams);
                imageView.setImageBitmap(bitmap);
                linearLayout.addView(imageView);
            }
        };
        tienIchModel.getHinhAnhTienIch(listHinh,iTienIch);
    }
}
