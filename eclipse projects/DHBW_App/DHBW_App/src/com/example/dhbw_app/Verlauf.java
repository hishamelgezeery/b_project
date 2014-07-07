package com.example.dhbw_app;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

public class Verlauf extends Activity {

	private LinearLayout layout;
	private GraphicalView jChartView;

	private XYSeriesRenderer jRenderer = new XYSeriesRenderer();
	private XYSeriesRenderer jRenderer2 = new XYSeriesRenderer();
	private XYMultipleSeriesDataset jMultiSet = new XYMultipleSeriesDataset();
	private XYMultipleSeriesRenderer jMultiRenderer = new XYMultipleSeriesRenderer();
	private XYSeries jValues;
	private XYSeries jCover;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_verlauf);
		
	    layout = (LinearLayout)findViewById(R.id.chart);
	    
	    jValues = new XYSeries("Verlauf 1");
	    jCover = new XYSeries("Verlauf 2");
	    
	    // Verlauf 1
	    jMultiSet.addSeries(jValues);
	    jMultiSet.addSeries(jCover);
	    jRenderer.setColor(Color.RED);
	    jRenderer.setLineWidth((float) 3.0);
	    jMultiRenderer.addSeriesRenderer(jRenderer);
	    jMultiRenderer.setMarginsColor(Color.WHITE);
	    
	    // Verlauf 2
	    jRenderer2.setColor(Color.BLUE);
	    jRenderer2.setFillBelowLine(true);
	    jRenderer2.setFillBelowLineColor(Color.GRAY);
	    jRenderer2.setLineWidth((float) 3.0);
	    jMultiRenderer.addSeriesRenderer(jRenderer2);
	    
	    jMultiRenderer.setPanEnabled(false, false);
	    jMultiRenderer.setZoomEnabled(false, false);
	    
	    // Achsenbeschriftung
	    jMultiRenderer.setXTitle("x-Achse");
	    jMultiRenderer.setYTitle("Y-Achse");
	    jMultiRenderer.setAxisTitleTextSize(22);
	    jMultiRenderer.setAxesColor(Color.BLACK);
	    jMultiRenderer.setLabelsColor(Color.BLACK); 
	    jMultiRenderer.setLabelsTextSize(20);
	    jMultiRenderer.setYLabelsAlign(Align.RIGHT);
	    jMultiRenderer.setXLabelsAlign(Align.RIGHT);
	    jMultiRenderer.setShowLegend(false);
	    jMultiRenderer.setChartTitle("Titel");
	    jMultiRenderer.setChartTitleTextSize(20);
	  
	    // in Chart zeichen und auf Layout darstellen
	    jChartView = ChartFactory.getLineChartView(this, jMultiSet, jMultiRenderer);
	    layout.addView(jChartView);
	    
	    // Verlauf zeichnen
	    zeichnen();
	    
	    Log.e("Schlag", "Create_Verlauf");

	}
	
	   @Override
	   protected void onResume() {
	   	super.onResume();
	   	
	   	Log.e("Schlag", "Resume_Verlauf");
	   }
	   
	   @Override
	   protected void onStop() {
		   super.onPause();

		   Log.e("Schlag", "Stop_Verlauf");
	   }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.verlauf, menu);
		return true;
	}
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.action_settings:
        	Toast.makeText(getApplicationContext(), R.string.hello_world ,Toast.LENGTH_SHORT).show();
        }
        return false;
    }
    
	private void zeichnen (){
		
		double variable=0;
		
		jValues.clear();
		jCover.clear();
		
		// Verlauf 1
		for(int i = 0; i < 10; i++){
			variable++;;
			jValues.add(variable, i);
		}

		jMultiRenderer.setXAxisMax(jValues.getMaxX());
		jMultiRenderer.setYAxisMax(jValues.getMaxY());
		jMultiRenderer.setYAxisMin(jValues.getMinY());
		jMultiRenderer.setAntialiasing(true);
		
		// Verlauf 2
		for (int i = 0; i <= 5; i++){
			jCover.add(jValues.getX(i), jValues.getY(i));
		}
		
		jChartView.repaint();
	}

}