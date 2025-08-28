package com.chengxusheji.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
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
import com.chengxusheji.service.GoodsTypeService;
import com.chengxusheji.po.GoodsType;

//GoodsType管理控制层
@Controller
@RequestMapping("/GoodsType")
public class GoodsTypeController extends BaseController {

    /*业务层对象*/
    @Resource GoodsTypeService goodsTypeService;

	@InitBinder("goodsType")
	public void initBinderGoodsType(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("goodsType.");
	}
	/*跳转到添加GoodsType视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new GoodsType());
		return "GoodsType_add";
	}

	/*客户端ajax方式提交添加商品类型信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated GoodsType goodsType, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
        goodsTypeService.addGoodsType(goodsType);
        message = "商品类型添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	/*ajax方式按照查询条件分页查询商品类型信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(String goodTypeName,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (goodTypeName == null) goodTypeName = "";
		if(rows != 0)goodsTypeService.setRows(rows);
		List<GoodsType> goodsTypeList = goodsTypeService.queryGoodsType(goodTypeName, page);
	    /*计算总的页数和总的记录数*/
	    goodsTypeService.queryTotalPageAndRecordNumber(goodTypeName);
	    /*获取到总的页码数目*/
	    int totalPage = goodsTypeService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = goodsTypeService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(GoodsType goodsType:goodsTypeList) {
			JSONObject jsonGoodsType = goodsType.getJsonObject();
			jsonArray.put(jsonGoodsType);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询商品类型信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<GoodsType> goodsTypeList = goodsTypeService.queryAllGoodsType();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(GoodsType goodsType:goodsTypeList) {
			JSONObject jsonGoodsType = new JSONObject();
			jsonGoodsType.accumulate("goodTypeId", goodsType.getGoodTypeId());
			jsonGoodsType.accumulate("goodTypeName", goodsType.getGoodTypeName());
			jsonArray.put(jsonGoodsType);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询商品类型信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(String goodTypeName,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (goodTypeName == null) goodTypeName = "";
		List<GoodsType> goodsTypeList = goodsTypeService.queryGoodsType(goodTypeName, currentPage);
	    /*计算总的页数和总的记录数*/
	    goodsTypeService.queryTotalPageAndRecordNumber(goodTypeName);
	    /*获取到总的页码数目*/
	    int totalPage = goodsTypeService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = goodsTypeService.getRecordNumber();
	    request.setAttribute("goodsTypeList",  goodsTypeList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("goodTypeName", goodTypeName);
		return "GoodsType/goodsType_frontquery_result"; 
	}

     /*前台查询GoodsType信息*/
	@RequestMapping(value="/{goodTypeId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer goodTypeId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键goodTypeId获取GoodsType对象*/
        GoodsType goodsType = goodsTypeService.getGoodsType(goodTypeId);

        request.setAttribute("goodsType",  goodsType);
        return "GoodsType/goodsType_frontshow";
	}

	/*ajax方式显示商品类型修改jsp视图页*/
	@RequestMapping(value="/{goodTypeId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer goodTypeId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键goodTypeId获取GoodsType对象*/
        GoodsType goodsType = goodsTypeService.getGoodsType(goodTypeId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonGoodsType = goodsType.getJsonObject();
		out.println(jsonGoodsType.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新商品类型信息*/
	@RequestMapping(value = "/{goodTypeId}/update", method = RequestMethod.POST)
	public void update(@Validated GoodsType goodsType, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		try {
			goodsTypeService.updateGoodsType(goodsType);
			message = "商品类型更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "商品类型更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除商品类型信息*/
	@RequestMapping(value="/{goodTypeId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer goodTypeId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  goodsTypeService.deleteGoodsType(goodTypeId);
	            request.setAttribute("message", "商品类型删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "商品类型删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条商品类型记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String goodTypeIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = goodsTypeService.deleteGoodsTypes(goodTypeIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出商品类型信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(String goodTypeName, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(goodTypeName == null) goodTypeName = "";
        List<GoodsType> goodsTypeList = goodsTypeService.queryGoodsType(goodTypeName);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "GoodsType信息记录"; 
        String[] headers = { "商品类型id","类型名称"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<goodsTypeList.size();i++) {
        	GoodsType goodsType = goodsTypeList.get(i); 
        	dataset.add(new String[]{goodsType.getGoodTypeId() + "",goodsType.getGoodTypeName()});
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
			response.setHeader("Content-disposition","attachment; filename="+"GoodsType.xls");//filename是下载的xls的名，建议最好用英文 
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
