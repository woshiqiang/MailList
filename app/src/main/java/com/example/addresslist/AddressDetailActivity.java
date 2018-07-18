package com.example.addresslist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 通讯录详情
 */
public class AddressDetailActivity extends AppCompatActivity {

    /*通讯录id*/
    private int addressId;
    /*返回按钮*/
    private TextView tvBack;
    /*姓名输入框*/
    private EditText etName;
    /*手机号输入框*/
    private EditText etPhone;
    /*保存按钮*/
    private Button btnSave;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


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
        btnSave = (Button) findViewById(R.id.btn_save);

        addressId = getIntent().getIntExtra("id", -1);
        String name = getIntent().getStringExtra("name");
        String phone = getIntent().getStringExtra("phone");
        if (name != null) {
            etName.setText(name);
        }
        if (phone != null) {
            etPhone.setText(phone);
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
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
        AddressBean noteBean = new AddressBean();
        noteBean.setName(name);
        noteBean.setPhone(phone);
        noteBean.setId(addressId);
        //更新通讯录
        DbHelper.getInstance(this)
                .updateAddress(noteBean);
        Toast.makeText(AddressDetailActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
    }


}
