$(function () {
	$.ajax({
		url : "Goods/" + $("#goods_goodsId_edit").val() + "/update",
		type : "get",
		data : {
			//goodsId : $("#goods_goodsId_edit").val(),
		},
		beforeSend : function () {
			$.messager.progress({
				text : "正在获取中...",
			});
		},
		success : function (goods, response, status) {
			$.messager.progress("close");
			if (goods) { 
				$("#goods_goodsId_edit").val(goods.goodsId);
				$("#goods_goodsId_edit").validatebox({
					required : true,
					missingMessage : "请输入商品id",
					editable: false
				});
				$("#goods_goodTypeObj_goodTypeId_edit").combobox({
					url:"GoodsType/listAll",
					valueField:"goodTypeId",
					textField:"goodTypeName",
					panelHeight: "auto",
					editable: false, //不允许手动输入 
					onLoadSuccess: function () { //数据加载完毕事件
						$("#goods_goodTypeObj_goodTypeId_edit").combobox("select", goods.goodTypeObjPri);
						//var data = $("#goods_goodTypeObj_goodTypeId_edit").combobox("getData"); 
						//if (data.length > 0) {
							//$("#goods_goodTypeObj_goodTypeId_edit").combobox("select", data[0].goodTypeId);
						//}
					}
				});
				$("#goods_goodsName_edit").val(goods.goodsName);
				$("#goods_goodsName_edit").validatebox({
					required : true,
					missingMessage : "请输入商品名称",
				});
				$("#goods_goodsRfid_edit").val(goods.goodsRfid);
				$("#goods_goodsRfid_edit").validatebox({
					required : true,
					missingMessage : "请输入商品rfid",
				});
				$("#goods_goodPrice_edit").val(goods.goodPrice);
				$("#goods_goodPrice_edit").validatebox({
					required : true,
					validType : "number",
					missingMessage : "请输入保质期",
					invalidMessage : "保质期输入不对",
				});
				$("#goods_goodsDescribe_edit").val(goods.goodsDescribe);
				$("#goods_goodsDescribe_edit").validatebox({
					required : true,
					missingMessage : "请输入商品描述",
				});
				$("#goods_goodIntoTIme_edit").datetimebox({
					value: goods.goodIntoTIme,
					required: true,
					showSeconds: true,
				});
				$("#goods_goodsNumb_edit").val(goods.goodsNumb);
				$("#goods_goodsNumb_edit").validatebox({
					required : true,
					validType : "integer",
					missingMessage : "请输入仓库数量",
					invalidMessage : "仓库数量输入不对",
				});
				$("#goods_goodsUserObj_user_name_edit").combobox({
					url:"UserInfo/listAll",
					valueField:"user_name",
					textField:"name",
					panelHeight: "auto",
					editable: false, //不允许手动输入 
					onLoadSuccess: function () { //数据加载完毕事件
						$("#goods_goodsUserObj_user_name_edit").combobox("select", goods.goodsUserObjPri);
						//var data = $("#goods_goodsUserObj_user_name_edit").combobox("getData"); 
						//if (data.length > 0) {
							//$("#goods_goodsUserObj_user_name_edit").combobox("select", data[0].user_name);
						//}
					}
				});
			} else {
				$.messager.alert("获取失败！", "未知错误导致失败，请重试！", "warning");
				$(".messager-window").css("z-index",10000);
			}
		}
	});

	$("#goodsModifyButton").click(function(){ 
		if ($("#goodsEditForm").form("validate")) {
			$("#goodsEditForm").form({
			    url:"Goods/" +  $("#goods_goodsId_edit").val() + "/update",
			    onSubmit: function(){
					if($("#goodsEditForm").form("validate"))  {
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
                	var obj = jQuery.parseJSON(data);
                    if(obj.success){
                        $.messager.alert("消息","信息修改成功！");
                        $(".messager-window").css("z-index",10000);
                        //location.href="frontlist";
                    }else{
                        $.messager.alert("消息",obj.message);
                        $(".messager-window").css("z-index",10000);
                    } 
			    }
			});
			//提交表单
			$("#goodsEditForm").submit();
		} else {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		}
	});
});
