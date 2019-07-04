package com.example.foodyappversion2.control;

import com.example.foodyappversion2.model.ThanhVienModel;

public class  ThanhVienModelControler {

    public static void setThanhVienModel(String name, String hinhanh, String uid)
    {
        ThanhVienModel thanhVienMode2 = new ThanhVienModel();
        ThanhVienModel thanhVienModel = new ThanhVienModel(name,hinhanh,uid);
        thanhVienMode2.setThemThanhVienModel(thanhVienModel);
    }
}
