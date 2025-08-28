var goodsType_manage_tool = null; 
$(function () { 
	initGoodsTypeManageTool(); //建立GoodsType管理对象
	goodsType_manage_tool.init(); //如果需要通过下拉框查询，首先初始化下拉框的值
	$("#goodsType_manage").datagrid({
		url : 'GoodsType/list',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 5,
		pageList : [5, 10, 15, 20, 25],
		pageNumber : 1,
		sortName : "goodTypeId",
		sortOrder : "desc",
		toolbar : "#goodsType_manage_tool",
		columns : [[
			{
				field : "goodTypeId",
				title : "商品类型id",
				width : 70,
			},
			{
				field : "goodTypeName",
				title : "类型名称",
				width : 140,
			},
		]],
	});

	$("#goodsTypeEditDiv").dialog({
		title : "修改管理",
		top: "50px",
		width : 700,
		height : 515,
		modal : true,
		closed : true,
		iconCls : "icon-edit-new",
		buttons : [{
			text : "提交",
			iconCls : "icon-edit-new",
			handler : function () {
				if ($("#goodsTypeEditForm").form("validate")) {
					//验证表单 
					if(!$("#goodsTypeEditForm").form("validate")) {
						$.messager.alert("错误提示","你输入的信息还有错误！","warning");
					} else {
						$("#goodsTypeEditForm").form({
						    url:"GoodsType/" + $("#goodsType_goodTypeId_edit").val() + "/update",
						    onSubmit: function(){
								if($("#goodsTypeEditForm").form("validate"))  {
				                	$.messager.progress({
										text : "正在提交数据中...",
									});
				                	return true;
				                } else { 
				                    return false; 
				                }
						    },
						    success:function(data){
						    	$.messager.progress("close");
						    	console.log(data);
			                	var obj = jQuery.parseJSON(data);
			                    if(obj.success){
			                        $.messager.alert("消息","信息修改成功！");
			                        $("#goodsTypeEditDiv").dialog("close");
			                        goodsType_manage_tool.reload();
			                    }else{
			                        $.messager.alert("消息",obj.message);
			                    } 
						    }
						});
						//提交表单
						$("#goodsTypeEditForm").submit();
					}
				}
			},
		},{
			text : "取消",
			iconCls : "icon-redo",
			handler : function () {
				$("#goodsTypeEditDiv").dialog("close");
				$("#goodsTypeEditForm").form("reset"); 
			},
		}],
	});
});

function initGoodsTypeManageTool() {
	goodsType_manage_tool = {
		init: function() {
		},
		reload : function () {
			$("#goodsType_manage").datagrid("reload");
		},
		redo : function () {
			$("#goodsType_manage").datagrid("unselectAll");
		},
		search: function() {
			var queryParams = $("#goodsType_manage").datagrid("options").queryParams;
			queryParams["goodTypeName"] = $("#goodTypeName").val();
			$("#goodsType_manage").datagrid("options").queryParams=queryParams; 
			$("#goodsType_manage").datagrid("load");
		},
		exportExcel: function() {
			$("#goodsTypeQueryForm").form({
			    url:"GoodsType/OutToExcel",
			});
			//提交表单
			$("#goodsTypeQueryForm").submit();
		},
		remove : function () {
			var rows = $("#goodsType_manage").datagrid("getSelections");
			if (rows.length > 0) {
				$.messager.confirm("确定操作", "您正在要删除所选的记录吗？", function (flag) {
					if (flag) {
						var goodTypeIds = [];
						for (var i = 0; i < rows.length; i ++) {
							goodTypeIds.push(rows[i].goodTypeId);
						}
						$.ajax({
							type : "POST",
							url : "GoodsType/deletes",
							data : {
								goodTypeIds : goodTypeIds.join(","),
							},
							beforeSend : function () {
								$("#goodsType_manage").datagrid("loading");
							},
							success : function (data) {
								if (data.success) {
									$("#goodsType_manage").datagrid("loaded");
									$("#goodsType_manage").datagrid("load");
									$("#goodsType_manage").datagrid("unselectAll");
									$.messager.show({
										title : "提示",
										msg : data.message
									});
								} else {
									$("#goodsType_manage").datagrid("loaded");
									$("#goodsType_manage").datagrid("load");
									$("#goodsType_manage").datagrid("unselectAll");
									$.messager.alert("消息",data.message);
								}
							},
						});
					}
				});
			} else {
				$.messager.alert("提示", "请选择要删除的记录！", "info");
			}
		},
		edit : function () {
			var rows = $("#goodsType_manage").datagrid("getSelections");
			if (rows.length > 1) {
				$.messager.alert("警告操作！", "编辑记录只能选定一条数据！", "warning");
			} else if (rows.length == 1) {
				$.ajax({
					url : "GoodsType/" + rows[0].goodTypeId +  "/update",
					type : "get",
					data : {
						//goodTypeId : rows[0].goodTypeId,
					},
					beforeSend : function () {
						$.messager.progress({
							text : "正在获取中...",
						});
					},
					success : function (goodsType, response, status) {
						$.messager.progress("close");
						if (goodsType) { 
							$("#goodsTypeEditDiv").dialog("open");
							$("#goodsType_goodTypeId_edit").val(goodsType.goodTypeId);
							$("#goodsType_goodTypeId_edit").validatebox({
								required : true,
								missingMessage : "请输入商品类型id",
								editable: false
							});
							$("#goodsType_goodTypeName_edit").val(goodsType.goodTypeName);
							$("#goodsType_goodTypeName_edit").validatebox({
								required : true,
								missingMessage : "请输入类型名称",
							});
						} else {
							$.messager.alert("获取失败！", "未知错误导致失败，请重试！", "warning");
						}
					}
				});
			} else if (rows.length == 0) {
				$.messager.alert("警告操作！", "编辑记录至少选定一条数据！", "warning");
			}
		},
	};
}
