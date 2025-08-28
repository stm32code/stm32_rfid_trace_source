package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.GoodsType;

import com.chengxusheji.mapper.GoodsTypeMapper;
@Service
public class GoodsTypeService {

	@Resource GoodsTypeMapper goodsTypeMapper;
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

    /*添加商品类型记录*/
    public void addGoodsType(GoodsType goodsType) throws Exception {
    	goodsTypeMapper.addGoodsType(goodsType);
    }

    /*按照查询条件分页查询商品类型记录*/
    public ArrayList<GoodsType> queryGoodsType(String goodTypeName,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(!goodTypeName.equals("")) where = where + " and t_goodsType.goodTypeName like '%" + goodTypeName + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return goodsTypeMapper.queryGoodsType(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<GoodsType> queryGoodsType(String goodTypeName) throws Exception  { 
     	String where = "where 1=1";
    	if(!goodTypeName.equals("")) where = where + " and t_goodsType.goodTypeName like '%" + goodTypeName + "%'";
    	return goodsTypeMapper.queryGoodsTypeList(where);
    }

    /*查询所有商品类型记录*/
    public ArrayList<GoodsType> queryAllGoodsType()  throws Exception {
        return goodsTypeMapper.queryGoodsTypeList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(String goodTypeName) throws Exception {
     	String where = "where 1=1";
    	if(!goodTypeName.equals("")) where = where + " and t_goodsType.goodTypeName like '%" + goodTypeName + "%'";
        recordNumber = goodsTypeMapper.queryGoodsTypeCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取商品类型记录*/
    public GoodsType getGoodsType(int goodTypeId) throws Exception  {
        GoodsType goodsType = goodsTypeMapper.getGoodsType(goodTypeId);
        return goodsType;
    }

    /*更新商品类型记录*/
    public void updateGoodsType(GoodsType goodsType) throws Exception {
        goodsTypeMapper.updateGoodsType(goodsType);
    }

    /*删除一条商品类型记录*/
    public void deleteGoodsType (int goodTypeId) throws Exception {
        goodsTypeMapper.deleteGoodsType(goodTypeId);
    }

    /*删除多条商品类型信息*/
    public int deleteGoodsTypes (String goodTypeIds) throws Exception {
    	String _goodTypeIds[] = goodTypeIds.split(",");
    	for(String _goodTypeId: _goodTypeIds) {
    		goodsTypeMapper.deleteGoodsType(Integer.parseInt(_goodTypeId));
    	}
    	return _goodTypeIds.length;
    }
}
