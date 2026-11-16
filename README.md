Yêu cầu: JDK 17 trở lên, Python 3.10 trở lên và XAMPP.

1. Mở XAMPP, bật **Apache** và **MySQL**. Truy cập `http://localhost/phpmyadmin`, vào **Import** và chạy lần lượt:
   - `database/schema.sql`
   - `database/data.sql`

2. Sao chép file `database.properties.example` thành `database.properties`, sau đó sửa thông tin kết nối theo MySQL trên máy:

   ```properties
   url=jdbc:mariadb://127.0.0.1:3306/online_department_store3?useUnicode=true&characterEncoding=UTF-8
   user=root
   password=
   ```

   Thay `3306`, `root` và `password` nếu máy dùng cổng hoặc tài khoản khác. Cấu hình trên dùng mật khẩu rỗng.

3. Chạy **START.cmd**, đợi khởi động xong rồi mở `http://localhost:8080/`.

   Lần chạy đầu cần kết nối Internet để tải các thành phần chạy web. Đặt biến môi trường `JAVA_HOME` trỏ đến thư mục JDK và thêm Python vào PATH.

Tài khoản:

| Vai trò | Tên đăng nhập | Mật khẩu |
| --- | --- | --- |
| Khách hàng | kh0001 | 123456 |
| Nhân viên bán hàng | nv0012 | 123456 |

Chạy **STOP.cmd** để dừng web.
