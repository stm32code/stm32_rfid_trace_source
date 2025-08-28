package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class GoodsInto {
    /*商品入箱id*/
    private Integer goodsintoId;
    public Integer getGoodsintoId(){
        return goodsintoId;
    }
    public void setGoodsintoId(Integer goodsintoId){
        this.goodsintoId = goodsintoId;
    }

    /*入箱商品*/
    private Goods goodsIntoObj;
    public Goods getGoodsIntoObj() {
        return goodsIntoObj;
    }
    public void setGoodsIntoObj(Goods goodsIntoObj) {
        this.goodsIntoObj = goodsIntoObj;
    }

    /*入箱数量*/
    @NotNull(message="必须输入入箱数量")
    private Integer intoNumb;
    public Integer getIntoNumb() {
        return intoNumb;
    }
    public void setIntoNumb(Integer intoNumb) {
        this.intoNumb = intoNumb;
    }

    /*入箱时间*/
    @NotEmpty(message="入箱时间不能为空")
    private String intoTIme;
    public String getIntoTIme() {
        return intoTIme;
    }
    public void setIntoTIme(String intoTIme) {
        this.intoTIme = intoTIme;
    }

    /*商品rfid*/
    @NotEmpty(message="商品rfid不能为空")
    private String goodsRfid;
    public String getGoodsRfid() {
        return goodsRfid;
    }
    public void setGoodsRfid(String goodsRfid) {
        this.goodsRfid = goodsRfid;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonGoodsInto=new JSONObject(); 
		jsonGoodsInto.accumulate("goodsintoId", this.getGoodsintoId());
		jsonGoodsInto.accumulate("goodsIntoObj", this.getGoodsIntoObj().getGoodsName());
		jsonGoodsInto.accumulate("goodsIntoObjPri", this.getGoodsIntoObj().getGoodsId());
		jsonGoodsInto.accumulate("intoNumb", this.getIntoNumb());
		jsonGoodsInto.accumulate("intoTIme", this.getIntoTIme().length()>19?this.getIntoTIme().substring(0,19):this.getIntoTIme());
		jsonGoodsInto.accumulate("goodsRfid", this.getGoodsRfid());
		return jsonGoodsInto;
    }}