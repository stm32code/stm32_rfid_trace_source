$(function () {
	$("#goods_goodTypeObj_goodTypeId").combobox({
	    url:'GoodsType/listAll',
	    valueField: "goodTypeId",
	    textField: "goodTypeName",
	    panelHeight: "auto",
        editable: false, //不允许手动输入
        required : true,
        onLoadSuccess: function () { //数据加载完毕事件
            var data = $("#goods_goodTypeObj_goodTypeId").combobox("getData"); 
            if (data.length > 0) {
                $("#goods_goodTypeObj_goodTypeId").combobox("select", data[0].goodTypeId);
            }
        }
	});
	$("#goods_goodsName").validatebox({
		required : true, 
		missingMessage : '请输入商品名称',
	});

	$("#goods_goodsRfid").validatebox({
		required : true, 
		missingMessage : '请输入商品rfid',
	});

	$("#goods_goodPrice").validatebox({
		required : true,
		validType : "number",
		missingMessage : '请输入保质期',
		invalidMessage : '保质期输入不对',
	});

	$("#goods_goodsDescribe").validatebox({
		required : true, 
		missingMessage : '请输入商品描述',
	});

	$("#goods_goodIntoTIme").datetimebox({
	    required : true, 
	    showSeconds: true,
	    editable: false
	});

	$("#goods_goodsNumb").validatebox({
		required : true,
		validType : "integer",
		missingMessage : '请输入仓库数量',
		invalidMessage : '仓库数量输入不对',
	});

	$("#goods_goodsUserObj_user_name").combobox({
	    url:'UserInfo/listAll',
	    valueField: "user_name",
	    textField: "name",
	    panelHeight: "auto",
        editable: false, //不允许手动输入
        required : true,
        onLoadSuccess: function () { //数据加载完毕事件
            var data = $("#goods_goodsUserObj_user_name").combobox("getData"); 
            if (data.length > 0) {
                $("#goods_goodsUserObj_user_name").combobox("select", data[0].user_name);
            }
        }
	});
	//单击添加按钮
	$("#goodsAddButton").click(function () {
		//验证表单 
		if(!$("#goodsAddForm").form("validate")) {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		} else {
			$("#goodsAddForm").form({
			    url:"Goods/add",
			    onSubmit: function(){
					if($("#goodsAddForm").form("validate"))  { 
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
                    //此处data={"Success":true}是字符串
                	var obj = jQuery.parseJSON(data); 
                    if(obj.success){ 
                        $.messager.alert("消息","保存成功！");
                        $(".messager-window").css("z-index",10000);
                        $("#goodsAddForm").form("clear");
                    }else{
                        $.messager.alert("消息",obj.message);
                        $(".messager-window").css("z-index",10000);
                    }
			    }
			});
			//提交表单
			$("#goodsAddForm").submit();
		}
	});

	//单击清空按钮
	$("#goodsClearButton").click(function () { 
		$("#goodsAddForm").form("clear"); 
	});
});



function readRfid() {
	$.ajax({
		url : "user/getRfid",
		type : "get",
		data : {
			//productNo : rows[0].productNo,
		},
		beforeSend : function () {
			$.messager.progress({
				text : "正在获取中...",
			});
		},
		success : function (result, response, status) {
			$.messager.progress("close");
			if (result.success) { 
				$.messager.alert("成功","刷卡成功！");
				$("#goods_goodsRfid").val(result.msg);
				
			} else {
				$.messager.alert("错误！", result.msg, "warning");
			}
		}
	});				
} 
