package rakhadiasry.app.portalti16;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import rakhadiasry.app.portalti16.Adapter.MahasiswaAdapter;
import rakhadiasry.app.portalti16.Data.MahasiswaRepository;
import rakhadiasry.app.portalti16.Entity.Mahasiswa;



public class FavoriteActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MahasiswaAdapter adapter;
    private MahasiswaRepository mahasiswaRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_fav);

        mahasiswaRepository = new MahasiswaRepository(this);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                List<Mahasiswa> mahasiswas = mahasiswaRepository.getMahasiswas();
                if (mahasiswas.size() > 0 ){
                    adapter = new MahasiswaAdapter(mahasiswas);
                    recyclerView.setAdapter(adapter);
                }else{
                    Toast.makeText(FavoriteActivity.this, "Tidak ada item", Toast.LENGTH_SHORT).show();
                }
                return null;
            }
        }.execute();


    }
}
