package ru.liga.forecaster.util;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import ru.liga.forecaster.model.CurrencyRate;
import ru.liga.forecaster.model.type.Currency;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class CreateCourseGraph {
    private final DefaultCategoryDataset dataset;

    public File createRatesGraph(List<List<CurrencyRate>> rates , List<Currency> currency) throws IOException {
        setData(rates , currency);
        return getCurrencyRatesAsGraph();
    }

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
    private static JFreeChart createChart(CategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createLineChart(
                "Currency Forecaster" ,
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
    private void setData(List<List<CurrencyRate>> rateList , List<Currency> currencyList) {
        dataset.clear();
        for (int j = 0; j < currencyList.size(); j++) {
            List<CurrencyRate> tempRate = rateList.get(j);
            for (CurrencyRate cur : tempRate) {
                dataset.addValue(
                        convertToRealCourse(cur.getNominal() , cur.getCourse()) ,
                        currencyList.get(j) ,
                        cur.getDate());
            }
        }
    }

    private BigDecimal convertToRealCourse(int nominal , BigDecimal course) {
        BigDecimal nominalDivisor = new BigDecimal(nominal);
        return course.divide(nominalDivisor , 4 , RoundingMode.HALF_UP);
    }

    private File getCurrencyRatesAsGraph() throws IOException {
        JFreeChart chart = createChart(dataset);
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setAutoRangeIncludesZero(false);
        File lineChart = new File("lineChart.png");
        ImageIO.write(chart.createBufferedImage(1000 , 400) , "png" , lineChart);
        return lineChart;
    }
}
