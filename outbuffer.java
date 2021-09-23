package main;

import java.io.*;
class Outbuffuer {
public static void main(String arg[])throws IOException{
BufferedReader b = new BufferedReader(newFileReader("C://c.txt"));
boolean c;

do{
String s = b.readLine();

System.out.println(s);

}while(c = b.read()!=-1);
}
}