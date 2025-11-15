/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom1.hrm.UI;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.text.NumberFormat;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import com.nhom1.hrm.Models.Gender;
import com.nhom1.hrm.SQL.ConnectSQL;
import com.nhom1.hrm.SQL.StatsDAO;

/**
 *
 * @author Kris
 */
public class StatsCharts {
    public static ChartPanel pie(String title, Map<String,Integer> data) {
        DefaultPieDataset<String> ds = new DefaultPieDataset<>();
        
        //short ver
        data.forEach(ds::setValue);
        //full ver
        /*for (var e : data.entrySet()) {
            ds.setValue(e.getKey(), e.getValue());
        }*/

        JFreeChart chart = ChartFactory.createPieChart(title, ds, true, true, false);

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setSimpleLabels(true);
        plot.setInteriorGap(0.04);
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}: {1} ({2})", NumberFormat.getIntegerInstance(),NumberFormat.getPercentInstance()));

        return new ChartPanel(chart);
    }

    public static ChartPanel bar(String title, String catAxis, String valAxis,
                                 Map<String,Integer> data) {
        DefaultCategoryDataset ds = new DefaultCategoryDataset();
        data.forEach((k,v) -> ds.addValue(v, "Total", k));
        JFreeChart chart = ChartFactory.createBarChart(title, catAxis, valAxis, ds);
        return new ChartPanel(chart);
    }

    public static void mount(JPanel host, ChartPanel cp) {
        host.removeAll();
        if (!(host.getLayout() instanceof BorderLayout)) {
            host.setLayout(new BorderLayout());
        }
        host.add(cp, BorderLayout.CENTER);
        host.revalidate();
        host.repaint();
    }

    //
    public static void loadGenderChartInto(JPanel genderPanel) {
        try (Connection c = ConnectSQL.getConnection()) {
            var byGender = StatsDAO.countByGender(c); // lấy Map<String,Integer> từ DB
            ChartPanel cpGender = pie("Employees by Gender", byGender);
            mount(genderPanel, cpGender);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Load stats failed: " + e.getMessage());
        }
    }

    //
    public static void loadDepartmentChartInto(JPanel deptPanel) {
        try (Connection c = ConnectSQL.getConnection()) {
            var byDept = StatsDAO.countByDepartment(c);
            ChartPanel cpDept = bar("By Department", "Department", "Employees", byDept);
            mount(deptPanel, cpDept);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Load stats failed: " + e.getMessage());
        }
    }

    public static void loadEducationChartInto(JPanel eduPanel) {
        try (Connection c = ConnectSQL.getConnection()) {
            var byEdu = StatsDAO.countByEducation(c);
            ChartPanel cpEdu = bar("By Education", "Education", "Employees", byEdu);
            mount(eduPanel, cpEdu);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Load stats failed: " + e.getMessage());
        }
    }

    public static void loadJobLevelChartInto(JPanel levelPanel) {
        try (Connection c = ConnectSQL.getConnection()) {
            var byLevel = StatsDAO.countByJobLevel(c);
            ChartPanel cpLevel = bar("By Job Level", "Level", "Employees", byLevel);
            mount(levelPanel, cpLevel);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Load stats failed: " + e.getMessage());
        }
    }
}
