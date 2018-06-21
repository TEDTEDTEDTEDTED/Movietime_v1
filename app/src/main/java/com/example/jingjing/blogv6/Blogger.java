package com.example.jingjing.blogv6;

public class Blogger {
    String name;
    String account;
    String password;
    String atten;
    String fan;
    String money;
    String pic;

    public Blogger(){

    }

    public Blogger(String name, String account, String password,String pic) {
        this.name = name;
        this.account = account;
        this.password = password;
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }

    public String getAtten() {
        return atten;
    }

    public String getFan() {
        return fan;
    }

    public String getMoney() {
        return money;
    }

    public String getPic() {
        return pic;
    }
}


