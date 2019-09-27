<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/static/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="/static/easyui/themes/icon.css">
<script type="text/javascript" src="/static/easyui/jquery.min.js"></script>
<script type="text/javascript" src="/static/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/static/easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>

<table id="dg${TABLE_NAME?cap_first}"></table>
<div id="dg${TABLE_NAME?cap_first}_toolbar" style="padding:3px">
	<#if (SEARCHFIELDS?size>0) >
		<#list SEARCHFIELDS as FIELD>
			<span>${FIELD.ColumnLabel}:</span>
			<input id="${TABLE_NAME}_${FIELD.ColumnName}" style="line-height:24px;border:1px solid #ccc">
		</#list>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="do${TABLE_NAME?cap_first}Search()">查询</a>
		<span>&nbsp;</span>
	</#if>
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true"  onclick="openAdd${TABLE_NAME?cap_first}Dialog()">增加</a>
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="do${TABLE_NAME?cap_first}MulitpleSelect()">多选</a>
</div>

<div id="dlg${TABLE_NAME?cap_first}" class="easyui-dialog" closed="true" closable="false" >
	<form id="frm${TABLE_NAME?cap_first}" method="post">
		<input type="hidden" name="${FRMFIELDS[0].ColumnName}">
		<table cellspacing="8px">
		    <#assign n = FRMFIELDS?size />
		    <#list 1..(n-1) as i>
			<tr>
				<td>${FRMFIELDS[i].ColumnLabel}</td>
				<td>
					<input type="text" name="${FRMFIELDS[i].ColumnName}" class="easyui-validatebox">
				</td>
			</tr>
			</#list>
		</table>
	</form>
</div>

	
<!-- 以href加载时，所有js、css不能放置在<head></head>内 -->
<script type="text/javascript" >
$(function(){
	$('#dg${TABLE_NAME?cap_first}').datagrid({
	    url:'${URL_PREFIX}/${TABLE_NAME}/list.do',
	    fit:true,
	    columns:[[    
	        {field:'ck',title:'ID',checkbox: true},
	        <#list DGFIELDS as FIELD>
	        {field:'${FIELD.ColumnName}',title:'${FIELD.ColumnLabel}',width:100},   
	        </#list>    
	        {field:'xxx',title:'操作',width:100,formatter: function(value,row,index){
				return "<a href='#' onclick='openModify${TABLE_NAME?cap_first}Dialog("+row.id+")'>编辑</a> | <a href='#' onclick='del${TABLE_NAME?cap_first}("+row.id+")'>删除</a>";
			}
		}
	    ]],
	    striped:true,
	    // 显示分页工具条
	    pagination:true,
	    // 分页条位置
	    pagePosition:"bottom",
	    // 初始化页数
	    pageNumber:1,
	    // 每页显示多少条记录
	    pageSize:3,
	    pageList:[3,5,10],
	    toolbar: '#dg${TABLE_NAME?cap_first}_toolbar' //工具栏

	}); 
	
	var pager_dg${TABLE_NAME?cap_first} = $('#dg${TABLE_NAME?cap_first}').datagrid().datagrid('getPager');
	pager_dg${TABLE_NAME?cap_first}.pagination({
		buttons:[{
			text:'多选',
			iconCls:'icon-remove',
			handler:function(){
				do${TABLE_NAME?cap_first}MulitpleSelect();
			}
		},{
			text:'增加',
			iconCls:'icon-add',
			handler:function(){
				openAdd${TABLE_NAME?cap_first}Dialog();
			}
		}]
	});			
	
	$('#dlg${TABLE_NAME?cap_first}').dialog({
		buttons: [{
			text:'保存',
			iconCls: 'icon-save',
			handler: function(){
				save${TABLE_NAME?cap_first}();
			}
	    	},
			{
	    		text:'关闭',
				iconCls: 'icon-cancel',
				handler: function(){
					$('#dlg${TABLE_NAME?cap_first}').dialog('close');
				}
			}
	    ]

	}); 

});

<#if (SEARCHFIELDS?size>0) >
function do${TABLE_NAME?cap_first}Search(){
	$('#dg${TABLE_NAME?cap_first}').datagrid('load',{
	<#list SEARCHFIELDS as FIELD>
		${FIELD.ColumnName}: $('#${TABLE_NAME}_${FIELD.ColumnName}').val() <#if FIELD_has_next>,</#if>
	</#list>
	});
}
</#if>

function do${TABLE_NAME?cap_first}MulitpleSelect(){
	var checkedItems = $('#dg${TABLE_NAME?cap_first}').datagrid('getChecked');
	var ids = [];
	$.each(checkedItems, function(index, item){
		ids.push(item.${FRMFIELDS[0].ColumnName});
	});
	alert(ids);
}

function del${TABLE_NAME?cap_first}(id){
	// 提交数据到Action
	$.post("${URL_PREFIX}/${TABLE_NAME}/delete.do","id="+id, function(result) {
			$.messager.show({
				title:'提示信息',
				msg:result.message,
				timeout:3000,
				showType:'slide'
			});
			$("#dg${TABLE_NAME?cap_first}").datagrid("reload");
		});
}


function openModify${TABLE_NAME?cap_first}Dialog(id) {

	$.post("${URL_PREFIX}/${TABLE_NAME}/modify.do","id="+id, function(data) {
		    console.log(data);
			$("#dlg${TABLE_NAME?cap_first}").attr("method","Modify");
			<#list FRMFIELDS as FIELD>
			$("#frm${TABLE_NAME?cap_first} input[name='${FIELD.ColumnName}']").val(data.${FIELD.ColumnName}); 
			</#list>
			$("#dlg${TABLE_NAME?cap_first}").dialog("setTitle", "修改${TABLE_CNAME}");
			$("#dlg${TABLE_NAME?cap_first}").dialog("open");
		});

}

function openAdd${TABLE_NAME?cap_first}Dialog() {
	$("#dlg${TABLE_NAME?cap_first}").attr("method","Add");
	
	clear${TABLE_NAME?cap_first}Form();
	$("#dlg${TABLE_NAME?cap_first}").dialog("setTitle", "添加${TABLE_CNAME}");
	$("#dlg${TABLE_NAME?cap_first}").dialog("open");
}

//清空表单
function clear${TABLE_NAME?cap_first}Form(){
	<#list FRMFIELDS as FIELD>
	$("#frm${TABLE_NAME?cap_first} input[name='${FIELD.ColumnName}']").val('${FIELD.ColumnDefaultValue}'); 
	</#list>
	//$("typeName").val("");
}

function close${TABLE_NAME?cap_first}Dialog() {
	console.log($("#dlg${TABLE_NAME?cap_first}").attr("method"));
	
	celear${TABLE_NAME?cap_first}Form();
	$("#dlg${TABLE_NAME?cap_first}").dialog("close"); //关闭对话框
}

function save${TABLE_NAME?cap_first}() {
	myurl="${URL_PREFIX}/${TABLE_NAME}/add_submit.do";
	if('Modify' == $("#dlg${TABLE_NAME?cap_first}").attr("method")){
		myurl="${URL_PREFIX}/${TABLE_NAME}/modify_submit.do";		
	}
	
	console.log($("#frm${TABLE_NAME?cap_first}").serialize());
	
	$.post(myurl,$("#frm${TABLE_NAME?cap_first}").serialize(), function(result) {
		$.messager.show({
			title:'提示信息',
			msg:result.message,
			timeout:3000,
			showType:'slide'
		});
		$("#dlg${TABLE_NAME?cap_first}").dialog("close"); //关闭对话框
		$("#dg${TABLE_NAME?cap_first}").datagrid("reload");
	});
	
}

</script>

</body>
</html>