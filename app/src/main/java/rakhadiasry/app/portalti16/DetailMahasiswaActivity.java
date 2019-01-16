package rakhadiasry.app.portalti16;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import rakhadiasry.app.portalti16.Entity.Mahasiswa;
import rakhadiasry.app.portalti16.Network.Network;
import rakhadiasry.app.portalti16.Network.Routes;
import rakhadiasry.app.portalti16.Util.Consts;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Rakha Diasry on 17/01/2019.
 */


public class DetailMahasiswaActivity extends AppCompatActivity {

    private EditText edtName,edtNim ;
    private Button btnAdd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_mahasiswa);

        //casting untuk semua view
        edtName = (EditText)findViewById(R.id.edt_name);
        edtNim = (EditText)findViewById(R.id.edt_nim);
        btnAdd = (Button) findViewById(R.id.btn_add);


        String action = getIntent().getStringExtra(Consts.KEY_ACTION_DETAIL);
        switch (action){
            case Consts.INTENT_ADD:
                btnAdd.setText("TAMBAH MAHASISWA");
                btnAdd.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        String name = edtName.getText().toString();
                        String nim = edtNim.getText().toString();
                        if (!name.isEmpty() && !nim.isEmpty()){
                            addNewMahasiswa(name,nim);
                        } else {
                            Toast.makeText(DetailMahasiswaActivity.this,
                                    "Maaf, gak boleh kosong",
                                    Toast.LENGTH_LONG).show();
                        }

                        addNewMahasiswa(name,nim);
                    }
                });
                break;

            case Consts.INTENT_EDIT:
                final Mahasiswa mahasiswa = (Mahasiswa) getIntent().getSerializableExtra("mahasiswa");
                edtName.setText(mahasiswa.getName());
                edtNim.setText(mahasiswa.getNim());

                btnAdd.setText("EDIT");
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mahasiswa.setName(edtName.getText().toString());
                        mahasiswa.setNim(edtName.getText().toString());
                        updateMahasiswa(mahasiswa);
                    }
                });
                break;
        }

    }

    private void updateMahasiswa(Mahasiswa mahasiswa){
        Routes services = Network.request().create(Routes.class);

        String mahasiswaId = String.valueOf(mahasiswa.getId());
        String name = mahasiswa.getName();
        String nim = mahasiswa.getNim();

        services.updateMahasiswa(mahasiswaId, name, nim).enqueue(new Callback<Mahasiswa>() {
            @Override
            public void onResponse(Call<Mahasiswa> call, Response<Mahasiswa> response) {
                if (response.isSuccessful()){
                    Toast.makeText(DetailMahasiswaActivity.this,
                            "update berhasil",
                            Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Mahasiswa> call, Throwable t) {

            }
        });
    }
    private void addNewMahasiswa(String name, String nim) {
        Routes services = Network.request().create(Routes.class);

        //menambahkan post terhadap data mahasiswa aru dari api add.php

        services.postMahasiswa(name, nim).enqueue(new Callback<Mahasiswa>() {
            @Override
            public void onResponse(Call<Mahasiswa> call, Response<Mahasiswa> response) {
                if (response.isSuccessful()){
                    //ketika posnya berhasil, maka akan kembali ke main
                    finish();//ini akan destroy this activity
                }else{
                    Toast.makeText(DetailMahasiswaActivity.this, "Maaf",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Mahasiswa> call, Throwable tgit ) {
                Toast.makeText(DetailMahasiswaActivity.this, "Maaf",Toast.LENGTH_LONG).show();

            }
        });
    }
}
