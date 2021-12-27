
public class Transaksi extends Barang implements Diskon {
	
	public int harga;
    public int diskon;
    public Transaksi(Integer harga,Integer diskon){
        this.harga = harga;
        this.diskon = diskon;
    }

    @Override
    public  int harga(Integer harga){
        this.harga = harga;
        if(harga > 100000 ){
            this.harga = (int)((double)(this.harga*0.90));
        }else if(harga > 50000){
            this.harga = (int)((double)(this.harga*0.95));
        }else if(harga > 25000){
            this.harga = (int)((double)(this.harga*0.97));
        }else{
            this.harga = (int)((double)(this.harga*0.99));
        }
        return this.harga;
    }

    @Override
    public  int Discount(Integer diskon){
        this.harga = diskon;
        if(harga > 100000 ){
            this.harga = (int)((double)(this.harga*0.10));
        }else if(harga > 50000){
            this.harga = (int)((double)(this.harga*0.05));
        }else if(harga > 25000){
            this.harga = (int)((double)(this.harga*0.03));
        }else{
            this.harga = (int)((double)(this.harga*0.01));
        }
        return this.harga;
	}
	
}
