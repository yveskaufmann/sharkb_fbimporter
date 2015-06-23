package net.sharkfw.apps.fb.core.importer;

import net.sharkfw.knowledgeBase.SharkKB;
import org.springframework.social.facebook.api.Facebook;

import java.util.List;

public interface FBImporter {

    /**
     * Writes Data from the Facebook API
     * into a SharkKB.
     *
     * @throws FBImportException
     */
    void performImport() throws FBImportException;

    /**
     * Return an instance of the Facebook API
     * wrapper.In order to use the Facebook API easily.
     *
     * @return the instance of the Facebook API.
     */
     Facebook getFacebookAPI();

    /**
     * Return an instance of a SharkKB in which the
     * data from the facebook API should be imported.
     *
     * @return instance of a shark KB.
     */
    public SharkKB getSharkKb();


    /**
     * Retrieve a list of required permission which
     * are need in order do perform the import.
     *
     * @return the list of permissions.
     */
     List<String> getRequiredPermissions();


    /**
     * Retrieve a list of dependent importers which must
     * have already runned before this importers could be performed.
     *
     * @return the list of dependent importers.
     */
     List<String> getDependentImporters();

    /**
     * Return the unique name of this importers.
     *
     * @return the name of this importers.
     */
    default String getName() {
        return this.getClass().getName();
    }

}
