<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/goodsType.css" />
<div id="goodsTypeAddDiv">
	<form id="goodsTypeAddForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">类型名称:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="goodsType_goodTypeName" name="goodsType.goodTypeName" style="width:200px" />

			</span>

		</div>
		<div class="operation">
			<a id="goodsTypeAddButton" class="easyui-linkbutton">添加</a>
			<a id="goodsTypeClearButton" class="easyui-linkbutton">重填</a>
		</div> 
	</form>
</div>
<script src="${pageContext.request.contextPath}/GoodsType/js/goodsType_add.js"></script> 
