package ru.liga.forecaster.util;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import ru.liga.forecaster.model.CurrencyRate;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateCourseGraph {

    private static final long serialVersionUID = 1L;
    private final DefaultCategoryDataset dataset;

    /**
     * Creates a new demo instance.
     */
    public CreateCourseGraph() {
        this.dataset = new DefaultCategoryDataset();
    }

    /**
     * Creates a sample chart.
     *
     * @param dataset the dataset.
     * @return The chart.
     */
    private static JFreeChart CreateChart(CategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createLineChart(
                null ,
                "date" /* x-axis label*/ ,
                "course" /* y-axis label */ ,
                dataset);
        chart.setBackgroundPaint(Color.WHITE);
        CategoryPlot plot = (CategoryPlot) chart.getPlot();

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createStandardTickUnits());
        return chart;
    }

    /**
     * Returns a sample dataset.
     */
    private void SetData(List<List<CurrencyRate>> rateList , List<String> currencyList) {
        dataset.clear();
        for (int j = 0; j < currencyList.size(); j++) {
            List<CurrencyRate> tempRate = rateList.get(j);
            for (CurrencyRate cur : tempRate) {
                dataset.addValue(
                        convertToRealCourse(cur.getNominal(),cur.getCourse()) ,
                        currencyList.get(j) ,
                        cur.getDate());
            }
        }
    }
    private BigDecimal convertToRealCourse(int nominal , BigDecimal course) {
        BigDecimal nominalDivisor = new BigDecimal(nominal);
        return course.divide(nominalDivisor , 4 , RoundingMode.HALF_UP);
    }
    private File GetCurrencyRatesAsGraph() throws IOException {
        JFreeChart chart = CreateChart(dataset);
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setAutoRangeIncludesZero(false);
        File lineChart = new File("lineChart.png");
        ImageIO.write(chart.createBufferedImage(1000 , 400) , "png" , lineChart);
        return lineChart;
    }

    public File CreateRatesGraph(List<List<CurrencyRate>> rates , String cur) throws IOException {
        String[] args = cur.split(",");
        List<String> currency = new ArrayList<>(Arrays.asList(args));
        SetData(rates , currency);
        return GetCurrencyRatesAsGraph();
    }
}
