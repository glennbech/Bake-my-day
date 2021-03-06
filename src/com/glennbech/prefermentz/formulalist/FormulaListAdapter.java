package com.glennbech.prefermentz.formulalist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.glennbech.prefermentz.R;
import com.glennbech.prefermentz.model.Formula;

import java.util.List;

/**
 * @author Glenn Bech
 */
public class FormulaListAdapter extends ArrayAdapter<Formula> {

    public FormulaListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public FormulaListAdapter(Context context, List<Formula> formulas) {
        super(context, R.layout.formulaitem, formulas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.formulaitem, null);
        }

        final Formula formula = getItem(position);
        TextView nameView = (TextView) v.findViewById(R.id.name);
        nameView.setText(formula.getName());

        return v;
    }


}
