package com.example.task;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Spinner spSpecialty, spWorkers;
    Button btnSpeciality;
    TextView textView;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spSpecialty = (Spinner) findViewById(R.id.spSpecialty);
        spWorkers = (Spinner) findViewById(R.id.spWorkers);
        textView = (TextView) findViewById(R.id.textView);
        btnSpeciality = (Button) findViewById(R.id.btnSpeciality);
        dbHelper = new DBHelper(this);

        btnSpeciality.setOnClickListener(this);

        ArrayAdapter<?> adapterSpecialty =
                ArrayAdapter.createFromResource(this, R.array.specialty, android.R.layout.simple_spinner_item);
        adapterSpecialty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSpecialty.setAdapter(adapterSpecialty);

        spSpecialty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {

                textView.setText("");

                String[] choose = getResources().getStringArray(R.array.specialty);

                SQLiteDatabase database = dbHelper.getWritableDatabase();

                String query = "SELECT " + DBHelper.COLUMN_F_NAME + ", "
                        + DBHelper.COLUMN_L_NAME + ", "
                        + DBHelper.COLUMN_BIRTHDAY  + ", "
                        + DBHelper.COLUMN_NAME+ " FROM " + DBHelper.TABLE_NAME;
                Cursor cursor = database.rawQuery(query, null);
                String item_fName;
                String item_lName;
                String item_birthday;
                String item_name;
                String day = "";
                String month = "";
                String year = "";
                while (cursor.moveToNext()){
                    item_fName = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_F_NAME));
                    item_lName = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_L_NAME));
                    item_birthday = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_BIRTHDAY));
                    item_name = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_NAME));

                    String[] words = item_birthday.split("\\.");
                    if (item_birthday.length() == 10) {
                        for (int i = 0; i < words.length; i++) {
                            switch (i) {
                                case 0:
                                    day = words[0];
                                    break;
                                case 1:
                                    month = words[1];
                                    break;
                                case 2:
                                    year = words[2];
                                    break;
                            }
                        }

                        if (month.charAt(0) == 0) {
                            month = month.replace('0', ' ');
                            month = month.trim();
                        }

                        int intDay = Integer.parseInt(day);
                        int intMonth = Integer.parseInt(month);
                        int intYear = Integer.parseInt(year);

                        LocalDate now = LocalDate.now();
                        LocalDate birthday = LocalDate.of(intYear, intMonth, intDay);
                        Period period = Period.between(now, birthday);
                        int age = Math.abs(period.getYears());

                        if (choose[selectedItemPosition].equals(item_name)) {
                            textView.append(item_fName + " " + item_lName + " " + age + "\n");
                        }
                    } else {
                        if (choose[selectedItemPosition].equals(item_name)) {
                            textView.append(item_fName + " " + item_lName + " " + "-" + "\n");
                        }
                    }
                }
                cursor.close();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayAdapter<?> adapterWorkers =
                ArrayAdapter.createFromResource(this, R.array.workers, android.R.layout.simple_spinner_item);
        adapterWorkers.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spWorkers.setAdapter(adapterWorkers);

        spWorkers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {

                String[] choose = getResources().getStringArray(R.array.workers);

                SQLiteDatabase database = dbHelper.getWritableDatabase();

                String query = "SELECT " + DBHelper.COLUMN_F_NAME + ", "
                        + DBHelper.COLUMN_L_NAME + ", "
                        + DBHelper.COLUMN_BIRTHDAY  + ", "
                        + DBHelper.COLUMN_NAME+ " FROM " + DBHelper.TABLE_NAME;

                Cursor cursor = database.rawQuery(query, null);
                String item_fName;
                String item_lName;
                String item_birthday;
                String item_name;
                String day = "";
                String month = "";
                String year = "";
                int countID = 0;

                while (cursor.moveToNext()){
                    item_fName = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_F_NAME));
                    item_lName = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_L_NAME));
                    item_birthday = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_BIRTHDAY));
                    item_name = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_NAME));

                    countID++;

                    String[] words = item_birthday.split("\\.");
                    if (item_birthday.length() == 10) {
                        for (int i = 0; i < words.length; i++) {
                            switch (i) {
                                case 0:
                                    day = words[0];
                                    break;
                                case 1:
                                    month = words[1];
                                    break;
                                case 2:
                                    year = words[2];
                                    break;
                            }
                        }

                        if (month.charAt(0) == 0) {
                            month = month.replace('0', ' ');
                            month = month.trim();
                        }

                        int intDay = Integer.parseInt(day);
                        int intMonth = Integer.parseInt(month);
                        int intYear = Integer.parseInt(year);

                        LocalDate now = LocalDate.now();
                        LocalDate birthday = LocalDate.of(intYear, intMonth, intDay);
                        Period period = Period.between(now, birthday);
                        int age = Math.abs(period.getYears());

                        String full_name = "";
                        full_name = full_name.concat(item_fName);
                        full_name = full_name.concat(" ");
                        full_name = full_name.concat(item_lName);

                        if (choose[selectedItemPosition].equals(full_name) & countID == selectedItemPosition + 1) {
                            textView.setText(item_fName + " " + item_lName + " " + age + " " + item_birthday + " " + item_name + "\n");
                        }
                    } else {

                        String full_name = "";
                        full_name = full_name.concat(item_fName);
                        full_name = full_name.concat(" ");
                        full_name = full_name.concat(item_lName);

                        if (choose[selectedItemPosition].equals(full_name) & countID == selectedItemPosition + 1) {
                            textView.setText(item_fName + " " + item_lName + " " + "-" + " " + item_name + "\n");
                        }
                    }
                }
                cursor.close();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSpeciality:

                textView.setText("");

                SQLiteDatabase database = dbHelper.getWritableDatabase();

                String query = "SELECT " + DBHelper.COLUMN_NAME+ " FROM " + DBHelper.TABLE_NAME;
                Cursor cursor = database.rawQuery(query, null);

                ArrayList <String> name = new ArrayList<>();
                String item_name;

                while (cursor.moveToNext()){
                    item_name = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_NAME));
                    if (!name.contains(item_name)){
                        name.add(item_name);
                    }
                }
                for (int i = 0; i < name.size(); i++){
                    textView.append(name.get(i) + "\n");
                }
                cursor.close();
                break;
        }
    }
}