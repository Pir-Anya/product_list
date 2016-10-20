package com.example.anuta_list;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	// Создаём пустой массив для хранения имен продуктов
	public ArrayList<String> product_names= new ArrayList<String>();
	// Создаём адаптер ArrayAdapter, чтобы привязать массив к ListView
	public ArrayAdapter<String> adapter ;
	private DatabaseHandler db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		super.onCreate(savedInstanceState);
	
		ListView listView = (ListView) findViewById(R.id.listView);
		final EditText editText = (EditText) findViewById(R.id.editText);

		
		db = new DatabaseHandler(this);
		
		//set_product_names();
		//заполняем массив данными из базы
		List<Product> products = db.getAllProducts();
		for(Product pr : products)
        {
			//adapter.add(pr._name);
			product_names.add(pr._name);
			
        }
			
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, product_names);
		adapter.setNotifyOnChange(true);
		
				
		// Привяжем массив через адаптер к ListView		
		listView.setAdapter(adapter);

		//удаляет продукт из списка
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
					long id) {
				Toast.makeText(getApplicationContext(), ((TextView) itemClicked).getText(),
				        Toast.LENGTH_SHORT).show();
				   String txt_clicked = (String)((TextView) itemClicked).getText();
				   Product pr = new Product(0,txt_clicked ,0);
				   db.deleteProduct(pr);
				   set_product_names();
				   adapter.notifyDataSetChanged();
			}
		});
		
				
		Button btnPlus = (Button) findViewById(R.id.buttonPlus);
		
		OnClickListener oclBtnOk = new OnClickListener() {
		    @Override
		    public void onClick(View v) {
		        // TODO Auto-generated method stub
		    	addData();
		    	set_product_names();
		    	adapter.notifyDataSetChanged();
		    }
		};
		
		btnPlus.setOnClickListener(oclBtnOk);
	}
	
	//добавляем продукт в список
	public void addData(){
		final EditText editText = (EditText) findViewById(R.id.editText);
		String w=editText.getText().toString();
		if (w.length() == 0) return;
		db.addProduct(new Product(w, "1"));
		set_product_names();
		adapter.notifyDataSetChanged();		
		editText.setText("");
	
	}

	//меню
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_send) {
			gotosendSmsActivity();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	//отправка продуктов по Смс
	public void gotosendSmsActivity(){
		/*Intent intent = new Intent(this, SendSmsActivity.class);
		intent.putExtra("sendData", "dffdsgdf");
		startActivity(intent);
		*/
		
	    String toSms="smsto:";
	    String catname = "";
	    for (int i = 0; i < product_names.size(); i++) {
	    	catname = catname + product_names.get(i) + ", ";
	    }
	    catname = catname.substring(0,catname.length()-2);
	    String messageText= "Купи "+catname ;
	    Intent sms=new Intent(Intent.ACTION_SENDTO, Uri.parse(toSms));
	 
	    sms.putExtra("sms_body", messageText);
	    startActivity(sms);
		
	}
	
	public void set_product_names(){
		product_names.clear();
		List<Product> products = db.getAllProducts();
		for(Product pr : products)
        {
			//adapter.add(pr._name);
			product_names.add(pr._name);
			
        }
	}

}