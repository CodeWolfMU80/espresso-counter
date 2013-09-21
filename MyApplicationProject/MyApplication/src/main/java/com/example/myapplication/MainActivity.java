package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends Activity {

    ImageButton singleEspressoButton;
    ImageButton saveToFileButton;
    ImageButton readDataFromFileButton;
    TextView espressoCounter;
    TextView dataLabel;
    FileOutputStream fos = null;
    String FILENAME = "dataFile.txt";
    List<EspressoDTO> espressoDTOList = new ArrayList<EspressoDTO>();
    EspressoDTO todayEspressoDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addButtonListener();
        todayEspressoDTO = new EspressoDTO();
        todayEspressoDTO.numOfEspressi = 0;
        todayEspressoDTO.dataSetDate   = new Date(System.currentTimeMillis());
    }

    @Override
    protected void onStart() {
        super.onStart();
        readDataFromFile();
        espressoCounter = (TextView) findViewById(R.id.espressoCounter01);
        espressoCounter.setText("Test: " + todayEspressoDTO.numOfEspressi);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void addButtonListener() {
        singleEspressoButton = (ImageButton) findViewById(R.id.imageButton1);
        singleEspressoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                espressoCounter = (TextView) findViewById(R.id.espressoCounter01);
                espressoCounter.setText("Test: " + ++todayEspressoDTO.numOfEspressi);
            }
        });

        saveToFileButton = (ImageButton) findViewById(R.id.imageButton4);
        saveToFileButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date currDate = new Date(System.currentTimeMillis());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String currDateString = sdf.format(currDate);
                storeDataToFile(numOfEspressi, currDateString);
            }
        });

//        readDataFromFileButton = (ImageButton) findViewById(R.id.imageButton5);
//        readDataFromFileButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                espressoDTOList
//                dataLabel = (TextView) findViewById(R.id.espressoCounter02);
//                dataLabel.setText(zahl);
//            }
//        });
    }

    public void readDataFromFile() {
        String inputString = "";
        try {
            FileInputStream fileInputStream = openFileInput(FILENAME);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader bReader = new BufferedReader(new InputStreamReader());
            StringBuffer stringBuffer = new StringBuffer();
            while ((inputString = bReader.readLine()) != null) {
                EspressoDTO currentDTO   = new EspressoDTO();
                todayEspressoDTO         = currentDTO;
                String[] myValues        = inputString.split(";");
                SimpleDateFormat sdf     = new SimpleDateFormat("yyyy-MM-dd");
                currentDTO.dataSetDate   = sdf.parse(myValues[0]);
                currentDTO.numOfEspressi = Integer.parseInt(myValues[1]);
                espressoDTOList.add(currentDTO);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void storeDataToFile(int numOfEsp, String currentDate) {
        try {
            fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(new StringBuilder().append(currentDate).append(";").append(numOfEsp).toString().getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
}
