class Homem extends Thread {
  int id;
  int delay;
  BanheiroUni b;

  // Construtor
  Homem (int id, int delayTime, BanheiroUni b) {
    this.id = id;
    this.delay = delayTime;
    this.b = b;
  }

  // Método executado pelo thread
  public void run () {
    double j=777777777.7, i;
    try {
      for (;;) {
        this.b.EntraHomem(this.id); 
        for (i=0; i<100000000; i++) {j=j/2;} //...loop bobo para simbolizar o tempo no banheiro
        this.b.SaiHomem(this.id); 
        sleep(this.delay);
      }
    } catch (InterruptedException e) { return; }
  }
}