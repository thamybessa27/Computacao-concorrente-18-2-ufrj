#include <stdlib.h>
#include <stdio.h>
#include <semaphore.h>
#include <pthread.h>
#include <unistd.h>

#define CADEIRAS_BARBEARIA 6
#define TOTAL_CLIENTES 80


sem_t clientes;       //Número de clientes
sem_t barbeiros;     // Número de barbeiros que trabalham nessa barbearia
sem_t mutex;        //Exclusão mútua
int espera_clientes = 0; //Contador de clientes a espera de corte de cabelo
void vai_embora();
void cortar_cabelo();
void cabelo_sendo_cortado();

void barbeiro(){
	while(1){
     		sem_wait(&clientes);        // Se não existir clientes, ele dorme
     		sem_wait(&mutex);          //Obtém o acesso à espera_clientes
     		espera_clientes --;       //Diminui um ao número de clientes na espera
     		sem_post(&barbeiros);    //O barbeiro está pronto para cortar o cabelo
     		sem_post(&mutex);       //Libera o acesso à espera_clientes
     		cortar_cabelo();
 }
}

void cliente(){
	sem_wait(&mutex);          //Obtém o acesso à espera_clientes
    	if(CADEIRAS_BARBEARIA > espera_clientes){
		espera_clientes++;          //Acrescenta um ao número de clientes na espera
		sem_post(&clientes);       // Cliente pronto para ter o cabelo cortado
        	sem_post(&mutex);         // Libera o acesso à espera_clientes
        	sem_wait(&barbeiros);    // Se o barbeiro tiver ocupado, espera
        	cabelo_sendo_cortado();
	    }
    	else{
		sem_post(&mutex); //Libera o acesso à espera_clientes
		vai_embora(); //Não há mais cadeiras vazias, barbearia cheia
	}
}

void vai_embora(){
	printf("Barbearia cheia, cliente foi embora\n");
}

void cortar_cabelo(){
	printf("Barbeiro esta cortando cabelo\n");
	sleep(2);
}

void cabelo_sendo_cortado(){
	sleep(2);
}

int main(){

	pthread_t barbeirothread, clientethread[TOTAL_CLIENTES];
	int i=0, sleeptime;


	// Inicializa semaforos
	sem_init(&clientes, 0, 0);
	sem_init(&barbeiros, 0, 1);
	sem_init(&mutex, 0, 1);

	//Cria nova thread, no caso a barbeiro
	pthread_create( &barbeirothread, NULL,(void *) barbeiro, NULL );

	for(i=0 ; i<TOTAL_CLIENTES ;i++){
		sleeptime = rand()%6;
		sleep(sleeptime);
		pthread_create(&clientethread[i], NULL, (void *) cliente, NULL);
	}

	pthread_join( barbeirothread, NULL);
	for(i=0 ; i < TOTAL_CLIENTES ; i++){
		pthread_join( clientethread[i], NULL);
	}

	return 0;
}
