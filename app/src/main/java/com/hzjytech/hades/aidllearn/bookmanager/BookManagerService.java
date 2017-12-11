package com.hzjytech.hades.aidllearn.bookmanager;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import com.hzjytech.hades.aidllearn.Book;
import com.hzjytech.hades.aidllearn.IBookManager;
import com.hzjytech.hades.aidllearn.IOnNewBookArrivedListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class BookManagerService extends Service {

    private static final String TAG="BMS";

    /**
     * 采用支持并发读/写的CopyOnWriteArrayList
     * 避免多个客户端同时连接的时候，多个线程同时访问导致的问题
     */
    private CopyOnWriteArrayList<Book> mBookList=new CopyOnWriteArrayList<>();

    private AtomicBoolean mIsServiceDestroyed=new AtomicBoolean(false);

    private RemoteCallbackList<IOnNewBookArrivedListener> mListenerList=new
            RemoteCallbackList<>();

    private Binder mBinder=new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);

        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {

            mListenerList.register(listener);
        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {

            mListenerList.unregister(listener);
        }

    };
    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book(1,"Android"));
        mBookList.add(new Book(2,"Ios"));

        new Thread(new ServiceWorker()).start();
    }

    class ServiceWorker implements Runnable{

        @Override
        public void run() {
            if (!mIsServiceDestroyed.get()) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for(int i=0;i<5;i++) {
                    int bookId = mBookList.size() + 1;
                    Book newBook = new Book(bookId, "new Book#" + bookId);

                    try {
                        onNewBookArrived(newBook);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

    private void onNewBookArrived(Book book) throws RemoteException {
        mBookList.add(book);

        final int N=mListenerList.beginBroadcast();

        for(int i=0;i<N;i++){
            IOnNewBookArrivedListener l=mListenerList.getBroadcastItem(i);
            if(l != null){
                l.onNewBookArrived(book);
            }
        }
        mListenerList.finishBroadcast();
    }

    @Override
    public void onDestroy() {
        mIsServiceDestroyed.set(true);
        super.onDestroy();
    }

    public BookManagerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
