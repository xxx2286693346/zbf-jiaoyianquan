package com.ycl.deupload.controller;

import com.ycl.deupload.config.ExcelBuilder;
import com.ycl.deupload.config.SheetInfo;
import com.ycl.deupload.data.DefaultHandlers;
import com.zbf.common.entity.ResponseResult;
import com.zbf.common.entity.my.BaseUser;
import com.zbf.common.utils.UID;
import com.zbf.common.utils.UUIDGenerator;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @description:
 * @projectName:zbf-jiaoyianquan
 * @see:com.ycl.deupload.controller
 * @author:袁成龙
 * @createTime:2020/9/20 14:33
 * @version:1.0
 */
public class ExecController {



    List<BaseUser> studentList;

    ResponseResult responseResult = new ResponseResult();

    /**
     * 生成测试数据 studentList  数量自己定义
     */
  /*  public void initList() {
        studentList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            studentList.add(genStudent(i));
        }
    }*/



    /*
     * 导入
     * */

    public void readXlsx()
    {
        try
        {
            //获取该xlsx文件
            OPCPackage pkg=OPCPackage.open("F:\\Demo0714\\ex\\a.xlsx");
            //加载xlsx
            XSSFWorkbook excel=new XSSFWorkbook(pkg);
            //获取第一个sheet
            XSSFSheet sheet0=excel.getSheetAt(0);
            //循环每一行
            for (Iterator rowIterator = sheet0.iterator(); rowIterator.hasNext();)
            {
                XSSFRow row=(XSSFRow) rowIterator.next();
              //   student = new Student();
                //获取该列                      第0列
                XSSFCell name = row.getCell(0);
                //赋值名字
                //student.setName(name.toString());


                //存入集合
               // studentList.add(student);
            }
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 导出
     *
     * @throws Exception
     */
    @RequestMapping("getexec")
    public ResponseResult simple(List<BaseUser> list, String coluName, String fiedname) throws Exception {
        String substring = coluName.substring(0, coluName.length() - 1);
        System.out.println(substring);
        String substring1 = fiedname.substring(0, fiedname.length() - 1);
        System.out.println(substring1);
        /*
         * 定义sheetInfo
         */         //
        String sheetName = "用户信息表";
        //创建xslx列明
        String[] columnNames = {"姓名", "身份证号", "性别", "出生日期", "语文分数", "数学分数", "英语分数"};
        //创建实体类Student 列名       xslx 与 Student 名字的一一对应
        String[] classFieldNames = {"name", "cardId", "sex", "birthday", "grade.chineseGrade", "grade.mathGrade", "grade.englishGrade"};
        //创建生成xslx                        //sheet名    xlsx列名       实体类名           集合数据
        SheetInfo sheetInfo = new SheetInfo(sheetName, columnNames, classFieldNames, list);
        /*
         * 通过workbook写入文件
         */
        //通过 sheetInfo 创建 workbook
        SXSSFWorkbook workbook = ExcelBuilder.createWorkbook(sheetInfo);
        //创建文件位置
        FileOutputStream file = new FileOutputStream("I:/getExec/"+ UID.getUUID16() +".xls");
        //写入
        workbook.write(file);
        //关闭
        file.close();
        //通过workbook需要删除临时文件
        workbook.dispose();
        responseResult.setCode(1006);
        return responseResult;
        /*
         * 直接写入文件
         */
        //FileOutputStream file = new FileOutputStream("simple.xlsx");
        //ExcelBuilder.writeOutputStream(sheetInfo, file);
        //file.close();
    }


















































    /**
     * 在simple的基础上
     * 添加标题 title
     * 使用Converter，对性别进行转换，对日期进行格式化
     *
     * @throws Exception
     */
   /* public void simpleConverter() throws Exception {
        String sheetName = "学生信息表";
        String title = "学生信息表";
        String[] columnNames = {"姓名", "身份证号", "性别", "出生日期", "语文分数", "数学分数", "英语分数"};
        String[] classFieldNames = {"name", "cardId", "sex", "birthday", "grade.chineseGrade", "grade.mathGrade", "grade.englishGrade"};
        SheetInfo sheetInfo = new SheetInfo(sheetName, title, columnNames, classFieldNames, studentList);
        *//*
         * 转换器
         *//*
        //性别从 0|1 转换为 女生|男生
        sheetInfo.putConverter("sex", sexConverter);
        //性别转换也可以使用Map，Map适合数据简单的映射，Converter适合对值进行复杂操作
        //Map sexMap = new HashMap<>();
        //sexMap.put(1, "男生");
        //sexMap.put(0, "女生");
        //sheetInfo.putConverter("sex", sexMap);

        //日期格式转换为 yyyy-MM-dd
        sheetInfo.putConverter("birthday", DefaultHandlers.dateConverter);

        SXSSFWorkbook workbook = ExcelBuilder.createWorkbook(sheetInfo);
        FileOutputStream file = new FileOutputStream("simpleConverter.xlsx");
        workbook.write(file);
        file.close();
        workbook.dispose();
    }

    *//**
     * 在simpleConverter的基础上
     * 使用#开头的虚拟字段定义 序号列 总分数列，并为虚拟字段添加Converter
     *
     * @throws Exception
     *//*
    public void simpleConverterVirtual() throws Exception {
        String sheetName = "学生信息表";
        String title = "学生信息表";
        String[] columnNames = {"序号", "姓名", "身份证号", "性别", "出生日期", "语文分数", "数学分数", "英语分数", "总分数"};
        String[] classFieldNames = {"#order", "name", "cardId", "sex", "birthday", "grade.chineseGrade", "grade.mathGrade", "grade.englishGrade", "#countGrade"};
        SheetInfo sheetInfo = new SheetInfo(sheetName, title, columnNames, classFieldNames, studentList);
        *//*
         * 转换器
         *//*
        sheetInfo.putConverter("sex", sexConverter)
                .putConverter("birthday", DefaultHandlers.dateConverter);
        //虚拟字段 #order，通过转换器生成序号
        sheetInfo.putConverter("#order", DefaultHandlers.orderConverter);
        //虚拟字段 #countGrade，通过转换器生成总分数
        sheetInfo.putConverter("#countGrade", countGradeConverter);

        SXSSFWorkbook workbook = ExcelBuilder.createWorkbook(sheetInfo);
        FileOutputStream file = new FileOutputStream("simpleConverterVirtual.xlsx");
        workbook.write(file);
        file.close();
        workbook.dispose();
    }

    *//**
     * 在simpleConverterVirtual的基础上
     * 使用Counter，对 性别列 进行统计
     *
     * @throws Exception
     *//*
    public void simpleConverterVirtualCounter() throws Exception {
        String sheetName = "计算机学院";
        String title = "学生信息表";
        String[] columnNames = {"序号", "姓名", "身份证号", "性别", "出生日期", "语文分数", "数学分数", "英语分数", "总分数"};
        String[] classFieldNames = {"#order", "name", "cardId", "sex", "birthday", "grade.chineseGrade", "grade.mathGrade", "grade.englishGrade", "#countGrade"};
        SheetInfo sheetInfo = new SheetInfo(sheetName, title, columnNames, classFieldNames, studentList);
        *//*
         * 转换器
         *//*
        sheetInfo.putConverter("sex", sexConverter)
                .putConverter("birthday", DefaultHandlers.dateConverter)
                .putConverter("#order", DefaultHandlers.orderConverter)
                .putConverter("#countGrade", countGradeConverter);
        *//*
         * 统计器
         *//*
        //序号列，统计结果显示“合计”字样
        sheetInfo.putCounter("#order", DefaultHandlers.orderCounter);
        //统计男女人数
        sheetInfo.putCounter("sex", sexCounter);

        SXSSFWorkbook workbook = ExcelBuilder.createWorkbook(sheetInfo);
        FileOutputStream file = new FileOutputStream("simpleConverterVirtualCounter.xlsx");
        workbook.write(file);
        file.close();
        workbook.dispose();
    }

    *//**
     * 在simpleConverterVirtualCounter基础上
     * 设置自定义style，对60分以下的分数标红，纯数字列自适应会遮挡，对身份证长度进行调整
     *
     * @throws Exception
     *//*
    public void sheetStyle() throws Exception {
        String sheetName = "学生信息表";
        String title = "学生信息表";
        String[] columnNames = {"序号", "姓名", "身份证号", "性别", "出生日期", "语文分数", "数学分数", "英语分数", "总分数"};
        String[] classFieldNames = {"#order", "name", "cardId", "sex", "birthday", "grade.chineseGrade", "grade.mathGrade", "grade.englishGrade", "#countGrade"};
        SheetInfo sheetInfo = new SheetInfo(sheetName, title, columnNames, classFieldNames, studentList);
        *//*
         * 转换器 统计器
         *//*
        sheetInfo.putConverter("sex", sexConverter)
                .putConverter("birthday", DefaultHandlers.dateConverter)
                .putConverter("#order", DefaultHandlers.orderConverter)
                .putConverter("#countGrade", countGradeConverter)
                .putCounter("#order", DefaultHandlers.orderCounter)
                .putCounter("sex", sexCounter);
        *//*
         * 自定义样式，对分数列小于60分的分数设置为红色字体
         *//*
        sheetInfo.setSheetStyle(new MySheetStyle(sheetInfo));

        SXSSFWorkbook workbook = ExcelBuilder.createWorkbook(sheetInfo);
        FileOutputStream file = new FileOutputStream("sheetStyle.xlsx");
        workbook.write(file);
        file.close();
        workbook.dispose();
    }

    *//**
     * 两个sheet
     *
     * @throws Exception
     *//*
    @Test
    public void twoSheet() throws Exception {
        String sheetName1 = "学生信息表";
        String title1 = "学生信息表";
        String[] columnNames1 = {"序号", "姓名", "身份证号", "性别", "出生日期"};
        String[] classFieldNames1 = {"#order", "name", "cardId", "sex", "birthday"};
        SheetInfo sheetInfo1 = new SheetInfo(sheetName1, title1, columnNames1, classFieldNames1, studentList);
        sheetInfo1.putConverter("#order", DefaultHandlers.orderConverter)
                .putConverter("sex", sexConverter)
                .putConverter("birthday", DefaultHandlers.dateConverter)
                .putCounter("#order", DefaultHandlers.orderCounter)
                .putCounter("sex", sexCounter)
                .setSheetStyle(new MySheetStyle(sheetInfo1));

        String sheetName2 = "学生分数表";
        String title2 = "学生分数表";
        String[] columnNames2 = {"序号", "姓名", "语文分数", "数学分数", "英语分数", "总分数"};
        String[] classFieldNames2 = {"#order", "name", "grade.chineseGrade", "grade.mathGrade", "grade.englishGrade", "#countGrade"};
        SheetInfo sheetInfo2 = new SheetInfo(sheetName2, title2, columnNames2, classFieldNames2, studentList);
        sheetInfo2.putConverter("#order", DefaultHandlers.orderConverter)
                .putConverter("#countGrade", countGradeConverter)
                .setSheetStyle(new MySheetStyle(sheetInfo2));

        List<SheetInfo> sheetInfos = new ArrayList<>();
        sheetInfos.add(sheetInfo1);
        sheetInfos.add(sheetInfo2);

        SXSSFWorkbook workbook = ExcelBuilder.createWorkbook(sheetInfos);
        FileOutputStream file = new FileOutputStream("twoSheet.xlsx");
        workbook.write(file);
        file.close();
        workbook.dispose();
    }

    *//**
     * 性别Converter
     * value是Converter所属字段所对应的值
     * listIndex是value所属对象所在list的index
     * columnIndex是当前字段名所在classFieldNames的index
     *//*
    static Converter sexConverter = (SheetInfo sheetInfo, Object value, int listIndex, int columnIndex) -> {
        *//*
         *基本类型换被转换为对应的包装类型，这里int对应的是Integer，使用equals判断相等
         *//*
        if (value.equals(1)) {
            return "男生";
        } else {
            return "女生";
        }
    };
    *//**
     * 总分数Converter
     *//*
    static Converter countGradeConverter = (SheetInfo sheetInfo, Object value, int listIndex, int columnIndex) -> {
        *//*
         * 虚拟字段传入的value是当前完整对象，在本例中是student对象
         *//*
        if (value instanceof Student) {
            Student student = (Student) value;
            Grade grade = student.getGrade();
            if (grade != null) {
                return grade.getChineseGrade() + grade.getMathGrade() + grade.getEnglishGrade();
            }
        }
        return null;
    };
    *//**
     * 性别统计 Counter
     * result 保存了当前的合计结果，使用自定义类型要注意重写toString方法，以便在统计行显示自己想要的结果
     *//*
    static Counter sexCounter = (SheetInfo sheetInfo, Object value, int listIndex, int columnIndex, Object result) -> {
        if (result == null) {
            result = new SexCountResult();
        }
        if (result instanceof SexCountResult) {
            SexCountResult sexCountResult = (SexCountResult) result;
            if (value.equals(1)) {
                sexCountResult.addManNum();
            } else {
                sexCountResult.addWomanNum();
            }
        }
        return result;
    };

    String[] chinese = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖", "拾"};

    private Student genStudent(int i) {
        Student student = new Student();
        student.setName("张" + chinese[i % 11]);
        student.setCardId("11111111111111111" + i % 10);
        student.setSex(i % 2);
        student.setBirthday(new Date(new Date().getTime() - 15 * 365 * 24 * 60 * 60 * 1000));
        Grade grade = new Grade();
        grade.setChineseGrade(i % 10 * 10 + i % 10);
        grade.setMathGrade(i % 10 * 10 + i % 10);
        grade.setEnglishGrade(i % 10 * 10 + i % 10);
        student.setGrade(grade);
        return student;
    }*/



}