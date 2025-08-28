<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.GoodsType" %>
<%@ page import="com.chengxusheji.po.UserInfo" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
<title>商品添加</title>
<link href="<%=basePath %>plugins/bootstrap.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-dashen.css" rel="stylesheet">
<link href="<%=basePath %>plugins/font-awesome.css" rel="stylesheet">
<link href="<%=basePath %>plugins/animate.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
</head>
<body style="margin-top:70px;">
<jsp:include page="../header.jsp"></jsp:include>
<div class="container">
	<div class="row">
		<div class="col-md-12 wow fadeInUp" data-wow-duration="0.5s">
			<div>
				<!-- Nav tabs -->
				<ul class="nav nav-tabs" role="tablist">
			    	<li role="presentation" ><a href="<%=basePath %>Goods/frontlist">商品列表</a></li>
			    	<li role="presentation" class="active"><a href="#goodsAdd" aria-controls="goodsAdd" role="tab" data-toggle="tab">添加商品</a></li>
				</ul>
				<!-- Tab panes -->
				<div class="tab-content">
				    <div role="tabpanel" class="tab-pane" id="goodsList">
				    </div>
				    <div role="tabpanel" class="tab-pane active" id="goodsAdd"> 
				      	<form class="form-horizontal" name="goodsAddForm" id="goodsAddForm" enctype="multipart/form-data" method="post"  class="mar_t15">
						  <div class="form-group">
						  	 <label for="goods_goodTypeObj_goodTypeId" class="col-md-2 text-right">商品类型:</label>
						  	 <div class="col-md-8">
							    <select id="goods_goodTypeObj_goodTypeId" name="goods.goodTypeObj.goodTypeId" class="form-control">
							    </select>
						  	 </div>
						  </div>
						  <div class="form-group">
						  	 <label for="goods_goodsName" class="col-md-2 text-right">商品名称:</label>
						  	 <div class="col-md-8">
							    <input type="text" id="goods_goodsName" name="goods.goodsName" class="form-control" placeholder="请输入商品名称">
							 </div>
						  </div>
						  <div class="form-group">
						  	 <label for="goods_goodsRfid" class="col-md-2 text-right">商品rfid:</label>
						  	 <div class="col-md-8">
							    <input type="text" id="goods_goodsRfid" name="goods.goodsRfid" class="form-control" placeholder="请输入商品rfid" readOnly style="width:60%;float:left" >
								<span onclick="getRfid();" class="btn btn-primary bottom5 top5" style="float:left;margin-left:10px;">刷卡</span>
							 </div>
						  </div>
						  <div class="form-group">
						  	 <label for="goods_goodPrice" class="col-md-2 text-right">保质期:</label>
						  	 <div class="col-md-8">
							    <input type="text" id="goods_goodPrice" name="goods.goodPrice" class="form-control" placeholder="请输入保质期">
							 </div>
						  </div>
						  <div class="form-group">
						  	 <label for="goods_goodsDescribe" class="col-md-2 text-right">商品描述:</label>
						  	 <div class="col-md-8">
							    <textarea id="goods_goodsDescribe" name="goods.goodsDescribe" rows="8" class="form-control" placeholder="请输入商品描述"></textarea>
							 </div>
						  </div>
						  <div class="form-group">
						  	 <label for="goods_goodIntoTImeDiv" class="col-md-2 text-right">生产时间:</label>
						  	 <div class="col-md-8">
				                <div id="goods_goodIntoTImeDiv" class="input-group date goods_goodIntoTIme col-md-12" data-link-field="goods_goodIntoTIme">
				                    <input class="form-control" id="goods_goodIntoTIme" name="goods.goodIntoTIme" size="16" type="text" value="" placeholder="请选择生产时间" readonly>
				                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
				                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
				                </div>
						  	 </div>
						  </div>
						  <div class="form-group" style="display:none;">
						  	 <label for="goods_goodsNumb" class="col-md-2 text-right">仓库数量:</label>
						  	 <div class="col-md-8">
							    <input type="text" id="goods_goodsNumb" name="goods.goodsNumb" class="form-control" placeholder="请输入仓库数量">
							 </div>
						  </div>
						  <div class="form-group"  style="display:none;">
						  	 <label for="goods_goodsUserObj_user_name" class="col-md-2 text-right">所属用户2:</label>
						  	 <div class="col-md-8">
							    <select id="goods_goodsUserObj_user_name" name="goods.goodsUserObj.user_name" class="form-control">
							    </select>
						  	 </div>
						  </div>
				          <div class="form-group">
				             <span class="col-md-2""></span>
				             <span onclick="ajaxGoodsAdd();" class="btn btn-primary bottom5 top5">添加</span>
				          </div>
						</form> 
				        <style>#goodsAddForm .form-group {margin:10px;}  </style>
					</div>
				</div>
			</div>
		</div>
	</div> 
</div>

<jsp:include page="../footer.jsp"></jsp:include> 
<script src="<%=basePath %>plugins/jquery.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap.js"></script>
<script src="<%=basePath %>plugins/wow.min.js"></script>
<script src="<%=basePath %>plugins/bootstrapvalidator/js/bootstrapValidator.min.js"></script>
<script type="text/javascript" src="<%=basePath %>plugins/bootstrap-datetimepicker.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=basePath %>plugins/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script>
var basePath = "<%=basePath%>";
	//提交添加商品信息
	function ajaxGoodsAdd() { 
		//提交之前先验证表单
		$("#goodsAddForm").data('bootstrapValidator').validate();
		if(!$("#goodsAddForm").data('bootstrapValidator').isValid()){
			return;
		}
		jQuery.ajax({
			type : "post",
			url : basePath + "Goods/frontAdd",
			dataType : "json" , 
			data: new FormData($("#goodsAddForm")[0]),
			success : function(obj) {
				if(obj.success){ 
					alert("保存成功！");
					$("#goodsAddForm").find("input").val("");
					$("#goodsAddForm").find("textarea").val("");
				} else {
					alert(obj.message);
				}
			},
			processData: false, 
			contentType: false, 
		});
	} 
	

	function getRfid() {
		$.ajax({
			type : "get",
			url : basePath + "Goods/getRfid",
			dataType : "json" , 
			data: {},
			success: function(obj,response,status) { 
				if(obj.success){
					alert("刷卡成功！");
					$("#goods_goodsRfid").val(obj.rfid);
					//$("#productNo").val(obj.productNo);
				} else {
					alert(obj.msg);
				}
			}
		});
	}
	
$(function(){
	/*小屏幕导航点击关闭菜单*/
    $('.navbar-collapse > a').click(function(){
        $('.navbar-collapse').collapse('hide');
    });
    new WOW().init();
	//验证商品添加表单字段
	$('#goodsAddForm').bootstrapValidator({
		feedbackIcons: {
			valid: 'glyphicon glyphicon-ok',
			invalid: 'glyphicon glyphicon-remove',
			validating: 'glyphicon glyphicon-refresh'
		},
		fields: {
			"goods.goodsName": {
				validators: {
					notEmpty: {
						message: "商品名称不能为空",
					}
				}
			},
			
			"goods.goodPrice": {
				validators: {
					notEmpty: {
						message: "保质期不能为空",
					},
					numeric: {
						message: "保质期不正确"
					}
				}
			},
			"goods.goodsDescribe": {
				validators: {
					notEmpty: {
						message: "商品描述不能为空",
					}
				}
			},
			"goods.goodIntoTIme": {
				validators: {
					notEmpty: {
						message: "生产时间不能为空",
					}
				}
			},
			"goods.goodsNumb": {
				validators: {
					notEmpty: {
						message: "仓库数量不能为空",
					},
					integer: {
						message: "仓库数量不正确"
					}
				}
			},
		}
	}); 
	//初始化商品类型下拉框值 
	$.ajax({
		url: basePath + "GoodsType/listAll",
		type: "get",
		success: function(goodsTypes,response,status) { 
			$("#goods_goodTypeObj_goodTypeId").empty();
			var html="";
    		$(goodsTypes).each(function(i,goodsType){
    			html += "<option value='" + goodsType.goodTypeId + "'>" + goodsType.goodTypeName + "</option>";
    		});
    		$("#goods_goodTypeObj_goodTypeId").html(html);
    	}
	});
	//初始化所属用户2下拉框值 
	$.ajax({
		url: basePath + "UserInfo/listAll",
		type: "get",
		success: function(userInfos,response,status) { 
			$("#goods_goodsUserObj_user_name").empty();
			var html="";
    		$(userInfos).each(function(i,userInfo){
    			html += "<option value='" + userInfo.user_name + "'>" + userInfo.name + "</option>";
    		});
    		$("#goods_goodsUserObj_user_name").html(html);
    	}
	});
	//生产时间组件
	$('#goods_goodIntoTImeDiv').datetimepicker({
		language:  'zh-CN',  //显示语言
		format: 'yyyy-mm-dd hh:ii:ss',
		weekStart: 1,
		todayBtn:  1,
		autoclose: 1,
		minuteStep: 1,
		todayHighlight: 1,
		startView: 2,
		forceParse: 0
	}).on('hide',function(e) {
		//下面这行代码解决日期组件改变日期后不验证的问题
		$('#goodsAddForm').data('bootstrapValidator').updateStatus('goods.goodIntoTIme', 'NOT_VALIDATED',null).validateField('goods.goodIntoTIme');
	});
})
</script>
</body>
</html>
