package com.glennbech.prefermentz.formuladetails;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.glennbech.prefermentz.R;
import com.glennbech.prefermentz.model.FormulaComponent;
import com.glennbech.prefermentz.persistence.RecipeOpenHelper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Glenn Bech
 */
public class FormulaComponentListAdapter extends ArrayAdapter<FormulaComponent> {

    private RecipeOpenHelper roHelper;

    public FormulaComponentListAdapter(Context context, List<FormulaComponent> formulas) {
        super(context, R.layout.formulaitem, formulas);
        roHelper = new RecipeOpenHelper(getContext());
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
        tvCurrentValue.setText(Float.toString(currentComponent.getBp() * 100) + "%");

        TextView ingredientName = (TextView) v.findViewById(R.id.ingredientName);
        ingredientName.setText(currentComponent.getI().getName());

        final SeekBar sb = (SeekBar) v.findViewById(R.id.seekbar);
        int progress = (int) (currentComponent.getBp() * 100);
        sb.setProgress(progress);

        v.findViewById(R.id.enterbpvaluebutton).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                showAlert(currentComponent);
                tvCurrentValue.setText(Float.toString(currentComponent.getBp() * 100) + "%");
            }
        });


        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                currentComponent.setBp(progress / 100f);
                tvCurrentValue.setText(Float.toString(currentComponent.getBp() * 100) + "%");
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        return v;
    }

    public void showAlert(final FormulaComponent currentComponent) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle(currentComponent.getI().getName());

// Set an EditText view to get user input

        final EditText input = new EditText(getContext());
        alert.setView(input);
        input.setInputType(InputType.TYPE_CLASS_PHONE);
        alert.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                currentComponent.getId();
                Dao<FormulaComponent, Integer> dao = roHelper.getFormulaComponentDao(getContext());
                try {
                    dao.delete(currentComponent);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                remove(currentComponent);
                notifyDataSetInvalidated();
            }
        });

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString();
                try {
                    float bp = Float.parseFloat(value);
                    if (bp > 100) {
                        Toast.makeText(getContext(), "Enter a number between 1 and 100", Toast.LENGTH_LONG).show();
                        return;
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Invalid number", Toast.LENGTH_LONG).show();
                    return;
                }
                currentComponent.setBp(Float.parseFloat(value) / 100f);
                notifyDataSetChanged();
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        alert.show();
    }
}
