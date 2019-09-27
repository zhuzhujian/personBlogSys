package com;
 
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
 
public class CodeUtil {
	
	static String URL_PREFIX="/admin";
	
	static String PACKAGE_PREFIX="com.blog.";
	static String PACKAGE_PATH_PREFIX="com\\blog\\";
	
	static String TEMPLATE_JS_NAME="table.js.tpl";
	static String TEMPLATE_HTML_NAME="table.html.tpl";
	static String TEMPLATE_MODEL_NAME="model.java.tpl";
	static String TEMPLATE_SERVICE_NAME="service.java.tpl";
	static String TEMPLATE_CONTROLLER_NAME="controller.java.tpl";
	static String TEMPLATE_DAO_NAME="dao.java.tpl";

	static String TEMPLATE_PAGEBEAN_NAME="PageBean.java.tpl";
	static String TEMPLATE_STATUSBEAN_NAME="StatusBean.java.tpl";
	
	
	static String TEMPLATE_OUTPUT_PATH=".\\src\\test\\resources\\output\\";
	
	static String BEANS_LAYER_NAME="beans";
	static String MODEL_LAYER_NAME="entitys";
	static String DAO_LAYER_NAME="dao";
	static String SERVICE_LAYER_NAME="service";
	static String CONTROLLER_LAYER_NAME="controller";
	
	static String DB_NAME="DB_BLOG";
	
	/**
	 * 注意，本程序假设每个数据库都有一个id字段，类型为整数
	 * @param args
	 */

	public static void main(String[] args) {
		try {
			//-----创建目录-----------
			String [] packages=new String[] {BEANS_LAYER_NAME,MODEL_LAYER_NAME,DAO_LAYER_NAME,SERVICE_LAYER_NAME,CONTROLLER_LAYER_NAME};
			for(String pack : packages) {
				File file = new File(TEMPLATE_OUTPUT_PATH+PACKAGE_PATH_PREFIX+pack);
				file.mkdirs();
			}
			
			//创建一个合适的FreeMarker Configration对象
			Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);
			System.out.println(Thread.currentThread().getContextClassLoader ().getResource(""));
			String path=Thread.currentThread().getContextClassLoader ().getResource("").getPath();// 设置模板路径
			configuration.setDirectoryForTemplateLoading(new File(path));
			configuration.setDefaultEncoding("UTF-8");   //这个一定要设置，不然在生成的页面中 会乱码

			
			//-----从数据库查询表的元信息-----------
			DriverManagerDataSource datasource = new DriverManagerDataSource();
//			datasource.setDriverClassName("org.sqlite.JDBC");
//			datasource.setUrl("jdbc:sqlite::resource:books.db");
			datasource.setDriverClassName("com.mysql.jdbc.Driver");
			datasource.setUrl("jdbc:mysql://localhost:3306/db_blog?&characterEncoding=utf8&user=root&password=root&useInformationSchema=true");
			
			JdbcTemplate jdbcTpl = new JdbcTemplate(datasource);
			
			DatabaseMetaData meta1 =jdbcTpl.getDataSource().getConnection().getMetaData(); // 获取所有表信息 
			ResultSet resultSet1=meta1.getTables(null, null, "%", null);
			while (resultSet1.next()) {
				String table_name=resultSet1.getString("TABLE_NAME");
				String table_comment=resultSet1.getString("REMARKS"); 
				if(table_comment==null || "".equals(table_comment)) table_comment=table_name;
				generate_one_tale(configuration,jdbcTpl,table_name,table_comment);
				System.out.println("--------------generate code for :TABLE_NAME="+table_name+" "+table_comment+"-----------------");
				} 
			resultSet1.close();			

			Map<String, Object> paramMap = new HashMap<String, Object>();

			paramMap.put("BEANS_LAYER_NAME", BEANS_LAYER_NAME);
			paramMap.put("PACKAGE_PREFIX", PACKAGE_PREFIX);
			configuration.getTemplate(TEMPLATE_PAGEBEAN_NAME).process(paramMap, new OutputStreamWriter(new FileOutputStream(TEMPLATE_OUTPUT_PATH+PACKAGE_PATH_PREFIX+BEANS_LAYER_NAME+"\\PageBean.java"),"UTF-8"));
			configuration.getTemplate(TEMPLATE_STATUSBEAN_NAME).process(paramMap, new OutputStreamWriter(new FileOutputStream(TEMPLATE_OUTPUT_PATH+PACKAGE_PATH_PREFIX+BEANS_LAYER_NAME+"\\StatusBean.java"),"UTF-8"));
			
			System.out.println("*************请刷新src/test/resources/output/文件夹查看文件********************************");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void generate_one_tale(Configuration configuration,JdbcTemplate jdbcTpl,String TABLE_NAME,String TABLE_CNAME) {
		try {
			//-----准备数据-----------
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("TABLE_NAME", TABLE_NAME);
			paramMap.put("TABLE_CNAME", TABLE_CNAME);
			paramMap.put("URL_PREFIX", URL_PREFIX);
			paramMap.put("PACKAGE_PREFIX", PACKAGE_PREFIX);

			paramMap.put("BEANS_LAYER_NAME", BEANS_LAYER_NAME);
			paramMap.put("MODEL_LAYER_NAME", MODEL_LAYER_NAME);
			paramMap.put("DAO_LAYER_NAME", DAO_LAYER_NAME);
			paramMap.put("SERVICE_LAYER_NAME", SERVICE_LAYER_NAME);
			paramMap.put("CONTROLLER_LAYER_NAME", CONTROLLER_LAYER_NAME);
			
					
			ArrayList dgfields = new ArrayList();  //存放datagrid字段的列元数据
			ArrayList frmfields = new ArrayList(); //存放表单字段的列元数据
			ArrayList searchfields = new ArrayList(); //存放搜索字段的列元数据
			paramMap.put("DGFIELDS", dgfields);
			paramMap.put("FRMFIELDS", frmfields);
			paramMap.put("SEARCHFIELDS", searchfields);
			
			SqlRowSet rowset = jdbcTpl.queryForRowSet("select * from "+TABLE_NAME);
			SqlRowSetMetaData meta = rowset.getMetaData();
			
			HashMap<String,HashMap> columns = new HashMap<String,HashMap>();
			
			for(int i=1; i<=meta.getColumnCount(); i++) {
//				System.out.println("ColumnName="+meta.getColumnName(i));
//				System.out.println("ColumnLabel="+meta.getColumnLabel(i));
//				System.out.println("ColumnTypeName="+meta.getColumnClassName(i));
//				System.out.println("-------------");
				HashMap field = new HashMap(); 
				String type=meta.getColumnClassName(i);
				type=type.substring(type.lastIndexOf(".")+1);
				String val="";
				if("Integer".equals(type) || "Float".equals(type) || "Float".equals(type) ) val="0";
				
				if("Timestamp".equals(type))type="Date";

				field.put("ColumnName",meta.getColumnName(i));
				field.put("ColumnLabel",meta.getColumnLabel(i));
				field.put("ColumnType",type);
				field.put("ColumnDefaultValue",val);
				
				columns.put(meta.getColumnName(i), field); //用于后面修改中文名
				
				dgfields.add(field);
				frmfields.add(field);
				searchfields.add(field);  //字段为搜索字段
				
			}
			
			//----------读取字段的英文名和中文名---------------
			DatabaseMetaData meta2 =jdbcTpl.getDataSource().getConnection().getMetaData(); // 获取所有表信息 
			ResultSet resultSet2 = meta2.getColumns( null, null,TABLE_NAME, "%"); 
			while (resultSet2.next()) {
				String columnname=resultSet2.getString("COLUMN_NAME");
				String comment=resultSet2.getString("REMARKS"); 
				if(!"".equals(comment))
					columns.get(columnname).put("ColumnLabel", comment);
				System.out.println("ColumnName="+columnname);
				System.out.println("ColumnLabel="+comment);
				System.out.println("ColumnTypeName="+columns.get(columnname).get("ColumnType"));
				System.out.println("--------------------------------");
			} 
			resultSet2.close();			
			
			
			
//			//设第1个字段为搜索字段
//			searchfields.add(dgfields.get(1));
//			searchfields.add(dgfields.get(2));
			String CLASSNAME=TABLE_NAME.substring(0, 1).toUpperCase() + TABLE_NAME.substring(1);
			

			//-----填写模板-----------
//			configuration.getTemplate(TEMPLATE_JS_NAME).process(paramMap, new OutputStreamWriter(new FileOutputStream(TEMPLATE_OUTPUT_PATH+TABLE_NAME+".js"),"UTF-8"));
			configuration.getTemplate(TEMPLATE_HTML_NAME).process(paramMap, new OutputStreamWriter(new FileOutputStream(TEMPLATE_OUTPUT_PATH+TABLE_NAME+".html"),"UTF-8"));
			configuration.getTemplate(TEMPLATE_MODEL_NAME).process(paramMap, new OutputStreamWriter(new FileOutputStream(TEMPLATE_OUTPUT_PATH+PACKAGE_PATH_PREFIX+MODEL_LAYER_NAME+"\\"+CLASSNAME+".java"),"UTF-8"));
			configuration.getTemplate(TEMPLATE_DAO_NAME).process(paramMap, new OutputStreamWriter(new FileOutputStream(TEMPLATE_OUTPUT_PATH+PACKAGE_PATH_PREFIX+DAO_LAYER_NAME+"\\"+CLASSNAME+"Dao.java"),"UTF-8"));
			configuration.getTemplate(TEMPLATE_SERVICE_NAME).process(paramMap, new OutputStreamWriter(new FileOutputStream(TEMPLATE_OUTPUT_PATH+PACKAGE_PATH_PREFIX+SERVICE_LAYER_NAME+"\\"+CLASSNAME+"Service.java"),"UTF-8"));
			configuration.getTemplate(TEMPLATE_CONTROLLER_NAME).process(paramMap, new OutputStreamWriter(new FileOutputStream(TEMPLATE_OUTPUT_PATH+PACKAGE_PATH_PREFIX+CONTROLLER_LAYER_NAME+"\\"+CLASSNAME+"Controller.java"),"UTF-8"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
