package com.winterchen.service.user;

import com.winterchen.dao.VpfnMapper;
import com.winterchen.model.Vpfn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class VpfnService {
    @Autowired
    VpfnMapper vpfnMapper;

    public void getMapper(String type) throws IOException {
        File file = null;
        if ("V".equals(type)) {
            file = new File("E:\\view.txt");
        } else if ("P".equals(type)) {
            file = new File("E:\\proc.txt");
        } else if ("FN".equals(type)) {
            file = new File("E:\\fun.txt");
        }

        File resource = new File("C:\\bgy\\optimization_backend\\service-resources\\src\\main\\resources\\mybatis");
        File customer = new File("C:\\bgy\\optimization_backend\\service-customer\\src\\main\\resources\\mybatis");

        List<File> file1 = DataService.getFile(resource);
        List<File> file2 = DataService.getFile(customer);

        file1.addAll(file2);

        HashSet<String> set = DataService.txt2Set(file);


        ArrayList<Vpfn> vpfns = new ArrayList<>();
        for (String name : set) {
            for (File file3 : file1) {
                String s = DataService.txt2String(file3);
                if (isContain(s.toLowerCase(), name.toLowerCase())) {
                    Vpfn vpfn = new Vpfn();
                    vpfn.setName(name);
                    vpfn.setSource(file3.getName());
                    vpfn.setType(type);
                    vpfns.add(vpfn);
                    if ("V".equals(type)) {
                        File view = new File("E:\\corp\\view\\" + name + ".sql");
                        File source = new File("C:\\Users\\Administrator\\Desktop\\view\\dbo." + name + ".View.sql");
                        FileCopyUtils.copy(source, view);

                    } else if ("P".equals(type)) {
                        File view = new File("E:\\corp\\proc\\" + name + ".sql");
                        File source = new File("C:\\Users\\Administrator\\Desktop\\proc\\dbo." + name + ".StoredProcedure.sql");
                        FileCopyUtils.copy(source, view);

                    } else if ("FN".equals(type)) {
                        File view = new File("E:\\corp\\fun\\" + name + ".sql");
                        File source = new File("C:\\Users\\Administrator\\Desktop\\fun\\dbo." + name + ".UserDefinedFunction.sql");
                        FileCopyUtils.copy(source, view);

                    }
                    vpfnMapper.insert(vpfn);

                }
            }
        }

    }

    public void updateFile(String type) throws IOException {
        Vpfn vpfn = new Vpfn();
        if ("V".equals(type)) {
            vpfn.setType("V");
        } else if ("P".equals(type)) {
            vpfn.setType("P");
        } else if ("FN".equals(type)) {
            vpfn.setType("FN");
        }
        List<Vpfn> list = vpfnMapper.select(vpfn);
        List<Vpfn> all = vpfnMapper.selectAll();

        for (Vpfn vpfn1 : list) {
            File source = null;
            if ("V".equals(type)) {
                source = new File("C:\\Users\\Administrator\\Desktop\\view\\dbo." + vpfn1.getName() + ".View.sql");
            } else if ("P".equals(type)) {
                source = new File("C:\\Users\\Administrator\\Desktop\\proc\\dbo." + vpfn1.getName() + ".StoredProcedure.sql");
            } else if ("FN".equals(type)) {
                source = new File("C:\\Users\\Administrator\\Desktop\\fun\\dbo." + vpfn1.getName() + ".UserDefinedFunction.sql");
            }

            String s = DataService.txt2String(source);
            StringBuilder sb = new StringBuilder();
            sb.append(s);
            HashSet<String> use = new HashSet<>();

            for (Vpfn vpfn2 : all) {

                String name = vpfn2.getName();
                int index = 0;
                String s1 = sb.toString();
                System.out.println(name);

                while ((index = containIndex(sb.toString(), name)) != -1) {
                    sb.replace(index, index + name.length(), name +"_NEU");
                }


                String s2 = replaceOneComment(s1);
                String s3 = replaceComments(s2);
                System.out.println("-----1");
                int i;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(s3);
                while ((i = containIndex(stringBuilder.toString(), name)) != -1) {
                    stringBuilder.replace(i, i + name.length(), name +"_NEU");
                    if (!name.equals(vpfn1.getName())) {
                        use.add(name);
                    }
                }
                System.out.println("-----2");

            }
            File file3 =null;
            if ("V".equals(type)) {
                file3 = new File("E:\\corp\\view_neu\\" + vpfn1.getName() + "_NEU.sql");
            } else if ("P".equals(type)) {
                file3 = new File("E:\\corp\\proc_neu\\" + vpfn1.getName() + "_NEU.sql");
            } else if ("FN".equals(type)) {
                file3 = new File("E:\\corp\\fun_neu\\" + vpfn1.getName() + "_NEU.sql");
            }
            file3.createNewFile();

            FileWriter fw = new FileWriter(file3.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(sb.toString());
            bw.close();
            StringBuilder stringBuilder = new StringBuilder();
            for (String s1 : use) {
                stringBuilder.append(s1 + ",");
            }
            vpfn1.setUsesource(stringBuilder.toString());

            vpfnMapper.updateByPrimaryKey(vpfn1);

        }


    }

    public void getFromVpfn(String type) throws IOException {
        File file = null;
        if ("V".equals(type)) {
            file = new File("E:\\view.txt");
        } else if ("P".equals(type)) {
            file = new File("E:\\proc.txt");
        } else if ("FN".equals(type)) {
            file = new File("E:\\fun.txt");
        }

        File fun = new File("E:\\corp\\fun");
        File proc = new File("E:\\corp\\proc");
        File view = new File("E:\\corp\\view");

        List<File> file1 = DataService.getFile(fun);
        List<File> file2 = DataService.getFile(proc);
        List<File> file3 = DataService.getFile(view);

        file1.addAll(file2);
        file1.addAll(file3);

        HashSet<String> set = DataService.txt2Set(file);


        for (String name : set) {
            for (File file4 : file1) {

                String s1 = DataService.txt2String(file4);
                String s2 = replaceOneComment(s1);
                String s = replaceComments(s2);

                if (isContain(s.toLowerCase(), name.toLowerCase())) {

                    Vpfn vpfn = new Vpfn();
                    vpfn.setName(name);
                    vpfn.setSource(file4.getName());
                    vpfn.setType(type);
                    String replace = file4.getName().replace(".sql", "");
                    if (name.equals(replace)) {
                        continue;
                    }

                    if ("V".equals(type)) {
                        File view1 = new File("E:\\corp\\view\\" + name + ".sql");
                        File source = new File("C:\\Users\\Administrator\\Desktop\\view\\dbo." + name + ".View.sql");
                        FileCopyUtils.copy(source, view1);

                    } else if ("P".equals(type)) {
                        File view1 = new File("E:\\corp\\proc\\" + name + ".sql");
                        File source = new File("C:\\Users\\Administrator\\Desktop\\proc\\dbo." + name + ".StoredProcedure.sql");
                        FileCopyUtils.copy(source, view1);

                    } else if ("FN".equals(type)) {
                        File view1 = new File("E:\\corp\\fun\\" + name + ".sql");
                        File source = new File("C:\\Users\\Administrator\\Desktop\\fun\\dbo." + name + ".UserDefinedFunction.sql");
                        FileCopyUtils.copy(source, view1);

                    }

                    List<Vpfn> list = vpfnMapper.select(vpfn);
                    if (list.size() == 0) {
                        vpfnMapper.insert(vpfn);
                    }

                }
            }
        }

    }


    public static void main(String[] args) {

        /*String a = "Pr";
        String b = "Proc Pr Pr";

        boolean contain = isContain(b, a);
        System.out.println(contain);

        StringBuilder sb = new StringBuilder();
        sb.append(b);

        Integer index = 0;
        while ((index = containIndex(sb.toString(), a)) != -1) {
            sb.replace(index, index + a.length(), "Pr_NEU");
        }


        System.out.println(sb.toString());*/

        File source = new File("C:\\Users\\Administrator\\Desktop\\dbo.funGetParkExpired.UserDefinedFunction.sql");
        String s = DataService.txt2String(source);

        String s2 = replaceComments(s);
        String s1 = replaceOneComment(s2);

        System.out.println(s1);

    }
    
    public static String replaceComments(String s){
        StringBuilder sb = new StringBuilder();
        sb.append(s);

        int i;
        while ((i=s.indexOf("/*") )!= -1) {
            int i1 = s.indexOf("*/");
            sb.replace(i,i1+2,"");
            s = sb.toString();
        }
        return sb.toString();
    }

    public static String replaceOneComment(String s){
        StringBuilder sb = new StringBuilder();
        sb.append(s);

        int i;
        while ((i=s.indexOf("--") )!= -1) {
            int i1 = s.indexOf("\r\n",i);

            sb.replace(i,i1,"");

            s = sb.toString();
        }
        return sb.toString();
    }

    public static List<Integer> findAllIndex(String string, int index, String findStr) {
        List<Integer> list = new ArrayList<>();
        if (index != -1) {
            int num = string.indexOf(findStr, index);
            list.add(num);
            //递归进行查找
            List myList = findAllIndex(string, string.indexOf(findStr, num + 1), findStr);
            list.addAll(myList);
        }
        return list;
    }

    public static boolean isContain(String content, String value) {

        List<Integer> allIndex = findAllIndex(content, 0, value);

        boolean flag = true;

        for (Integer i : allIndex) {
            if (i == -1) {
                return false;
            }

            char c = 0;
            try {
                c = content.charAt(i + value.length());
            } catch (Exception e) {
                return true;
            }

            char[] arr = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'
                    , 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
                    'U', 'V', 'W', 'X', 'Y', 'Z',
                    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'
                    , 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
                    'u', 'v', 'w', 'x', 'y', 'z', '_'
            };
            flag = true;

            for (char c1 : arr) {
                if (c1 == c) {
                    flag = false;
                }
            }

        }

        return flag;


    }


    public static Integer containIndex(String content, String value) {

        List<Integer> allIndex = findAllIndex(content.toLowerCase(), 0, value.toLowerCase());
        int index = -1;

        for (Integer i : allIndex) {


            boolean flag = true;

            if (i == -1) {
                return -1;
            }

            char c = 0;
            try {
                c = content.charAt(i + value.length());
            } catch (Exception e) {
                flag = true;
                index = i;
                break;
            }

            char[] arr = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'
                    , 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
                    'U', 'V', 'W', 'X', 'Y', 'Z',
                    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'
                    , 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
                    'u', 'v', 'w', 'x', 'y', 'z', '_'
            };

            for (char c1 : arr) {
                if (c1 == c) {
                    flag = false;
                }
            }

            if (flag) {
                index = i;
                break;
            }
        }


        return index;
    }

}

