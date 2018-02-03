package org.ferris.resiste.console.application;

import java.io.File;
import javax.enterprise.inject.Vetoed;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@Vetoed
public class ApplicationDirectory extends File {

    private static final long serialVersionUID = 7491901906021288631L;

    public ApplicationDirectory(String path) {
        super(path);
    }
}
