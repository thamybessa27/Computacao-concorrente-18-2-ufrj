class Mulher extends Thread {
  int id;
  int delay;
  BanheiroUni b;

  // Construtor
  Mulher (int id, int delayTime, BanheiroUni b) {
    this.id = id;
    this.delay = delayTime;
    this.b = b;
  }

  // Método executado pela thread
  public void run () {
    double j=777777777.7, i;
    try {
      for (;;) {
        this.b.EntraMulher(this.id);
        for (i=0; i<100000000; i++) {j=j/2;} //...loop bobo para simbolizar o tempo no banheiro
        this.b.SaiMulher(this.id);
        sleep(this.delay); 
      }
    } catch (InterruptedException e) { return; }
  }
}
