package com.rayonit.camera.model;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CameraRepository extends MongoRepository<CameraEntity, String>,CameraRepositoryCustom {

}
