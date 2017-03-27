package com.atguigu.cpes.bean;

public class Certtype {
    private Integer id;
    private String acctype;
    private Integer certid;

    public void setId(Integer id){
        this.id = id;
    }
    public Integer getId(){
        return this.id;
    }

    public void setAcctype(String acctype){
        this.acctype = acctype;
    }
    public String getAcctype(){
        return this.acctype;
    }

    public void setCertid(Integer certid){
        this.certid = certid;
    }
    public Integer getCertid(){
        return this.certid;
    }

}