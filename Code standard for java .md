# Code standard for java

## JAVA书写规范

### 1. 命名

1.1 Class/Interface：大写字母开头而其他字母都小写；类的名字应是名词，混合大小写，每个词的第一个字母大写。尽量保证你的类的名字简单并是描述性的。使用完整的单词-避免头字语和缩写（除非缩写比长的格式更广泛使用，例如URL or HTML）class ImageSprite , interface RasterDelegate

1.2 方法应是动词，混合大小写，第一个字母小写，每个内部的词的第一个字母大写；除了变量，所有的实例、类和类的常量都是以小写字母开头的混和大小写。内部的词以大写字母开头。变量名字不应以下划线或美元符$开始，尽管二者都是允许的。run() ,runFast() , getBackground()

1.3 变量名字应简短但有意义。变量名字的选择应该是可记忆的---就是说给普通的人指出它的使用的目的，除非是临时的。临时变量的一般的名字对于整形变量是i,j,k,m,和n，对于字符是c,d,和e。int i;char c;float myWidth

```jsx
static final int MIN_WIDTH = 4;static final int MAX_WIDTH = 999;
```

### 2. 文件

2.1 开头：注释(name,version，date,copyright)，package,import；/** Classname** Version information** Date** Copyright notice*/package java.awt;import java.awt.peer.CanvasPeer;

2.2 Class/Interface：JavaDoc文档注释，体（包含程序、运行和其他注释）；变量，实例，方法等：按public，protect，private排列；尽量少用public。参见最后example

### 3. 缩进

3.1 避免每行多于80 个字符，逗号后或运算符前断开

```jsx
someMethod(longExpression1, longExpression2, longExpression3,longExpression4, longExpression5);
```

```jsx
var = someMethod1(longExpression1,someMethod2(longExpression2,longExpression3));
```

3.2 较高级的断开比较低级的断开更好，新行应与同级上一行的开头对齐；以下是断开算术式的两个例子。第一个较好，因为断开出现在算术式以外，处于较高一级。

```jsx
longName1 = longName2 * (longName3 + longName4 - longName5)+ 4 * longname6; // PREFER
```

```jsx
longName1 = longName2 * (longName3 + longName4- longName5) + 4 * longname6; // AVOID
```

3.3 缩排的一个单位应使用4 个空格，不使用制表符而尽量使用空格。通常语句使用8个空格缩进，就要折行，由于传统的（4个空格）缩进使观察程序体比较难。例如：

```jsx
if ((condition1 && condition2)|| (condition3 && condition4)||!(condition5 && condition6)) { file://BAD WRAPSdoSomethingAboutIt(); file://MAKE THIS LINE EASY TO MISS}
```

```jsx
file://USE THIS INDENTATION INSTEADif ((condition1 && condition2)|| (condition3 && condition4)||!(condition5 && condition6)) {doSomethingAboutIt();}
```

### 4. 注释

Java程序有两类注释：文档注释和执行注释。

4.1 文档注释只有java具有，/**...*/。能够被javadoc 工具生成HTML文档，描述类，接口，构造器，方法和字段，相当于使用指南。/*** Class description goes here.** @version 1.82 18 Mar 1999* @author Firstname Lastname*/

4.2 执行注释是那些在使用在C++中的/*...*/和//。目的为了理解程序和运行，包括不适合于文档注释的内容，块状或一行，使用/*...*/，注释程序可使用//，也可放在短行后面，尽量对齐。

```jsx
/* A class implementation comment can go here. */// implementation comment
```

### 5. 声明

5.1 每行一个声明，加以注释；

int level; // indentation levelint size; // size of table

5.2 不要在同一行放置不同的类型；

```jsx
int foo, fooarray[]; file://WRONG!
```

5.3 只在块的开始处，放置声明；第一次使用时再声明，增强代码的可移植性。

```jsx
if (condition) {int int2 = 0; // beginning of "if" block...}for (int i = 0; i < maxLoops; i++) { ... }
```

5.4 方法后紧跟"(" ，"{"出现在行尾。Sample(int i, int j) {ivar1 = i;ivar2 = j;}

### 6. 语句

6.1 每行最多包括一个语句；argv++; // Correctargc--; // Correctargv++; argc--; // AVOID!if (condition) {statements;} else {statements;}for (initialization; condition; update) {statements;}

6.2 比较重要的一个：try {statements;}catch (ExceptionClass e) {statements;}

### 7. 空格

7.1 利用空行分隔代码段来提高可读性；

7.2 使用空行：类，接口，方法之间，在一个方法的本地变量和它的第一个语句间；

7.3 被一个圆括号跟着的关键字应被一个空格所分开，例"while (true) { ",但方法不分开。

7.4 参数列表中的逗号之后使用一个空格。

```jsx
public void doSomethingElse(Object someParam, String twoParam)
```

7.5 二进制的操作符与数以空格分开,例"a = (a + b) / (c * d);" 。

### 8. 其它

8.1 避免使用一个对象来访问一个类的（静态）变量或方法。而是使用一个类的名字；

```jsx
classMethod(); file://OKAClass.classMethod(); file://OKanObject.classMethod(); file://AVOID!
```

8.2 避免分配多个变量给同样值在一个单独的语句中；fooBar.fChar = barFoo.lchar = 'c'; // AVOID!

8.3 适当使用()和{}来分隔运算和程序。

### 9. 性能有关 (优化代码，调试，运行)

避免太多的使用 synchronized 关键字 ；尽量使用 StringBuffer 对象；尽量不要混合使用AWT 和 Swing 组件，等等。

下面是一个参考范例。

```jsx
/** @(#)Blah.java 1.82 99/03/18** Copyright (c) 1994-1999 Sun Microsystems, Inc.* 901 San Antonio Road, Palo Alto, California, 94303, U.S.A.* All rights reserved.** This software is the confidential and proprietary information of Sun* with Sun.....*/

package java.blah;

import java.blah.blahdy.BlahBlah;

/*** Class description goes here.** @version 1.82 18 Mar 1999* @author Firstname Lastname*/public class Blah extends SomeClass {/* 执行注释. */

/** classVar1 文档注释*/public static int classVar1;

/*** classVar2 多于一行的文档注释* 注释*/private static Object classVar2;

/** instanceVar1 文档注释 */public Object instanceVar1;

/** instanceVar2 文档注释 */protected int instanceVar2;

/** instanceVar3 文档注释 */private Object[] instanceVar3;

/*** ...构造器 Blah 文档注释...*/public Blah() {// ...执行注释 goes here...}

/*** ...方法 doSomething 文档注释...*/public void doSomething() {// ...执行注释 goes here...}

/*** ...方法 doSomethingElse文档注释..* @param someParam参数描述*/public void doSomethingElse(Object someParam) {// ...执行注释goes here...}
```