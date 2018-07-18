package com.example.addresslist;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 添加通讯录
 */
public class AddAddressActivity extends Activity {

    /*返回按钮*/
    private TextView tvBack;
    /*姓名输入框*/
    private EditText etName;
    /*手机号输入框*/
    private EditText etPhone;
    /*添加按钮*/
    private Button btnAdd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);


        //返回按钮
        tvBack = findViewById(R.id.tv_back);
        //返回点击
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //关闭页面
                finish();
            }
        });

        etName = (EditText) findViewById(R.id.et_name);
        etPhone = (EditText) findViewById(R.id.et_phone);
        btnAdd = (Button) findViewById(R.id.btn_add);

        //添加按钮点击
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //保存并关闭页面
                saveAddress();
                finish();
            }
        });
    }

    /**
     * 保存通讯录
     */
    private void saveAddress() {
        String name = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        AddressBean addressBean = new AddressBean();
        addressBean.setName(name);
        addressBean.setPhone(phone);
        DbHelper.getInstance(this)
                .saveAddress(addressBean);
        Toast.makeText(AddAddressActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
    }


}
