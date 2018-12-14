public class Rooms {

    // Arbitrary number for max threads
    private int maxPerRoom = 6;
    private int rooms[];
    private ReentrantLock lock;
    private Rooms.Handler exitHandlers[];

    public interface Handler {
        void onEmpty();
    }

    public Rooms(int m) {
        rooms = new int[m];
        for(int i = 0; i < m; i++)
            rooms[i] = 0;

        exitHandlers = new Handler[m];

        lock = new ReentrantLock();
    }

    void enter(int room){
        lock.lock();
        try {
            boolean othersEmpty = true;
            for (int i = 0; i < rooms.length; i++) {
                if (i != room && rooms[i] > 0){
                    // Se outra cela já está ocupada
                    othersEmpty = false;
                    break;
                }
            }

            if(othersEmpty && rooms[room] < maxPerRoom ){
                //Todas celas estão vazias e a sala desejada não está cheio
                rooms[room]++;
            }
        } finally {
            lock.unlock();
        }
    }

    boolean exit(){
        lock.lock();
        try {
            for (int i = 0; i < rooms.length; i++) {
                if (rooms[i] > 0)
                    if( --rooms[i] == 0 ) {
                        exitHandlers[i].onEmpty(); //Enquanto é executado, todas as threads esperam.
                        return true;
                    }
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    public void setExitHandler(int i, Rooms.Handler h) {
        exitHandlers[i] = h;
    }
}
}
