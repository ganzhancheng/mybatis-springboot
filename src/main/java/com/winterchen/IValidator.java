package com.winterchen;

import java.util.List;

public interface IValidator<T> {

    default void beforeValidate(){}

    default void afterValidate(List<T> success, List<T> error){}

}