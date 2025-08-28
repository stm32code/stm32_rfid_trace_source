package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class GoodsOut {
    /*出箱id*/
    private Integer outId;
    public Integer getOutId(){
        return outId;
    }
    public void setOutId(Integer outId){
        this.outId = outId;
    }

    /*商品出箱id*/
    private Goods outGoodsRfid;
    public Goods getOutGoodsRfid() {
        return outGoodsRfid;
    }
    public void setOutGoodsRfid(Goods outGoodsRfid) {
        this.outGoodsRfid = outGoodsRfid;
    }

    /*出箱数量*/
    @NotNull(message="必须输入出箱数量")
    private Integer outNumb;
    public Integer getOutNumb() {
        return outNumb;
    }
    public void setOutNumb(Integer outNumb) {
        this.outNumb = outNumb;
    }

    /*出箱时间*/
    @NotEmpty(message="出箱时间不能为空")
    private String outTime;
    public String getOutTime() {
        return outTime;
    }
    public void setOutTime(String outTime) {
        this.outTime = outTime;
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
    	JSONObject jsonGoodsOut=new JSONObject(); 
		jsonGoodsOut.accumulate("outId", this.getOutId());
		jsonGoodsOut.accumulate("outGoodsRfid", this.getOutGoodsRfid().getGoodsName());
		jsonGoodsOut.accumulate("outGoodsRfidPri", this.getOutGoodsRfid().getGoodsId());
		jsonGoodsOut.accumulate("outNumb", this.getOutNumb());
		jsonGoodsOut.accumulate("outTime", this.getOutTime().length()>19?this.getOutTime().substring(0,19):this.getOutTime());
		jsonGoodsOut.accumulate("goodsRfid", this.getGoodsRfid());
		return jsonGoodsOut;
    }}