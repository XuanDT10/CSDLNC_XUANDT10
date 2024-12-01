package controller;
import java.util.ArrayList;
import java.util.List;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;

import model.Product;

public class Controller {
    private ObjectContainer db;

    // Constructor: Mở kết nối với db4o
    public Controller() {
        db = Db4o.openFile("products.db4o");  // Mở hoặc tạo cơ sở dữ liệu db4o
    }

    // Phương thức thêm sản phẩm vào db4o
    public void addProduct(Product product) {
        db.store(product);  // Lưu đối tượng sản phẩm vào db4o
        System.out.println("Sản phẩm đã được lưu vào db4o: " + product);
    }

    // Phương thức hiển thị tất cả sản phẩm từ db4o
    public void displayProducts() {
        // Truy vấn tất cả đối tượng Product trong db4o
        ObjectSet<Product> products = db.queryByExample(Product.class);
        if (products.isEmpty()) {
            System.out.println("Không có sản phẩm nào trong cơ sở dữ liệu.");
        } else {
            System.out.println("Danh sách sản phẩm từ db4o:");
            while (products.hasNext()) {
                System.out.println(products.next());
            }
        }
    }

    
    // Phương thức xóa tất cả sản phẩm
    public void deleteAllProducts() {
        // Tạo truy vấn để tìm tất cả sản phẩm
        Query query = db.query();
        query.constrain(Product.class); // Constrain để tìm tất cả các đối tượng kiểu Product
        List<Product> products = query.execute(); // Thực thi truy vấn và lấy danh sách sản phẩm
        
        // Xóa tất cả các sản phẩm
        for (Product product : products) {
            db.delete(product);
        }

        db.commit(); // Cam kết thay đổi vào cơ sở dữ liệu
    }
    
    // Đóng kết nối db4o khi không sử dụng nữa
    
    
    
    
    public void closeDb() {
        db.close();
    }

    public List<Product> getAllProducts() {
        // Tạo một danh sách để chứa sản phẩm
        List<Product> productList = new ArrayList<>();

        // Sử dụng db4o để truy vấn tất cả sản phẩm
        ObjectSet<Product> result = db.queryByExample(Product.class);

        // Lặp qua kết quả và thêm vào danh sách
        while (result.hasNext()) {
            productList.add(result.next());
        }

        return productList;
    }
}

