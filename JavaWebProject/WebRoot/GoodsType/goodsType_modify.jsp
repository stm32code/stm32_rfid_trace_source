<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/goodsType.css" />
<div id="goodsType_editDiv">
	<form id="goodsTypeEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">商品类型id:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="goodsType_goodTypeId_edit" name="goodsType.goodTypeId" value="<%=request.getParameter("goodTypeId") %>" style="width:200px" />
			</span>
		</div>

		<div>
			<span class="label">类型名称:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="goodsType_goodTypeName_edit" name="goodsType.goodTypeName" style="width:200px" />

			</span>

		</div>
		<div class="operation">
			<a id="goodsTypeModifyButton" class="easyui-linkbutton">更新</a> 
		</div>
	</form>
</div>
<script src="${pageContext.request.contextPath}/GoodsType/js/goodsType_modify.js"></script> 
