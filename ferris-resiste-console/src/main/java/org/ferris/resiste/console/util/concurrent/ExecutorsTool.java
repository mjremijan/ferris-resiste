/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ferris.resiste.console.util.concurrent;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * This class exists because it's needed for JUnit testing static methods calls.
 * To create a ScheduledExecutorService, a static method call is made on the
 * Executors class. In order to JUnit test this and verify the static method on
 * the Executors class actually gets called, this wrapper "Tool" class is
 * required.
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class ExecutorsTool {

    public ScheduledExecutorService getScheduledExecutorService() {
        return Executors.newScheduledThreadPool(2);
    }
}
