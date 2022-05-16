package vn.edu.huflit.npl_19dh110839.models;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CartDao {
    @Query("SELECT * FROM Cart")
    List<vn.edu.huflit.npl_19dh110839.models.Cart> getAll();

    @Insert
    void insertAll(vn.edu.huflit.npl_19dh110839.models.Cart... carts);

    @Insert
    void insertCart(vn.edu.huflit.npl_19dh110839.models.Cart cart);

    @Delete
    void deleteCart(vn.edu.huflit.npl_19dh110839.models.Cart cart);

    @Update
    void updateCart(vn.edu.huflit.npl_19dh110839.models.Cart cart);

    @Delete
    void deleteMultiCart(vn.edu.huflit.npl_19dh110839.models.Cart... cart);

    @Query("DELETE FROM Cart")
    void delete();

    @Query("UPDATE Cart SET quantity = :qty WHERE foodKey = :foodKey")
    void updateQty(String foodKey,int qty);

    @Query("SELECT * FROM Cart WHERE foodKey = :foodKey")
    Cart findByID(String foodKey);
}
