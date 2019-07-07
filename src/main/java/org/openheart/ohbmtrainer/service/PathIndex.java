package org.openheart.ohbmtrainer.service;

import java.util.Objects;

public class PathIndex implements Comparable {
    private Integer index;
    private String path;

    public PathIndex(Integer index, String path) {
        this.index = index;
        this.path = path;
    }

    public Integer getIndex() {
        return index;
    }

    public String getPath() {
        return path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PathIndex that = (PathIndex) o;
        return index.equals(that.index);
    }

    @Override
    public String toString() {
        return "PathIndex{" +
                "index=" + index +
                ", path='" + path + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(index);
    }

    @Override
    public int compareTo(Object o) {
        return this.index.compareTo(((PathIndex) o).getIndex());
    }
}
