package com.jobsscan.service;

import com.jobsscan.domain.LabelEntity;

public interface GeneralService<T> {

     T saveIfNotExist(T obj);
}
