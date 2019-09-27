package ${PACKAGE_PREFIX}${MODEL_LAYER_NAME};
<#assign contains_date = 0 />
<#list DGFIELDS as FIELD>
	<#if FIELD.ColumnType == "Date" >
		<#assign contains_date = 1 />
	</#if>
</#list>    
<#if contains_date == 1 >
import java.util.Date;
</#if>

public class ${TABLE_NAME?cap_first} {
    <#list DGFIELDS as FIELD>
    private ${FIELD.ColumnType} ${FIELD.ColumnName};   //${FIELD.ColumnLabel}
    </#list>    

    <#list DGFIELDS as FIELD>
	public ${FIELD.ColumnType} get${FIELD.ColumnName?cap_first}() {
		return ${FIELD.ColumnName};
	}
	public void set${FIELD.ColumnName?cap_first}(${FIELD.ColumnType} ${FIELD.ColumnName}) {
		this.${FIELD.ColumnName} = ${FIELD.ColumnName};
	}
	
    </#list>    


}
