package com.nee.demo.distributed.rmi;




import com.nee.demo.distributed.service.HelloService;
import com.nee.demo.distributed.service.impl.HelloServiceImpl;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiServer {

    public static void main(String[] args) {

        try {
            HelloService helloService = new HelloServiceImpl();
            Registry registry = LocateRegistry.createRegistry(1099);

            Naming.rebind("rmi://127.0.0.1/helloService", helloService);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
