<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.Goods" %>
<%@ page import="com.chengxusheji.po.GoodsType" %>
<%@ page import="com.chengxusheji.po.UserInfo" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    List<Goods> goodsList = (List<Goods>)request.getAttribute("goodsList");
    //获取所有的goodTypeObj信息
    List<GoodsType> goodsTypeList = (List<GoodsType>)request.getAttribute("goodsTypeList");
    //获取所有的goodsUserObj信息
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    int currentPage =  (Integer)request.getAttribute("currentPage"); //当前页
    int totalPage =   (Integer)request.getAttribute("totalPage");  //一共多少页
    int recordNumber =   (Integer)request.getAttribute("recordNumber");  //一共多少记录
    GoodsType goodTypeObj = (GoodsType)request.getAttribute("goodTypeObj");
    String goodsName = (String)request.getAttribute("goodsName"); //商品名称查询关键字
    String goodsRfid = (String)request.getAttribute("goodsRfid"); //商品rfid查询关键字
    String goodsDescribe = (String)request.getAttribute("goodsDescribe"); //商品描述查询关键字
    String goodIntoTIme = (String)request.getAttribute("goodIntoTIme"); //生产时间查询关键字
    UserInfo goodsUserObj = (UserInfo)request.getAttribute("goodsUserObj");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
<title>商品查询</title>
<link href="<%=basePath %>plugins/bootstrap.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-dashen.css" rel="stylesheet">
<link href="<%=basePath %>plugins/font-awesome.css" rel="stylesheet">
<link href="<%=basePath %>plugins/animate.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
</head>
<body style="margin-top:70px;">
<div class="container">
<jsp:include page="../header.jsp"></jsp:include>
	<div class="row"> 
		<div class="col-md-9 wow fadeInDown" data-wow-duration="0.5s">
			<div>
				<!-- Nav tabs -->
				<ul class="nav nav-tabs" role="tablist">
			    	<li><a href="<%=basePath %>index.jsp">首页</a></li>
			    	<li role="presentation" class="active"><a href="#goodsListPanel" aria-controls="goodsListPanel" role="tab" data-toggle="tab">商品列表</a></li>
			    	<li role="presentation" ><a href="<%=basePath %>Goods/goods_frontAdd.jsp" style="display:none;">添加商品</a></li>
				</ul>
			  	<!-- Tab panes -->
			  	<div class="tab-content">
				    <div role="tabpanel" class="tab-pane active" id="goodsListPanel">
				    		<div class="row">
				    			<div class="col-md-12 top5">
				    				<div class="table-responsive">
				    				<table class="table table-condensed table-hover">
				    					<tr class="success bold"><td>序号</td><td>商品id</td><td>商品类型</td><td>商品名称</td><td>商品rfid</td><td>保质期</td><td>商品描述</td><td>生产时间</td><td>操作</td></tr>
				    					<% 
				    						/*计算起始序号*/
				    	            		int startIndex = (currentPage -1) * 5;
				    	            		/*遍历记录*/
				    	            		for(int i=0;i<goodsList.size();i++) {
					    	            		int currentIndex = startIndex + i + 1; //当前记录的序号
					    	            		Goods goods = goodsList.get(i); //获取到商品对象
 										%>
 										<tr>
 											<td><%=currentIndex %></td>
 											<td><%=goods.getGoodsId() %></td>
 											<td><%=goods.getGoodTypeObj().getGoodTypeName() %></td>
 											<td><%=goods.getGoodsName() %></td>
 											<td><%=goods.getGoodsRfid() %></td>
 											<td><%=goods.getGoodPrice() %></td>
 											<td><%=goods.getGoodsDescribe() %></td>
 											<td><%=goods.getGoodIntoTIme() %></td>
 											
 											<td>
 												<a href="<%=basePath  %>Goods/<%=goods.getGoodsId() %>/frontshow"><i class="fa fa-info"></i>&nbsp;查看</a>&nbsp;
 												<a href="#" onclick="goodsEdit('<%=goods.getGoodsId() %>');" style="display:none;"><i class="fa fa-pencil fa-fw"></i>编辑</a>&nbsp;
 												<a href="#" onclick="goodsDelete('<%=goods.getGoodsId() %>');" style="display:none;"><i class="fa fa-trash-o fa-fw"></i>删除</a>
 											</td> 
 										</tr>
 										<%}%>
				    				</table>
				    				</div>
				    			</div>
				    		</div>

				    		<div class="row">
					            <div class="col-md-12">
						            <nav class="pull-left">
						                <ul class="pagination">
						                    <li><a href="#" onclick="GoToPage(<%=currentPage-1 %>,<%=totalPage %>);" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
						                     <%
						                    	int startPage = currentPage - 5;
						                    	int endPage = currentPage + 5;
						                    	if(startPage < 1) startPage=1;
						                    	if(endPage > totalPage) endPage = totalPage;
						                    	for(int i=startPage;i<=endPage;i++) {
						                    %>
						                    <li class="<%= currentPage==i?"active":"" %>"><a href="#"  onclick="GoToPage(<%=i %>,<%=totalPage %>);"><%=i %></a></li>
						                    <%  } %> 
						                    <li><a href="#" onclick="GoToPage(<%=currentPage+1 %>,<%=totalPage %>);"><span aria-hidden="true">&raquo;</span></a></li>
						                </ul>
						            </nav>
						            <div class="pull-right" style="line-height:75px;" >共有<%=recordNumber %>条记录，当前第 <%=currentPage %>/<%=totalPage %> 页</div>
					            </div>
				            </div> 
				    </div>
				</div>
			</div>
		</div>
	<div class="col-md-3 wow fadeInRight">
		<div class="page-header">
    		<h1>商品查询</h1>
		</div>
		<form name="goodsQueryForm" id="goodsQueryForm" action="<%=basePath %>Goods/frontlist" class="mar_t15" method="post">
            <div class="form-group">
				<label for="goodsRfid">商品rfid:</label>
				<input type="text" id="goodsRfid" name="goodsRfid" value="<%=goodsRfid %>" readOnly class="form-control" placeholder="请输入商品rfid">
				<span onclick="getRfid();" class="btn btn-primary bottom5 top5" style="float:left;margin-left:10px;">刷卡</span>
			</div>
            
            
            <div class="form-group">
            	<label for="goodTypeObj_goodTypeId">商品类型：</label>
                <select id="goodTypeObj_goodTypeId" name="goodTypeObj.goodTypeId" class="form-control">
                	<option value="0">不限制</option>
	 				<%
	 				for(GoodsType goodsTypeTemp:goodsTypeList) {
	 					String selected = "";
 					if(goodTypeObj!=null && goodTypeObj.getGoodTypeId()!=null && goodTypeObj.getGoodTypeId().intValue()==goodsTypeTemp.getGoodTypeId().intValue())
 						selected = "selected";
	 				%>
 				 <option value="<%=goodsTypeTemp.getGoodTypeId() %>" <%=selected %>><%=goodsTypeTemp.getGoodTypeName() %></option>
	 				<%
	 				}
	 				%>
 			</select>
            </div>
			<div class="form-group">
				<label for="goodsName">商品名称:</label>
				<input type="text" id="goodsName" name="goodsName" value="<%=goodsName %>" class="form-control" placeholder="请输入商品名称">
			</div>






			





			<div class="form-group">
				<label for="goodsDescribe">商品描述:</label>
				<input type="text" id="goodsDescribe" name="goodsDescribe" value="<%=goodsDescribe %>" class="form-control" placeholder="请输入商品描述">
			</div>






			<div class="form-group">
				<label for="goodIntoTIme">生产时间:</label>
				<input type="text" id="goodIntoTIme" name="goodIntoTIme" class="form-control"  placeholder="请选择生产时间" value="<%=goodIntoTIme %>" onclick="SelectDate(this,'yyyy-MM-dd')" />
			</div>
            <div class="form-group" style="display:none;">
            	<label for="goodsUserObj_user_name">所属用户4：</label>
                <select id="goodsUserObj_user_name" name="goodsUserObj.user_name" class="form-control">
                	<option value="">不限制</option>
	 				<%
	 				for(UserInfo userInfoTemp:userInfoList) {
	 					String selected = "";
 					if(goodsUserObj!=null && goodsUserObj.getUser_name()!=null && goodsUserObj.getUser_name().equals(userInfoTemp.getUser_name()))
 						selected = "selected";
	 				%>
 				 <option value="<%=userInfoTemp.getUser_name() %>" <%=selected %>><%=userInfoTemp.getName() %></option>
	 				<%
	 				}
	 				%>
 			</select>
            </div>
            <input type=hidden name=currentPage value="<%=currentPage %>" />
            <button type="submit" class="btn btn-primary">查询</button>
        </form>
	</div>

		</div>
	</div> 
<div id="goodsEditDialog" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title"><i class="fa fa-edit"></i>&nbsp;商品信息编辑</h4>
      </div>
      <div class="modal-body" style="height:450px; overflow: scroll;">
      	<form class="form-horizontal" name="goodsEditForm" id="goodsEditForm" enctype="multipart/form-data" method="post"  class="mar_t15">
		  <div class="form-group">
			 <label for="goods_goodsId_edit" class="col-md-3 text-right">商品id:</label>
			 <div class="col-md-9"> 
			 	<input type="text" id="goods_goodsId_edit" name="goods.goodsId" class="form-control" placeholder="请输入商品id" readOnly>
			 </div>
		  </div> 
		  <div class="form-group">
		  	 <label for="goods_goodTypeObj_goodTypeId_edit" class="col-md-3 text-right">商品类型:</label>
		  	 <div class="col-md-9">
			    <select id="goods_goodTypeObj_goodTypeId_edit" name="goods.goodTypeObj.goodTypeId" class="form-control">
			    </select>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="goods_goodsName_edit" class="col-md-3 text-right">商品名称:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="goods_goodsName_edit" name="goods.goodsName" class="form-control" placeholder="请输入商品名称">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="goods_goodsRfid_edit" class="col-md-3 text-right">商品rfid:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="goods_goodsRfid_edit" name="goods.goodsRfid" class="form-control" placeholder="请输入商品rfid">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="goods_goodPrice_edit" class="col-md-3 text-right">保质期:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="goods_goodPrice_edit" name="goods.goodPrice" class="form-control" placeholder="请输入保质期">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="goods_goodsDescribe_edit" class="col-md-3 text-right">商品描述:</label>
		  	 <div class="col-md-9">
			    <textarea id="goods_goodsDescribe_edit" name="goods.goodsDescribe" rows="8" class="form-control" placeholder="请输入商品描述"></textarea>
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="goods_goodIntoTIme_edit" class="col-md-3 text-right">生产时间:</label>
		  	 <div class="col-md-9">
                <div class="input-group date goods_goodIntoTIme_edit col-md-12" data-link-field="goods_goodIntoTIme_edit">
                    <input class="form-control" id="goods_goodIntoTIme_edit" name="goods.goodIntoTIme" size="16" type="text" value="" placeholder="请选择生产时间" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="goods_goodsNumb_edit" class="col-md-3 text-right">仓库数量:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="goods_goodsNumb_edit" name="goods.goodsNumb" class="form-control" placeholder="请输入仓库数量">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="goods_goodsUserObj_user_name_edit" class="col-md-3 text-right">所属用户4:</label>
		  	 <div class="col-md-9">
			    <select id="goods_goodsUserObj_user_name_edit" name="goods.goodsUserObj.user_name" class="form-control">
			    </select>
		  	 </div>
		  </div>
		</form> 
	    <style>#goodsEditForm .form-group {margin-bottom:5px;}  </style>
      </div>
      <div class="modal-footer"> 
      	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      	<button type="button" class="btn btn-primary" onclick="ajaxGoodsModify();">提交</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<jsp:include page="../footer.jsp"></jsp:include> 
<script src="<%=basePath %>plugins/jquery.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap.js"></script>
<script src="<%=basePath %>plugins/wow.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap-datetimepicker.min.js"></script>
<script src="<%=basePath %>plugins/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jsdate.js"></script>
<script>
var basePath = "<%=basePath%>";
/*跳转到查询结果的某页*/
function GoToPage(currentPage,totalPage) {
    if(currentPage==0) return;
    if(currentPage>totalPage) return;
    document.goodsQueryForm.currentPage.value = currentPage;
    document.goodsQueryForm.submit();
}

/*可以直接跳转到某页*/
function changepage(totalPage)
{
    var pageValue=document.goodsQueryForm.pageValue.value;
    if(pageValue>totalPage) {
        alert('你输入的页码超出了总页数!');
        return ;
    }
    document.goodsQueryForm.currentPage.value = pageValue;
    documentgoodsQueryForm.submit();
}
function getRfid() {
	$.ajax({
		type : "get",
		url : basePath + "GoodsInto/getRfid",
		dataType : "json" , 
		data: {},
		success: function(obj,response,status) { 
			if(obj.success){
				alert("刷卡成功！");
				$("#goodsRfid").val(obj.rfid);
				$("#goodsName").html(obj.goodsName);
				
			} else {
				alert(obj.msg);
			}
		}
	});
}

/*弹出修改商品界面并初始化数据*/
function goodsEdit(goodsId) {
	$.ajax({
		url :  basePath + "Goods/" + goodsId + "/update",
		type : "get",
		dataType: "json",
		success : function (goods, response, status) {
			if (goods) {
				$("#goods_goodsId_edit").val(goods.goodsId);
				$.ajax({
					url: basePath + "GoodsType/listAll",
					type: "get",
					success: function(goodsTypes,response,status) { 
						$("#goods_goodTypeObj_goodTypeId_edit").empty();
						var html="";
		        		$(goodsTypes).each(function(i,goodsType){
		        			html += "<option value='" + goodsType.goodTypeId + "'>" + goodsType.goodTypeName + "</option>";
		        		});
		        		$("#goods_goodTypeObj_goodTypeId_edit").html(html);
		        		$("#goods_goodTypeObj_goodTypeId_edit").val(goods.goodTypeObjPri);
					}
				});
				$("#goods_goodsName_edit").val(goods.goodsName);
				$("#goods_goodsRfid_edit").val(goods.goodsRfid);
				$("#goods_goodPrice_edit").val(goods.goodPrice);
				$("#goods_goodsDescribe_edit").val(goods.goodsDescribe);
				$("#goods_goodIntoTIme_edit").val(goods.goodIntoTIme);
				$("#goods_goodsNumb_edit").val(goods.goodsNumb);
				$.ajax({
					url: basePath + "UserInfo/listAll",
					type: "get",
					success: function(userInfos,response,status) { 
						$("#goods_goodsUserObj_user_name_edit").empty();
						var html="";
		        		$(userInfos).each(function(i,userInfo){
		        			html += "<option value='" + userInfo.user_name + "'>" + userInfo.name + "</option>";
		        		});
		        		$("#goods_goodsUserObj_user_name_edit").html(html);
		        		$("#goods_goodsUserObj_user_name_edit").val(goods.goodsUserObjPri);
					}
				});
				$('#goodsEditDialog').modal('show');
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*删除商品信息*/
function goodsDelete(goodsId) {
	if(confirm("确认删除这个记录")) {
		$.ajax({
			type : "POST",
			url : basePath + "Goods/deletes",
			data : {
				goodsIds : goodsId,
			},
			success : function (obj) {
				if (obj.success) {
					alert("删除成功");
					$("#goodsQueryForm").submit();
					//location.href= basePath + "Goods/frontlist";
				}
				else 
					alert(obj.message);
			},
		});
	}
}

/*ajax方式提交商品信息表单给服务器端修改*/
function ajaxGoodsModify() {
	$.ajax({
		url :  basePath + "Goods/" + $("#goods_goodsId_edit").val() + "/update",
		type : "post",
		dataType: "json",
		data: new FormData($("#goodsEditForm")[0]),
		success : function (obj, response, status) {
            if(obj.success){
                alert("信息修改成功！");
                $("#goodsQueryForm").submit();
            }else{
                alert(obj.message);
            } 
		},
		processData: false,
		contentType: false,
	});
}

$(function(){
	/*小屏幕导航点击关闭菜单*/
    $('.navbar-collapse > a').click(function(){
        $('.navbar-collapse').collapse('hide');
    });
    new WOW().init();

    /*生产时间组件*/
    $('.goods_goodIntoTIme_edit').datetimepicker({
    	language:  'zh-CN',  //语言
    	format: 'yyyy-mm-dd hh:ii:ss',
    	weekStart: 1,
    	todayBtn:  1,
    	autoclose: 1,
    	minuteStep: 1,
    	todayHighlight: 1,
    	startView: 2,
    	forceParse: 0
    });
})
</script>
</body>
</html>

