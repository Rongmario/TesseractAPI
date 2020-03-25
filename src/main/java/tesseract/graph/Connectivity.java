package tesseract.graph;

import tesseract.util.Dir;


/**
 * A class that acts as a controller for a connectivity of the connectables.
 */
public class Connectivity {

    /**
     * @param connectable The given instance.
     * @return Gets the connectivity for instance.
     */
    public static byte of(IConnectable connectable) {
        byte connectivity = 0;

        for (Dir direction : Dir.VALUES) {
            if (connectable.connects(direction)) {
                connectivity = Connectivity.with(connectivity, direction);
            }
        }

        return connectivity;
    }

    /**
     * @param connectivity The provided state.
     * @param side The direction index.
     * @return Connectivity state for a connection.
     */
    public static byte with(byte connectivity, Dir side) {
        return (byte) (connectivity | (1 << side.ordinal()));
    }

    /**
     * @param connectivity The provided state.
     * @param side The direction index.
     * @return True if a  connection is exist, false otherwise.
     */
    public static boolean has(byte connectivity, Dir side) {
        return (connectivity & (1 << side.ordinal())) > 0;
    }

    /**
     * Simple interface for a updates.
     */
    public interface IListener {

        /**
         * Update callback method.
         */
        void update();
    }

    /**
     * The Cache is a class that should work with connections.
     */
    public static class Cache<C> {

        private byte connectivity;
        private C value;
        private IListener listener;

        private Cache() { }

        public static <C extends IConnectable> Cache<C> of(C value, IListener listener) {
            Cache<C> cache = new Cache<>();
            cache.value = value;
            cache.connectivity = Connectivity.of(value);
            cache.listener = listener;
            return cache;
        }

        public static <C extends IConnectable> Cache<C> of(C value) {
            Cache<C> cache = new Cache<>();
            cache.value = value;
            cache.connectivity = Connectivity.of(value);
            cache.listener = () -> { };
            return cache;
        }

        /**
         * @param direction The direction index.
         * @return True when connect, false otherwise.
         */
        public boolean connects(Dir direction) {
            return Connectivity.has(connectivity, direction);
        }

        /**
         * @return Returns the connection state.
         */
        public byte connectivity() {
            return connectivity;
        }

        /**
         * @return Returns the cache object.
         */
        public C value() {
            return value;
        }

        /**
         * @return Returns the cache listener.
         */
        public IListener listener() {
            return listener;
        }
    }
}
