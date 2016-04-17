package com.example.vojjalateja.causelist;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.webkit.WebView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class load extends Activity {
    Context context;
    private WebView mwebview;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.getinfo);
        context=this;
        mwebview=(WebView)findViewById(R.id.webView2);
        mwebview.getSettings().setLoadWithOverviewMode(true);
        mwebview.getSettings().setUseWideViewPort(true);
        mwebview.getSettings().setBuiltInZoomControls(true);
        mwebview.getSettings().setDisplayZoomControls(true);
        int inte=getIntent().getIntExtra("internetconnection",0);
        if(inte==0)
        {
            File f=new File(Environment.getExternalStorageDirectory(),"/causelist.html");
            if(f.exists())
                mwebview.loadUrl("file:///"+f.getAbsolutePath());
            else
                Toast.makeText(context, "No file to display and no internet connection", Toast.LENGTH_LONG).show();
        }
        else
        {
            String hh="<html>\n" +
                    "<head>\n" +
                    "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">\n" +
                    "<title>CAUSE LIST</title>\n" +
                    "<style type=\"text/css\">\n" +
                    "body {\n" +
                    "\tfont-size: 12px;\n" +
                    "\tfont-family: Courier New, Courier, arial;\n" +
                    "\tbackground-color: #F8E0E6;\n" +
                    "}\n" +
                    "\n" +
                    ".header {\n" +
                    "\tfont-weight: bold;\n" +
                    "}\n" +
                    "\n" +
                    "table {\n" +
                    "\twidth: 100%;\n" +
                    "}\n" +
                    "\n" +
                    "@media screen and (max-width: 600px) {\n" +
                    "\ttable {\n" +
                    "\t\tborder: 0;\n" +
                    "\t}\n" +
                    "\ttable thead {\n" +
                    "\t\tdisplay: none;\n" +
                    "\t}\n" +
                    "\ttable tr {\n" +
                    "\t\tmargin-bottom: 10px;\n" +
                    "\t\tdisplay: block;\n" +
                    "\t\tborder-bottom: 2px solid #ddd;\n" +
                    "\t}\n" +
                    "\ttable td {\n" +
                    "\t\tdisplay: block;\n" +
                    "\t\ttext-align: right;\n" +
                    "\t\tfont-size: 13px;\n" +
                    "\t\tborder-bottom: 1px dotted #ccc;\n" +
                    "\t}\n" +
                    "\ttable td:last-child {\n" +
                    "\t\tborder-bottom: 0;\n" +
                    "\t}\n" +
                    "\ttable td:before {\n" +
                    "\t\tcontent: attr(data-label);\n" +
                    "\t\tfloat: left;\n" +
                    "\t\ttext-transform: uppercase;\n" +
                    "\t\tfont-weight: bold;\n" +
                    "\t}\n" +
                    "}\n" +
                    "</style>\n" +
                    "</head>\n" +
                    "<body>\n"+
                    "\t<br>\n"+"\t<table>"+Main.mainhtmlcontents+"\t</table>\n"+
                    "\n"+"\n"+"\t\n"+"</body>\n"+"</html>";
            File f=new File(Environment.getExternalStorageDirectory(),"/causelist.txt");
            try{
                if(f.exists())
                    f.delete();
                if(!f.exists())
                    f.createNewFile();
                FileOutputStream fos=new FileOutputStream(f);
                fos.write(hh.getBytes());
                fos.close();
                f.renameTo(new File(Environment.getExternalStorageDirectory(),"/causelist.html"));
            }catch(Exception e){
                e.printStackTrace();
            }
            mwebview.loadData(hh,"text/html","UTF-8");
        }
    }
}