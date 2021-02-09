package com.jorgelopezendrina.chatpartecliente.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UtilThread {
    private static final int NUMBER_OF_THREADS = Runtime.getRuntime().availableProcessors();
    public static final ExecutorService threadWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
}
