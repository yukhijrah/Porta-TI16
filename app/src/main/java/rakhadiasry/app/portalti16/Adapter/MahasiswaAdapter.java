package rakhadiasry.app.portalti16.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import rakhadiasry.app.portalti16.DetailMahasiswaActivity;
import rakhadiasry.app.portalti16.Entity.Mahasiswa;
import rakhadiasry.app.portalti16.Holder.MahasiswaHolder;
import rakhadiasry.app.portalti16.R;
import rakhadiasry.app.portalti16.Util.Consts;

/**
 * Created by Rakha Diasry on 17/01/2019.
 */
public class MahasiswaAdapter extends RecyclerView.Adapter<MahasiswaHolder> {

    private List<Mahasiswa> mahasiswas = new ArrayList<>();
    private MahasiswaListener listener;

    public void setListener(MahasiswaListener listener) {
        this.listener = listener;
    }

    public MahasiswaAdapter(List<Mahasiswa> mahasiswas) {

        this.mahasiswas = mahasiswas;
    }


    @Override
    public MahasiswaHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //source repo umair retrofit
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mahasiswa,parent, false);
        final MahasiswaHolder mahasiswaHolder = new MahasiswaHolder(view);

        final Context context = mahasiswaHolder.itemView.getContext();
        mahasiswaHolder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //definisikan posisi untuk get mahasiswa
                int adapterPosition = mahasiswaHolder.getAdapterPosition();
                Mahasiswa mahasiswa = mahasiswas.get(adapterPosition);

                Intent detailIntent = new Intent(context, DetailMahasiswaActivity.class);
                detailIntent.putExtra("mahasiswa",mahasiswa);
                detailIntent.putExtra(Consts.KEY_ACTION_DETAIL, Consts.INTENT_EDIT);
                context.startActivity(detailIntent);
            }
        });

        return mahasiswaHolder;
    }

    @Override
    public void onBindViewHolder(MahasiswaHolder holder, final int position) {
        holder.txtnama.setText(mahasiswas.get(position).getName());
        holder.txtnim.setText(mahasiswas.get(position).getNim());

        //tambahkan holder delete beserta dengan listemermua
        holder.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnDelete(mahasiswas.get(position).getId());
            }
        });

        //fungsi fav
        holder.btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnFav(mahasiswas.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mahasiswas.size();
    }
    //kita lakukan di activity, dengan memanfaatkan interface
    public interface MahasiswaListener{
        void OnDelete(int mhsId);
        void OnFav(Mahasiswa mahasiswa);
    }
}