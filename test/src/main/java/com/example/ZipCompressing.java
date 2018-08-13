package com.example;

/**
 * Created by Administrator on 2018/8/8 0008.
 */

import java.io.*;
import java.util.zip.*;

/**
 * @author Dana·Li
 *         <p>
 *         程序实现了ZIP压缩[compression]
 *         <p>
 *         大致功能包括用了多态，递归等JAVA核心技术，可以对单个文件和任意级联文件夹进行压缩和解压。 需在代码中自定义源输入路径和目标输出路径。
 *         <p>
 *         在本段代码中，实现的是压缩部分
 */
public class ZipCompressing {
    private int k = 1; // 定义递归次数变量

    public void zip(String zipFileName, File inputFile) throws Exception {
        System.out.println("压缩中...");
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
        BufferedOutputStream bo = new BufferedOutputStream(out);
        zip(out, inputFile, inputFile.getName(), bo);
        bo.close();
        out.close(); // 输出流关闭
        System.out.println("压缩完成");
    }

    private void zip(ZipOutputStream out, File f, String base,
                     BufferedOutputStream bo) throws Exception { // 方法重载
        if (f.isDirectory()) {
            File[] fl = f.listFiles();
            if (fl.length == 0) {
                out.putNextEntry(new ZipEntry(base + "/")); // 创建zip压缩进入点base
                System.out.println(base + "/");
            }
            for (int i = 0; i < fl.length; i++) {
                zip(out, fl[i], base + "/" + fl[i].getName(), bo); // 递归遍历子文件夹
            }
            System.out.println("第" + k + "次递归");
            k++;
        } else {
            out.putNextEntry(new ZipEntry(base)); // 创建zip压缩进入点base
            System.out.println(base);
            FileInputStream in = new FileInputStream(f);
            BufferedInputStream bi = new BufferedInputStream(in);
            int b;
            while ((b = bi.read()) != -1) {
                bo.write(b); // 将字节流写入当前zip目录
            }
            bi.close();
            in.close(); // 输入流关闭
        }
    }

    /**
     * 测试
     *
     * @param args
     */
    public static void main(String[] args) {
        ZipCompressing book = new ZipCompressing();
        try {
            book.zip("F:\\ziptest.zip", new File("F:\\ziptest"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}