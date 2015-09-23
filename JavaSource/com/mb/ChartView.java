package com.mb;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import org.apache.poi.ss.usermodel.charts.LegendPosition;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LegendPlacement;
import org.primefaces.model.chart.PieChartModel;

import com.facade.PendenciaFacade;
import com.model.Status;

@ManagedBean
public class ChartView implements Serializable {

	private BarChartModel animatedModel2;
	private PieChartModel pieModel1;
	private PieChartModel pieModel2;

	@PostConstruct
	public void init() {
		createAnimatedModels();
		createPieModels();
	}
	
	public PieChartModel getPieModel1() {
        return pieModel1;
    }
	
	public PieChartModel getPieModel2() {
        return pieModel2;
    }
	
	private void createPieModels() {
        createPieModel1();
        createPieModel2();
    }
	
	private void createPieModel1() {
        pieModel1 = new PieChartModel();
        
        String sql = "SELECT p.status, COUNT(*) as qtd FROM Pendencia p group by p.status";

		PendenciaFacade facade = new PendenciaFacade();
		List<Object[]> lista = facade.buscaComQuery(sql);

		BarChartModel model = new BarChartModel();
		ChartSeries setor;
		for (Object[] objects : lista) {				
			pieModel1.set(objects[0].toString() + " - " + (Number) objects[1], (Number) objects[1]);
		}
         
        pieModel1.setTitle("Pendências por Status");
        pieModel1.setLegendPosition("w");
        pieModel1.setShowDataLabels(true);
    }
	
	private void createPieModel2() {
        pieModel2 = new PieChartModel();
        
        String sql = "SELECT p.prioridade, COUNT(*) as qtd FROM Pendencia p " +
					 "	where p.prioridade is not null " +
					 "	and p.status <> 'FECHADO'" +
					 "	group by p.prioridade";

		PendenciaFacade facade = new PendenciaFacade();
		List<Object[]> lista = facade.buscaComQuery(sql);

		BarChartModel model = new BarChartModel();
		ChartSeries prioridade;
		for (Object[] objects : lista) {				
			pieModel2.set(objects[0].toString() + " - " + (Number) objects[1], (Number) objects[1]);
		}
         
        pieModel2.setTitle("Pendências em aberto por prioridade");
        pieModel2.setLegendPosition("w");
        pieModel2.setShowDataLabels(true);
    }

	public BarChartModel getAnimatedModel2() {
		return animatedModel2;
	}
	
	private void createAnimatedModels() {
		animatedModel2 = initBarModel();
		animatedModel2.setTitle("Pendências em aberto por setor");
		animatedModel2.setAnimate(true);
		animatedModel2.setLegendPosition("su");
		animatedModel2.setLegendCols(2);
		animatedModel2.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
		animatedModel2.setShowPointLabels(true);
		Axis yAxis = animatedModel2.getAxis(AxisType.Y);
		yAxis.setLabel("Quantidade de pendências");
	}
	
	private BarChartModel initBarModel() {
		String sql = "SELECT s.nome, "
				+ "COUNT(*) AS qtd_Pendencia "
				+ "FROM Pendencia p, Setor s "
				+ "WHERE p.setor.id = s.id "
				+ "AND p.status <> 'FECHADO' "
				+ "GROUP BY s.nome";

		PendenciaFacade facade = new PendenciaFacade();
		List<Object[]> lista = facade.buscaComQuery(sql);

		BarChartModel model = new BarChartModel();
		ChartSeries setor;
		for (Object[] objects : lista) {
			setor = new ChartSeries(objects[0].toString());
			setor.set("Setores", (Number) objects[1]);
			model.addSeries(setor);
		}

		return model;
	}
}