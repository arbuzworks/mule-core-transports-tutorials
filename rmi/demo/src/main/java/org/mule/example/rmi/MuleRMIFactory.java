package org.mule.example.rmi;

import java.rmi.server.UnicastRemoteObject;

import  org.mule.jndi.MuleInitialContextFactory;
import javax.naming.InitialContext;

public class MuleRMIFactory
{

    public Object create() throws Exception
    {
        InitialContext ic = new InitialContext();
        ic.addToEnvironment(InitialContext.INITIAL_CONTEXT_FACTORY, MuleInitialContextFactory.class.getName());

        HelloImpl obj = new HelloImpl();
        Hello stub = (Hello) UnicastRemoteObject.exportObject(obj, 0);

        ic.bind("org.mule.example.rmi.Hello", stub);
        
        return ic;
    }

}