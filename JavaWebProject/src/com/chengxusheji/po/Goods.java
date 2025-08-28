package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Goods {
    /*商品id*/
    private Integer goodsId;
    public Integer getGoodsId(){
        return goodsId;
    }
    public void setGoodsId(Integer goodsId){
        this.goodsId = goodsId;
    }

    /*商品类型*/
    private GoodsType goodTypeObj;
    public GoodsType getGoodTypeObj() {
        return goodTypeObj;
    }
    public void setGoodTypeObj(GoodsType goodTypeObj) {
        this.goodTypeObj = goodTypeObj;
    }

    /*商品名称*/
    @NotEmpty(message="商品名称不能为空")
    private String goodsName;
    public String getGoodsName() {
        return goodsName;
    }
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
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

    /*保质期*/
    @NotNull(message="必须输入保质期")
    private Float goodPrice;
    public Float getGoodPrice() {
        return goodPrice;
    }
    public void setGoodPrice(Float goodPrice) {
        this.goodPrice = goodPrice;
    }

    /*商品描述*/
    @NotEmpty(message="商品描述不能为空")
    private String goodsDescribe;
    public String getGoodsDescribe() {
        return goodsDescribe;
    }
    public void setGoodsDescribe(String goodsDescribe) {
        this.goodsDescribe = goodsDescribe;
    }

    /*生产时间*/
    @NotEmpty(message="生产时间不能为空")
    private String goodIntoTIme;
    public String getGoodIntoTIme() {
        return goodIntoTIme;
    }
    public void setGoodIntoTIme(String goodIntoTIme) {
        this.goodIntoTIme = goodIntoTIme;
    }

    /*仓库数量*/
    @NotNull(message="必须输入仓库数量")
    private Integer goodsNumb;
    public Integer getGoodsNumb() {
        return goodsNumb;
    }
    public void setGoodsNumb(Integer goodsNumb) {
        this.goodsNumb = goodsNumb;
    }

    /*所属用户3*/
    private UserInfo goodsUserObj;
    public UserInfo getGoodsUserObj() {
        return goodsUserObj;
    }
    public void setGoodsUserObj(UserInfo goodsUserObj) {
        this.goodsUserObj = goodsUserObj;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonGoods=new JSONObject(); 
		jsonGoods.accumulate("goodsId", this.getGoodsId());
		jsonGoods.accumulate("goodTypeObj", this.getGoodTypeObj().getGoodTypeName());
		jsonGoods.accumulate("goodTypeObjPri", this.getGoodTypeObj().getGoodTypeId());
		jsonGoods.accumulate("goodsName", this.getGoodsName());
		jsonGoods.accumulate("goodsRfid", this.getGoodsRfid());
		jsonGoods.accumulate("goodPrice", this.getGoodPrice());
		jsonGoods.accumulate("goodsDescribe", this.getGoodsDescribe());
		jsonGoods.accumulate("goodIntoTIme", this.getGoodIntoTIme().length()>19?this.getGoodIntoTIme().substring(0,19):this.getGoodIntoTIme());
		jsonGoods.accumulate("goodsNumb", this.getGoodsNumb());
		jsonGoods.accumulate("goodsUserObj", this.getGoodsUserObj().getName());
		jsonGoods.accumulate("goodsUserObjPri", this.getGoodsUserObj().getUser_name());
		return jsonGoods;
    }}