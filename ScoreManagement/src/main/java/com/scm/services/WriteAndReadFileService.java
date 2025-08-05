package com.scm.services;

public interface WriteAndReadFileService {
    byte[] exportScoresToPDF(String classDetailId, String userId);
}
