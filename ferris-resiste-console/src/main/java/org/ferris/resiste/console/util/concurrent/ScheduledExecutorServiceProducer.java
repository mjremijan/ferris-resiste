/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ferris.resiste.console.util.concurrent;

import java.util.concurrent.ScheduledExecutorService;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class ScheduledExecutorServiceProducer {

    @Inject
    protected ExecutorsTool executorsTool;

    @Produces
    public ScheduledExecutorService getScheduledExecutorService() {
        return executorsTool.getScheduledExecutorService();
    }
}
