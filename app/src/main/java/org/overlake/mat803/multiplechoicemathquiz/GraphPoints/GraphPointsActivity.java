package org.overlake.mat803.multiplechoicemathquiz.GraphPoints;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import org.overlake.mat803.multiplechoicemathquiz.R;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.PointsGraphSeries;
import java.util.ArrayList;

public class GraphPointsActivity extends AppCompatActivity {

    public static final String KEY_POINT_LIST = "keyPointList";

    PointsGraphSeries<DataPoint> xySeries;
    private Button btnAddPoint;
    private EditText mX, mY;
    GraphView mScatterPlot;
    private ArrayList<XYValue> xyValueArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_points);
        mX = findViewById(R.id.numberX);
        mY = findViewById(R.id.numberY);
        mScatterPlot = findViewById(R.id.scatter_plot);
        btnAddPoint = findViewById(R.id.btn_add_point);
        xyValueArray = new ArrayList<>();
        init();
        if (savedInstanceState != null) {
            xyValueArray = savedInstanceState.getParcelableArrayList(KEY_POINT_LIST);
            if (xyValueArray.size() != 0) {
                createScatterPlot();
            } else {
            }
        }
    }

    private void init() {
        xySeries = new PointsGraphSeries<>();
        btnAddPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mX.getText().toString().equals("") && !mY.getText().toString().equals("")) {
                    double x = Double.parseDouble(mX.getText().toString());
                    double y = Double.parseDouble(mY.getText().toString());
                    xyValueArray.add(new XYValue(x, y));
                    init();
                } else {
                    Toast.makeText(GraphPointsActivity.this, "You must fill out both fields!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        if (xyValueArray.size() != 0) {
            createScatterPlot();
        } else {
        }
    }

    private void createScatterPlot() {
        xyValueArray = sortArray(xyValueArray);
        for (int i = 0; i < xyValueArray.size(); i++) {
            try {
                double x = xyValueArray.get(i).getX();
                double y = xyValueArray.get(i).getY();
                xySeries.appendData(new DataPoint(x,y), true, 1000);
            } catch (IllegalArgumentException e) {
            }
        }
        xySeries.setShape(PointsGraphSeries.Shape.RECTANGLE);
        xySeries.setColor(Color.BLUE);
        xySeries.setSize(20f);

        mScatterPlot.getViewport().setScalable(true);
        mScatterPlot.getViewport().setScalableY(true);
        mScatterPlot.getViewport().setScrollable(true);
        mScatterPlot.getViewport().setScrollableY(true);


        mScatterPlot.getViewport().setYAxisBoundsManual(true);
        mScatterPlot.getViewport().setMaxY(150);
        mScatterPlot.getViewport().setMinY(-150);

        mScatterPlot.getViewport().setXAxisBoundsManual(true);
        mScatterPlot.getViewport().setMaxX(150);
        mScatterPlot.getViewport().setMinX(-150);

        mScatterPlot.addSeries(xySeries);
    }

    private ArrayList<XYValue> sortArray(ArrayList<XYValue> array){
        int factor = Integer.parseInt(String.valueOf(Math.round(Math.pow(array.size(),2))));
        int m = array.size() - 1;
        int count = 0;
        while (true) {
            m--;
            if (m <= 0) {
                m = array.size() - 1;
            }
            try {
                double tempY = array.get(m - 1).getY();
                double tempX = array.get(m - 1).getX();
                if (tempX > array.get(m).getX()) {
                    array.get(m - 1).setY(array.get(m).getY());
                    array.get(m).setY(tempY);
                    array.get(m - 1).setX(array.get(m).getX());
                    array.get(m).setX(tempX);
                } else if (tempX == array.get(m).getX()) {
                    count++;
                } else if (array.get(m).getX() > array.get(m - 1).getX()) {
                    count++;
                }
                if (count == factor) {
                    break;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                break;
            }
        }
        return array;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(KEY_POINT_LIST, xyValueArray);
    }
}
