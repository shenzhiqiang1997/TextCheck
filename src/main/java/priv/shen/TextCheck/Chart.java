package priv.shen.TextCheck;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Chart extends Application {
    private static String stageTitle;
    private static String XLabel;
    private static String YLabel;
    private static String chartTitle;
    private static String seriesName;
    private static List<Point> points=new ArrayList<Point>();


    @Override public void start(Stage stage) {
        //设置窗口标题
        stage.setTitle(stageTitle);
        //创建横纵坐标
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        //设置横纵坐标标签
        xAxis.setLabel(XLabel);
        yAxis.setLabel(YLabel);

        //创建折线图表
        final LineChart<Number,Number> lineChart =
                new LineChart<Number,Number>(xAxis,yAxis);

        //设置折线图标题
        lineChart.setTitle(chartTitle);
        //创建一条折线
        XYChart.Series series = new XYChart.Series();
        //设置折线名称
        series.setName(seriesName);

        //在折线上添加点
        for (Point point:
             points) {
            series.getData().add(new XYChart.Data(point.x, point.y));
        }

        //图表的大小
        Scene scene  = new Scene(lineChart,800,600);
        //将折线加入到图表中
        lineChart.getData().add(series);

        //将图表设置到窗口中
        stage.setScene(scene);
        //展现窗口
        stage.show();
    }

    public static void main(String[] args) throws Exception {
        int[] wordNums=new int[]{3,5,7,9,10};
        for (int wordNum:
                wordNums) {
            System.out.println("------------------------------------------------------------------------");
            System.out.println("check time for "+ wordNum +" words text:");

            double averageTime=Experiment.checkTextAverageTime(wordNum, 100);

            System.out.println("average check time is "+averageTime+" s");

            points.add(new Point(wordNum,averageTime));
            System.out.println();
            System.out.println();
        }

        //启动折线图
        stageTitle="experiment";
        XLabel="单词数量/个";
        YLabel="平均耗时/s";
        chartTitle="折线图";
        seriesName="平均耗时";
        launch();
    }
}
