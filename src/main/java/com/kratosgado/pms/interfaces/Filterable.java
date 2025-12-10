package com.kratosgado.pms.interfaces;

import java.util.List;
import java.util.function.Predicate;

public interface Filterable<T> {

  List<T> filter(Predicate<T> predicate);
}
