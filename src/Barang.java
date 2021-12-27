import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Scanner;

public class Barang implements CRUD{

      static Connection conn;
      static Statement stm;
      static ResultSet rs;
      

      static Scanner input = new Scanner(System.in);
      private int harga;
      private int jumlah;
      private int subtotal;
      private int diskon;
      Transaksi transaksi;
  
    public Connection getKoneksi() {
        
        String url = "jdbc:mysql://localhost:3306/minimarket";
        String user = "root";
        String pass = "";
   
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn  = DriverManager.getConnection(url, user, pass);
            System.out.print("koneksi berhasil");
        }catch (Exception e){
            System.out.println(e);
        }
        return conn;
    }

    public static int menuPenjualan(){
        
            Calendar calendar = Calendar.getInstance();
            System.out.println(" \n\t" + calendar.getTime());
            System.out.println();
             System.out.println(
            """
                    =============================================
                                    SELAMAT DATANG
                    =============================================
                                      FUTURE MART

                    Menyediakan kebutuhan sehari-hari
                    Buka 24 Jam
                
            1. Lihat pesanan
            2. Masukkan pesanan
            3. ubah pesanan
            4. batalkan pesanan
            5. cari pesanan 
            """
            );
            
            
            System.out.print("Masukkan pilihan   : ");
            int pilihan =  input.nextInt();
            return pilihan;
       
    }   

    @Override
    public  void tampilData(){
        
        String sql =  "SELECT * FROM penjualan";
        try {
            stm = conn.createStatement();
            rs =  stm.executeQuery(sql);
            
            String judul = "daftar pesanan";
            System.out.println("\t\t\t-----------------------");
            System.out.printf("\t\t\t    %s\n",judul.toUpperCase());
            System.out.println("\t\t\t-----------------------\n");
           
            
            System.out.println("| No Faktur | Nama\t  | Harga  | Jumlah |  Diskon |  Subtotal |");
            System.out.println("---------------------------------------------------------");
            while(rs.next()){
                int no_faktur =  rs.getInt("no_faktur");
                String nama =  rs.getString("nama");
                int harga =  rs.getInt("harga");
                int jumlah=  rs.getInt("jumlah");
                int diskon=  rs.getInt("diskon");
                int subtotal = rs.getInt("subtotal");
                System.out.println(String.format("|   %d\t    | %-10s  | %-6d | %-6d |  %-5d  |  %-6d   |", no_faktur,nama,harga,jumlah,diskon,subtotal));
                
            }
            char[] str = {'T','e','r','i','m','a',' ','K','a','s','i','h'};
            String str2 = "";

            str2 = String.copyValueOf(str);
            str2 = str2.toUpperCase();
            System.out.printf("\n\t\t\t    %s \n\n",str2);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void pesan(){
        try {
            System.out.print("\nMasukkan no_faktur : ");
            int  no_faktur = input.nextInt();
          
            input.nextLine();

            System.out.print("Masukkan nama      : ");
            String nama = input.nextLine();

            System.out.print("Masukkan harga     :");
            int harga = input.nextInt();

            System.out.print("Masukkan jumlah    : ");
            jumlah = input.nextInt();
            
            if(harga < 0 || jumlah < 0 || no_faktur < 0){
                throw new IllegalArgumentException("angka tidak boleh negatif");
            }
            transaksi = new Transaksi(harga, diskon);
            this.harga = harga * jumlah;
            subtotal = transaksi.harga(this.harga);
            diskon =  transaksi.Discount(this.harga);
            
            stm = conn.createStatement();
            String sql =  "SELECT * FROM penjualan";
            stm = conn.createStatement();
            rs =  stm.executeQuery(sql);
           
            while(rs.next()){
                if(no_faktur == rs.getInt("no_faktur")){
                    System.out.println("\nno faktur telah ada");
                    throw new SQLException();
                }   
               
            }
            String sql2 =  "INSERT INTO penjualan (no_faktur,nama,harga,jumlah,diskon,subtotal) VALUE('"+no_faktur+"','"+nama+"','"+harga+"','"+jumlah+"','"+diskon+"','"+subtotal+"')";
            stm.executeUpdate(sql2);
            System.out.println("Barang dimasukkan ke pesanan");
        
           
        } catch (IllegalArgumentException  | SQLException e) {
            e.printStackTrace();
        }
    
    }

     
    @Override
    public void updatePesan(){
        try {
            tampilData();
            System.out.println();
            System.out.print("Masukkan no faktur: ");
            int no_faktur =  input.nextInt();
            input.nextLine();

            System.out.print("Masukkan nama     : ");
            String nama = input.nextLine();

            System.out.print("Masukkan harga    : ");
            int harga = input.nextInt();

            System.out.print("Masukkan jumlah   : ");
            int jumlah = input.nextInt();

            if(harga < 0 || jumlah < 0 || no_faktur < 0){
                throw new IllegalArgumentException("angka tidak boleh negatif");
            }
            
            transaksi = new Transaksi(harga, diskon);
            this.harga = harga * jumlah;
            subtotal = transaksi.harga(this.harga);
            diskon =  transaksi.Discount(this.harga);
            boolean isFound = false;
          
            String sql =  "SELECT * FROM penjualan";
            stm = conn.createStatement();
            rs =  stm.executeQuery(sql);
           
            while(rs.next()){
                if(no_faktur == rs.getInt("no_faktur")){
                    isFound = true;
                }   
            }

            if(!isFound){
                throw new SQLException("data tidak ditemukan");
            }else{
                String sql2 = "UPDATE penjualan SET no_faktur='"+no_faktur+"',nama='"+nama+"',harga='"+harga+"',jumlah='"+jumlah+"' WHERE no_faktur= '"+no_faktur+"'";
                stm.executeUpdate(sql2);
                System.out.println("Barang pesanan berhasil diupdate");
            }
        } catch (SQLException | IllegalArgumentException e) {
            e.printStackTrace();
        }
       
    }

    @Override
    public void batalPesan(){
        try {
            tampilData();
            System.out.println();
            System.out.print("Masukkan no faktur : ");
            int no_faktur = input.nextInt();

            if(no_faktur < 0){
                throw new IllegalArgumentException("angka tidak boleh negatif");
            }

            boolean isFound = false;
          
            String sql =  "SELECT * FROM penjualan";
            stm = conn.createStatement();
            rs =  stm.executeQuery(sql);
           
            while(rs.next()){
                if(no_faktur == rs.getInt("no_faktur")){
                    isFound = true;
                }   
            }

            if(!isFound){
                throw new SQLException("data tidak ditemukan");
            }else{
                String sql2 = "DELETE FROM  penjualan WHERE no_faktur= " +no_faktur;
                stm.executeUpdate(sql2);
                System.out.println("Barang batal di pesan");
            }
        } catch (SQLException | IllegalArgumentException e) {
            e.printStackTrace();
        }

       
    }

    @Override
    public void cariData(){
        try {

            System.out.print("masukkan keyword (nama barang/no faktur) : ");
            String keyword = input.next();
            
            String sql = "SELECT * FROM penjualan WHERE no_faktur LIKE '%"+keyword+"%' OR  nama LIKE '%"+keyword+"%' ";

            stm = conn.createStatement();
            rs =  stm.executeQuery(sql);

            System.out.println("| No_faktur | nama\t  | harga  | jumlah |  diskon |  subtotal |");
                    System.out.println("---------------------------------------------------------");
            while(rs.next()){
                    int no_faktur =  rs.getInt("no_faktur");
                    String nama = rs.getString("nama");
                    int jumlah =  rs.getInt("jumlah");
                    int harga=  rs.getInt("harga");
                    int diskon=  rs.getInt("diskon");
                    int subtotal = rs.getInt("subtotal");
                    
                    System.out.println(String.format("|   %d\t    | %-10s  | %-6d | %-6d |  %-5d  |  %-6d   |", no_faktur,nama,harga,jumlah,diskon,subtotal));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
					
    }
}