package rakhadiasry.app.portalti16.Network;

import rakhadiasry.app.portalti16.Entity.DaftarMahasiswa;
import rakhadiasry.app.portalti16.Entity.Mahasiswa;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;



public interface Routes{
    //didapat dari https://ti16-api.herokuapp.com/ (Network)
    @GET("dev/list_mahasiswa")
    Call<DaftarMahasiswa> getMahasiswa();

    //didapat dari https://ti16-api.herokuapp.com/ (Network)
    @FormUrlEncoded
    @POST("dev/insert_mahasiswa")
    Call<Mahasiswa> postMahasiswa(
            @Field("name") String name,
            @Field("nim") String nim
    );

    @DELETE("mahasiswatest/{mhsId}")
    Call<Mahasiswa> deleteMahasiswa(
            //tergantung idnya
            @Path("mhsId") String mhsId
    );

    @PUT("mahasiswatest/{mhsId}")
    @FormUrlEncoded
    Call<Mahasiswa> updateMahasiswa(
            @Path("mhsId") String mhsId,
            @Field("name") String name,
            @Field("nim") String nim
    );

}
