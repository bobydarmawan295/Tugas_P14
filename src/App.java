
import java.util.InputMismatchException;
import java.util.Scanner;


public class App {
    public static void main(String[] args) throws Exception {
     
        try{
            Scanner input = new Scanner(System.in);
            Barang connection = new Barang();
            connection.getKoneksi();
            boolean lanjut = true;

            System.out.println();
            while(lanjut){
                   
                    switch(Barang.menuPenjualan()){
        
                        case 1:
                            connection.tampilData();
                            break;
        
                        case 2:
                            connection.pesan();
                            break;
                        case 3: 
                            connection.updatePesan();
                            break;
                        case 4:
                            connection.batalPesan();
                            break;
                        case 5:
                            connection.cariData();
                            break;
                        default:
                            System.out.println("pilihan tidak ada");

                        break;
        
                    }
                System.out.print("Apakah anda ingin melanjutkan y/n ?  ");
                String pilihan =  input.next();
                lanjut = pilihan.equalsIgnoreCase("y");
             }
             input.close();
                  
        }catch (InputMismatchException e){
            e.printStackTrace();
        }
    
        
    }
       

}
       
        


