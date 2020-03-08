package com.example.ledger_companion.ui.home

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.LogPrinter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.ledger_companion.R
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF

class HomeFragment : Fragment() {








//    override fun onValueSelected(e: Entry?, h: Highlight?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
////        if (e == null)
////            return;
////        Log.i("VAL SELECTED",
////            "Value: " + e.getY() + ", index: " + h?.getX()
////                    + ", DataSet index: " + h?.getDataSetIndex());
//
//    }
//
//    override fun onNothingSelected() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//        Log.i("PieChart", "nothing selected");
//    }


    private lateinit var homeViewModel: HomeViewModel
    private lateinit var chart: PieChart



    var seekvalue = 10

    private fun setData(count: Int, range: Float) {
        val entries : ArrayList<PieEntry> = ArrayList<PieEntry>()

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (i in 0 until count) {
            entries.add(
                PieEntry(
                    (Math.random() * range + range / 5).toFloat(),
                    "teste"
//                    resources.getDrawable(R.drawable.ic_menu_share)
                )
            )
        }

        val dataSet = PieDataSet(entries, "Election Results")

        dataSet.setDrawIcons(false)

        dataSet.sliceSpace = 3f
        dataSet.iconsOffset = MPPointF(0f, 40f)
        dataSet.selectionShift = 5f

        // add a lot of colors

        val colors : ArrayList<Int> = ArrayList<Int>()

        for (c in ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c)

        for (c in ColorTemplate.JOYFUL_COLORS)
            colors.add(c)

        for (c in ColorTemplate.COLORFUL_COLORS)
            colors.add(c)

        for (c in ColorTemplate.LIBERTY_COLORS)
            colors.add(c)

        for (c in ColorTemplate.PASTEL_COLORS)
            colors.add(c)

        colors.add(ColorTemplate.getHoloBlue())

        dataSet.setColors(colors)
        //dataSet.setSelectionShift(0f);

        val data = PieData(dataSet)
        // data.setValueFormatter(PercentFormatter(chart))
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.WHITE)
        //data.setValueTypeface(tfLight)
        chart.data = data

        // undo all highlights
//        chart.highlightValues(null)

//        chart.invalidate()
//        chart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        val textView: TextView = root.findViewById(R.id.text_home)
        val seekBarX : SeekBar? = root?.findViewById(R.id.seekBar1)
        chart = root.findViewById(R.id.chart1)

        homeViewModel.text.observe(this, Observer {
            textView.text = it
        })

        //data.setValueTypeface(tfLight)
//        var seekBarY: SeekBar
//        var tvX : TextView
//        var tvY : TextView
//        tvX = root.findViewById(R.id.tvXMax)
//        tvY = root.findViewById(R.id.tvYMax)


        seekBarX?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar
//                text_view.text = "Progress : $i"
                setData(i,5f)
                chart.animateY(1200)
                Log.v("logging","phew")
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Do something
//                Toast.makeText(applicationContext,"start tracking",Toast.LENGTH_SHORT).show()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Do something
//                Toast.makeText(applicationContext,"stop tracking",Toast.LENGTH_SHORT).show()
            }
        })
//        seekBarX?.setOnSeekBarChangeListener(this)

//        val seekBarY : SeekBar = root.findViewById(R.id.seekBar2)
//        seekBarY.setOnSeekBarChangeListener(this)
        return root


//        seekBarY.setOnSeekBarChangeListener(this)

//        chart.setUsePercentValues(true);
//        chart.getDescription().setEnabled(false);
//        chart.setExtraOffsets(5.0f, 10f, 5f, 5f);
//
//        chart.setDragDecelerationFrictionCoef(0.95f);
//
//        //chart.setCenterTextTypeface(tfLight);
//        //chart.setCenterText(generateCenterSpannableText());
//
//        chart.setDrawHoleEnabled(true);
//        chart.setHoleColor(Color.WHITE);
//
//        chart.setTransparentCircleColor(Color.WHITE);
//        chart.setTransparentCircleAlpha(110);
//
//        chart.setHoleRadius(58f);
//        chart.setTransparentCircleRadius(61f);
//
//        chart.setDrawCenterText(true);
//
//        chart.setRotationAngle(0f);
//        // enable rotation of the chart by touch
//        chart.setRotationEnabled(true);
//        chart.setHighlightPerTapEnabled(true);
//
//        // chart.setUnit(" €");
//        // chart.setDrawUnitsInChart(true);
//
//        // add a selection listener
//        chart.setOnChartValueSelectedListener(this);

//        seekBarX?.setProgress(4);
//        seekBarY.setProgress(10);

//         chart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
//         chart.spin(2000, 0, 360f);

//        val l :Legend = chart.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
//        l.setOrientation(Legend.LegendOrientation.VERTICAL);
//        l.setDrawInside(false);
//        l.setXEntrySpace(7f);
//        l.setYEntrySpace(0f);
//        l.setYOffset(0f);
//
//        // entry label styling
//        chart.setEntryLabelColor(Color.WHITE);
//        // chart.setEntryLabelTypeface(tfRegular);
//        chart.setEntryLabelTextSize(12f);



    }

}