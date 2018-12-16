package OutterSort;

import java.io.File;

public class test {
    public static void main(String[] args) throws Exception {
        long start=System.currentTimeMillis();
        File inputFile=new File("E:\\OutterSort\\myInputFile.txt");
        File outputFile=new File("E:\\OutterSort\\outputFile.txt");
        File tempFile=new File("E:\\OutterSort\\tempFile");
        if (outputFile.exists())
            outputFile.delete();
        OutterSort.sort(inputFile,outputFile,tempFile);
        long end=System.currentTimeMillis();
        System.out.println(end-start);
    }
}
