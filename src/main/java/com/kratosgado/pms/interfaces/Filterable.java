package com.kratosgado.pms.interfaces;

import java.util.function.Predicate;

public interface Filterable<T> {

  T[] filter(Predicate<T> predicate);
}
