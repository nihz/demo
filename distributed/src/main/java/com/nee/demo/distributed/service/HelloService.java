package com.nee.demo.distributed.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HelloService extends Remote {

    String hello(String name) throws RemoteException;
}
