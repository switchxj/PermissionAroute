package com.qzzx.paroute.regist

import com.android.build.api.transform.Context
import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.Format
import com.android.build.api.transform.JarInput;
import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformInput
import com.android.build.api.transform.TransformOutputProvider
import com.android.build.gradle.internal.pipeline.TransformManager
import javassist.ClassPool
import javassist.CtClass
import javassist.CtMethod
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils
import org.gradle.api.Project

import java.util.Set
import java.util.function.Consumer;

/**
 * Created by Administrator on 2018/8/7 0007.
 */

public class RegistTransform extends Transform {

    Project project;
    CtClass load;
    JarInput jarInput;

    String registClass = "com.qzzx.android.paroute.launcher.PAroute";

    ArrayList<String> list = new ArrayList<String>();


    public RegistTransform(Project project) {
        this.project = project;
    }


    @Override
    public String getName() {
        return "paroutregisttransform";
    }

    // 指定输入的类型，通过这里的设定，可以指定我们要处理的文件类型
    //这样确保其他类型的文件不会传入
    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    // 指定Transform的作用范围
    @Override
    Set<QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    public boolean isIncremental() {
        return false;
    }

    //具体的处理
    @Override
    void transform(Context context, Collection<TransformInput> inputs,
                   Collection<TransformInput> referencedInputs,
                   TransformOutputProvider outputProvider, boolean isIncremental) {

        System.out.println("开始装换-------------------------------------" + Thread.currentThread().getName() + "  " + Thread.currentThread().getId());

        inputs.each { TransformInput input ->
            input.directoryInputs.size()


            def directSize = input.directoryInputs.size();

            input.directoryInputs.each {

                DirectoryInput directoryInput ->
                    //         classPool.appendClassPath(directoryInput.file.absolutePath)
                    CtClass load;
                    CtMethod loadMethod;



                    directoryInput.file.eachFileRecurse {
                        File file ->


                            String des = file.absolutePath.replace("\\", ".");
                            if (des.contains(Constant.ROOTLOADPREFIX)) {
                                System.out.println("class路径= " + file.absolutePath)
                                list.add(des.substring(des.indexOf(Constant.ROOTLOADPREFIX)));
                            }

                    }

                    // 获取output目录
                    def dest = outputProvider.getContentLocation(directoryInput.name,
                            directoryInput.contentTypes, directoryInput.scopes,
                            Format.DIRECTORY)

                    // 将input的目录复制到output指定目录
                    FileUtils.copyDirectory(directoryInput.file, dest)
            }



            input.jarInputs.each {
                JarInput jarInput ->

                    def jarName = jarInput.name


                    boolean isCopy = true;


                    def dess;

                    if (load == null) {
                        try {
                            load = changemethod(jarInput.file.absolutePath)
                            isCopy = false;
                            this.jarInput = jarInput;
                            System.out.println("找到了：" + jarInput.file.absolutePath);
                        } catch (Exception ex) {
                            System.out.println("没有找到");
                        }
                    }

                    if (isCopy) {
                        dess = copyJar(jarInput, jarName, outputProvider, null)
                    }

            }


        }

        File newJar;
        System.out.println("开始插入 111111111111111----------");
        if (load != null) {
            if (load.isFrozen()) {
                load.defrost();
            }
            System.out.println("开始插入 222222222222211111111----------");
            final CtMethod loadMethod = load.getDeclaredMethod("wareHouse", null);
            System.out.println("开始插入 11111111111112222222222222----------");
            list.forEach(new Consumer<String>() {
                @Override
                void accept(String s) {
                    System.out.println("开始插入 3333333333333----------");
                    System.out.println(" s=" + s);
                    if (loadMethod == null) {
                        System.out.println("开始插入 898989898----------");

                    } else {
                        System.out.println(" 开始插入78787878----------" + s);
                        //  s="com.qzzx.android.paroute.appearance.template.IRouterRoot.class"
                        loadMethod.insertAfter("loadwareHouse(\"" + s + "\");");
                    }
                }
            })
            System.out.println("开始插入 999999999998888----------");

            File registFile = new File("registFile");
            System.out.println("registFile=" + registFile.absolutePath);
            if (!registFile.exists()) {
                registFile.mkdirs();
            }
            load.writeFile(registFile.absolutePath);
            newJar = new File(registFile, jarInput.file.name);
            FileUtils.copyFile(jarInput.file, newJar);
            boolean result = replaceJarClss(newJar.absolutePath, registFile.absolutePath, registClass.replace(".", "\\") + ".class");
            System.out.println("result=" + result);

        }


        System.out.println("开始插入 4444444444444444444----------");
        if (jarInput != null) {

            copyJar(jarInput, jarInput.name, outputProvider, newJar)
        }
        jarInput = null;
        if (load != null) {
            load.detach()
        }
        load = null;

//        classPool.clearImportedPackages();
//        classPool = null;

        System.out.println("全完了 111111111111111----------");

    }

    /**
     * 使用命令行的方式替换jar文件内的class文件
     *
     * @param jarPath jar的路径
     * @param classRootPath class的所在的根路径（去除包名的路径）
     * @param classPath class 所在的路径
     * @return 返回true 代表成功 false 代表失败
     */
    static boolean replaceJarClss(String jarPath, String classRootPath, String classPath) {

        boolean isSuccess = true;

        StringBuffer command = new StringBuffer();

        command.append("jar -uvf ").
                append(jarPath).append(" ").append(classPath);

        try {
            Process process = Runtime.getRuntime().exec(command.toString(), null, new File(classRootPath));
//执行命令生成cube
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


    private CtClass changemethod(String path) {
        CtClass load;
        ClassPool classPool = new ClassPool();
        classPool.insertClassPath(path);
        load = classPool.get(registClass);


        return load;

    }

    private String copyJar(JarInput jarInput, String jarName, TransformOutputProvider outputProvider, File newJar) {


        if (newJar != null) {
            System.out.println('newJar=' + newJar.absolutePath);
        }
        def md5Name = DigestUtils.md5Hex(newJar == null ? jarInput.file.getAbsolutePath() : newJar.absolutePath)
        if (jarName.endsWith(".jar")) {

            jarName = jarName.subSequence(0, jarName.length() - 4);

        }

        def dest = outputProvider.getContentLocation(jarName + md5Name
                , jarInput.contentTypes, jarInput.scopes, Format.JAR)
        System.out.println("src=" + jarInput.file.getAbsolutePath());
        System.out.println("dest=" + dest);

        FileUtils.copyFile(newJar == null ? jarInput.file : newJar, dest)

        return dest;
    }

}
