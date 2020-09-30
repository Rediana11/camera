package com.rayonit.camera.model;

import lombok.Data;

import java.util.List;

@Data
public class PageData {
    private List<CameraEntity> cameras;
    private long totalElements;

    public PageData(List<CameraEntity> cameras, long totalElements) {
        this.cameras = cameras;
        this.totalElements = totalElements;
    }
}
