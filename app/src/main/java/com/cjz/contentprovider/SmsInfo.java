package com.cjz.contentprovider;

public class SmsInfo {

    private int _id;
    private String address;
    private String body;

    public SmsInfo(int _id,String address,String body){
        this._id=_id;
        this.address=address;
        this.body=body;
    }

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        this._id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
