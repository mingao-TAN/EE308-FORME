package main;

import java.io.*;
import java.util.HashSet;

public class LAB2 {
    static int keywordTotalNum = 0;
    static int switchNum = 0;
    static int [] caseNum = new int[500];
    static int elseifNum = 0;
    static int elseNum = 0;
    static int [] ifelseStack = new int [5000];
    static int stackLen = 0;
    static boolean annotationFlag = false;
    static boolean stringFlag = false;

    // this function is to init the keywords list
    public static void InitKeyWordUtil(HashSet<String> keywords){
        keywords.add("auto");
        keywords.add("break");
        keywords.add("char");
        keywords.add("const");
        keywords.add("continue");
        keywords.add("default");
        keywords.add("do");
        keywords.add("double");
        keywords.add("enum");
        keywords.add("extern");
        keywords.add("float");
        keywords.add("for");
        keywords.add("goto");
        keywords.add("int");
        keywords.add("long");
        keywords.add("register");
        keywords.add("return");
        keywords.add("short");
        keywords.add("signed");
        keywords.add("sizeof");
        keywords.add("static");
        keywords.add("struct");
        keywords.add("typedef");
        keywords.add("union");
        keywords.add("unsigned");
        keywords.add("void");
        keywords.add("volatile");
        keywords.add("while");
    }

    // check if char c is 'a' to 'z'
    public static boolean IsWord(char c){
        return c >= 'a' && c <= 'z';
    }

    // dealing with the stack with "{" , "}" , ";" , "if" , "else if" , "else"
    public static void WorkOnStack(){
        int [] currentStack = new int [4000];
        int now=0;
        /*
        * we assume :
        * { = 6
        * } = 9
        * ; = 0
        * if = 1
        * else if = 2
        * else = 3
        **/
        for(int i = 1 ; i <= stackLen ; i++){
            switch (ifelseStack[i]){
                case 3:
                    if(currentStack[now-1] == 2){
                        // if a "else if" before this else, change the topping "if;else if;" to ";"
                        now -= 4;
                        if(currentStack[now] != 0){
                            currentStack[++now] = 0;
                        }
                        elseifNum++;
                    }else if(currentStack[now-1] == 1){
                        // if a "else if" before this else, change the topping "if;" to ";"
                        now -= 2;
                        if(currentStack[now] != 0){
                            currentStack[++now] = 0;
                        }
                        elseNum++;
                    }
                case 2:
                    // if heading is not a "else if ;", in stack
                    if(currentStack[now] != 0 || currentStack[now-1] != 2){
                        currentStack[++now] = 2;
                    }
                    break;
                case 9:
                    // {}  => ;
                    if(currentStack[now] == 6){
                        if(currentStack[now-1] != 0){
                            currentStack[now] = 0;
                        }else{
                            now--;
                        }
                    // {;} => ;
                    }else if(currentStack[now] == 0 && currentStack[now-1] == 6){
                        if(currentStack[now-2] != 0){
                            currentStack[now-1] = 0;
                        }else{
                            now -= 2;
                        }
                    }
                    break;
                case 0:
                    // won't stack in if the top is already ";"
                    if(currentStack[now] != 0){
                        currentStack[++now] = 0;
                    }
                    break;
                case 1:
                    currentStack[++now] = ifelseStack[i];
                    break;
                case 6:
                    currentStack[++now] = ifelseStack[i];
                    break;
            }
        }
    }

    // delete "//" and following content in this line

    public static String DeleteLineAnnotation(String str){
        for(int i = 0; i < str.length() ;i++){
            if(str.charAt(i) == '/' && str.charAt(i) == '/'){
                return str.substring(0,i);
            }
        }
        return str;
    }

    // mark a annotation flag if /* appears, cancel it until find */
    // we won't work on annotation contents
    public static String DeleteBarAnnotation(String str){
        StringBuilder sb = new StringBuilder(200);
        for(int i = 0; i < str.length() ;i++){
            if(!annotationFlag){
                if(str.charAt(i) == '/' && str.charAt(i+1) == '*'){
                    annotationFlag = true;
                }else{
                    sb.append(str.charAt(i));
                }
            }else{
                if(str.charAt(i) == '*' && str.charAt(i+1) == '/'){
                    i++;
                    annotationFlag = false;
                }
            }
        }
        return sb.toString();
    }

    // same as bar annotation
    public static String DeleteInsideString(String str){
        StringBuilder sb = new StringBuilder(200);
        for(int i = 0; i < str.length() ;i++){
//            System.out.print(str.charAt(i));
//            System.out.println(stringFlag);
            if(!stringFlag){
                if(str.charAt(i) == '"'){
                    stringFlag = true;
                }else{
                    sb.append(str.charAt(i));
                }
            }else{

                if(str.charAt(i) == '"'){
                    stringFlag = false;
                }
            }
        }
        return sb.toString();
    }


    // hanle with each line(without annotation and string)
    public static void HandleLine(String line, HashSet <String> keywords){
        // delete contents we don't want
        line = DeleteLineAnnotation(line);
        line = DeleteInsideString(line);
        line = DeleteBarAnnotation(line);

        int lineLen = line.length();
//        System.out.println(line);

        int headIndex = 0;
        int endIndex = 0;
        boolean inWord = false;
        // ergodic every char in the line
        for (int i = 0; i < lineLen; i++){
            if(IsWord(line.charAt(i))){
                if(!inWord){
                    // find the head to a word
                    headIndex = i;
                    inWord = true;
                }
            }else{
                if(inWord){
                    // find the end to a word
                    endIndex = i;
                    String currentWord = line.substring(headIndex, endIndex);

                    // judge if the word is a keyword
                    switch (currentWord){
                        case "if":
                            keywordTotalNum++;
                            ifelseStack[++stackLen] = 1;
                            i = endIndex;
                            break;
                        case "else":
                            if (endIndex+3 <= line.length() &&line.substring(headIndex, endIndex + 3).equals("else if")) {
                                keywordTotalNum +=2;
                                ifelseStack[++stackLen] = 2;
                                i = endIndex+3;
                            } else {
                                keywordTotalNum++;
                                ifelseStack[++stackLen] = 3;
                                i = endIndex;
                            }
                            break;
                        case "switch":
                            // use a array to store case number of each switch
                            keywordTotalNum++;
                            switchNum++;
                            i = endIndex;
                            break;
                        case "case":
                            keywordTotalNum++;
                            caseNum[switchNum]++;
                            i = endIndex;
                            break;
                        default:
                            // check if this word is a keyword except above
                            for(String str:keywords){
                                if(currentWord.equals(str)){
                                    keywordTotalNum++;
                                    break;
                                }
                            }
                            i = endIndex;
                    }
                    inWord = false;
                }
            }
            // check if this char is { or } or ;
            switch (line.charAt(i)) {
                case '{':
                    ifelseStack[++stackLen] = 6;
                    break;
                case '}':
                    ifelseStack[++stackLen] = 9;
                    break;
                case ';':
                    if (ifelseStack[stackLen] != 0) {
                        ifelseStack[++stackLen] = 0;
                    }
                    break;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        HashSet <String> keywords = new HashSet<String>();
        InitKeyWordUtil(keywords);

        // read file path and requirement level from console
        BufferedReader conReader = new BufferedReader(new InputStreamReader(System.in));
        String filePath;
        System.out.println("Enter the path of file.");
        filePath = conReader.readLine();

        int requireLevel;
        System.out.println("Enter the level of requirement:");
        requireLevel = conReader.read()-'0';
        while(requireLevel != 1 && requireLevel != 2 && requireLevel != 3 && requireLevel != 4){
            System.out.println("Invalid requirement level! The requirement level should be 1~4");
            System.out.println("Enter the level of requirement:");
            requireLevel = conReader.read();
        }

        // read line by line from ordered file
        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = bufferedReader.readLine();

        while(line != null){
            HandleLine(line,keywords);
            line = bufferedReader.readLine();
        }

        bufferedReader.close();
        fileReader.close();

        // 1st order
        System.out.println("total num:"+keywordTotalNum);

        // 2nd order
        if(requireLevel >= 2){
            System.out.println("switch num:"+switchNum);
            System.out.print("case num:");
            for(int i = 1; i <= switchNum; i++){
                System.out.print(caseNum[i]+" ");
            }
        }

        // 3rd order
        if(requireLevel >= 3){
            WorkOnStack();
            System.out.println("\nif-else num:"+elseNum);
        }

        //4th order
        if(requireLevel == 4){
            System.out.println("if-elseif-else num:"+elseifNum);
        }
    }
}