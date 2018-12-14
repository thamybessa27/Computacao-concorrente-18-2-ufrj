class Banheiro {
  static final int M = 8;
  static final int H = 7;

  public static void main (String[] args) {
    int i;
    BanheiroUni b = new BanheiroUni();// Monitor
    Mulher[] m = new Mulher[M];       // Mulheres
    Homem[] h = new Homem[H];         // Homens

    for (i=0; i<M; i++) {
       m[i] = new Mulher(i+1, (i+1)*500, b);
       m[i].start(); 
    }
    for (i=0; i<H; i++) {
       h[i] = new Homem(i+1, (i+1)*500, b);
       h[i].start(); 
    }
  }
}