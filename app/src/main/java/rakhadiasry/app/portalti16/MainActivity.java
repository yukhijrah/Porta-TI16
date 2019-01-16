package rakhadiasry.app.portalti16;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import rakhadiasry.app.portalti16.Adapter.MahasiswaAdapter;
import rakhadiasry.app.portalti16.Data.MahasiswaRepository;
import rakhadiasry.app.portalti16.Entity.DaftarMahasiswa;
import rakhadiasry.app.portalti16.Entity.Mahasiswa;
import rakhadiasry.app.portalti16.Network.Network;
import rakhadiasry.app.portalti16.Network.Routes;
import rakhadiasry.app.portalti16.Util.Consts;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    //public List<Mahasiswa> mahasiswas = new ArrayList<>();
    private RecyclerView recyclerView;
    public FloatingActionButton buttonAdd;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //casting recycler source repo umair
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        //LinearLayoutManager linear = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //casting Button
        buttonAdd = (FloatingActionButton) findViewById(R.id.btn_add);
        requestDaftarMahasiswa();
    }

    @Override
    protected void onStart() {
        super.onStart();
        requestDaftarMahasiswa();
        btnMove();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //menampilkan menu di activity
        getMenuInflater().inflate(R.menu.menu_name, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //agarr bisa di click
        switch (item.getItemId()) {
            case R.id.menu_favorite:
                startActivity(new Intent(MainActivity.this, FavoriteActivity.class));
                finish();
                //ketika menu di fav, maka panggil
                requestDaftarMahasiswa();
                break;

            case R.id.menu_refresh:
                //ketika menu di refresh, maka panggil
                requestDaftarMahasiswa();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void requestDaftarMahasiswa() {
        //pertama, memanggil request dari retrofit yang sudah dibuat
        final Routes services = Network.request().create(Routes.class);

        //kite melakukan request terhadap getMahasiswa()
        services.getMahasiswa().enqueue(new Callback<DaftarMahasiswa>() {
            @Override
            public void onResponse(Call<DaftarMahasiswa> call, Response<DaftarMahasiswa> response) {
                //cek request yang dilakukan, berhasil atau tidak
                if (response.isSuccessful()) {
                    //casting data yang didapatkan menjadi DaftarMahasiswa
                    DaftarMahasiswa mahasiswas = response.body();

                    //get title
                    Log.d("TI16", mahasiswas.getTitle());

                    //tampilkan daftar mahasiswa di recycler view
                    MahasiswaAdapter adapter = new MahasiswaAdapter(mahasiswas.getData());

                    //untuk handle item delet di item mahasiswa
                    //untuk menghapus data di API
                    adapter.setListener(new MahasiswaAdapter.MahasiswaListener() {
                        @Override
                        public void OnDelete(int mhsId) {
                            String id = String.valueOf(mhsId);
                            deleteMahasiswa(services, id);
                        }

                        @Override
                        public void OnFav(Mahasiswa mahasiswa) {
                            MahasiswaRepository mahasiswaRepository = new MahasiswaRepository(MainActivity.this);
                            mahasiswaRepository.insertMahasiswa(mahasiswa);
                        }
                    });
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<DaftarMahasiswa> call, Throwable t) {
                onMahasiswaError();
            }
        });
    }

    private void onMahasiswaError() {
        Toast.makeText(
                MainActivity.this,
                "gagal",
                Toast.LENGTH_LONG).show();

    }

    //digunakan untuk pindah antar layer
    private void btnMove() {
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DetailMahasiswaActivity.class);
                intent.putExtra(Consts.KEY_ACTION_DETAIL, Consts.INTENT_ADD);

                startActivity(intent);
            }
        });
    }

    private void deleteMahasiswa(final Routes services, final String mhsId) {
        //jika alert akan memunculkan ini
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        //title dari aletnya
        alert.setTitle(R.string.app_name);
        //pesan alertnya
        alert.setMessage("are you sure ? ");

        //jika tidak
        alert.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        //jika iya maka akan menghapus berdasarkan idnya
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                services.deleteMahasiswa(mhsId).enqueue(new Callback<Mahasiswa>() {

                    @Override
                    public void onResponse(Call<Mahasiswa> call, Response<Mahasiswa> response) {
                        if (response.isSuccessful()) {
                            //jika berhasil akan merequest daftar mahasiswa
                            requestDaftarMahasiswa();
                        } else {
                            //jika terdapat error saat menghapus memunculkan methode ini
                            onMahasiswaError();
                        }
                    }

                    @Override
                    public void onFailure(Call<Mahasiswa> call, Throwable t) {
                        onMahasiswaError();

                    }
                });
            }
        });
        alert.show();
    }

}