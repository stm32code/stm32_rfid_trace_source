package com.chengxusheji.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.chengxusheji.utils.ExportExcelUtil;
import com.chengxusheji.utils.UserException;
import com.chengxusheji.service.GoodsService;
import com.chengxusheji.po.Goods;
import com.chengxusheji.service.GoodsTypeService;
import com.chengxusheji.po.GoodsType;
import com.chengxusheji.service.UserInfoService;
import com.chengxusheji.po.UserInfo;

//Goods管理控制层
@Controller
@RequestMapping("/Goods")
public class GoodsController extends BaseController {

    /*业务层对象*/
    @Resource GoodsService goodsService;

    @Resource GoodsTypeService goodsTypeService;
    @Resource UserInfoService userInfoService;
	@InitBinder("goodTypeObj")
	public void initBindergoodTypeObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("goodTypeObj.");
	}
	@InitBinder("goodsUserObj")
	public void initBindergoodsUserObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("goodsUserObj.");
	}
	@InitBinder("goods")
	public void initBinderGoods(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("goods.");
	}
	/*跳转到添加Goods视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new Goods());
		/*查询所有的GoodsType信息*/
		List<GoodsType> goodsTypeList = goodsTypeService.queryAllGoodsType();
		request.setAttribute("goodsTypeList", goodsTypeList);
		/*查询所有的UserInfo信息*/
		List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
		request.setAttribute("userInfoList", userInfoList);
		return "Goods_add";
	}

	
	@RequestMapping(value="/getRfid",method=RequestMethod.GET)
	public void getRfid(Model model,ServletRequest request,HttpServletResponse response,HttpSession session) throws Exception {
		String user_name = (String)session.getAttribute("user_name");
		boolean success = true; 
		String msg = "操作成功";
		String productNo = "";  //车牌号
		String rfid = "";
		// 获取ServletContext
		ServletContext context = request.getServletContext();
		// 获取全局变量
		rfid = (String) context.getAttribute("rfidId");
		
		if(rfid == null) {
			msg = "获取RFID失败，请刷卡！";
			success = false;
		} else {
			Goods goods = goodsService.getGoodsByRfid(rfid);
			if(goods != null) {
				msg = "你刷的这个卡已经绑定过商品！";
				success = false;
			} 
		}
		
		// 设置全局变量
		context.setAttribute("rfidId", null);	
		response.setContentType("text/json;charset=UTF-8");  
		PrintWriter out = response.getWriter();  
	    //将要被返回到客户端的对象   
	    JSONObject json=new JSONObject();   
	    json.accumulate("success", success);
	    json.accumulate("msg", msg);
	    json.accumulate("rfid", rfid);
	    out.println(json.toString());   
	    out.flush();  
	    out.close();  
	}
	
	
	/*客户端ajax方式提交添加商品信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated Goods goods, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
        goodsService.addGoods(goods);
        message = "商品添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	
	/*客户端ajax方式提交添加商品信息*/
	@RequestMapping(value = "/frontAdd", method = RequestMethod.POST)
	public void frontAdd(@Validated Goods goods, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
		String user_name = (String)session.getAttribute("user_name");
		UserInfo user = userInfoService.getUserInfo(user_name);
		goods.setGoodsUserObj(user);
        goodsService.addGoods(goods);
        message = "商品添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	
	
	
	/*ajax方式按照查询条件分页查询商品信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(@ModelAttribute("goodTypeObj") GoodsType goodTypeObj,String goodsName,String goodsRfid,String goodsDescribe,String goodIntoTIme,@ModelAttribute("goodsUserObj") UserInfo goodsUserObj,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (goodsName == null) goodsName = "";
		if (goodsRfid == null) goodsRfid = "";
		if (goodsDescribe == null) goodsDescribe = "";
		if (goodIntoTIme == null) goodIntoTIme = "";
		if(rows != 0)goodsService.setRows(rows);
		List<Goods> goodsList = goodsService.queryGoods(goodTypeObj, goodsName, goodsRfid, goodsDescribe, goodIntoTIme, goodsUserObj, page);
	    /*计算总的页数和总的记录数*/
	    goodsService.queryTotalPageAndRecordNumber(goodTypeObj, goodsName, goodsRfid, goodsDescribe, goodIntoTIme, goodsUserObj);
	    /*获取到总的页码数目*/
	    int totalPage = goodsService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = goodsService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(Goods goods:goodsList) {
			JSONObject jsonGoods = goods.getJsonObject();
			jsonArray.put(jsonGoods);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询商品信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<Goods> goodsList = goodsService.queryAllGoods();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(Goods goods:goodsList) {
			JSONObject jsonGoods = new JSONObject();
			jsonGoods.accumulate("goodsId", goods.getGoodsId());
			jsonGoods.accumulate("goodsName", goods.getGoodsName());
			jsonArray.put(jsonGoods);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询商品信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(@ModelAttribute("goodTypeObj") GoodsType goodTypeObj,String goodsName,String goodsRfid,String goodsDescribe,String goodIntoTIme,@ModelAttribute("goodsUserObj") UserInfo goodsUserObj,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (goodsName == null) goodsName = "";
		if (goodsRfid == null) goodsRfid = "";
		if (goodsDescribe == null) goodsDescribe = "";
		if (goodIntoTIme == null) goodIntoTIme = "";
		List<Goods> goodsList = goodsService.queryGoods(goodTypeObj, goodsName, goodsRfid, goodsDescribe, goodIntoTIme, goodsUserObj, currentPage);
	    /*计算总的页数和总的记录数*/
	    goodsService.queryTotalPageAndRecordNumber(goodTypeObj, goodsName, goodsRfid, goodsDescribe, goodIntoTIme, goodsUserObj);
	    /*获取到总的页码数目*/
	    int totalPage = goodsService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = goodsService.getRecordNumber();
	    request.setAttribute("goodsList",  goodsList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("goodTypeObj", goodTypeObj);
	    request.setAttribute("goodsName", goodsName);
	    request.setAttribute("goodsRfid", goodsRfid);
	    request.setAttribute("goodsDescribe", goodsDescribe);
	    request.setAttribute("goodIntoTIme", goodIntoTIme);
	    request.setAttribute("goodsUserObj", goodsUserObj);
	    List<GoodsType> goodsTypeList = goodsTypeService.queryAllGoodsType();
	    request.setAttribute("goodsTypeList", goodsTypeList);
	    List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
	    request.setAttribute("userInfoList", userInfoList);
		return "Goods/goods_frontquery_result"; 
	}

	
	/*前台按照查询条件分页查询商品信息*/
	@RequestMapping(value = { "/userFrontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String userFrontlist(@ModelAttribute("goodTypeObj") GoodsType goodTypeObj,String goodsName,String goodsRfid,String goodsDescribe,String goodIntoTIme,@ModelAttribute("goodsUserObj") UserInfo goodsUserObj,Integer currentPage, Model model, HttpServletRequest request,HttpSession session) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (goodsName == null) goodsName = "";
		if (goodsRfid == null) goodsRfid = "";
		if (goodsDescribe == null) goodsDescribe = "";
		if (goodIntoTIme == null) goodIntoTIme = "";
		String user_name = (String)session.getAttribute("user_name");
		goodsUserObj = new UserInfo();
		goodsUserObj.setUser_name(user_name);
		List<Goods> goodsList = goodsService.queryGoods(goodTypeObj, goodsName, goodsRfid, goodsDescribe, goodIntoTIme, goodsUserObj, currentPage);
	    /*计算总的页数和总的记录数*/
	    goodsService.queryTotalPageAndRecordNumber(goodTypeObj, goodsName, goodsRfid, goodsDescribe, goodIntoTIme, goodsUserObj);
	    /*获取到总的页码数目*/
	    int totalPage = goodsService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = goodsService.getRecordNumber();
	    request.setAttribute("goodsList",  goodsList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("goodTypeObj", goodTypeObj);
	    request.setAttribute("goodsName", goodsName);
	    request.setAttribute("goodsRfid", goodsRfid);
	    request.setAttribute("goodsDescribe", goodsDescribe);
	    request.setAttribute("goodIntoTIme", goodIntoTIme);
	    request.setAttribute("goodsUserObj", goodsUserObj);
	    List<GoodsType> goodsTypeList = goodsTypeService.queryAllGoodsType();
	    request.setAttribute("goodsTypeList", goodsTypeList);
	    List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
	    request.setAttribute("userInfoList", userInfoList);
		return "Goods/goods_userFrontquery_result"; 
	}
	
	
	
     /*前台查询Goods信息*/
	@RequestMapping(value="/{goodsId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer goodsId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键goodsId获取Goods对象*/
        Goods goods = goodsService.getGoods(goodsId);

        List<GoodsType> goodsTypeList = goodsTypeService.queryAllGoodsType();
        request.setAttribute("goodsTypeList", goodsTypeList);
        List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
        request.setAttribute("userInfoList", userInfoList);
        request.setAttribute("goods",  goods);
        return "Goods/goods_frontshow";
	}

	/*ajax方式显示商品修改jsp视图页*/
	@RequestMapping(value="/{goodsId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer goodsId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键goodsId获取Goods对象*/
        Goods goods = goodsService.getGoods(goodsId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonGoods = goods.getJsonObject();
		out.println(jsonGoods.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新商品信息*/
	@RequestMapping(value = "/{goodsId}/update", method = RequestMethod.POST)
	public void update(@Validated Goods goods, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		try {
			goodsService.updateGoods(goods);
			message = "商品更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "商品更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除商品信息*/
	@RequestMapping(value="/{goodsId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer goodsId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  goodsService.deleteGoods(goodsId);
	            request.setAttribute("message", "商品删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "商品删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条商品记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String goodsIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = goodsService.deleteGoodss(goodsIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出商品信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(@ModelAttribute("goodTypeObj") GoodsType goodTypeObj,String goodsName,String goodsRfid,String goodsDescribe,String goodIntoTIme,@ModelAttribute("goodsUserObj") UserInfo goodsUserObj, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(goodsName == null) goodsName = "";
        if(goodsRfid == null) goodsRfid = "";
        if(goodsDescribe == null) goodsDescribe = "";
        if(goodIntoTIme == null) goodIntoTIme = "";
        List<Goods> goodsList = goodsService.queryGoods(goodTypeObj,goodsName,goodsRfid,goodsDescribe,goodIntoTIme,goodsUserObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "Goods信息记录"; 
        String[] headers = { "商品id","商品类型","商品名称","商品rfid","保质期","商品描述","生产时间","仓库数量","所属用户3"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<goodsList.size();i++) {
        	Goods goods = goodsList.get(i); 
        	dataset.add(new String[]{goods.getGoodsId() + "",goods.getGoodTypeObj().getGoodTypeName(),goods.getGoodsName(),goods.getGoodsRfid(),goods.getGoodPrice() + "",goods.getGoodsDescribe(),goods.getGoodIntoTIme(),goods.getGoodsNumb() + "",goods.getGoodsUserObj().getName()});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		OutputStream out = null;//创建一个输出流对象 
		try { 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Goods.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
			String rootPath = request.getSession().getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,_title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
    }
}
