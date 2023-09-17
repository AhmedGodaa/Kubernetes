package com.example.k8stest

import io.micrometer.observation.annotation.Observed
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
@Observed
interface TestRepository : MongoRepository<Test, String>