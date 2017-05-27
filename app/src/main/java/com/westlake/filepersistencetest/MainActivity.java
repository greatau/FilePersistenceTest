package com.westlake.filepersistencetest;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static android.R.attr.data;
import static android.R.attr.name;
import static android.R.id.edit;

public class MainActivity extends AppCompatActivity
{
    private EditText mEdit;
    private Button mSaveButton,mClearButton;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEdit = (EditText) findViewById(R.id.edittext);
        String inputText = load();
        if (!TextUtils.isEmpty(inputText))
        {
            mEdit.setText(inputText);
            mEdit.setSelection(inputText.length());
            Toast.makeText(this, "Restoring succeeded",Toast.LENGTH_SHORT).show();
        }


        mSaveButton = (Button) findViewById(R.id.save_button);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v)
            {
                String inputText = mEdit.getText().toString();
                save(inputText);
            }



        });

        mClearButton = (Button) findViewById(R.id.clear_button);
        mClearButton.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v)
            {
                String inputText = "";
                save(inputText);

            }

        });

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        //String inputText = mEdit.getText().toString();
        //save(inputText);
    }

    /**
     *  save the input text to local file when activity is destroyed.
     */
    private void save(String inputText)
    {
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try
        {
            /**
             * default saved file location is: /data/data/<package name>/files/filename
             * file name is: data
             * MODE_PRIVATE:overwrite the file when the file is existed.
             */
            out = openFileOutput("data", Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(inputText);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (writer != null)
                {
                    writer.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     *  load the data from the local file which file name is data.
     */
    private String load()
    {
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try
        {
            in = openFileInput("data");
            reader = new BufferedReader( new InputStreamReader(in));
            String line = "";
            while (( line = reader.readLine()) != null)
            {
                content.append(line);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (reader != null)
            {
                try
                {
                    reader.close();
                }
                catch ( IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }
}

















