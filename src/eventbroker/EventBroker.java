package eventbroker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

final public class EventBroker {

    protected Set<EventListener> listeners = new HashSet<EventListener>();
    protected final static EventBroker broker = new EventBroker();
    protected Map<String, HashSet<EventListener>> filteredListeners = new HashMap<String,HashSet<EventListener>>();

    private EventBroker() {
    }

    public static EventBroker getEventBroker() {
        return broker;
    }

    public void addEventListener(EventListener s) {
        listeners.add(s);
    }
    
    public void addEventListener(String type, EventListener s) {
        if (filteredListeners.get(type) == null) {
            filteredListeners.put(type, new HashSet<EventListener>());
        } 
        filteredListeners.get(type).add(s);
    }

    public void removeEventListener(EventListener s) {
        listeners.remove(s);
    }

    void addEvent(EventPublisher source, Event e) {
        process(source, e);
    }

    private void process(EventPublisher source, Event e) {
        for (EventListener l : listeners) {
            if (l != source) {
                l.handleEvent(e); // prevent loops !
            }
        }
        if (filteredListeners.get(e.getType()) != null) {
            for (EventListener el: filteredListeners.get(e.getType())) {
                el.handleEvent(e);
            }
        }
    }
}
