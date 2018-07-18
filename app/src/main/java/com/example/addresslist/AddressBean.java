package com.example.addresslist;

/**
 * 通讯录实体类
 */
public class AddressBean {

    private int id;
    private String name; //姓名
    private String phone; //电话
    private boolean isChecked;//是否选中

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
