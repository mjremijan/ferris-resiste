package org.ferris.resiste.console.retry;

import java.io.Serializable;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import org.slf4j.Logger;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@Interceptor
@ExceptionRetry
public class ExceptionRetryInterceptor implements Serializable {

    @Inject
    protected Logger log;

    @AroundInvoke
     public Object retryIfExceptionCaught(InvocationContext ctx) throws Exception {
         Exception caught = null;
         for (int i=1, imax=4; i<=imax; i++) {
             try {
                 return ctx.proceed();
             }
             catch (ExceptionBreak e) {
                 caught = e;
                 log.warn(String.format("ExceptionBreak caught on attempt %d of %d", i, imax), e);
                 break;
             }
             catch (Exception e) {
                 caught = e;
                 log.warn(String.format("Exception caught on attempt %d of %d", i, imax), e);
             }
             try {
                 Thread.sleep(1000 * 5);
             } catch (InterruptedException e) {}
         }
         throw caught;
     }
}
