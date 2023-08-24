package com.example.k8stest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class K8sTestApplication

fun main(args: Array<String>) {
    runApplication<K8sTestApplication>(*args)
}
