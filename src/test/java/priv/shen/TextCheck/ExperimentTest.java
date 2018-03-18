package priv.shen.TextCheck;

import org.junit.Test;


public class ExperimentTest {
    @Test
    public void testCheck() throws Exception {
        int[] wordNums=new int[]{3,5,7,9,10};
        for (int wordNum:
             wordNums) {
            System.out.println("check for "+ wordNum +" words text:");
            Experiment.checkText(wordNum);
            System.out.println();
            System.out.println();
        }
    }

    @Test
    public void testCheckTestAverageTime() throws Exception {
        int[] wordNums=new int[]{3,5,7,9,10};
        for (int wordNum:
                wordNums) {
            System.out.println("------------------------------------------------------------------------");
            System.out.println("check time for "+ wordNum +" words text:");
            System.out.println("average check time is "+Experiment.checkTextAverageTime(wordNum, 100)+"s");
            System.out.println();
            System.out.println();
        }
    }
}
