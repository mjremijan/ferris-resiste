package org.ferris.resiste.console.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class IteratorRemoveTest {

    @Test
    public void testLinkedList() {
        LinkedList<String> strs
            = new LinkedList<>();

        strs.add("hello");
        strs.add("doctor");
        strs.add("name");
        strs.add("continue");
        strs.add("yesterday");
        strs.add("tomorrow");

        Iterator<String> itr
            = strs.iterator();

        while (itr.hasNext()) {
            String next = itr.next();
            if (next.length() > 4) {
                itr.remove();
            }
        }

        Assert.assertEquals(1, strs.size());
    }

    @Test
    public void testArrayList() {
        ArrayList<String> strs
            = new ArrayList<>();

        strs.add("hello");
        strs.add("doctor");
        strs.add("name");
        strs.add("continue");
        strs.add("yesterday");
        strs.add("tomorrow");

        Iterator<String> itr
            = strs.iterator();

        while (itr.hasNext()) {
            String next = itr.next();
            if (next.length() > 4) {
                itr.remove();
            }
        }

        Assert.assertEquals(1, strs.size());
    }

    @Test
    public void testForLoop() {
        ArrayList<String> strs
            = new ArrayList<>();

        strs.add("hello");
        strs.add("doctor");
        strs.add("name");
        strs.add("continue");
        strs.add("yesterday");
        strs.add("tomorrow");

        for (Iterator<String> itr = strs.iterator(); itr.hasNext(); ) {
            String next = itr.next();
            if (next.length() > 4) {
                itr.remove();
            }
        }

        Assert.assertEquals(1, strs.size());
    }
}
