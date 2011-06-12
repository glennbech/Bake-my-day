package com.glennbech.prefermentz.formuladetails;

import android.content.Context;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.TextView;
import com.glennbech.prefermentz.R;
import com.glennbech.prefermentz.model.FormulaComponent;

import java.util.List;

/**
 * @author Glenn Bech
 */
public class FormulaComponentListAdapter extends ArrayAdapter<FormulaComponent> {

    public FormulaComponentListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public FormulaComponentListAdapter(Context context, List<FormulaComponent> formulas) {
        super(context, R.layout.formulaitem, formulas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.formulacomponentitem, null);
        }

        final TextView tvCurrentValue = (TextView) v.findViewById(R.id.currentValue);

        final FormulaComponent currentComponent = getItem(position);
        currentComponent.loadIngredient(getContext());
        tvCurrentValue.setText(Float.toString(currentComponent.getBp() * 100) + "%");

        TextView ingredientName = (TextView) v.findViewById(R.id.ingredientName);
        ingredientName.setText(currentComponent.getI().getName());

        SeekBar sb = (SeekBar) v.findViewById(R.id.seekbar);
        int progress = (int) (currentComponent.getBp() * 100);
        sb.setProgress(progress);

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                currentComponent.setBp(progress / 100f);
                tvCurrentValue.setText(Float.toString(currentComponent.getBp() * 100) + "%");
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                Vibrator v = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(100);
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                Vibrator v = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(100);
            }
        });
        return v;
    }





}
