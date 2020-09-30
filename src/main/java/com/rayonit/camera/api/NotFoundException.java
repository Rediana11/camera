package com.rayonit.camera.api;

public class NotFoundException extends RuntimeException {

    public NotFoundException() {
    }

    public NotFoundException(String msg) {
        super(msg);
    }
}
