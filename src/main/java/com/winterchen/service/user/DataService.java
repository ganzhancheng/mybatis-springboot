package com.winterchen.service.user;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.winterchen.DBUtils;
import com.winterchen.DatabaseUtil;
import com.winterchen.dao.AltersqlMapper;
import com.winterchen.dao.ColumnsMapper;
import com.winterchen.dao.DataMapper;
import com.winterchen.model.Altersql;
import com.winterchen.model.Columns;
import com.winterchen.model.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DataService {


    @Autowired
    DataMapper dataMapper;
    @Autowired
    ColumnsMapper columnsMapper;

    @Autowired
    AltersqlMapper altersqlMapper;



    public void addData(String corpid) {
        DatabaseUtil.setURL(corpid);
        ArrayList<Data> list = new ArrayList<>();
        List<String> tableNames = DatabaseUtil.getTableNames();
        for (String tableName : tableNames) {
            List<Columns> columnNames = DatabaseUtil.getColumnNames(tableName, Integer.parseInt(corpid));
            StringBuilder stringBuffer = new StringBuilder();
            for (Columns columnName : columnNames) {
                stringBuffer.append(columnName.getColumnName() + ",");
            }

            Data data = new Data();
            data.setCorpid(Integer.parseInt(corpid));
            data.setTableName(tableName);
            data.setColumns(stringBuffer.toString());
            list.add(data);
            if (columnNames.size() > 0) {
                columnsMapper.insertList(columnNames);
            }
        }
        DBUtils.insertListExcute(list,dataMapper,100);
    }


    public void check(String corpid){
        DatabaseUtil.setURL(corpid);

        Data data1 = new Data();
        data1.setIsuse("true");
        List<Data> datas = dataMapper.select(data1);

        ArrayList<Altersql> altersqls = new ArrayList<>();

        for (Data data : datas) {
            String tableName = data.getTableName();

            try {
                List<String> columnName = DatabaseUtil.getColumnName(tableName);

                String columns = data.getColumns();

                String[] arr = columns.split(",");
                ArrayList<String> list = new ArrayList<>();
                for (int i = 0; i < arr.length; i++) {
                    String col = arr[i];
                    list.add(col);
                }

                list.removeAll(columnName);


                if (list.size() > 0) {
                    for (String col : list) {
                        Columns columns1 = new Columns();
                        columns1.setTableName(tableName);
                        columns1.setColumnName(col);
                        List<Columns> select = columnsMapper.select(columns1);
                        Columns columns2 = select.get(0);

                        Altersql altersql = new Altersql();
                        altersql.setCorpid(Integer.parseInt(corpid));

                        StringBuilder sb = new StringBuilder();
                        sb.append("ALTER TABLE [dbo].[" + columns2.getTableName() + "] ADD [" + columns2.getColumnName() + "] " + columns2.getColumnType());

                        if ("timestamp".equals(columns2.getColumnType())||
                                "bigint".equals(columns2.getColumnType())||
                                "smallint".equals(columns2.getColumnType()) ||
                                "datetime".equals(columns2.getColumnType())
                        ) {
                            sb.append(" " + columns2.getNullable());
                        } else if ("decimal".equals(columns2.getColumnType())) {
                            sb.append("(" + (columns2.getSize()-2) + ",2) " + columns2.getNullable());
                        } else {
                            sb.append("(" + columns2.getSize() + ") " + columns2.getNullable());
                        }


                        altersql.setSqls(sb.toString());
                        altersql.setTableName(tableName);
                        altersql.setColumns(col);
                        altersqls.add(altersql);



                    }
                }

            } catch (Exception e) {
                Altersql altersql = new Altersql();
                altersql.setCorpid(Integer.parseInt(corpid));
                altersql.setTableName(tableName);
                altersqls.add(altersql);

            }



        }
        if (altersqls.size() > 0) {

            DBUtils.insertListExcute(altersqls,altersqlMapper,100);
        }
    }

    public void isuse() {

        File file = new File("C:\\bgy\\optimization_backend\\service-resources\\src\\main\\resources");
        File file111 = new File("C:\\bgy\\optimization_backend\\service-customer\\src\\main\\resources");

        ArrayList<File> list = new ArrayList<>();
        ArrayList<File> list2 = new ArrayList<>();

        List<File> file1 = getFile(file, list);

        List<File> file3 = getFile(file111, list2);

        file1.addAll(file3);

        List<Data> data = dataMapper.selectAll();
        for (Data datum : data) {
            String tableName = datum.getTableName();
            System.out.println("=======tableName:"+tableName+"==========");
            for (File file2 : file1) {
                String s = txt2String(file2);
                if (s.toLowerCase().contains(tableName.toLowerCase())) {
                    datum.setIsuse("true");
                    System.out.println("=======tableName:"+tableName+"===true=======");
                    break;
                }
            }
        }

        DBUtils.updateListExcute(data,dataMapper,100);
    }

    public static void main(String[] args) throws IOException {
        File file1 = new File("E:\\corp\\fun");

        File file4 = new File("E:\\1.txt");
        HashSet<String> strings = txt2Set(file4);

        ArrayList<File> files = new ArrayList<>();
        List<File> file2 = getFile(file1, files);
        for (File file : file2) {
            String viewName = file.getName().replace("dbo.", "")
                    .replace(".UserDefinedFunction.sql","")
                    .replace(".View.sql","")
                    .replace(".StoredProcedure.sql","");

            String str = txt2String(file);
            for (String string : strings) {

                if (str.contains(string)) {
                    str= str.replace(string, string + "_NEU");
                }
            }
            // if (str.contains(viewName)) {
           // str = str.replace(viewName, viewName + "_NEU");
            File file3 = new File("E:\\corp\\fun_neu\\" + viewName + "_NEU.sql");
            file3.createNewFile();

            FileWriter fw = new FileWriter(file3.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(str);
            bw.close();

        }

        //}

       method2();
       // method1();


    }

    /**
     * 筛选出可用的视图和存储过程
     * @throws IOException
     */
    private static void method2() throws IOException {
        File file = new File("E:/1.txt");

        HashSet<String> strings = txt2Set(file);

        File file1 = new File("C:\\Users\\Administrator\\Desktop\\fun");
        ArrayList<File> files = new ArrayList<>();
        List<File> file2 = getFile(file1, files);


        List<File> files1 = new ArrayList<>();

        for (String view : strings) {
            List<File> list = file2.stream().filter(f -> f.getName().replace("dbo.","")
                    .replace(".UserDefinedFunction.sql","")
                    .replace(".View.sql","")
                    .replace(".StoredProcedure.sql","")
                    .toLowerCase()
                    .equals(view.replace("\r\n", "").toLowerCase())).collect(Collectors.toList());
            files1.addAll(list);
        }

        for (File file3 : files1) {

            File file4 = new File("E:/corp/fun/" + file3.getName());
            FileCopyUtils.copy(file3, file4);
        }
    }



    /**
     * 找视图和存储过程
     */
    private static void method1() {
        File file = new File("E:/1.txt");
        File view = new File("E:/所有函数.txt");

        HashSet<String> strings = txt2Set(file);
        HashSet<String> views = txt2Set(view);

        File file1 = new File("E:\\corp\\view");
        ArrayList<File> files = new ArrayList<>();

        List<File> file2 = getFile(file1, files);

        HashSet<String> set = new HashSet<>();

        StringBuilder stringBuilder = new StringBuilder();
        for (File file3 : file2) {
            String s = txt2String(file3);
            stringBuilder.append(s);
        }
        String s1 = stringBuilder.toString();
        for (String name : strings) {
            name = name.replace("\r\n", "");

            s1 = s1.toLowerCase();
            if (s1.contains(name.toLowerCase())) {
                set.add(name);
            }
        }

        for (String s : views) {
            set.add(s.replace("\r\n", ""));
        }

        for (String s : set) {

            System.out.println(s);
        }
    }

    public static String txt2String(File file){
        StringBuilder result = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"GB2312"));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                result.append(System.lineSeparator()+s);
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }

    public static HashSet<String> txt2Set(File file){
        HashSet<String> strings = new HashSet<>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                strings.add(s);
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return strings;
    }

    public static List<File> getFile(File file,ArrayList<File> files1){
        File[] files = file.listFiles();
        if (files != null) {

            for (File file1 : files) {
                getFile(file1, files1);
            }

        }else{
            files1.add(file);
        }
        return files1;
    }

    public static List<File> getFile(File file){
        ArrayList<File> list = new ArrayList<>();
        File[] files = file.listFiles();
        if (files != null) {
            for (File file1 : files) {
                List<File> file2 = getFile(file1);
                list.addAll(file2);
            }

        }else{
            list.add(file);
        }
        return list;
    }
}
