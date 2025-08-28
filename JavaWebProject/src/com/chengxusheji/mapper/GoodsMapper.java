package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.Goods;

public interface GoodsMapper {
	/*添加商品信息*/
	public void addGoods(Goods goods) throws Exception;

	/*按照查询条件分页查询商品记录*/
	public ArrayList<Goods> queryGoods(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有商品记录*/
	public ArrayList<Goods> queryGoodsList(@Param("where") String where) throws Exception;

	/*按照查询条件的商品记录数*/
	public int queryGoodsCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条商品记录*/
	public Goods getGoods(int goodsId) throws Exception;
	
	/*根据rfid查询某条商品记录*/
	public Goods getGoodsByRfid(String rfid);

	/*更新商品记录*/
	public void updateGoods(Goods goods) throws Exception;

	/*删除商品记录*/
	public void deleteGoods(int goodsId) throws Exception;

	

}
