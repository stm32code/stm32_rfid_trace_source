package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class GoodsType {
    /*商品类型id*/
    private Integer goodTypeId;
    public Integer getGoodTypeId(){
        return goodTypeId;
    }
    public void setGoodTypeId(Integer goodTypeId){
        this.goodTypeId = goodTypeId;
    }

    /*类型名称*/
    @NotEmpty(message="类型名称不能为空")
    private String goodTypeName;
    public String getGoodTypeName() {
        return goodTypeName;
    }
    public void setGoodTypeName(String goodTypeName) {
        this.goodTypeName = goodTypeName;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonGoodsType=new JSONObject(); 
		jsonGoodsType.accumulate("goodTypeId", this.getGoodTypeId());
		jsonGoodsType.accumulate("goodTypeName", this.getGoodTypeName());
		return jsonGoodsType;
    }}