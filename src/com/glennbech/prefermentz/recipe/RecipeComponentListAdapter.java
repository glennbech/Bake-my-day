package com.glennbech.prefermentz.recipe;

import android.content.Context;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.glennbech.prefermentz.R;
import com.glennbech.prefermentz.model.RecipeComponent;

import java.text.DecimalFormat;
import java.util.List;

/**
 * @author Glenn Bech
 */
public class RecipeComponentListAdapter extends ArrayAdapter<RecipeComponent> {


    private float ozFactor = 28.3495231f;
    private static DecimalFormat f = new DecimalFormat("###.#");


    public RecipeComponentListAdapter(Context context, List<RecipeComponent> recipeComponents) {
        super(context, R.layout.recipeitem, recipeComponents);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.recipeitem, null);
        }

        RecipeComponent rc = getItem(position);

        boolean useMetric = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("metric", true);
        String unitName = useMetric ? " g" : " oz";

        final TextView ingredientName = (TextView) v.findViewById(R.id.recipeingredientname);
        ingredientName.setText(rc.getI().getName());

        final TextView ingredientWeight = (TextView) v.findViewById(R.id.recipingredientweight);
        ingredientWeight.setText(f.format(rc.getWeight() / (useMetric ? 1f : ozFactor)) + unitName);

        final TextView ingredientBp = (TextView) v.findViewById(R.id.recipeingredientpercent);
        ingredientBp.setText("(" + f.format(rc.getFc().getBp() * 100) + "%)");

        return v;
    }
}
