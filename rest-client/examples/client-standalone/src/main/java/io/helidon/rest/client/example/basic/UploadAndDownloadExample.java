/*
 * Copyright (c) 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package io.helidon.rest.client.example.basic;

import java.nio.file.Path;

import io.helidon.rest.client.ClientResponse;
import io.helidon.rest.client.FilePublisher;
import io.helidon.rest.client.FileSubscriber;
import io.helidon.rest.client.RestClient;

/**
 * TODO javadoc.
 */
public class UploadAndDownloadExample {
    public static void main(String[] args) {

    }

    void upload(Path filePath, String uri) {
        RestClient client = RestClient.create();
        client.put(uri)
                .send(FilePublisher.create(filePath))
                .thenApply(ClientResponse::status)
                .thenAccept(System.out::println)
                .toCompletableFuture()
                .join();
    }

    void download(String uri, Path filePath) {
        RestClient client = RestClient.create();
        client.get(uri)
                .send()
                .thenApply(ClientResponse::content)
                .thenAccept(content -> content.subscribe(FileSubscriber.create(filePath)))
                .toCompletableFuture()
                .thenAccept(o -> System.out.println("Download completed"))
                .join();
    }
}
