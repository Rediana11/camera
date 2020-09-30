package com.rayonit.camera.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CameraRepositoryCustomImpl implements CameraRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<CameraEntity> findByName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        return mongoTemplate.find(query, CameraEntity.class);
    }

    @Override
    public List<CameraCount> groupByResolution() {
        GroupOperation groupOperation = Aggregation.group("resolution").count().as("count");
        ProjectionOperation projectionOperation = Aggregation.project("count").and("resolution").previousOperation();
        // filtering same age count > 1
        MatchOperation matchOperation = Aggregation.match(new Criteria("count").gt(0));
        Aggregation aggregation = Aggregation.newAggregation(groupOperation,projectionOperation, matchOperation);
        AggregationResults<CameraCount> result = mongoTemplate.aggregate(aggregation, "camera", CameraCount.class);
        return result.getMappedResults();
    }

    @Override
    public PageData findAll(String search, Pageable pageable) {
        Query query = new Query();
        query.with(pageable);
        if(search!=null) {
            Criteria orCriteria = new Criteria();
            List<Criteria> orExpression =  new ArrayList<>();
            orExpression.add(new Criteria().and("name").regex(search));
            orExpression.add(new Criteria().and("model").regex(search));
            orExpression.add(new Criteria().and("resolution").regex(search));
            orExpression.add(new Criteria().and("ip").regex(search));
            query.addCriteria(orCriteria.orOperator(orExpression.toArray(new Criteria[0])));
        }
        return new PageData(mongoTemplate.find(query, CameraEntity.class),countAll(search));
    }

    public long countAll(String search) {
        Query query = new Query();
        if(search!=null) {
            Criteria orCriteria = new Criteria();
            List<Criteria> orExpression =  new ArrayList<>();
            orExpression.add(new Criteria().and("name").regex(search));
            orExpression.add(new Criteria().and("model").regex(search));
            orExpression.add(new Criteria().and("resolution").regex(search));
            orExpression.add(new Criteria().and("ip").regex(search));
            query.addCriteria(orCriteria.orOperator(orExpression.toArray(new Criteria[0])));
        }
        return  mongoTemplate.count(query, CameraEntity.class);
    }
}
