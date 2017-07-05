package com.example.kh.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kh.myapplication.Module.HOCSINH;
import com.example.kh.myapplication.Sqlite.MySqlite;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.txtData)
    TextView txtData;
    private List<HOCSINH> list;
    @BindView(R.id.etName)
    EditText etLop;
    @BindView(R.id.etLop)
    EditText etName;
    @BindView(R.id.etId)
    EditText etId;
    private static String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    public void load(){
        StringBuilder stringBuilder = new StringBuilder("");
        list = MySqlite.getMySqlite(this).getAllData();
        for(HOCSINH hs: list){
            stringBuilder.append("Id: "+hs.getId()+", Name: "+hs.getName()+", Lop: "+hs.getLop()+"\n");
        }
        txtData.setText(stringBuilder.toString());
        Log.i(TAG, "load: ");
    }
    @OnClick(R.id.btnLoadData)
    public void btnLoadData(){
        load();
    }
    @OnClick(R.id.btnInsert)
    public void btnInsert(){
        if(etName.getText().toString().trim().length()>0)
        MySqlite.getMySqlite(this).insert(etName.getText().toString().trim(),etLop.getText().toString().trim());
        else
            Toast.makeText(this, "null name", Toast.LENGTH_SHORT).show();
        load();
        Log.i(TAG, "btnInsert: ");
    }
    @OnClick(R.id.btnUpdate)
    public void btnUpdate(){

          if(  MySqlite.getMySqlite(this).update(Integer.parseInt( etId.getText().toString().trim()), etName.getText().toString(),etLop.getText().toString().trim()))
              Toast.makeText(this, "update success", Toast.LENGTH_SHORT).show();
       else
            Toast.makeText(this, " update fail", Toast.LENGTH_SHORT).show();
        load();
        Log.i(TAG, "btnUpdate: ");
    }
    @OnClick(R.id.btnDelete)
    public void btnDelete(){
        if(  MySqlite.getMySqlite(this).delete(Integer.parseInt( etId.getText().toString().trim())))
            Toast.makeText(this, "delete success", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, " delete fail", Toast.LENGTH_SHORT).show();
        load();
        Log.i(TAG, "btnDelete: ");
    }
}
