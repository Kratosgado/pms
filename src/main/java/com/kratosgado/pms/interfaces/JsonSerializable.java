
package com.kratosgado.pms.interfaces;

public interface JsonSerializable<T> {

  String toJson();

  T fromJson(String json);
}
