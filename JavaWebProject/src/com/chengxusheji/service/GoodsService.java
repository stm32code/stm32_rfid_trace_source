package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.GoodsType;
import com.chengxusheji.po.UserInfo;
import com.chengxusheji.po.Goods;

import com.chengxusheji.mapper.GoodsMapper;
@Service
public class GoodsService {

	@Resource GoodsMapper goodsMapper;
    /*每页显示记录数目*/
    private int rows = 10;;
    public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加商品记录*/
    public void addGoods(Goods goods) throws Exception {
    	goodsMapper.addGoods(goods);
    }

    /*按照查询条件分页查询商品记录*/
    public ArrayList<Goods> queryGoods(GoodsType goodTypeObj,String goodsName,String goodsRfid,String goodsDescribe,String goodIntoTIme,UserInfo goodsUserObj,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(null != goodTypeObj && goodTypeObj.getGoodTypeId()!= null && goodTypeObj.getGoodTypeId()!= 0)  where += " and t_goods.goodTypeObj=" + goodTypeObj.getGoodTypeId();
    	if(!goodsName.equals("")) where = where + " and t_goods.goodsName like '%" + goodsName + "%'";
    	if(!goodsRfid.equals("")) where = where + " and t_goods.goodsRfid like '%" + goodsRfid + "%'";
    	if(!goodsDescribe.equals("")) where = where + " and t_goods.goodsDescribe like '%" + goodsDescribe + "%'";
    	if(!goodIntoTIme.equals("")) where = where + " and t_goods.goodIntoTIme like '%" + goodIntoTIme + "%'";
    	if(null != goodsUserObj &&  goodsUserObj.getUser_name() != null  && !goodsUserObj.getUser_name().equals(""))  where += " and t_goods.goodsUserObj='" + goodsUserObj.getUser_name() + "'";
    	int startIndex = (currentPage-1) * this.rows;
    	return goodsMapper.queryGoods(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<Goods> queryGoods(GoodsType goodTypeObj,String goodsName,String goodsRfid,String goodsDescribe,String goodIntoTIme,UserInfo goodsUserObj) throws Exception  { 
     	String where = "where 1=1";
    	if(null != goodTypeObj && goodTypeObj.getGoodTypeId()!= null && goodTypeObj.getGoodTypeId()!= 0)  where += " and t_goods.goodTypeObj=" + goodTypeObj.getGoodTypeId();
    	if(!goodsName.equals("")) where = where + " and t_goods.goodsName like '%" + goodsName + "%'";
    	if(!goodsRfid.equals("")) where = where + " and t_goods.goodsRfid like '%" + goodsRfid + "%'";
    	if(!goodsDescribe.equals("")) where = where + " and t_goods.goodsDescribe like '%" + goodsDescribe + "%'";
    	if(!goodIntoTIme.equals("")) where = where + " and t_goods.goodIntoTIme like '%" + goodIntoTIme + "%'";
    	if(null != goodsUserObj &&  goodsUserObj.getUser_name() != null && !goodsUserObj.getUser_name().equals(""))  where += " and t_goods.goodsUserObj='" + goodsUserObj.getUser_name() + "'";
    	return goodsMapper.queryGoodsList(where);
    }

    /*查询所有商品记录*/
    public ArrayList<Goods> queryAllGoods()  throws Exception {
        return goodsMapper.queryGoodsList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(GoodsType goodTypeObj,String goodsName,String goodsRfid,String goodsDescribe,String goodIntoTIme,UserInfo goodsUserObj) throws Exception {
     	String where = "where 1=1";
    	if(null != goodTypeObj && goodTypeObj.getGoodTypeId()!= null && goodTypeObj.getGoodTypeId()!= 0)  where += " and t_goods.goodTypeObj=" + goodTypeObj.getGoodTypeId();
    	if(!goodsName.equals("")) where = where + " and t_goods.goodsName like '%" + goodsName + "%'";
    	if(!goodsRfid.equals("")) where = where + " and t_goods.goodsRfid like '%" + goodsRfid + "%'";
    	if(!goodsDescribe.equals("")) where = where + " and t_goods.goodsDescribe like '%" + goodsDescribe + "%'";
    	if(!goodIntoTIme.equals("")) where = where + " and t_goods.goodIntoTIme like '%" + goodIntoTIme + "%'";
    	if(null != goodsUserObj &&  goodsUserObj.getUser_name() != null && !goodsUserObj.getUser_name().equals(""))  where += " and t_goods.goodsUserObj='" + goodsUserObj.getUser_name() + "'";
        recordNumber = goodsMapper.queryGoodsCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取商品记录*/
    public Goods getGoods(int goodsId) throws Exception  {
        Goods goods = goodsMapper.getGoods(goodsId);
        return goods;
    }
    
    /*根据rfid获取商品记录*/
    public Goods getGoodsByRfid(String rfid) {
    	Goods goods = goodsMapper.getGoodsByRfid(rfid);
        return goods;
	}

    /*更新商品记录*/
    public void updateGoods(Goods goods) throws Exception {
        goodsMapper.updateGoods(goods);
    }

    /*删除一条商品记录*/
    public void deleteGoods (int goodsId) throws Exception {
        goodsMapper.deleteGoods(goodsId);
    }

    /*删除多条商品信息*/
    public int deleteGoodss (String goodsIds) throws Exception {
    	String _goodsIds[] = goodsIds.split(",");
    	for(String _goodsId: _goodsIds) {
    		goodsMapper.deleteGoods(Integer.parseInt(_goodsId));
    	}
    	return _goodsIds.length;
    }
	
}
