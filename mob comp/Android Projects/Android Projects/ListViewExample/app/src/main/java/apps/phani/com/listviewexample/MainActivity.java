package apps.phani.com.listviewexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] family = {"Phanindra", "Indrani", "Sweety", "Kumari"};
        ListAdapter theAdapter = new MyAdapter(this, family);
        ListView theListView = (ListView) findViewById(R.id.theListView);
        theListView.setAdapter(theAdapter);
        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String selected = getString(R.string.prefix_selected_text) + String.valueOf(adapterView.getItemAtPosition(position));
                Toast.makeText(MainActivity.this, selected, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
