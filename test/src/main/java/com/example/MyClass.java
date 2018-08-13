package com.example;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CodeConverter;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import javassist.expr.NewArray;

public class MyClass {

    public static void main(String[] args) {
//        try {
//            changemethod("E:\\classes.jar");
//            File f = new File(".\\com\\qzzx\\android\\paroute\\launcher\\PAroute.class");
//            System.out.print(f.getAbsolutePath());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//
//        try {
//            //    replaceFile("E:\\appout\\aa\\com\\qzzx\\android\\paroute\\launcher\\PAroute.class", "E:\\appout\\PAroute.class");
//
//            compressing("E:\\appout\\aa\\com", "E:\\appout\\newjar.jar");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        boolean isResult = replaceJarClss("D:\\test\\PermissionAroute\\paroute_api\\build\\intermediates\\bundles\\default\\classes.jar", "D:\\test\\PermissionAroute\\registFile", "com\\qzzx\\android\\paroute\\launcher\\PAroute.class");
        System.out.print("isResult=" + isResult);

//        File file = new File("");
//        System.out.print("file=" + file.getAbsolutePath());

    }

    /**
     * 使用命令行的方式替换jar文件内的class文件
     *
     * @param jarPath       jar的路径
     * @param classRootPath class的所在的根路径（去除包名的路径）
     * @param classPath     class 所在的路径
     * @return 返回true 代表成功 false 代表失败
     */
    static boolean replaceJarClss(String jarPath, String classRootPath, String classPath) {

        boolean isSuccess = true;

        StringBuffer command = new StringBuffer();

        command.append("jar -uvf ").
                append(jarPath).append(" ").append(classPath);

        try {
            Process process = Runtime.getRuntime().exec(command.toString(), null, new File(classRootPath));//执行命令生成cube
            int result = process.waitFor();
            if (result != 0) {
                isSuccess = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            isSuccess = false;
        }


        return isSuccess;
    }


    /**
     * 替换文件并压缩
     *
     * @param src
     * @param des
     */
    public static void replaceFile(String src, String des) throws IOException {

        File file = new File(src);
        if (file.exists() && file.isFile()) {
            file.delete();
        }
        FileUtils.copyFile(new File(des), file);
    }


    /**
     * 压缩文件
     */
    public static void compressing(String inputPath, String outPath) throws Exception {

        ZipCompressing zipCompressing = new ZipCompressing();
        zipCompressing.zip(outPath, new File(inputPath));
    }


    public static void changemethod(String path) throws Exception {
//取得需要反编译的jar文件，设定路径
        ClassPool classPool = new ClassPool();
        classPool.insertClassPath(path);
        CtClass load = classPool.get("com.qzzx.android.paroute.launcher.PAroute");

        if (load.isFrozen()) {
            load.defrost();
        }
        final CtMethod loadMethod = load.getDeclaredMethod("wareHouse");
        String s = "wrtasdasdfasdasd adasdmkasodn  aop[s";
        loadMethod.insertAfter(" loadwareHouse(\"" + s + "\");");
        loadMethod.insertBefore(" int i=0; i=100; ");
        loadMethod.insertAfter("int i=10;");
        loadMethod.instrument(new ExprEditor() {
            @Override
            public void edit(MethodCall a) throws CannotCompileException {
                super.edit(a);
            }
        });

        load.writeFile();
        load.detach();
        System.out.println("--------------------=" + path);
    }
}
