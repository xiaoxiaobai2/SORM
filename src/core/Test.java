package core;

import bean.Configuration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        Configuration configuration=DBManger.getConf();
        File file=new File("src\\java.java");
        BufferedWriter bw=new BufferedWriter(new FileWriter(file));
    }
}
