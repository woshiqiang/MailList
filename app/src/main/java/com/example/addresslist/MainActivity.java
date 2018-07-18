package com.example.addresslist;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends Activity {

    private ListView lv;
    private List<AddressBean> notes;
    private MyAdapter adapter;
    private TextView tvAdd;
    private Button btnEdit, btnCheckAll;//编辑,全选
    private boolean isShowSelect;
    private TextView tvDelete;//删除按钮
    private boolean isSelectAll = true;//true ：全选 false 全不选

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //编辑
        btnEdit = (Button) findViewById(R.id.btn_edit);
        //删除按钮
        tvDelete = (TextView) findViewById(R.id.tv_delete);
        tvDelete.setVisibility(View.GONE);
        //全选
        btnCheckAll = (Button) findViewById(R.id.btn_check_all);
        btnCheckAll.setVisibility(View.GONE);

        //列表
        lv = (ListView) findViewById(R.id.lv);
        //添加按钮
        tvAdd = (TextView) findViewById(R.id.tv_add);
        //查询数据库数据
        notes = DbHelper.getInstance(this).getAllAddress();
        //列表数据填充
        adapter = new MyAdapter(this, notes);
        lv.setAdapter(adapter);
        //列表点击，进入详情
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, AddressDetailActivity.class);
                intent.putExtra("id", notes.get(position).getId());
                intent.putExtra("name", notes.get(position).getName());
                intent.putExtra("phone", notes.get(position).getPhone());
                startActivity(intent);
            }
        });


        //长按删除
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                //列表对话框
                final String[] items = {"删除"};
                new AlertDialog.Builder(MainActivity.this)
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        //确认对话框
                                        new AlertDialog.Builder(MainActivity.this)
                                                .setMessage("确定删除该条通讯录吗？")
                                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        //删除通讯录
                                                        int noteId = notes.get(position).getId();
                                                        DbHelper.getInstance(MainActivity.this).deleteAddress(noteId);
                                                        //刷新列表
                                                        notes = DbHelper.getInstance(MainActivity.this).getAllAddress();
                                                        adapter.updateAll(notes);
                                                    }
                                                })
                                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                    }
                                                })
                                                .show();
                                        break;
                                }
                            }
                        })
                        .show();
                return true;
            }
        });

        //添加按钮点击
        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddAddressActivity.class);
                startActivity(intent);
            }
        });

        //编辑
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init();
            }
        });

        //全选
        btnCheckAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSelectAll) { //全选
                    for (AddressBean b : notes) {
                        b.setChecked(true);
                    }
                } else { //全不选
                    for (AddressBean b : notes) {
                        b.setChecked(false);
                    }
                }
                isSelectAll = !isSelectAll;
                if (!isSelectAll) {
                    btnCheckAll.setText("全不选");
                } else {
                    btnCheckAll.setText("全选");
                }
                adapter.notifyDataSetChanged();
            }
        });

        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //拼接ids
                String condition = "";
                for (AddressBean b : notes) {
                    if (b.isChecked()) {
                        condition += b.getId() + ",";
                    }
                }
                if (TextUtils.isEmpty(condition)) {
                    Toast.makeText(MainActivity.this, "未选中", Toast.LENGTH_SHORT).show();
                    return;
                }
                //去掉结尾的逗号
                if (condition.endsWith(",")) {
                    condition = condition.substring(0, condition.length() - 1);
                }

                DbHelper.getInstance(MainActivity.this).deleteAddressByIds(condition);
                //不显示勾选框
                adapter.setShowSelect(false);
                btnEdit.setText("编辑");
                //删除后隐藏
                tvDelete.setVisibility(View.GONE);
                btnCheckAll.setVisibility(View.GONE);
                isSelectAll = true;
                //刷新列表
                notes.clear();
                adapter.setShowSelect(false);
                notes.addAll(DbHelper.getInstance(MainActivity.this).getAllAddress());
                adapter.updateAll(notes);
            }
        });

    }

    public void init() {
        //默认全不勾选
        for (AddressBean b : notes) {
            b.setChecked(false);
        }
        isSelectAll = true;

        isShowSelect = !isShowSelect;
        if (isShowSelect) {
            btnEdit.setText("取消");
            tvAdd.setVisibility(View.GONE);
            tvDelete.setVisibility(View.VISIBLE);
            btnCheckAll.setVisibility(View.VISIBLE);
        } else {
            btnEdit.setText("编辑");
            tvAdd.setVisibility(View.VISIBLE);
            tvDelete.setVisibility(View.GONE);
            btnCheckAll.setVisibility(View.GONE);
        }
        //显示勾选框
        adapter.setShowSelect(isShowSelect);
    }


    @Override
    protected void onResume() {
        super.onResume();
        notes = DbHelper.getInstance(this).getAllAddress();
        adapter.updateAll(notes);
    }


}
