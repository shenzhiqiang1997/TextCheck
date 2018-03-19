package priv.shen.TextCheck;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Experiment {

    public static void checkText(int wordNum) throws Exception{
        //得到相应长度的实验文本文件的字符输入流
        BufferedReader reader=new BufferedReader(new InputStreamReader(Experiment.class.getClassLoader().getResourceAsStream(String.valueOf(wordNum))));

        String text=null;
        //读入每个实验文本并纠错且输出结果
        while ((text=reader.readLine())!=null){
            System.out.println("------------------------------------------------------------------------");
            System.out.println("Text before check:"+text);

            String newText=TextCheck.check(text);

            System.out.println("Text after check:"+newText);
        }
    }

    public static double checkTextAverageTime(int wordNum,TextType type,int checkTimes,double exceptionInternal) throws Exception{
        //得到相应长度的实验文本文件的字符输入流
        BufferedReader reader=new BufferedReader(new InputStreamReader(Experiment.class.getClassLoader().getResourceAsStream(String.valueOf(wordNum)+"-"+type.identifier)));

        String text=null;

        //用于存放实验文本个数
        int textNum=0;
        //存放所有实验文本纠错的平均耗时之和
        double totalAverageTime=0;

        //第一个实验文本进行纠错时会进行一些加载耗时
        //导致第一次纠错耗时异常地高 所以预先进行纠错加载
        for (int i=0;i<100;i++){
            TextCheck.check("test a test.");
        }

        //读入每个实验文本并纠错且得到平均耗时
        while ((text=reader.readLine())!=null){
            //记录实验文本个数
            textNum++;

            //记录当前实验文本在给定纠错次数下的总耗时
            long totalTime=0;

            //循环纠错给定次数得出平均耗时
            for (int i=0;i<checkTimes;i++){
                long begin=System.currentTimeMillis();
                TextCheck.check(text);
                long end=System.currentTimeMillis();
                long interval=end-begin;
                System.out.println("text-"+textNum+" at the "+(i+1)+"th is "+(double)(interval)/1000+"s");
                if ((double)interval/1000>exceptionInternal){
                    checkTimes--;
                    continue;
                }
                totalTime+=interval;
            }
            //在一个实验文本循环纠错结束后 得到平均耗时
            double averageTime=(double) totalTime/(double) checkTimes;
            //将该实验文本纠错的平均耗时加到所有文本总平均耗时中
            totalAverageTime+=averageTime;
        }

        //将所有实验文本纠错的平均耗时总和除以实验个数得到一个实验文本的平均耗时
        //单位为秒
        return (totalAverageTime/(double) textNum)/1000;
    }
}
