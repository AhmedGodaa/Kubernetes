package com.example.k8stest

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.mongodb.core.mapping.Document

@Document("test")
data class Test(
    @JsonProperty("id") val id: String? = null,
    @JsonProperty("test") val test: String? = null,
)
