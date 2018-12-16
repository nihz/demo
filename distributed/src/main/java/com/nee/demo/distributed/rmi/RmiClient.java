package com.nee.demo.distributed.rmi;

import com.nee.demo.distributed.service.HelloService;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RmiClient {

    public static void main(String[] args) {
        try {
            HelloService helloService = (HelloService) Naming.lookup("rmi://127.0.0.1/helloService");
            System.out.println(helloService.hello("nee"));
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
