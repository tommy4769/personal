package tw.edu.pu.s1088123.personal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.protobuf.Any;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    Button btn1,btn2,btn3,btn4;
    TextView txt2;
    FirebaseFirestore db;
    EditText edt1,edt2,edt3,edt4;
    ListView LV;
    String dataselct;
    String xslect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        btn1 = findViewById(R.id.update);
        btn2 = findViewById(R.id.delete);
        btn3 = findViewById(R.id.calculate);
        btn4 = findViewById(R.id.check);
        txt2 = findViewById(R.id.text2);
        edt1 = findViewById(R.id.edittext1);
        edt2 = findViewById(R.id.edittext2);
        edt3 = findViewById(R.id.edittext3);
        edt4 = findViewById(R.id.edittext4);
        //LV = findViewById(R.id.list);
        db = FirebaseFirestore.getInstance();

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Map<String, String> personl=new HashMap<>();
                    personl.put("名稱",edt1.getText().toString());
                    personl.put("日期",edt2.getText().toString());
                    personl.put("身高",edt3.getText().toString());
                    personl.put("體重",edt4.getText().toString());


                    db.collection("personl").add(personl)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(getBaseContext(),"新增資料成功",Toast.LENGTH_LONG).show();
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getBaseContext(),"新增資料失敗",Toast.LENGTH_LONG).show();
                                }
                            });
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db.collection("personl").document("personl").delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                              Toast.makeText(getBaseContext(),"刪除成功",Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                              Toast.makeText(getBaseContext(),"刪除失敗",Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DecimalFormat nf = new DecimalFormat(("0.00"));
                EditText fheight = (EditText) view.findViewById(R.id.edittext3);
                EditText fweight = (EditText) view.findViewById(R.id.edittext4);
                double height = Double.parseDouble(fheight.getText().toString());
                double weight = Double.parseDouble(fweight.getText().toString());
                double BMI = weight/(height*height);
                TextView result = (TextView) view.findViewById(R.id.text2);
                result.setText(nf.format(BMI));
            }
        });






    }
}