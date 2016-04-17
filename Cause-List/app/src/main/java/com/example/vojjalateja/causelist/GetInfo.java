package com.example.vojjalateja.causelist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;

import com.koushikdutta.ion.Ion;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class GetInfo extends Activity {
    String htmlcontents;
    private Context context;
    private ArrayList<Integer> arrayList;
    private ImageView imageview;
    private String COMSTRING1="",COMSTRING2="",COMSTRING3="";
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        context=this;
        arrayList=new ArrayList<>();
        COMSTRING1=getIntent().getExtras().getString("comstring1");
        COMSTRING2=getIntent().getExtras().getString("comstring2");
        COMSTRING3="GP FOR INFORMATION AND TECHNOLOGY -TG-";
        if(COMSTRING2.equals("GP FOR GENERAL ADMINISTRATION"))
        {
            htmlcontents=Main.mainhtmlcontents;
        }
        else if(COMSTRING2.equals("GP FOR FINANCE and PLANNING"))
        {
            htmlcontents=Main2.mainhtmlcontents;
        }
        else if(COMSTRING2.equals("GP FOR INFORMATION AND TECHNOLOGY")) {
            htmlcontents = Main3.mainhtmlcontents;
        }
        imageview=(ImageView)findViewById(R.id.imageview);
        Ion.with(imageview).load("http://www.arborlanekennel.com/croogo/images/loading.gif");
        AsyncTask<Void,Void,String> task=new AsyncTask<Void,Void,String>(){
            @Override
            protected void onPreExecute(){
            }
            @Override
            protected String doInBackground(Void... arg0){
                String returnstring="";
                Document doc= Jsoup.parse(htmlcontents);
                Elements altr=doc.select("tr");

                int trlen=altr.size(),i;
                for(i=0;i<trlen;)
                {
                    Elements altd=altr.get(i).select("td");
                    if(altd.size()==1)
                    {
                        arrayList.add(i);i++;
                    }
                    else
                    {
                        if(altr.get(i).select("td").get(2).outerHtml().contains("&nbsp;"))
                        {
                            arrayList.add(i);i++;
                        }
                        else
                        {
                            int y=1;
                            ArrayList<Integer> arrayList2=new ArrayList<>();
                            for(;i<trlen;)
                            {
                                altd=altr.get(i).select("td");
                                if(altd.size()==1){break;}
                                if(altr.get(i).select("td").get(2).outerHtml().contains("&nbsp;"))break;
                                else if(altd.get(3).text().equals(COMSTRING1)||altd.get(3).text().equals(COMSTRING2)||altd.get(3).text().equals(COMSTRING3))y=0;
                                arrayList2.add(i);i++;
                            }
                            if(y==0)
                            {
                                for(int k=0;k<arrayList2.size();k++)
                                    arrayList.add(arrayList2.get(k));
                            }
                        }
                    }
                }
                for(int k=0;k<arrayList.size();k++)
                    returnstring=returnstring+altr.get(arrayList.get(k)).outerHtml();
                return returnstring;
            }
            @Override
            protected void onPostExecute(String result){
                if(COMSTRING2.equals("GP FOR GENERAL ADMINISTRATION")){
                    Main.mainhtmlcontents="<tr>\n"+"\t\t\t<td colspan=\"5\"align=\"center\"class=\"header\">GENERAL ADMINISTRAION</td>\n"+
                            "\t\t</tr>\n"+result;
                    Intent i=new Intent(context,Main2.class);
                    startActivity(i);
                }
                else if(COMSTRING2.equals("GP FOR FINANCE and PLANNING")){
                    Main.mainhtmlcontents=Main.mainhtmlcontents+"<tr>\n"+"\t\t\t<td colspan=\"5\"align=\"center\"class=\"header\">FINANCE AND PLANNING</td>\n"+
                            "\t\t</tr>\n"+result;
                    Intent i=new Intent(context,Main3.class);
                    startActivity(i);
                }
                else if(COMSTRING2.equals("GP FOR INFORMATION AND TECHNOLOGY")){
                    Main.mainhtmlcontents=Main.mainhtmlcontents+"<tr>\n"+"\t\t\t<td colspan=\"5\"align=\"center\"class=\"header\">INFORMATION TECHNOLOGY</td>\n"+
                            "\t\t</tr>\n"+result;
                    Intent i=new Intent(context,load.class);
                    i.putExtra("internetconnection", 1);
                    startActivity(i);
                }
            }
        };
        task.execute();
    }
    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }
}
