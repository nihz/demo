package com.nee.demo.distributed.service.impl;


import com.nee.demo.distributed.service.HelloService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class HelloServiceImpl extends UnicastRemoteObject implements HelloService {
    public HelloServiceImpl() throws RemoteException {
        super();
    }
    @Override
    public String hello(String name) throws RemoteException  {

        return name + ", hello";
    }
}
