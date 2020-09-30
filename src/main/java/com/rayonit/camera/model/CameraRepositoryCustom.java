package com.rayonit.camera.model;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CameraRepositoryCustom {

    List<CameraEntity> findByName(String name);

    List<CameraCount> groupByResolution();

    PageData findAll(String search, Pageable pageable);
}
