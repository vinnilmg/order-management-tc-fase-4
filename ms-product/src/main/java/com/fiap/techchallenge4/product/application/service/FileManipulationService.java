package com.fiap.techchallenge4.product.application.service;

import java.io.IOException;

public interface FileManipulationService {

    void moveFile(String directoryResource, String directoryDestination, String fileName) throws IOException;

}
