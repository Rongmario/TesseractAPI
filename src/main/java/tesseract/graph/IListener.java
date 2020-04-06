package tesseract.graph;

/**
 * Simple interface for a updates.
 */
public interface IListener {

    /**
     * Executes when the grid is having any updates of any nodes/connectors inside.
     * @param container The grid to use for cache operations.
     */
    void change(INode container);
}
