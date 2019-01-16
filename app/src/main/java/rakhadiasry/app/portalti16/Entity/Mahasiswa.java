package rakhadiasry.app.portalti16.Entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;



@Entity(tableName = "mahasiswa")
public class Mahasiswa implements Serializable{

    public void setId(int id) {
        this.id = id;
    }

    //setiap di table lebih enak ada idnya, jadi dijadiin primarykey ditabel
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String nim;

    public Mahasiswa(String name, String nim) {
        this.name = name;
        this.nim = nim;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getName() {
                return name;
    }

    public String getNim() {

        return nim;
    }


    public int getId() {
        return id;
    }
    }


