public class FirstThread extends Thread{
    String INPUT_STRING;
    String TEMPLATE;

    static int sum = 0;

    public FirstThread(String INPUT_STRING, String TEMPLATE) {
        this.INPUT_STRING = INPUT_STRING;
        this.TEMPLATE = TEMPLATE;
    }

    private static final Object lock = new Object();

    @Override
    public void run(){
        for (int i = 0; i < INPUT_STRING.length(); i++) {
            if (Matcher.match(String.valueOf(INPUT_STRING.charAt(i)), TEMPLATE)) {
                synchronized (lock) { // помечаем, что блок может выполняться только одним потоком одновременно
                    sum++;
                }
            }
        }

    }

    public static int getSum() {
        return sum;
    }
}
