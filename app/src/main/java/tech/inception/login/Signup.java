package tech.inception.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONException;
import org.json.JSONObject;

public class Signup extends AppCompatActivity {


    private EditText name_et , mobile_et , email_et , password_et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name_et = (EditText) findViewById(R.id.name_et);
        mobile_et = (EditText) findViewById(R.id.mobile_et);
        email_et = (EditText) findViewById(R.id.email_et);
        password_et = (EditText) findViewById(R.id.password_et);


    }

    public void signup(View v)
    {
       String name = name_et.getText().toString();
        String mobile = mobile_et.getText().toString();
        String email = email_et.getText().toString();
        String password = password_et.getText().toString();

        if(name.equals(""))
        {


            Toast.makeText(Signup.this , "enter your name" , Toast.LENGTH_SHORT).show();



            return;
        }

        JSONObject jobj = new JSONObject();

        try {
            jobj.put("name_key" , name);
            jobj.put("mobile_key" , mobile);
            jobj.put("email_key" , email);
            jobj.put("password_key" , password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jobjreq = new JsonObjectRequest("http://192.168.0.50/signup.php",
                jobj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if(response.getString("key").equals("0"))
                    {
                        Toast.makeText(Signup.this ,"email already exist" , Toast.LENGTH_SHORT).show();

                    }
                    else if(response.getString("key").equals("1")) {
                        Toast.makeText(Signup.this ,"done" , Toast.LENGTH_SHORT).show();

                    }

                    else {
                        Toast.makeText(Signup.this ,"error" , Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        jobjreq.setRetryPolicy(new DefaultRetryPolicy(20000, 3 , 2));

        AppController app = new AppController(Signup.this);

        app.addToRequestQueue(jobjreq);

    }


}
