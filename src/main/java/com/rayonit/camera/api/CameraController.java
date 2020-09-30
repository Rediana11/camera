package com.rayonit.camera.api;

import com.rayonit.camera.model.CameraCount;
import com.rayonit.camera.model.CameraEntity;
import com.rayonit.camera.model.CameraRepository;
import com.rayonit.camera.model.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/cameras")
public class CameraController {

    private final CameraRepository cameraRepository;

    @Autowired
    public CameraController(CameraRepository cameraRepository) {
        this.cameraRepository = cameraRepository;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CameraEntity>> getCameras(){
        return ResponseEntity.ok(cameraRepository.findAll());
    }

    @GetMapping(value = "/search",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PageData> getAllCamera(@RequestParam(required = false,value = "search") String search, @PageableDefault Pageable pageable){
        return ResponseEntity.ok(cameraRepository.findAll(search, pageable));
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CameraEntity> getCamera(@PathVariable("id") String id){
        return ResponseEntity.ok(cameraRepository.findById(id).orElse(null));
    }

    @GetMapping(value = "/name",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CameraEntity>> getCameraByName(@RequestParam("name") String name){
        return ResponseEntity.ok(cameraRepository.findByName(name));
    }

    @GetMapping(value = "/resolutionStatistic",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CameraCount>> getCameraGroupedByResolution(){
        return ResponseEntity.ok(cameraRepository.groupByResolution());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CameraEntity> add(@RequestBody CameraEntity cameraEntity){
        CameraEntity addedCamera = cameraRepository.save(cameraEntity);
       return ResponseEntity.status(HttpStatus.CREATED).body(addedCamera);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CameraEntity> update(@RequestBody CameraEntity details)  {
        CameraEntity camera = cameraRepository.findById(details.getId()).orElseThrow(NotFoundException::new);
        camera.setName(details.getName());
        camera.setModel(details.getModel());
        camera.setResolution(details.getResolution());
        camera.setIp(details.getIp());

        final CameraEntity updatedEmployee = cameraRepository.save(camera);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam String id){
         cameraRepository.deleteById(id);
         return ResponseEntity.ok().build();
    }


}
