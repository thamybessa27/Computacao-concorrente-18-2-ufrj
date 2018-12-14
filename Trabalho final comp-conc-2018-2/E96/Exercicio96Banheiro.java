class BanheiroUni {
  //declarar atributos do monitor
  int FilaHomem, FilaMulher, VezHomem, VezMulher, Mulher, Homem;

  // Construtor
  BanheiroUni() { 
    //inicializar os atributos do monitor
    this.FilaHomem = 0;
    this.FilaMulher = 0;
    this.VezHomem = 0;
    this.VezMulher = 0;
    this.Mulher = 0;
    this.Homem = 0;
  } 
  
  // Entrada para mulheres
  public synchronized void EntraMulher (int id) {
    try { 
      while(Homem > 0){
	System.out.println("Homens usando o banheiro. M[" + id + "] vai se bloquear.");
	FilaMulher++;
	wait();
	FilaMulher--; }
	Mulher++;
      System.out.println ("M[" + id + "]: entrou, total de " + Mulher + " mulheres no banheiro");
    } catch (InterruptedException e) { }
  }
  
  // Saida para mulheres
  public synchronized void SaiMulher (int id) {
      VezMulher--;
      Mulher--;
      System.out.println ("M[" + id + "]: saiu, restam " + Mulher + " mulheres no banheiro");
	if(FilaHomem > 0){
		VezHomem = FilaHomem;
		notifyAll();
	}
  }
  
  // Entrada para homens
  public synchronized void EntraHomem (int id) {
    try { 
       while(Mulher > 0){
		System.out.println("Mulheres no banheiro. H["+ id+"] vai se bloquear.");
		FilaHomem++;
		wait();
		FilaHomem--;
	}
	Homem++;
       System.out.println ("H[" + id + "]: entrou, total de " + Homem + " homens no banheiro");
    } catch (InterruptedException e) { }
  }
  
  // Saida para homens
  public synchronized void SaiHomem (int id) {
	VezHomem--;   
        Homem--;    
       System.out.println ("H[" + id + "]: saiu, restam " + Homem + " homens no banheiro");
	if(FilaMulher > 0){
		VezMulher = FilaMulher;
		notifyAll();
	}
  }
}
