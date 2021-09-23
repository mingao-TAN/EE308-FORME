package com.witwicky.util;

import java.io.BufferedReader;

import java.io.BufferedWriter;

import java.io.File;

import java.io.FileInputStream;

import java.io.FileOutputStream;

import java.io.InputStreamReader;

import java.io.OutputStreamWriter;

/**

* ɾ��Java�����е�ע��

*

* @author Alive

* @build 2010-12-23

*/

public class DeleteComments {
private static int count = 0;

/**

* ɾ���ļ��еĸ���ע�ͣ�����//��/* * /��

*

* @param charset

* �ļ�����

* @param file

* �ļ�

*/

public static void clearComment(File file, String charset) {
try {
// �ݹ鴦���ļ���

if (!file.exists()) {
return;

}

if (file.isDirectory()) {
File[] files = file.listFiles();

for (File f : files) {
clearComment(f, charset); // �ݹ����

}

return;

} else if (!file.getName().endsWith(".java")) {
// ��java�ļ�ֱ�ӷ���

return;

}

System.out.println("-----��ʼ�����ļ���" + file.getAbsolutePath());

// ���ݶ�Ӧ�ı����ʽ��ȡ

BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));

StringBuffer content = new StringBuffer();

String tmp = null;

while ((tmp = reader.readLine()) != null) {
content.append(tmp);

content.append("\n");

}

reader.close();

String target = content.toString();

// String s =

// target.replaceAll("\\/\\/[^\\n]*|\\/\\*([^\\*^\\/]*|[\\*^\\/*]*|[^\\**\\/]*)*\\*\\/",

// ""); 

String s = target.replaceAll("\\/\\/[^\\n]*|\\/\\*([^\\*^\\/]*|[\\*^\\/*]*|[^\\**\\/]*)*\\*+\\/", "");

// System.out.println(s);

// ʹ�ö�Ӧ�ı����ʽ���

BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), charset));

out.write(s);

out.flush();

out.close();

count++;

System.out.println("-----�ļ��������---" + count);

} catch (Exception e) {
e.printStackTrace();

}

}

public static void clearComment(String filePath, String charset) {
clearComment(new File(filePath), charset);

}

public static void clearComment(String filePath) {
clearComment(new File(filePath), "UTF-8");

}

public static void clearComment(File file) {
clearComment(file, "UTF-8");

}

public static void main(String[] args) {
// clearComment("D:\\eclipse3.3\\workspace\\Sanguosha"); //

// ɾ��Ŀ¼������java�ļ�ע��

// ɾ��ĳ�������ļ���ע��

clearComment("C:\\Users\\Administrator\\Desktop\\Observable.java");

}

}
