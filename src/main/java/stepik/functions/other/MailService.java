package stepik.functions.other;

import java.util.*;
import java.util.function.Consumer;

public class MailService<T> implements Consumer<Sendable<T>> {
    //        private Map<String, List<T>> map = new HashMap<String, Set<T>>();
    private Map<String, List<T>> map = new HashMap<String, List<T>>() {
        @Override
        public List<T> get(Object key) {
            return super.getOrDefault(key, new LinkedList<T>());
        }
    };

    public Map<String, List<T>> getMailBox() {
        return this.map;
    }

    @Override
    public void accept(Sendable<T> s) throws NullPointerException {
        String key = s.getTo();
        if (!map.containsKey(key)) map.put(key, new LinkedList<T>());
        map.get(key).add(s.getContent());


}

}