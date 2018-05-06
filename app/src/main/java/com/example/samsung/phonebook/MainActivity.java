package com.example.samsung.phonebook;


import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends ActionBarActivity {
    public  AdapterListItem adapter;
    private List<BeanItem> lists;
    private ArrayList<BeanItem> arraylist;
    private EditText input_value;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView phoneBookList = (ListView)findViewById(R.id.phoneBook_list);

        //Spinnerはアダプタが必要になるビューの一つ
        spinner = (Spinner)findViewById(R.id.spinner1);

        String[] member_name = {"altuve","otani","tanaka","darvishu","ryu","choo"};
        String[] member_hp = {"010","011","012","013","014","015"};

        lists = new ArrayList<BeanItem>();

        for(int i = 0; i<member_name.length; i++){
                BeanItem member = new BeanItem();
                member.member_name = member_name[i];
                member.member_hp = member_hp[i];
                lists.add(member);
        }

        arraylist = new ArrayList<BeanItem>();
        arraylist.addAll(lists);

        Collections.sort(arraylist,myComparator);

        adapter = new AdapterListItem(this,0,lists);
        phoneBookList.setAdapter(adapter);

        input_value =  (EditText)findViewById(R.id.editText);

        input_value.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                String text = input_value.getText().toString();
                getSearch(text);
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String str = getResources().getStringArray(R.array.name_sort)[position];
                sort(str,null);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    public void sort(String type, Integer index) {
        switch (type){
            case "昇順":
                Collections.sort(lists, myComparator);Collections.sort(arraylist, myComparator);
                if(index == null){}
                else lists.add(arraylist.get(index));
                adapter.notifyDataSetChanged();
                break;
            case "降順":
                Collections.sort(lists,myComparator);Collections.reverse(lists);
                Collections.sort(arraylist,myComparator);Collections.reverse(arraylist);
                if(index == null){}
                else lists.add(arraylist.get(index));
                adapter.notifyDataSetChanged();
                break;
        }
    }
    public void getSearch(String name){

        if (name.length() == 0) {
            lists.clear();
            lists.addAll(arraylist);
            String text = spinner.getSelectedItem().toString();
            sort(text,null);
        }else {
            findViewById(R.id.find_button).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    input_value = (EditText) findViewById(R.id.editText);
                    //문자열이 없을 때
                    if (input_value.getText().toString().length() == 0) input_value.setError("no input value");
                     else {
                        for (int i = 0; i < lists.size(); i++) {
                            if (lists.get(i).member_name.toString().contains(input_value.getText().toString())) {
                                lists.clear();
                                String text = spinner.getSelectedItem().toString();
                                sort(text,i);
                                break;
                            }
                        }
                    }
                }
            });

        }
        adapter.notifyDataSetChanged();
    }



    private final static Comparator<BeanItem> myComparator = new Comparator<BeanItem>(){
        private final Collator collator = Collator.getInstance();
        public int compare(BeanItem ob1,BeanItem ob2){
            return collator.compare(ob1.member_name,ob2.member_name);
        }
    };
}

