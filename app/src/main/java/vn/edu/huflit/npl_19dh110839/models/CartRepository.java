package vn.edu.huflit.npl_19dh110839.models;

import android.app.Application;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class CartRepository {
    CartDao cartDao;
    List<Cart> carts;
    public CartRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        cartDao = db.cartDao();
    }

    public CartRepository() {

    }

    public int getCountCart() throws ExecutionException, InterruptedException {
        Future<Integer> data = AppDatabase.databaseWriteExecutor.submit(() -> {
            int count = cartDao.getAll().stream().mapToInt((cart) -> cart.quantity).sum();
            return count;
        });

        return data.get();
    }

    public void updateQty(String foodKey, int qty) {
        AppDatabase.databaseWriteExecutor.submit(() -> {
            cartDao.updateQty(foodKey, qty);
        });
    }

    public Cart findByID(String foodKey) throws ExecutionException, InterruptedException {
        Future<Cart> cart = AppDatabase.databaseWriteExecutor.submit(() -> {
            Cart cartByID = cartDao.findByID(foodKey);
            return cartByID;
        });
        return cart.get();
    }

    ;

    public void insert(Cart cart) throws ExecutionException, InterruptedException {
        Cart findExisted = findByID(cart.foodKey);
        if (findExisted == null) {
            AppDatabase.databaseWriteExecutor.execute(() -> {
                cartDao.insertCart(cart);
            });
        } else {
            Cart findThenUpdate = findByID(cart.foodKey);
            Integer cartQty = findThenUpdate.quantity + 1;
            updateQty(cart.foodKey, cartQty);
        }
    }

    public void update(Cart cart) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            cartDao.updateCart(cart);
        });
    }

    public void delete(Cart cart) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            cartDao.deleteCart(cart);
        });
    }

    public void deleteAll() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            cartDao.delete();
        });
    }


    public List<Cart> getAllCarts() throws ExecutionException, InterruptedException {
        Future<List<Cart>> data = AppDatabase.databaseWriteExecutor.submit(() -> {
            List<Cart> dataCart = cartDao.getAll();
            return dataCart;
        });

        return data.get();
    }


}
