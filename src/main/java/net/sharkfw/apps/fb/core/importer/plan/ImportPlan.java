package net.sharkfw.apps.fb.core.importer.plan;

import net.sharkfw.apps.fb.core.importer.FBImportException;
import net.sharkfw.apps.fb.core.importer.FBImporter;
import net.sharkfw.knowledgeBase.SharkKBException;

/**
 * <p>A ImportPlan provides a collection
 * of importers which are executed by a ImportPlan
 * in a specific way.</p>
 *
 * <p>In simpler terms a Import Plan defines which importers should be used and in which order.</p>
 *
 */
public interface ImportPlan  {

    /**
     * <p>
     *     Executes all containing importers in it's specific order.
     * </p>
     * <p>
     *     A implementation should be aware the correct order of the importers.
     * </p>
     *
     * @throws FBImportException if an error occurs during the import process.
     */
    void execute() throws FBImportException, SharkKBException;

    /**
     * <p>
     *     Executes a specific importer and all importers which are
     *     required by this specific importer.
     * </p>
     *
     * @param importerName the name of the importer which should be executed.
     * @throws FBImportException if an error occurs during the import process.
     */
    void execute(String importerName) throws FBImportException;

    /**
     * Adds a importer to this import plan.
     * NOTE: If you add a importer of the same type twice
     * the implementation will replace the old one with the new one.
     *
     * @param importer the importer which should be added to this import plan.
     */
    void add(FBImporter importer);

    /**
     * Removes a importer from this import plan.
     *
     * @param importer the importer which should be removed from this import plan.
     */
    void remove(FBImporter importer);


    /**
     * Check if this import plan contains a specific importer.
     *
     * @param importer the specific importer
     * @return true if the importer is already in this import plan.
     */
    boolean hasImporter(FBImporter importer);

}