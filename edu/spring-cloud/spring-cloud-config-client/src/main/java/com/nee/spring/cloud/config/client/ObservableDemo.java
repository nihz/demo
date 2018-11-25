package com.nee.spring.cloud.config.client;

import java.util.Observable;
import java.util.Observer;

public class ObservableDemo {


    public static void main(String []args) {

        MyObservable observable = new MyObservable();

        observable.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                System.out.printf("");
            }
        });

        observable.notifyObservers();
    }


}


class MyObservable  extends Observable {

    protected synchronized void setChanged() {
       super.setChanged();
    }
}