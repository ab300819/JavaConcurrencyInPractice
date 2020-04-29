package com.exercise.demo.common.util;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.internal.DefaultCommentGenerator;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * mybatis 自定义注释
 *
 * @author mason
 * @date 2019-04-18
 */
public class CustomCommentGenerator extends DefaultCommentGenerator {

    public static final String LONG_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * entity 字段注释
     *
     * @param field
     * @param introspectedTable
     * @param introspectedColumn
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        field.addJavaDocLine("/**");

        String remarks = introspectedColumn.getRemarks();
        String[] remarkLines = remarks.split(System.getProperty("line.separator"));
        for (String remarkLine : remarkLines) {
            field.addJavaDocLine(" * " + remarkLine);
        }

        field.addJavaDocLine(" * " + introspectedTable.getFullyQualifiedTable() + '.' + introspectedColumn.getActualColumnName());
        field.addJavaDocLine(" */");
    }

    /**
     * entity 类注释
     *
     * @param topLevelClass
     * @param introspectedTable
     */
    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        topLevelClass.addJavaDocLine("/**");

        String remarks = introspectedTable.getRemarks();
        String[] remarkLines = remarks.split(System.getProperty("line.separator"));
        for (String remarkLine : remarkLines) {
            topLevelClass.addJavaDocLine(" * " + remarkLine);
        }
        topLevelClass.addJavaDocLine(" * " + introspectedTable.getFullyQualifiedTable());
        topLevelClass.addJavaDocLine(" * ");
        topLevelClass.addJavaDocLine(" * @date " + new SimpleDateFormat(LONG_DATE_FORMAT).format(new Date()));
        topLevelClass.addJavaDocLine(" * @author mason");

        topLevelClass.addJavaDocLine(" */");
    }

    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
    }

    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
    }

    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
    }

    @Override
    public void addComment(XmlElement xmlElement) {
    }
}
