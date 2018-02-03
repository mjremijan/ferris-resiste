package org.ferris.resiste.console.text.i18n;

import java.util.LinkedList;
import javax.enterprise.inject.Vetoed;

@Vetoed
public class LocalizedStringList extends LinkedList<String> {

    private static final long serialVersionUID = 4838482648148998802L;

    public void replace(CharSequence target, CharSequence replacement) {
        LinkedList<String> replaced = new LinkedList<String>();

        for (String s : this) {
            s = s.replace(target, replacement);
            replaced.add(s);
        }

        this.clear();
        this.addAll(replaced);
    }
}
