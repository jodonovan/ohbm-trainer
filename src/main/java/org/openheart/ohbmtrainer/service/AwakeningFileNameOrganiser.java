package org.openheart.ohbmtrainer.service;

import java.util.*;

public class AwakeningFileNameOrganiser {
    Map<String, SortedSet<PathIndex>> map = new HashMap<>();

    public void add(String name, PathIndex pathIndex) {
        if (map.get(name) == null) {
            map.put(name, new TreeSet<>());
        }

        map.get(name).add(pathIndex);
    }

    public void crop() {
        Set<String> namesToRemove = new HashSet<>();

        for (String key : map.keySet()) {
            if (map.get(key).size() < 2) {
                namesToRemove.add(key);
            }
        }

        namesToRemove.stream().forEach(name -> map.remove(name));
    }

    public Map<String, SortedSet<PathIndex>> getMap() {
        return map;
    }

    @Override
    public String toString() {
        return "AwakeningFileNameOrganiser{" +
                "map=" + map +
                '}';
    }


}
