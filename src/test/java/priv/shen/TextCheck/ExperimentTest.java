package priv.shen.TextCheck;

import org.junit.Test;



public class ExperimentTest extends Chart{
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
    public void test() throws Exception {
        Experiment.checkText(4);
        /*Experiment.checkText(3);*/
    }

    @Test
    public void testTime() throws Exception{
        long before=System.currentTimeMillis();
        TextCheck.check("hello");
        long after=System.currentTimeMillis();
        System.out.println(after-before);
    }

}
