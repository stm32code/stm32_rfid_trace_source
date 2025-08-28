package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.GoodsInto;

public interface GoodsIntoMapper {
	/*添加商品入箱信息*/
	public void addGoodsInto(GoodsInto goodsInto) throws Exception;

	/*按照查询条件分页查询商品入箱记录*/
	public ArrayList<GoodsInto> queryGoodsInto(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有商品入箱记录*/
	public ArrayList<GoodsInto> queryGoodsIntoList(@Param("where") String where) throws Exception;

	/*按照查询条件的商品入箱记录数*/
	public int queryGoodsIntoCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条商品入箱记录*/
	public GoodsInto getGoodsInto(int goodsintoId) throws Exception;

	/*更新商品入箱记录*/
	public void updateGoodsInto(GoodsInto goodsInto) throws Exception;

	/*删除商品入箱记录*/
	public void deleteGoodsInto(int goodsintoId) throws Exception;

}
