package com.example.chris.contactlistcustomlistview;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


/**
 * Created by Chris on 31/05/2016.
 */
public class AddContactBackgroundTask extends AsyncTask<String, Void, String>{

//    We set up this context so we can communicate with other activities, like AddContact.java
    Context ctx;

    AddContactBackgroundTask (Context ctx)
    {
        this.ctx=ctx;
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();

    }


    @Override
    protected String doInBackground(String... params) {

//        String reg_url="http://78.19.1.223/populisto/register.php";
        String reg_url="http://10.0.2.2/populisto/register.php";
//        String reg_url="http://192.168.0.7:80/populisto/register.php";
        String method = params[0];
        if (method.equals("addcontact"))
        {

          String contactname = params[1];
          String contactnumber = params[2];
          String category = params[3];
          String contactaddress = params[4];
          String comment = params[5];
            try {
URL url = new URL(reg_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS =httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                String data = URLEncoder.encode("contactname","UTF-8") + "=" + URLEncoder.encode(contactname,"UTF-8") + "&" +
                        URLEncoder.encode("contactnumber","UTF-8") + "=" + URLEncoder.encode(contactnumber,"UTF-8") + "&" +
                        URLEncoder.encode("category","UTF-8") + "=" + URLEncoder.encode(category,"UTF-8") + "&" +
                        URLEncoder.encode("contactaddress","UTF-8") + "=" + URLEncoder.encode(contactaddress,"UTF-8") + "&" +
                        URLEncoder.encode("comment","UTF-8") + "=" + URLEncoder.encode(comment,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS = httpURLConnection.getInputStream();
                IS.close();
                return "Contact added successfully";

            }

            catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }
        }

return null;

    }

    @Override
    protected void onProgressUpdate(Void... values) {

        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {

        Toast.makeText(ctx,result,Toast.LENGTH_LONG).show();
    }




}
