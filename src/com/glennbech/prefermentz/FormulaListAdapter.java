package com.glennbech.prefermentz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
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
        super(context, R.layout.formula, formulas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.formula, null);
        }

        final Formula formula = getItem(position);
        TextView nameView = (TextView) v.findViewById(R.id.name);
        nameView.setText(formula.getName());

        TextView countView = (TextView) v.findViewById(R.id.ingredientCount);
        String countString = getContext().getResources().getString(R.string.ingredientCount);
        countView.setText(countString + " " + formula.getFormulaComponentList().size());

        return v;
    }


}
