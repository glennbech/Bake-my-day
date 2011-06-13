package com.glennbech.prefermentz.addforumacomponent;

import android.content.Context;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.TextView;
import com.glennbech.prefermentz.R;
import com.glennbech.prefermentz.formuladetails.GravityAwareSeekbarList;
import com.glennbech.prefermentz.model.Ingredient;

import java.util.List;

/**
 * @author Glenn Bech
 */
public class IngredientListAdapter extends ArrayAdapter<Ingredient> {

    private GravityAwareSeekbarList gravityAwareSeekbarList;

    public IngredientListAdapter(Context context, List<Ingredient> formulas) {
        super(context, R.layout.formulaitem, formulas);
        gravityAwareSeekbarList = new GravityAwareSeekbarList(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.ingedient, null);
        }
        final TextView tvIngreidentName = (TextView) v.findViewById(R.id.ingredientListName);
        Ingredient i = getItem(position);
        tvIngreidentName.setText(i.getName());
        return v;
    }

}
