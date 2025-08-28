package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.GoodsOut;

public interface GoodsOutMapper {
	/*添加商品出箱信息*/
	public void addGoodsOut(GoodsOut goodsOut) throws Exception;

	/*按照查询条件分页查询商品出箱记录*/
	public ArrayList<GoodsOut> queryGoodsOut(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有商品出箱记录*/
	public ArrayList<GoodsOut> queryGoodsOutList(@Param("where") String where) throws Exception;

	/*按照查询条件的商品出箱记录数*/
	public int queryGoodsOutCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条商品出箱记录*/
	public GoodsOut getGoodsOut(int outId) throws Exception;

	/*更新商品出箱记录*/
	public void updateGoodsOut(GoodsOut goodsOut) throws Exception;

	/*删除商品出箱记录*/
	public void deleteGoodsOut(int outId) throws Exception;

}
